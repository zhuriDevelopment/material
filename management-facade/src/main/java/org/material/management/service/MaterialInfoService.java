package org.material.management.service;

import java.util.*;

import org.material.management.model.tablemodel.*;
import org.springframework.stereotype.Service;

@Service
public interface MaterialInfoService {

    List<MaterialBaseModel> getBaseInfoByParams (Map<String, Object> params);

    List<Object> getMaterialInfo (String spuCode, String spuName, List<Integer> types, int orgnizationId);

    int addMaterialCategory (String code,String name,int parentId);

    int updateMaterialCategory (String code, String name, int parentId);

    int deleteMaterialCategory (String code);
}
