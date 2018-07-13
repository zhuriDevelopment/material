package com.zhuri.material.materialmanagement.service.supplier;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

// 作为Service的补充类，以防止Service类过于复杂掩盖逻辑
// 根据提供的参数生成对应的参数Map
public class MaterialInfoServiceSupplier {
    // 对materialBase表
    public static Map<String, Object> splitBaseInfoParams (
                    Map<String, Object> params, 
                    String[] keyList,
                    String[] targetList) 
    {
        // 根据所给定的参数进行筛选
        Map<String, Object> baseInfoMap = new HashMap<String, Object>(16);
        baseInfoMap.clear();
        for (int i = 0; i < keyList.length; ++i) {
            String baseKey = keyList[i];
            String targetKey = targetList[i];
            if (params.containsKey(baseKey)) {
                baseInfoMap.put(targetKey, params.get(baseKey));
            }
        }
        return baseInfoMap;
    }

    // 对materialCategory表
    public static Map<String, Object> splitCategoryParams (
                    Map<String, Object> params,
                    String[] keyList,
                    String[] targetList) 
    {
        Map<String, Object> categoryMap = new HashMap<String, Object>(16);
        categoryMap.clear();
        for (int i = 0; i < keyList.length; ++i) {
            String baseKey = keyList[i];
            String targetKey = targetList[i];
            if (params.containsKey(baseKey)) {
                categoryMap.put(targetKey, params.get(baseKey));
            }
        }
        return categoryMap;
    }

    // 对material表
    public static Map<String, Object> splitMaterialParams (
                    Map<String, Object> params,
                    String[] keyList,
                    String[] targetList) 
    {
        Map<String, Object> materialMap = new HashMap<String, Object>(16);
        materialMap.clear();
        for (int i = 0; i < keyList.length; ++i) {
            String baseKey = keyList[i];
            String targetKey = targetList[i];
            if (params.containsKey(baseKey)) {
                materialMap.put(targetKey, params.get(baseKey));
            }
        }
        return materialMap;
    }    

}
