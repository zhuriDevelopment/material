package org.material.managementservice.service.info.impl.supplier;

import com.sun.xml.internal.ws.api.model.MEP;
import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialBaseModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequestForFormatProp;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequestForMaterial;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoBasePropResponse;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cplayer on 2019-02-27 03:52.
 * @version 1.0
 * 物料信息更新的工具类，存放更新的通用工具函数
 */

@Component
public class InfoModifyServiceSupplier {
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainMapper infoObtainMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 更新物料基本信息的函数
     *
     * 若成功更新返回MaterialErrCode.successUpdateMaterialBase
     * 不成功返回MaterialErrCode.failedUpdateMaterialBase
     *
     * @author cplayer
     * @date 2019-02-27 04:35
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateMaterialBase 代表成功
     *         MaterialErrCode.failedUpdateMaterialBase 代表失败
     *
     */
    public int updateMaterialInfoForBaseData (MaterialInfoModifyRequest params) {
        MaterialBaseModifyRequest updateBaseDatas = params.getBaseDatas();
        // 先检查spuCode是否为空
        if (updateBaseDatas.getSpuCode() == null) {
            logger.error("更新物料基本信息过程中，spuCode为空！禁止的操作！");
            return MaterialErrCode.failedUpdateMaterialBase;
        }
        // 再检查是否只有一条spuCode记录
        int recordNum = infoModifyMapper.countBaseBySpuCode(updateBaseDatas.getSpuCode());
        if (recordNum > 1) {
            logger.error("更新物料基本信息过程中，spuCode为指定代码的记录有多条！不正确的库内数据！");
            return MaterialErrCode.failedUpdateMaterialBase;
        }
        // 转换成MaterialBaseModel
        MaterialBaseModel param = new MaterialBaseModel();
        param.setSpuCode(updateBaseDatas.getSpuCode());
        param.setSpuName(updateBaseDatas.getSpuName());
        param.setType(updateBaseDatas.getType());
        param.setMaterialCatId(updateBaseDatas.getMaterialCatId());
        param.setSource(updateBaseDatas.getSource());
        param.setUsage(updateBaseDatas.getUsage());
        param.setDesignCode(updateBaseDatas.getDesignCode());
        param.setDesignVersion(updateBaseDatas.getDesignVersion());
        param.setMnemonic(updateBaseDatas.getMnemonic());
        param.setNote(updateBaseDatas.getNote());
        if (recordNum == 0) {
            // 若没有spuCode记录，则新增
            // 此时设置创建时间为当前服务器时间
            param.setCreateDate(new Date());
            generalMapper.insertMaterialBaseWithMaterialBaseParams(param);
            // 此时返回的id应该为新增记录的id
            int id = param.getId();
            logger.info("新增物料基本信息成功，新增记录id = " + id);
            return MaterialErrCode.successUpdateMaterialBase;
        } else {
            // 反之则更新
            int affectedRows = generalMapper.updateMaterialBaseWithMaterialBaseParams(param);
            // 检查受影响记录条数
            if (affectedRows <= 0) {
                return MaterialErrCode.failedUpdateMaterialBase;
            } else {
                return MaterialErrCode.successUpdateMaterialBase;
            }
        }
    }

