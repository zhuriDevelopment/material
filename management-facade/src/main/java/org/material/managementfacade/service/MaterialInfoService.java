package org.material.managementfacade.service;

import java.util.*;

import org.material.managementfacade.model.processmodel.MaterialCategoryTree;

import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.springframework.stereotype.Service;

@Service
public interface MaterialInfoService {

    List<Object> getAllBaseInfo ();

    List<Object> getBaseInfoByParams (Map<String, Object> params);

    List<Object> getMaterialInfo (String spuCode, String spuName, List<Integer> types, int orgnizationId);

    int updateMaterialInfo (String spuCode, String spuName, List<Object> data);

    MaterialCategoryTree getMaterialCategory ();

    int addMaterialCategory (String code, String name, int parentId);

    int updateMaterialCategory (String newName, String oldName, int parentId);

    int deleteMaterialCategory (int id, String code, String name, int parentId);

    List<Object> getAllMaterialBaseByCategoryInfos (int id);
}
