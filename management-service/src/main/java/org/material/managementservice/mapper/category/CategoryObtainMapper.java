package org.material.managementservice.mapper.category;

import org.apache.ibatis.annotations.Mapper;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@Mapper
@Component(value = "categoryObtainMapper")
public interface CategoryObtainMapper {
    List<MaterialCategoryModel> getMaterialCategoryByNameAndCode (String catName, String catCode);

    List<MaterialCategoryModel> getMaterialCategoryById (int id);

    List<MaterialCategoryModel> getAllMaterialCategory ();
}
