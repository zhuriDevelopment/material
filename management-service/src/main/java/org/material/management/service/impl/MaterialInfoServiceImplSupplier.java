package org.material.management.service.impl;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.material.management.model.propertymodel.ControlPropertyBean;
import org.material.management.model.propertymodel.purchaseandstore.PurchaseAndStoreList;
import org.material.management.mapper.MaterialInfoMapper;
import org.material.management.model.tablemodel.*;
import org.springframework.beans.factory.annotation.Autowired;

// 作为ServiceImpl的补充类，以防止ServiceImpl类过于复杂掩盖逻辑

public class MaterialInfoServiceImplSupplier {
    @Autowired
    private static PurchaseAndStoreList purchaseAndStoreList;
    @Autowired
    private static MaterialInfoMapper materialInfoMapper;

    // ---------------------------------------- 生成参数Map部分 ----------------------------------------
    // 根据提供的参数生成对应的参数Map的方法
    // 通用子方法
    private static Map<String, Object> chooseParams (Map<String, Object> params, String[] keyList, String[] targetList) {
        Map<String, Object> result = new HashMap<>(16);
        result.clear();
        for (int i = 0; i < keyList.length; ++i) {
            String baseKey = keyList[i];
            String targetKey = targetList[i];
            if (params.containsKey(baseKey)) {
                result.put(targetKey, params.get(baseKey));
            }
        }
        return result;
    }
    
    // 对materialBase表
    public static Map<String, Object> splitBaseInfoParams (
                    Map<String, Object> params, 
                    String[] keyList,
                    String[] targetList) 
    {
        // 根据所给定的参数进行筛选
        return chooseParams(params, keyList, targetList);
    }

    // 对materialCategory表
    public static Map<String, Object> splitCategoryParams (
                    Map<String, Object> params,
                    String[] keyList,
                    String[] targetList) 
    {
        return chooseParams(params, keyList, targetList);
    }

    // 对material表
    public static Map<String, Object> splitMaterialParams (
                    Map<String, Object> params,
                    String[] keyList,
                    String[] targetList) 
    {
        return chooseParams(params, keyList, targetList);
    }

    // ---------------------------------------- 获取控制信息部分 ----------------------------------------

    public static List<ControlPropertyBean> getPurchaseAndStorePropertyByIndex (int index, int orgnizationId, String spuCode) {
        // 获取物料分类id
        List<MaterialBaseModel> baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        // 不存在就返回空
        if (baseResult == null || baseResult.size() == 0) return null;
        // 此时应该只有一个分类id
        int materialCatId = baseResult.get(0).getMaterialCatId();
        // 获取物料id
        List<MaterialModel> materialResult = materialInfoMapper.getMaterialWithSpuCode(spuCode);
        if (materialResult == null || materialResult.size() == 0) return null;
        int materialId = materialResult.get(0).getId();
        // 确认版本号
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", materialCatId);
        params.put("materialId", materialId);
        params.put("origanizationCode", orgnizationId);
        List<MaterialCtrlPropValVerModel> ctrlVerResult = materialInfoMapper.getCtrlPropValVerWithCtrlPropValVerParams(params);
        // 需要确保结果只有一个，若有多个，取第一个
        int versionId = ctrlVerResult.get(0).getId();
        params.clear();
        params.put("versionId", versionId);
        List<MaterialCtrlPropValModel> ctrlValResult = materialInfoMapper.getCtrlPropValWithCtrlPropValParams(params);
        if (ctrlValResult == null) return null;
        List<ControlPropertyBean> result = new ArrayList<>();
        result.clear();
        for (MaterialCtrlPropValModel element : ctrlValResult) {
            int ctrlPropId = element.getMaterialCtrlPropId();
            List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropId(ctrlPropId);
            // 按id进行处理只可能有一个
            String name = ctrlPropResult.get(0).getName();
            String value = element.getValue();
            result.add(new ControlPropertyBean(name, value));
        }
        return result;
    }

    public static List<ControlPropertyBean> getPurchaseAndStoreProperties (int index, int orgnizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            int len = purchaseAndStoreList.getPurchasePropertiesList().length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getPurchaseAndStorePropertyByIndex(i, orgnizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getPurchaseAndStorePropertyByIndex(index, orgnizationId, spuCode);
        }
    }

    // 获取控制属性
    public static List<ControlPropertyBean> getAllControlPropertyByType (int type, int orgnizationId, String spuCode) {
        if (type == 5) {
            return getPurchaseAndStoreProperties(-1, orgnizationId, spuCode);
        }
        return null;
    }

}
