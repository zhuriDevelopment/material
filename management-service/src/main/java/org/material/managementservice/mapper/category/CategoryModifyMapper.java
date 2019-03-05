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
@Component(value = "CategoryModifyMapper")
public interface CategoryModifyMapper {
    int countMaterialCategoryByCode (String code);

    int countMaterialCategoryById (int id);

    int countMaterialCategoryByName (String name);

    int insertMaterialCategoryByParam (MaterialCategoryModel param);

    int countMaterialCategoryByParam (MaterialCategoryModel param);

    int updateMaterialCategoryName (String oldName, String newName, int parentId);

    List<MaterialCategoryModel> getMatarialCategoryByParentId (int parentId);

    int deleteMaterialCategoryByNameAndParentId (String name, int parentId);
}
