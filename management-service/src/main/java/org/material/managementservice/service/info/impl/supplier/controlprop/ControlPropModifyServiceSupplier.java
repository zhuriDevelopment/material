package org.material.managementservice.service.info.impl.supplier.controlprop;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialControlPropModifyRequestElement;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValVerModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cplayer on 2019-02-28 17:27
 * @version 1.0
 * 物料控制属性更新的工具类，存放具体的实现
 */
@Component
public class ControlPropModifyServiceSupplier {
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 根据给定的控制属性值列表以及版本id，更新对应的控制属性值
     *
     * @author cplayer
     * @date 2019-03-01 05:34     
     * @param params 更新物料信息请求的参数
     *
     * @param versionId 版本id
     *
     * @return MaterialErrCode.failedUpdateAllControlPropInMaterial 更新物料控制属性全部失败
     *         MaterialErrCode.failedUpdateSomeControlPropInMaterial 更新物料控制属性部分失败
     *         MaterialErrCode.successUpdateAllControlPropInMaterial 更新物料控制属性成功
     *
     */
    private int updateCtrPropsByCtrPropList (MaterialInfoModifyRequest params, int versionId) {
        List<MaterialControlPropModifyRequestElement> ctrPropList = params.getCtrPropDatas().getCtrPropList();
        int updateSingleResult = 0;
        // 获取所有可能的物料控制属性值表记录
        List<MaterialCtrlPropValModel> ctrPropValList = infoModifyMapper.getCtrlPropValWithVersionId(versionId);
        // 然后逐个查找对应的值，并存入HashMap
        Map<String, Integer> nameToPropVal = new HashMap<String, Integer>(16);
        for (MaterialCtrlPropValModel ctrPropValEle : ctrPropValList) {
            MaterialCtrlPropModel param = new MaterialCtrlPropModel();
            param.setId(ctrPropValEle.getMaterialCtrlPropId());
            List<MaterialCtrlPropModel> ctrPropFind = generalMapper.getCtrlPropWithCtrlPropParams(param);
            if (ctrPropFind.size() > 1) {
                logger.error(String.format("查询物料控制属性值过程中，有id = %d的复数条记录。", param.getId()));
            }
            MaterialCtrlPropModel targetProp = MaterialGeneral.getInitElementOrFirstElement(ctrPropFind, MaterialCtrlPropModel.class);
            if (targetProp.getId() != -1) {
                // 找到的对应的记录才缓存
                nameToPropVal.put(targetProp.getName(), targetProp.getId());
            } else {
                logger.error(String.format("查询物料控制属性值过程中，不存在id = %d的记录。"));
            }
        }
        for (MaterialControlPropModifyRequestElement element : ctrPropList) {
            String name = element.getName();
            String value = element.getValue();
            MaterialCtrlPropValModel param = new MaterialCtrlPropValModel();
            if (nameToPropVal.containsKey(name)) {
                // 存在对应的物料控制属性，修改
                param.setMaterialCtrlPropId(nameToPropVal.get(name));
                param.setValue(value);
                param.setVersionId(versionId);
                if (infoModifyMapper.updateCtrlPropValByParams(param) > 0) {
                    updateSingleResult++;
                }
            } else {
                // 不存在对应的物料控制属性，数据出错了！
                logger.error(String.format("不存在提交上来的物料控制属性名，属性名 = %s！"), name);
            }
        }
        if (updateSingleResult == 0) {
            return MaterialErrCode.failedUpdateAllControlProp;
        } else if (updateSingleResult < ctrPropList.size()) {
            return MaterialErrCode.failedUpdateSomeControlProp;
        } else {
            return MaterialErrCode.successUpdateAllControlProp;
        }
    }

    /**
     * 更新物料控制属性对应的功能函数
     *
     * @author cplayer
     * @date 2019-02-28 17:30
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateControlPropInMaterial 更新成功
     *         MaterialErrCode.failedUpdateControlPropInMaterial 更新失败
     *
     */
    public int updateMaterialInfoForCtrData (MaterialInfoModifyRequest params) {
        // 在后续设计出来之前，组织编码统一设置成-1
        String organizationCode = "-1";
        String spuCode = params.getSpuCode();
        // 故根据版本号、spu编码、物料分类id以及组织编码可以唯一确定一条控制属性版本
        // 获取物料基本信息记录，以获取物料分类id
        MaterialBaseModel baseInfo = MaterialGeneral.getInitElementOrFirstElement(
                        infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode()),
                        MaterialBaseModel.class);
        if (baseInfo.getMaterialCatId() == -1) {
            // 说明不存在对应的记录
            return MaterialErrCode.failedUpdateControlProp;
        }
        // 查询版本号
        // 设置查询参数
        MaterialCtrlPropValVerModel paramPropValVer = new MaterialCtrlPropValVerModel();
        paramPropValVer.setSpuCode(spuCode);
        paramPropValVer.setMaterialCatId(baseInfo.getMaterialCatId());
        paramPropValVer.setOrganizationCode(organizationCode);
        List<MaterialCtrlPropValVerModel> propValVerList = infoModifyMapper
                .getCtrlPropValVerWithCtrlPropValVerParams(paramPropValVer, new Timestamp(System.currentTimeMillis()));
        if (propValVerList.size() > 1) {
            // 日志中报错，但是不停下操作
            logger.error(String.format("在查询MaterialCtrlPropValVer表的过程中，对于spuCode = %s，组织编码 = %s，" +
                    "物料分类id = %d的情形存在复数条记录。",
                    spuCode,
                    organizationCode,
                    baseInfo.getMaterialCatId()));
        }
        MaterialCtrlPropValVerModel propValVerData = MaterialGeneral.getInitElementOrFirstElement(propValVerList, MaterialCtrlPropValVerModel.class);
        int versionId;
        if (propValVerData.getId() == -1) {
            // 说明不存在对应记录，进行插入操作
            // 默认版本号统一为"ver-001"，后续可修改
            String version = "ver-001";
            paramPropValVer.setVersion(version);
            // 起始时间为当前，终止时间默认为10年后
            paramPropValVer.setStartDate(new Timestamp(System.currentTimeMillis()));
            paramPropValVer.setEndDate(new Timestamp(System.currentTimeMillis() + 315360000000L));
            // 插入对应的版本号
            infoModifyMapper.insertCtrlPropValVerByParams(paramPropValVer);
            logger.info(String.format("插入新物料控制属性值版本的id = %d。", paramPropValVer.getId()));
            // 获取id
            versionId = paramPropValVer.getId();
        } else {
            // 存在对应记录，获取已经存在的版本号对应的id
            versionId = propValVerData.getId();
        }
        int updateResult = updateCtrPropsByCtrPropList(params, versionId);
        if (updateResult == MaterialErrCode.failedUpdateSomeControlProp ||
            updateResult == MaterialErrCode.failedUpdateAllControlProp) {
            updateResult = MaterialErrCode.failedUpdateControlProp;
        } else {
            updateResult = MaterialErrCode.successUpdateControlProp;
        }
        return updateResult;
    }
}
