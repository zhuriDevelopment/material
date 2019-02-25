package org.material.managementservice.service.info.impl.supplier.baseinfo;

import org.material.managementfacade.model.responsemodel.BaseInfoResponse;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.model.tablemodel.MaterialKeyPropModel;
import org.material.managementfacade.model.tablemodel.UnitModel;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.InfoObtainServiceSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cplayer on 2019-02-25 20:59
 * @version 1.0
 * getBaseInfo接口的补充类，用于存放其专用的接口函数
 */
@Component
public class BaseInfoObtainServiceSupplier {
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainServiceSupplier infoObtainServiceSupplier;

    /**
     * 获取所有的基本信息的详细信息
     *
     * 包括物料基本信息、物料分类信息、计量单位信息和物料关键属性信息
     * materialBase、materialCategory、unit、materialKeyProp
     *
     * @author cplayer
     * @date 2019-02-25 16:33
     * @param baseModels 待获取的基本信息列表
     *
     * @return org.material.managementfacade.model.responsemodel.BaseInfoResponse
     *
     */
    public BaseInfoResponse getBaseInfoAllParams (List<MaterialBaseModel> baseModels) {
        BaseInfoResponse result = new BaseInfoResponse();
        result.setBaseResult(baseModels);
        // 物料分类信息
        List<MaterialCategoryModel> catResult = new ArrayList<>();
        // 物料计量单位信息
        List<UnitModel> unitResult = new ArrayList<>();
        // 物料关键属性信息（可能有多个）
        List<List<MaterialKeyPropModel>> keyProps = new ArrayList<>();
        catResult.clear();
        unitResult.clear();
        keyProps.clear();
        for (MaterialBaseModel baseModel : baseModels) {
            List<MaterialCategoryModel> tmp;
            List<UnitModel> tmpUnit;
            MaterialCategoryModel paramCategory = new MaterialCategoryModel();
            paramCategory.setId(baseModel.getMaterialCatId());
            tmp = generalMapper.getMaterialCategoryWithMaterialCategoryParams(paramCategory);
            UnitModel paramUnit = new UnitModel();
            paramUnit.setId(baseModel.getDefaultUnitId());
            tmpUnit = generalMapper.getUnitWithUnitParams(paramUnit);
            List<MaterialKeyPropModel> keyPropList = infoObtainMapper.getAllKeyPropWithSpuCode(baseModel.getSpuCode());
            catResult.add(infoObtainServiceSupplier.getInitElementOrFirstElement(tmp, MaterialCategoryModel.class));
            unitResult.add(infoObtainServiceSupplier.getInitElementOrFirstElement(tmpUnit, UnitModel.class));
            keyProps.add(keyPropList);
        }
        result.setCatResult(catResult);
        result.setUnitResult(unitResult);
        result.setKeyPropResult(keyProps);
        return result;
    }
}
