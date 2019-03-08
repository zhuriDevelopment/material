package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.requestmodel.*;
import org.material.managementfacade.model.responsemodel.InfoModifyByCatCodeAndNameResp;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.service.info.InfoModifyService;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.category.CategoryModifyMapper;
import org.material.managementservice.mapper.category.CategoryObtainMapper;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.InfoModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.baseprop.BasePropModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.controlprop.ControlPropModifyServiceSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@Component
public class InfoModifyServiceImpl implements InfoModifyService {
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");
    @Autowired
    private InfoModifyServiceSupplier infoModifyServiceSupplier;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private CategoryObtainMapper categoryObtainMapper;
    @Autowired
    private CategoryModifyMapper categoryModifyMapper;
    @Autowired
    private BasePropModifyServiceSupplier basePropModifyServiceSupplier;
    @Autowired
    private ControlPropModifyServiceSupplier controlPropModifyServiceSupplier;

    /**
     * 更新物料信息的函数
     * <p>
     * 包含更新：
     * 物料基本信息（materialBase表）
     * 物料定义（material表）
     * SKU定义
     * 附件管理（暂无）
     * 控制属性：包括采购和库存属性、计划类属性、销售类属性、质量类属性和财务类属性
     * 计量单位
     * 规格信息
     *
     * @param params 更新物料信息请求的参数
     * @return 成功了的操作个数
     * @author cplayer
     * @date 2019-02-27 04:37
     */
    @Override
    public int updateMaterialInfo (MaterialInfoModifyRequest params) {
        int result = 0;
        String spuCode = params.getSpuCode();
        if (spuCode == null) {
            logger.error(String.format("上传的spu编码为空！"));
            return 0;
        }
        // 更新物料基本信息
        if (params.getBaseDatas() != null) {
            int updateBaseResult = infoModifyServiceSupplier.updateMaterialInfoForBaseData(params);
            // 成功的操作进入统计
            if (updateBaseResult == MaterialInfoErrCode.successUpdateMaterialBase) {
                result++;
            }
        }
        // 更新物料定义
        if (params.getMaterialDatas() != null) {
            int updateMaterialResult = infoModifyServiceSupplier.updateMaterialInfoForMaterialDataByMaterial(params);
            if (updateMaterialResult == MaterialInfoErrCode.successUpdateMaterial) {
                result++;
            }
        }
        // 更新物料定义中规格信息
        if (params.getFormatDatas() != null) {
            int updateFormatResult = infoModifyServiceSupplier.updateMaterialInfoForMaterialDataByFormat(params);
            if (updateFormatResult == MaterialInfoErrCode.successUpdateFormatInMaterial) {
                result++;
            }
        }
        // 更新物料sku定义
        if (params.getSkuDatas() != null) {
            int updateSkuResult = infoModifyServiceSupplier.updateMaterialInfoForSkuData(params);
            if (updateSkuResult == MaterialInfoErrCode.successUpdateSku) {
                result++;
            }
        }
        // 附件管理（不实现）
        // 控制属性
        if (params.getCtrPropDatas() != null) {
            int updateCtrPropResult = infoModifyServiceSupplier.updateMaterialInfoForCtrData(params);
            if (updateCtrPropResult == MaterialInfoErrCode.successUpdateControlProp) {
                result++;
            }
        }
        // 计量单位
        if (params.getUnitDatas() != null) {
            int updateUnitResult = infoModifyServiceSupplier.updateMaterialInfoForUnitData(params);
            if (updateUnitResult == MaterialInfoErrCode.successUpdateUnit) {
                result++;
            }
        }
        return result;
    }

    ;

    /**
     * 根据物料分类编码、物料名称以及待更新的数据更新物料信息的实现函数
     *
     * @param params 更新物料信息请求的参数
     * @return org.material.managementfacade.model.responsemodel.InfoModifyByCatCodeAndNameResp
     * @author cplayer
     * @date 2019-03-02 18:03
     */
    @Override
    public InfoModifyByCatCodeAndNameResp updateMaterialInfoWithCatCodeAndCatName
    (InfoModifyByCatCodeAndNameReq params) {
        InfoModifyByCatCodeAndNameResp result = new InfoModifyByCatCodeAndNameResp();
        // 获取物料分类id
        int catId = params.getId();
        // 检查物料分类信息是否需要更新
        List<MaterialCategoryModel> materialCatTmp = categoryObtainMapper.getMaterialCategoryById(catId);
        MaterialCategoryModel existedCatInfo = MaterialGeneral.getInitElementOrFirstElement(materialCatTmp, MaterialCategoryModel.class);
        if (!existedCatInfo.getName().equals(params.getCatName()) ||
            !existedCatInfo.getCode().equals(params.getCatCode()) ||
            existedCatInfo.getType() != params.getType()) {
            MaterialCategoryModel cateParam = new MaterialCategoryModel();
            cateParam.setId(catId);
            cateParam.setCode(params.getCatCode());
            cateParam.setName(params.getCatName());
            cateParam.setType(params.getType());
            int updateResult = categoryModifyMapper.updateMaterialCategoryByParams(cateParam);
            logger.info(String.format("更新物料分类id = %d的信息的返回值为%d。", catId, updateResult));
            result.setErrCode(MaterialInfoErrCode.successOperation);
        } else {
            result.setErrCode(MaterialInfoErrCode.unsubmitedOperation);
        }
        // 成功获取了物料分类id
        if (catId != -1) {
            logger.info("物料分类编码为" + params.getCatCode() + "的记录对应的物料分类id为：" + catId);
            // 更新物料基本属性，并设置错误码
            if (params.getBaseProps() != null) {
                int basePropResult = basePropModifyServiceSupplier.updateMaterialBasePropByCatId(catId, params.getBaseProps());
                result.setErrCodeInBaseProp(basePropResult);
            } else {
                result.setErrCodeInBaseProp(MaterialInfoErrCode.unsubmitedOperation);
            }
            // 更新物料控制属性
            if (params.getCtrProps() != null) {
                int ctrPropResult = controlPropModifyServiceSupplier.updateControlPropertyByCatIdAndTypeAndValue(params.getCtrProps(), catId);
                result.setErrCodeInCtrProp(ctrPropResult);
            } else {
                result.setErrCodeInCtrProp(MaterialInfoErrCode.unsubmitedOperation);
            }
        } else {
            // 返回不存在对应物料分类信息的错误码
            result.setErrCode(MaterialInfoErrCode.notFoundCategoryInUpdatingInfoWithCatIdAndName);
        }
        return result;
    }

