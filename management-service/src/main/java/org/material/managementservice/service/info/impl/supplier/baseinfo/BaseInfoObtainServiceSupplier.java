package org.material.managementservice.service.info.impl.supplier.baseinfo;

import org.material.managementfacade.model.responsemodel.BaseInfoResp;
import org.material.managementfacade.model.responsemodel.BaseInfoRespParams;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.model.tablemodel.UnitModel;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
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

    /**
     * 获取所有的基本信息的详细信息
     * <p>
     * 包括物料基本信息、物料分类信息、计量单位信息
     * materialBase、materialCategory、unit
     *
     * @param baseModels 待获取的基本信息列表
     * @return org.material.managementfacade.model.responsemodel.BaseInfoRespParams
     * @author cplayer
     * @date 2019-02-25 16:33
     */
    public BaseInfoResp getBaseInfoAllParams (List<MaterialBaseModel> baseModels) {
        BaseInfoResp result = new BaseInfoResp();
        BaseInfoRespParams resultParams = new BaseInfoRespParams();
        resultParams.setBaseResult(baseModels);
        // 物料分类信息
        List<MaterialCategoryModel> catResult = new ArrayList<>();
        // 物料计量单位信息
        List<UnitModel> unitResult = new ArrayList<>();
        catResult.clear();
        unitResult.clear();
        // 对于所有的物料基本信息，逐个寻找对应的物料分类详情和物料计量单位详情，以便前端填写物料分类名称和计量单位名称
        for (MaterialBaseModel baseModel : baseModels) {
            List<MaterialCategoryModel> tmp;
            List<UnitModel> tmpUnit;
            MaterialCategoryModel paramCategory = new MaterialCategoryModel();
            paramCategory.setId(baseModel.getMaterialCatId());
            tmp = generalMapper.getMaterialCategoryWithMaterialCategoryParams(paramCategory);
            UnitModel paramUnit = new UnitModel();
            paramUnit.setId(baseModel.getDefaultUnitId());
            tmpUnit = generalMapper.getUnitWithUnitParams(paramUnit);
            catResult.add(MaterialGeneral.getInitElementOrFirstElement(tmp, MaterialCategoryModel.class));
            unitResult.add(MaterialGeneral.getInitElementOrFirstElement(tmpUnit, UnitModel.class));
        }
        // 设置返回值
        resultParams.setCatResult(catResult);
        resultParams.setUnitResult(unitResult);
        result.setResult(resultParams);
        result.setErrCode(MaterialInfoErrCode.successObtainMaterialBase);
        return result;
    }
}
