package org.material.managementservice.service.info.impl.supplier.controlprop;

import org.material.managementfacade.model.propertymodel.finance.FinanceList;
import org.material.managementfacade.model.propertymodel.plan.PlanList;
import org.material.managementfacade.model.propertymodel.purchaseandstore.PurchaseAndStoreList;
import org.material.managementfacade.model.propertymodel.quality.QualityList;
import org.material.managementfacade.model.propertymodel.sales.SalesList;
import org.material.managementfacade.model.requestmodel.InfoModifyReq;
import org.material.managementfacade.model.requestmodel.infomodify.InfoModifyByCatCodeAndNameCtrPropReq;
import org.material.managementfacade.model.requestmodel.infomodify.MatCtrPropModifyReqEle;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValVerModel;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    @Autowired
    private PurchaseAndStoreList purchaseAndStoreList;
    @Autowired
    private PlanList planList;
    @Autowired
    private SalesList salesList;
    @Autowired
    private QualityList qualityList;
    @Autowired
    private FinanceList financeList;

    /**
     * 根据物料分类id和spu编码刷新物料控制属性或者物料控制属性值的函数
     *
     * @author cplayer
     * @date 2019-04-03 22:42
     * @param materialCatId 物料分类id
     * @param spuCode spu编码
     *
     * @return update所影响的行数
     *
     */
    public int refreshCtrlPropAndValBySpuCodeAndMaterialCatId (int materialCatId, String spuCode) {
        MaterialCtrlPropValVerModel searchParam = new MaterialCtrlPropValVerModel();
        searchParam.setOrganizationCode(MaterialGeneral.generalOrganizationCode);
        searchParam.setSpuCode(spuCode);
        List<MaterialCtrlPropValVerModel> searchRes = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(searchParam);
        MaterialCtrlPropValVerModel res = MaterialGeneral.getInitElementOrFirstElement(searchRes,
                MaterialCtrlPropValVerModel.class);
        if (res.getId() != -1) {
            if (materialCatId != res.getMaterialCatId()) {
                // 需要更新
               int versionId = res.getId();
               int refreshVerRes = infoModifyMapper.updateCtrlPropValVerCatIdById(versionId, materialCatId);
               logger.info(String.format("刷新物料控制属性值版本返回值为%d。", refreshVerRes));
               return refreshVerRes;
            } else {
                // 无需更新
                logger.debug(String.format("刷新物料控制属性值版本时，物料分类id = %d，spu编码 = %s的情况时出现了无需更新情况。",
                        materialCatId,
                        spuCode));
                return 0;
            }
        } else {
            // 无需更新
            logger.debug(String.format("刷新物料控制属性值版本时，物料分类id = %d，spu编码 = %s的情况时出现了未找到情况。",
                    materialCatId,
                    spuCode));
            return 0;
        }
    }

    /**
     * 根据待添加的物料控制属性值列表、版本ID以及参数添加对应的控制属性值
     *
     * @author cplayer
     * @date 2019-04-03 22:08
     * @param propList 控制属性值列表
     * @param versionId 版本id
     *
     * @return 成功添加的记录个数
     *
     */
    private int insertCtrlPropValByCtrPropList (List<MatCtrPropModifyReqEle> propList, int versionId) {
        int insertRes = 0;
        for (MatCtrPropModifyReqEle element : propList) {
            String name = element.getName();
            String value = element.getValue();
            // 先查找对应的物料控制属性
            MaterialCtrlPropModel searchParam = new MaterialCtrlPropModel();
            searchParam.setName(name);
            List<MaterialCtrlPropModel> searchRes = generalMapper.getCtrlPropWithCtrlPropParams(searchParam);
            MaterialCtrlPropModel res = MaterialGeneral.getInitElementOrFirstElement(searchRes, MaterialCtrlPropModel.class);
            if (res.getId() != -1) {
                MaterialCtrlPropValModel insertParam = new MaterialCtrlPropValModel();
                insertParam.setMaterialCtrlPropId(res.getId());
                insertParam.setVersionId(versionId);
                insertParam.setValue(value);
                infoModifyMapper.insertCtrlPropValByParams(insertParam);
                if (insertParam.getId() > 0) insertRes++;
            } else {
                logger.error(String.format("获取物料控制属性为%s的属性时未找到！", name));
            }
        }
        return insertRes;
    }

    /**
     * 根据给定的控制属性值列表以及版本id，更新对应的控制属性值
     *
     * @param params
     *         更新物料信息请求的参数
     * @param versionId
     *         版本id
     *
     * @return MaterialInfoErrCode.failedUpdateAllControlPropInMaterial 更新物料控制属性全部失败
     * MaterialInfoErrCode.failedUpdateSomeControlPropInMaterial 更新物料控制属性部分失败
     * MaterialInfoErrCode.successUpdateAllControlPropInMaterial 更新物料控制属性成功
     *
     * @author cplayer
     * @date 2019-03-01 05:34
     */
    private int updateCtrPropsByCtrPropList (InfoModifyReq params, int versionId) {
        List<MatCtrPropModifyReqEle> ctrPropList = params.getCtrPropDatas().getCtrPropList();
        int updateSingleResult = 0;
        // 获取所有可能的物料控制属性值表记录
        List<MaterialCtrlPropValModel> ctrPropValList = infoModifyMapper.getCtrlPropValWithVersionId(versionId);
        // 然后逐个查找对应的值，并存入HashMap
        Map<String, Integer> nameToPropVal = new HashMap<>(16);
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
                logger.error(String.format("查询物料控制属性值过程中，不存在id = %d的记录。", ctrPropValEle.getMaterialCtrlPropId()));
            }
        }
        // 查找哪些是存在的，哪些是不存在的
        List<MatCtrPropModifyReqEle> waitForAddCtrPropList = new ArrayList<>();
        for (MatCtrPropModifyReqEle element : ctrPropList) {
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
                // 不存在对应的物料控制属性，待插入。
                logger.error(String.format("不存在提交上来的物料控制属性名，属性名 = %s，故待添加！", name));
                waitForAddCtrPropList.add(element);
            }
        }
        int insertSingleResult = insertCtrlPropValByCtrPropList(waitForAddCtrPropList, versionId);
        if (updateSingleResult + insertSingleResult == 0) {
            return MaterialInfoErrCode.failedUpdateAllControlProp;
        } else if (updateSingleResult + insertSingleResult < ctrPropList.size()) {
            return MaterialInfoErrCode.failedUpdateSomeControlProp;
        } else {
            return MaterialInfoErrCode.successUpdateAllControlProp;
        }
    }

    /**
     * 更新物料控制属性对应的功能函数
     *
     * @param params
     *         更新物料信息请求的参数
     *
     * @return MaterialInfoErrCode.successUpdateControlPropInMaterial 更新成功
     * MaterialInfoErrCode.failedUpdateControlPropInMaterial 更新失败
     *
     * @author cplayer
     * @date 2019-02-28 17:30
     */
    public int updateMaterialInfoForCtrData (InfoModifyReq params) {
        // 在后续设计出来之前，组织编码统一设置成generalOrganizationCode
        String organizationCode = MaterialGeneral.generalOrganizationCode;
        String spuCode = params.getSpuCode();
        // 故根据版本号、spu编码、物料分类id以及组织编码可以唯一确定一条控制属性版本
        // 获取物料基本信息记录，以获取物料分类id
        MaterialBaseModel baseInfo = MaterialGeneral.getInitElementOrFirstElement(
                infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode()),
                MaterialBaseModel.class);
        if (baseInfo.getMaterialCatId() == -1) {
            // 说明不存在对应的记录
            return MaterialInfoErrCode.failedUpdateControlProp;
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
            String version = "ver-" + "0100-T-" + Integer.valueOf(baseInfo.getMaterialCatId()).toString();
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
        if (updateResult == MaterialInfoErrCode.failedUpdateSomeControlProp ||
                updateResult == MaterialInfoErrCode.failedUpdateAllControlProp) {
            updateResult = MaterialInfoErrCode.failedUpdateControlProp;
        } else {
            updateResult = MaterialInfoErrCode.successUpdateControlProp;
        }
        return updateResult;
    }

    /**
     * 根据物料控制属性分类获取对应的所有的物料属性名
     *
     * @param type
     *         物料属性分类
     *
     * @return 物料属性名列表
     *
     * @author cplayer
     * @date 2019-03-14 22:09
     */
    public String[] getNameListForCtrProp (int type) {
        String[] nameList;
        switch (type) {
            case 5:
                // 采购和库存属性：5
                nameList = purchaseAndStoreList.getPurchaseAndStoreList();
                break;
            case 6:
                // 计划类属性：6
                nameList = planList.getPlanList();
                break;
            case 7:
                // 销售类属性：7
                nameList = salesList.getSalesList();
                break;
            case 8:
                // 质量类属性：8
                nameList = qualityList.getQualityList();
                break;
            case 9:
                // 财务类属性：9
                nameList = financeList.getFinanceList();
                break;
            default:
                nameList = new String[0];
                break;
        }
        return nameList;
    }

    /**
     * 根据控制属性类别、组织编码，物料分类id，属性名和属性值更新对应控制属性的函数
     *
     * @param type
     *         控制属性类别
     * @param organizationCode
     *         组织编码
     * @param catId
     *         物料分类id
     * @param name
     *         属性名
     * @param value
     *         属性值
     * @param flag
     *         是否需要添加新的版本号
     *
     * @return MaterialInfoErrCode.notFoundControlPropertyType 提交上来的物料控制属性分类未找到
     * MaterialInfoErrCode.notFoundControlPropertyName 提交上来的物料控制属性名称未找到
     * 任意正值 更新成功
     *
     * @author cplayer
     * @date 2019-03-02 20:57
     */
    public int updateControlPropertyByCatIdAndTypeAndDatas (int type, String organizationCode, int catId, String name, String value, boolean flag) {
        int versionId;
        if (flag) {
            // 默认版本号
            String version = "ver-" + "0100-T-" + Integer.valueOf(catId).toString();
            // 填写参数
            MaterialCtrlPropValVerModel param = new MaterialCtrlPropValVerModel();
            param.setOrganizationCode(organizationCode);
            param.setMaterialCatId(catId);
            param.setSpuCode(MaterialGeneral.generalSpuCode);
            param.setVersion(version);
            param.setStartDate(new Timestamp(System.currentTimeMillis()));
            param.setEndDate(new Timestamp(System.currentTimeMillis() + 315360000000L));
            int insertResult = infoModifyMapper.insertCtrlPropValVerByParams(param);
            versionId = param.getId();
            if (insertResult <= 0) {
                logger.error(String.format("插入版本号 = %s，物料分类id = %d，组织编码为 = %d的控制信息时出现插入错误，返回值为%d。", version, catId, organizationCode, insertResult));
                return insertResult;
            }
        } else {
            MaterialCtrlPropValVerModel varParam = new MaterialCtrlPropValVerModel();
            varParam.setMaterialCatId(catId);
            varParam.setSpuCode(MaterialGeneral.generalSpuCode);
            varParam.setOrganizationCode(organizationCode);
            List<MaterialCtrlPropValVerModel> ctrlVerResult = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(varParam);
            // 需要确保结果只有一个版本id，若有多个，取第一个版本id
            versionId = MaterialGeneral.getInitElementOrFirstElement(ctrlVerResult, MaterialCtrlPropValVerModel.class).getId();
        }
        // 查找控制属性名对应的id
        MaterialCtrlPropModel propParam = new MaterialCtrlPropModel();
        propParam.setName(name);
        List<MaterialCtrlPropModel> ctrlPropResult = generalMapper.getCtrlPropWithCtrlPropParams(propParam);
        // 同样取第一个控制属性id
        int ctrlPropId = MaterialGeneral.getInitElementOrFirstElement(ctrlPropResult, MaterialCtrlPropModel.class).getId();
        String[] nameList = getNameListForCtrProp(type);
        if (nameList.length == 0) {
            return MaterialInfoErrCode.notFoundControlPropertyType;
        }
        // 若含有对应的属性名
        if (MaterialGeneral.checkList(nameList, name)) {
            int countRes = infoModifyMapper.countCtrlPropWithCtrlPropParams(ctrlPropId, versionId);
            if (countRes > 0) {
                return infoModifyMapper.updateCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
            } else {
                return infoModifyMapper.insertCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
            }

        } else {
            return MaterialInfoErrCode.notFoundControlPropertyName;
        }
    }

    /**
     * 根据物料分类id待更新属性值更新物料控制属性的功能函数
     *
     * @param updateDatas
     *         待更新的属性值
     * @param catId
     *         物料分类id
     *
     * @return MaterialInfoErrCode.failedUpdateControlPropertyByCatIdAndTypeAndValue 更新失败
     * MaterialInfoErrCode.successUpdateControlPropertyByCatIdAndTypeAndValue 更新成功
     *
     * @author cplayer
     * @date 2019-03-02 20:34
     */
    public int updateControlPropertyByCatIdAndTypeAndValue (
            List<InfoModifyByCatCodeAndNameCtrPropReq> updateDatas,
            int catId) {
        boolean hasFailed = false;
        for (InfoModifyByCatCodeAndNameCtrPropReq element : updateDatas) {
            int propertyType = element.getPropertyType();
            String organizationCode = element.getOrganizationCode();
            // 若没有提供组织编码，则将组织编码设为通用的组织编码
            if (organizationCode == null) {
                organizationCode = MaterialGeneral.generalOrganizationCode;
            }
            int countRes = infoModifyMapper.countControlPropertyByCatIdAndTypeAndDatas(catId, MaterialGeneral.generalSpuCode, organizationCode);
            logger.info(String.format("统计类别编码为%d，spu编码为%s，组织编码为%s的记录结果为：%d。", catId, MaterialGeneral.generalSpuCode, organizationCode, countRes));
            for (MatCtrPropModifyReqEle dataEle : element.getCtrPropList()) {
                String name = dataEle.getName();
                String value = dataEle.getValue();
                // 更新
                int updateEleRes = updateControlPropertyByCatIdAndTypeAndDatas(propertyType, organizationCode, catId, name, value, countRes <= 0);
                countRes++;
                if (updateEleRes <= 0) {
                    logger.info(String.format("更新类别为%d的控制属性为%s，值为%s时出错，返回值为%d。", propertyType, name, value, updateEleRes));
                    hasFailed = true;
                }
            }
        }
        if (hasFailed) {
            return MaterialInfoErrCode.failedUpdateControlPropertyByCatIdAndTypeAndValue;
        } else {
            return MaterialInfoErrCode.successUpdateControlPropertyByCatIdAndTypeAndValue;
        }
    }

    /**
     * 根据物料分类id删除所有物料控制属性的值
     *
     * @param catId
     *         物料分类id
     *
     * @return MaterialInfoErrCode.successDeleteAllCtrPropsByCatId 删除成功
     * MaterialInfoErrCode.failedDeleteAllCtrPropsByCatId 删除失败
     *
     * @author cplayer
     * @date 2019-03-09 05:02
     */
    public int deleteAllControlPropertyByCatId (int catId) {
        // 先获取版本号，然后再根据版本号逐个删除
        List<MaterialCtrlPropValVerModel> valVerList = infoObtainMapper.getMaterialCtrlPropValVerByCatId(catId);
        for (MaterialCtrlPropValVerModel element : valVerList) {
            int versionId = element.getId();
            int deleteResult = infoModifyMapper.deleteAllMaterialCtrlPropValByVersionId(versionId);
            logger.info(String.format("删除版本id = %d的记录的返回结果为：%d。", versionId, deleteResult));
        }
        return MaterialInfoErrCode.successDeleteAllBasePropByCatId;
    }
}
