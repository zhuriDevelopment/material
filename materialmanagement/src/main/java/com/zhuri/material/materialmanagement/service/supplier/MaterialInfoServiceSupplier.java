package com.zhuri.material.materialmanagement.service.supplier;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.zhuri.material.materialmanagement.bean.propertybean.ControlPropertyBean;
import com.zhuri.material.materialmanagement.bean.propertybean.purchaseandstore.PurchaseAndStoreList;
import org.springframework.beans.factory.annotation.Autowired;

// 作为Service的补充类，以防止Service类过于复杂掩盖逻辑

public class MaterialInfoServiceSupplier {
    @Autowired
    private static PurchaseAndStoreList purchaseAndStoreList;

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

    public static List<ControlPropertyBean> getPurchaseAndStorePropertyByIndex (int index, int orgnizationId) {
        return null;
    }

    public static List<ControlPropertyBean> getPurchaseAndStoreProperties (int index, int orgnizationId) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            int len = purchaseAndStoreList.getPurchasePropertiesList().length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getPurchaseAndStorePropertyByIndex(i, orgnizationId);
                if (!tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getPurchaseAndStorePropertyByIndex(index, orgnizationId);
        }
    }

    // 获取控制属性
    public static List<ControlPropertyBean> getAllControlPropertyByType (int type, int orgnizationId) {
        if (type == 5) {
            return getPurchaseAndStoreProperties(-1, orgnizationId);
        }
        return null;
    }

}
