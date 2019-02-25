package org.material.managementservice.mapper.info;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.model.tablemodel.UnitModel;
import org.material.managementservice.mapper.provider.MaterialInfoProvider;

import java.util.List;
import java.util.Map;

/**
* @author cplayer on 2019-02-25 02:24.
* @version 1.0 
*        
* 物料信息获取Mapper类，本类定义了物料信息获取的相关方法
*
*/
public interface MaterialInfoObtainMapper {
    List<MaterialBaseModel> getAllBaseInfo ();

    @Select("SELECT * FROM materialBase WHERE spuCode=#{spuCode};")
    List<MaterialBaseModel> getBaseInfoWithSpuCode (String spuCode);
}
