package org.material.management.service.impl;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.material.management.model.propertymodel.ControlPropertyBean;
import org.material.management.model.propertymodel.purchaseandstore.PurchaseAndStoreList;
import org.material.management.model.propertymodel.sales.SalesList;
import org.material.management.model.propertymodel.plan.PlanList;
import org.material.management.model.propertymodel.quality.QualityList;
import org.material.management.model.propertymodel.finance.FinanceList;
import org.material.management.mapper.MaterialInfoMapper;
import org.material.management.model.tablemodel.*;
import org.springframework.beans.factory.annotation.Autowired;

// 作为ServiceImpl的补充类，以防止ServiceImpl类过于复杂掩盖逻辑

public class MaterialInfoServiceImplSupplier {
    @Autowired
    private static PurchaseAndStoreList purchaseAndStoreList;
    @Autowired
    private static PlanList planList;
    @Autowired
    private static SalesList salesList;
    @Autowired
    private static QualityList qualityList;
    @Autowired
    private static FinanceList financeList;
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

    public static List<ControlPropertyBean> getAllControlPropByArray (String[] propNames, int orgnizationId, String spuCode) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) { return null; }
        // 此时应该只有一个分类id
        int materialCatId = baseResult.get(0).getMaterialCatId();
        // 获取物料id
        List<MaterialModel> materialResult = materialInfoMapper.getMaterialWithSpuCode(spuCode);
        if (materialResult == null || materialResult.size() == 0) { return null; }
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
        List<ControlPropertyBean> result = new ArrayList<>();
        result.clear();
        for (String propName : propNames) {
            params.clear();
            params.put("name", propName);
            List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropParams(params);
            int ctrlPropId = ctrlPropResult.get(0).getId();
            // 查找对应的属性值，根据版本号和属性名id
            params.clear();
            params.put("versionId", versionId);
            params.put("materialCtrlPropId", ctrlPropId);
            List<MaterialCtrlPropValModel> ctrlValResult = materialInfoMapper.getCtrlPropValWithCtrlPropValParams(params);
            if (ctrlValResult == null) { return null; }
            String value = ctrlValResult.get(0).getValue();
            result.add(new ControlPropertyBean(propName, value));
        }
        return result;
    }

    public static List<ControlPropertyBean> getControlPropByName (String propName, int orgnizationId, String spuCode) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) { return null; }
        // 此时应该只有一个分类id
        int materialCatId = baseResult.get(0).getMaterialCatId();
        // 获取物料id
        List<MaterialModel> materialResult = materialInfoMapper.getMaterialWithSpuCode(spuCode);
        if (materialResult == null || materialResult.size() == 0) { return null; }
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
        // 查找控制属性名对应的id
        params.clear();
        params.put("name", propName);
        List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropParams(params);
        int ctrlPropId = ctrlPropResult.get(0).getId();
        // 查找对应的属性值，根据版本号和属性名id
        params.clear();
        params.put("versionId", versionId);
        params.put("materialCtrlPropId", ctrlPropId);
        List<MaterialCtrlPropValModel> ctrlValResult = materialInfoMapper.getCtrlPropValWithCtrlPropValParams(params);
        if (ctrlValResult == null) { return null; }
        List<ControlPropertyBean> result = new ArrayList<>();
        result.clear();
        String value = ctrlValResult.get(0).getValue();
        result.add(new ControlPropertyBean(propName, value));
        return result;
    }

    public static List<ControlPropertyBean> getPurchaseAndStoreProperties (int index, int orgnizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            String[] purchasePropertiesList = purchaseAndStoreList.getPurchasePropertiesList();
            int len = purchasePropertiesList.length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(purchasePropertiesList[i], orgnizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(purchaseAndStoreList.getPurchasePropertiesList()[index], orgnizationId, spuCode);
        }
    }

    public static List<ControlPropertyBean> getPlanProperties(int index, int orgnizationId, String spuCode){
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            String[] planPropertiesList = planList.getPlanPropertiesList();
            int len = planPropertiesList.length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(planPropertiesList[i], orgnizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(planList.getPlanPropertiesList()[index], orgnizationId, spuCode);
        }
    }
    public static List<ControlPropertyBean> getSalesProperties (int index, int orgnizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            String[] salesPropertiesList = salesList.getSalesList();
            int len = salesPropertiesList.length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(salesPropertiesList[i], orgnizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(salesList.getSalesList()[index], orgnizationId, spuCode);
        }
    }

    public static List<ControlPropertyBean> getQualityProperties (int index, int orgnizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            String[] qualityPropertiesList = qualityList.getQualityList();
            int len = qualityPropertiesList.length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(qualityPropertiesList[i], orgnizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(qualityList.getQualityList()[index], orgnizationId, spuCode);
        }
    }
    public static List<ControlPropertyBean> getFinanceProperties (int index, int orgnizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = null;
            String[] financePropertiesList = financeList.getFinanceList();
            int len = financePropertiesList.length;
            for (int i = 0; i < len; ++i) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(financePropertiesList[i], orgnizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(financeList.getFinanceList()[index], orgnizationId, spuCode);
        }
    }

    // 获取控制属性
     /*
        采购和库存属性：5
        计划类属性：6
        销售类属性：7
        质量类属性：8
        财务类属性：9
    */
    public static List<ControlPropertyBean> getAllControlPropertyByType (int type, int orgnizationId, String spuCode) {
        switch(type){
            case 5:// 采购和库存属性：5
                return getPurchaseAndStoreProperties(-1, orgnizationId, spuCode);
            case 6://计划类属性：6
                return getPlanProperties(-1,orgnizationId,spuCode );
            case 7://销售类属性：7
                return getSalesProperties(-1,orgnizationId,spuCode);
            case 8://质量类属性：8
                return getQualityProperties(-1,orgnizationId,spuCode);
            case 9://财务类属性：9
                return getFinanceProperties(-1,orgnizationId,spuCode);
            default:
                return null;
        }

    }

}
