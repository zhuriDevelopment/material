package com.zhuri.material.materialmanagement.service.supplier;

import java.util.Map;
import java.util.HashMap;

// 作为Service的补充类，以防止Service类过于复杂掩盖逻辑
// 根据提供的参数生成对应的参数Map
public class MaterialInfoServiceSupplier {

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
        Map<String, Object> baseInfoMap = chooseParams(params, keyList, targetList);
        return baseInfoMap;
    }

    // 对materialCategory表
    public static Map<String, Object> splitCategoryParams (
                    Map<String, Object> params,
                    String[] keyList,
                    String[] targetList) 
    {
        Map<String, Object> categoryMap = chooseParams(params, keyList, targetList);
        return categoryMap;
    }

    // 对material表
    public static Map<String, Object> splitMaterialParams (
                    Map<String, Object> params,
                    String[] keyList,
                    String[] targetList) 
    {
        Map<String, Object> materialMap = chooseParams(params, keyList, targetList);
        return materialMap;
    }    

}