    /**
     * 根据spu编码和物料编码更新物料基本属性的实现函数
     *
     * @param params 更新物料信息请求的参数
     * @return MaterialInfoErrCode.successUpdateMaterialBaseWithSpuAndCatCode 更新成功
     * MaterialInfoErrCode.failedUpdateMaterialBaseWithSpuAndCatCode 更新失败
     * @author cplayer
     * @date 2019-03-03 05:10
     */
    @Override
    public int updateMaterialBasePropsBySpuCodeAndMaterialCodes
    (MaterialBaseModifyBySpuAndMatCodeRequest params) {
        int resultCode = MaterialInfoErrCode.successUpdateMaterialBaseWithSpuAndCatCode;
        for (MaterialBaseModifyBySpuAndMatCodeUpdateProps element : params.getUpdateValues()) {
            String materialCode = element.getMaterialCode();
            for (MaterialBaseModifyBySpuAndMatCodeUpdatePropsDatas keyValue : element.getValueList()) {
                String name = keyValue.getName();
                String value = keyValue.getValue();
                // spuCode, materialCode, name, value四个要素齐了
                // 先根据name查找materialBasePropId
                // 为此需要物料分类id
                int materialCatId = MaterialGeneral.getInitElementOrFirstElement(
                        infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode()),
                        MaterialBaseModel.class).getMaterialCatId();
                if (materialCatId == -1) {
                    // 说明没有对应的物料分类id
                    logger.error(String.format("查找spuCode = %s的记录时出现错误。", params.getSpuCode()));
                    resultCode = MaterialInfoErrCode.invalidOrNotFoundSpuCode;
                    continue;
                }
                // 查找对应的物料基本属性id
                List<MaterialBasePropModel> materialBasePropResult = infoObtainMapper.getMaterialBasePropWithNameAndMatCatId(name, materialCatId);
                if (materialBasePropResult.size() > 0) {
                    if (materialBasePropResult.size() > 1) {
                        logger.warn(String.format("查找name = %s，materialCatId = %d的记录时出现多个记录，应该只有一条记录！", name, materialCatId));
                    }
                    int materialBasePropId = materialBasePropResult.get(0).getId();
                    // 再查找已有的value
                    List<MaterialBasePropValModel> materialBasePropValResult = infoObtainMapper
                            .getMaterialBasePropValWithSpuAndCatCodeAndPropId(params.getSpuCode(), materialCode, materialBasePropId);
                    if (materialBasePropResult.size() > 1) {
                        logger.warn(String.format("查找spuCode = %s, materialCode = %s, materialBasePropId = %d的记录时出现多个记录，应该只有一条记录！",
                                params.getSpuCode(), materialCode, materialBasePropId));
                    }
                    String originValue = materialBasePropValResult.get(0).getValue();
                    // 再根据相同情况决定是否更新
                    if (!originValue.equals(value)) {
                        MaterialBasePropValModel propValParam = new MaterialBasePropValModel();
                        propValParam.setSpuCode(params.getSpuCode());
                        propValParam.setMaterialCode(materialCode);
                        propValParam.setMaterialBasePropId(materialBasePropId);
                        propValParam.setValue(value);
                        int updateResult = infoModifyMapper.updateMaterialBasePropValByParams(propValParam);
                        if (updateResult <= 0) {
                            resultCode = MaterialInfoErrCode.failedUpdateMaterialBaseWithSpuAndCatCode;
                        }
                        logger.debug("更新记录spuCode = %s, materialCode = %s, materialBasePropId = %d, value = %s的返回值为：%d",
                                params.getSpuCode(), materialCode, materialBasePropId, value, updateResult);
                    }
                }
            }
        }
        return resultCode;
    }
}
