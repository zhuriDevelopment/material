package org.material.management.service;

import java.util.*;

import org.material.management.model.tablemodel.*;
import org.material.management.model.processmodel.MaterialCategoryTree;

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
}
