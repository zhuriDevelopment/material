package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyByCatCodeAndNameRequest;
import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.responsemodel.MaterialInfoModifyByCatCodeAndNameResponse;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.service.info.InfoModifyService;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
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
    @Autowired
    private InfoModifyServiceSupplier infoModifyServiceSupplier;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private BasePropModifyServiceSupplier basePropModifyServiceSupplier;
    @Autowired
    private ControlPropModifyServiceSupplier controlPropModifyServiceSupplier;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 更新物料信息的函数
     *
     * 包含更新：
     * 物料基本信息（materialBase表）
     * 物料定义（material表）
     * SKU定义
     * 附件管理（暂无）
     * 控制属性：包括采购和库存属性、计划类属性、销售类属性、质量类属性和财务类属性
     * 计量单位
     * 规格信息
     *
     * @author cplayer
     * @date 2019-02-27 04:37
     * @param params 更新物料信息请求的参数
     *
     * @return 成功了的操作个数
     *
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
    };

    /**
     * 根据物料分类编码、物料名称以及待更新的数据更新物料信息的实现函数
     *
     * @author cplayer
     * @date 2019-03-02 18:03
     * @param params 更新物料信息请求的参数
     *
     * @return org.material.managementfacade.model.responsemodel.MaterialInfoModifyByCatCodeAndNameResponse
     *
     */
    @Override
    public MaterialInfoModifyByCatCodeAndNameResponse updateMaterialInfoWithCatCodeAndCatName
            (MaterialInfoModifyByCatCodeAndNameRequest params) {
        MaterialInfoModifyByCatCodeAndNameResponse result = new MaterialInfoModifyByCatCodeAndNameResponse();
        // 获取物料分类id
        MaterialCategoryModel cateParam = new MaterialCategoryModel();
        cateParam.setCode(params.getCatCode());
        List<MaterialCategoryModel> materialBaseTmp = generalMapper.getMaterialCategoryWithMaterialCategoryParams(cateParam);
        int catId = MaterialGeneral.getInitElementOrFirstElement(materialBaseTmp, MaterialCategoryModel.class).getId();
        // 成功获取了物料分类id
        if (catId != -1) {
            logger.info("物料分类编码为" + params.getCatCode() + "的记录对应的物料分类id为：" + catId);
            // 更新物料基本属性，并设置错误码
            if (params.getBaseProps() != null) {
                int basePropResult = basePropModifyServiceSupplier.updateMaterialBasePropByCatId(catId, params.getBaseProps());
                result.setErrCodeInBaseProp(basePropResult);
            }
            // 更新物料控制属性
            if (params.getCtrProps() != null) {
                int ctrPropResult = controlPropModifyServiceSupplier.updateControlPropertyByCatIdAndTypeAndValue(params.getCtrProps(), catId);
                result.setErrCodeInCtrProp(ctrPropResult);
            }
        } else {
            // 返回不存在对应物料分类信息的错误码
            result.setErrCode(MaterialInfoErrCode.notFoundCategoryInUpdatingInfoWithCatIdAndName);
        }
        return result;
    }
}
