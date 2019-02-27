package org.material.managementservice.mapper.general;

import org.material.managementfacade.model.tablemodel.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author cplayer on 2019-02-25 02:24.
* @version 1.0 
*        
* 物料通用Mapper类，本Mapper中存放着根据参数获取各数据表中数据的基本功能
*
*/
@Repository
public interface GeneralMapper {
    List<MaterialBaseModel> getMaterialBaseWithMaterialBaseParams (MaterialBaseModel param);

    List<MaterialModel> getMaterialWithMaterialParams (MaterialModel param);

    List<MaterialSkuModel> getMaterialSkuWithMaterialSkuParams (MaterialSkuModel param);

    List<MaterialCategoryModel> getMaterialCategoryWithMaterialCategoryParams(MaterialCategoryModel param);

    List<UnitModel> getUnitWithUnitParams (UnitModel param);

    List<MaterialBasePropModel> getMaterialBasePropWithMaterialBasePropParams (MaterialBasePropModel param);

    List<MaterialBasePropValModel> getMaterialBasePropValWithMaterialBasePropValParams (MaterialBasePropValModel param);

    List<MaterialCtrlPropModel> getCtrlPropWithCtrlPropParams (MaterialCtrlPropModel param);

    List<MaterialCtrlPropValModel> getCtrlPropValWithCtrlPropValParams (MaterialCtrlPropValModel param);

    List<MaterialCtrlPropValVerModel> getCtrlPropValVerWithCtrlPropValVerParams (MaterialCtrlPropValVerModel param);

    int insertMaterialBaseWithMaterialBaseParams (MaterialBaseModel param);

    int updateMaterialBaseWithMaterialBaseParams (MaterialBaseModel param);

    int insertMaterialWithMaterialParams (MaterialModel param);
}
