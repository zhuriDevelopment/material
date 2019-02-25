package org.material.managementservice.mapper.general;

import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementfacade.model.tablemodel.UnitModel;

import java.util.List;
import java.util.Map;

/**
* @author cplayer on 2019-02-25 02:24.
* @version 1.0 
*        
* 物料通用Mapper类，本Mapper中存放着根据参数获取各数据表中数据的基本功能
*
*/
public interface MaterialGeneralMapper {

    List<MaterialModel> getMaterialWithMaterialParams (MaterialModel param);

    List<MaterialCategoryModel> getMaterialCategoryWithMaterialCategoryParams(MaterialCategoryModel param);

    List<UnitModel> getUnitWithUnitParams (UnitModel param);
}