    /**
     * 更新物料定义页面中物料定义部分的函数
     *
     * @author cplayer
     * @date 2019-02-27 21:08
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateMaterialInMaterial 代表成功
     *         MaterialErrCode.failedUpdateMaterialInMaterial 代表失败
     *
     */
    public int updateMaterialInfoForMaterialDataByMaterial (MaterialInfoModifyRequest params) {
        // 先根据spuCode获得物料基本信息ID
        List<MaterialBaseModel> baseList = infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode());
        // 进而取得对应的物料基本信息对象，也就获得了id
        MaterialBaseModel baseInfo = MaterialGeneral.getInitElementOrFirstElement(baseList, MaterialBaseModel.class);
        if (baseInfo.getId() == -1) {
            // 若没有对应物料基本信息记录，则插入失败
            return MaterialErrCode.failedUpdateFormatInMaterial;
        }
        // 然后根据spuCode删除所有Material表记录
        int result = infoModifyMapper.deleteAllMaterialBySpuCode(params.getSpuCode());
        logger.info("删除了" + result + "条material表记录。");
        // 再完全重新插入
        result = 0;
        for (MaterialModifyRequestForMaterial element : params.getMaterialDatas().getMaterialList()) {
            MaterialModel param = new MaterialModel();
            param.setMaterialCode(element.getMaterialCode());
            param.setMaterialName(element.getMaterialName());
            param.setOldMaterialCode(element.getOldMaterialCode());
            param.setBarCode(element.getBarCode());
            param.setSpuCode(params.getSpuCode());
            param.setMaterialBaseId(baseInfo.getId());
            result = generalMapper.insertMaterialWithMaterialParams(param);
            logger.info("正在插入第" + result + "条记录！");
        }
        // 到此认为插入成功，返回正确结果
        return MaterialErrCode.successUpdateMaterialInMaterial;
    }


    /**
     * 更新单个物料规格属性值的函数
     *
     * @author cplayer
     * @date 2019-02-28 15:53
     * @param params 更新物料信息请求的参数
     *
     * @param data 单个待更新的规格属性值对象
     *
     * @param propList 物料基本属性列表
     *
     * @return MaterialErrCode.failedUpdateSingleFormatInMaterial 单个规格信息更新失败
     *         MaterialErrCode.successUpdateSingleFormatInMaterial 单个规格信息更新成功
     *         MaterialErrCode.notAllowedFormatObject 规格信息名字未找到
     *
     */
    public int updateMaterialInfoForMaterialDataBySingleFormat (MaterialInfoModifyRequest params,
                                                                MaterialModifyRequestForFormatProp data,
                                                                List<MaterialBasePropModel> propList) {
        // 若物料编码、值为空，则直接跳过
        if (data.getMaterialCode() == null || data.getValue() == null || data.getName() == null) {
            logger.error(String.format("物料规格信息中，存在不符合要求的对象！"));
            return MaterialErrCode.notAllowedFormatObject;
        }
        // 先获取待更新的属性名
        String name = data.getName();
        // 在物料基本属性列表中查找对应的名字
        int index = propList.indexOf(name);
        if (index != -1) {
            // 若存在，获取物料基本属性id
            int id = propList.get(index).getId();
            int count = infoModifyMapper.countMaterialBasePropValByParams(id,
                    data.getMaterialCode(),
                    params.getSpuCode());
            int modifyResult;
            MaterialBasePropValModel paramPropVal = new MaterialBasePropValModel();
            paramPropVal.setMaterialBasePropId(id);
            paramPropVal.setMaterialCode(data.getMaterialCode());
            paramPropVal.setSpuCode(params.getSpuCode());
            paramPropVal.setValue(data.getValue());
            if (count > 0) {
                // 若存在对应的物料基本属性值记录，则更新
                modifyResult = infoModifyMapper.updateMaterialBasePropValByParams(paramPropVal);
            } else {
                // 否则则插入
                modifyResult = infoModifyMapper.insertMaterialBasePropValByParams(paramPropVal);
                logger.info(String.format("更新的物料基本属性值记录id = %d。", paramPropVal.getId()));
            }
            if (modifyResult <= 0) {
                return MaterialErrCode.failedUpdateSingleFormatInMaterial;
            } else {
                return MaterialErrCode.successUpdateSingleFormatInMaterial;
            }
        } else {
            // 若不存在，返回错误
            logger.error(String.format("属性名为%s，物料编码为%s的规格属性不存在！"));
            return MaterialErrCode.notFoundFormatNameInDB;
        }
    }

    /**
     * 更新物料定义页面中规格部分的函数
     *
     * @author cplayer
     * @date 2019-02-27 21:15
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateFormatInMaterial 代表成功
     *         MaterialErrCode.failedUpdateFormatInMaterial 代表失败
     *
     */
    public int updateMaterialInfoForMaterialDataByFormat (MaterialInfoModifyRequest params) {
        // 先删除所有规格属性值，规格属性类别对应的是4
        int propertyType = 4;
        // 根据spu编码获取物料分类id
        MaterialBaseModel baseParam = new MaterialBaseModel();
        baseParam.setSpuCode(params.getSpuCode());
        List<MaterialBaseModel> baseList = generalMapper.getMaterialBaseWithMaterialBaseParams(baseParam);
        // 取第一个数据对象
        baseParam = MaterialGeneral.getInitElementOrFirstElement(baseList, MaterialBaseModel.class);
        if (baseParam.getId() == -1) {
            // 若不存在返回错误
            logger.error(String.format("检查spu编码为%s的时候并没有发现对应的数据！", params.getSpuCode()));
            return MaterialErrCode.failedUpdateFormatInMaterial;
        }
        // 然后根据物料分类id、属性类型（type = 4）筛选出所有当前spu编码可能出现的物料基本属性列表，并暂存
        // 必须保证同物料分类下属性名不重复！！
        MaterialBasePropModel paramProp = new MaterialBasePropModel();
        paramProp.setMaterialCatId(baseParam.getMaterialCatId());
        paramProp.setType(propertyType);
        List<MaterialBasePropModel> propList = generalMapper.getMaterialBasePropWithMaterialBasePropParams(paramProp);
        // 获取所有待更新的规格信息对象列表
        List<MaterialModifyRequestForFormatProp> formatList = params.getMaterialDatas().getFormatList();
        // 总的更新结果状态
        int sumModifyResult = MaterialErrCode.successUpdateFormatInMaterial;
        // 遍历待更新的规格对象信息
        for (MaterialModifyRequestForFormatProp formatData : formatList) {
            // 逐个调用更新函数更新
            int modifyResult = updateMaterialInfoForMaterialDataBySingleFormat(params, formatData, propList);
            if (modifyResult == MaterialErrCode.failedUpdateSingleFormatInMaterial ||
                modifyResult == MaterialErrCode.notAllowedFormatObject ||
                modifyResult == MaterialErrCode.notFoundFormatNameInDB) {
                sumModifyResult = MaterialErrCode.failedUpdateFormatInMaterial;
                logger.error(String.format("更新规格属性中，更新单个规格属性操作对应的错误码为：%d。", modifyResult));
            } else {
                logger.info(String.format("更新规格属性成功！"));
            }
        }
        return sumModifyResult;
    }

    /**
     * 更新物料定义页面中更新信息的函数
     *
     * @author cplayer
     * @date 2019-02-27 21:14
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateMaterial 代表成功
     *         MaterialErrCode.failedUpdateMaterial 代表失败
     *
     */
    public int updateMaterialInfoForMaterialData (MaterialInfoModifyRequest params) {
        // 更新物料定义部分的结果
        int materialResult = updateMaterialInfoForMaterialDataByMaterial(params);
        // 更新规格信息的结果
        int formatResult = updateMaterialInfoForMaterialDataByFormat(params);
        return materialResult * formatResult;
    }
}
