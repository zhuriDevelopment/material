package org.material.managementservice.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

// 本类所有的方法均要求做到对全部参数可用
// 根据所提供的Map动态生成对应的sql语言
// params里面包含key-value对，value将直接用于sql语句的构造
public class MaterialInfoProvider {
    // ---------------------------------------- 获取物料信息部分 ----------------------------------------
    public String getBaseInfoWithBaseInfoParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "mnemonic", "spuName", "description", "category", "designCode", "designVersion", "defaultUnitId", "source",
                "usage", "note", "materialCatId"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialBase");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getMaterialWithMaterialParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "materialCode", "materialName", "oldMaterialCode", "barCode", "materialBaseId", "purchasePrice",
                "sellingPrice"};
        return new SQL() {
            {
                SELECT("*");
                FROM("material");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getMaterialSkuWithMaterialSkuParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "skuCode", "materialId", "unitId", "purchasePrice", "sellingPrice", "description"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialSku");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getUnitWithUnitParams (Map<String, Object> params) {
        String[] keyList = {"id", "label", "name", "englishName", "relatedId", "conversionFactor", "sort"};
        return new SQL() {
            {
                SELECT("*");
                FROM("unit");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getMaterialUnitWithMaterialUnitParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "unitId", "relatedId", "conversionFactor", "sort"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialUnit");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getMaterialCategoryWithMaterialCategoryParams (Map<String, Object> params) {
        String[] keyList = {"id", "code", "name", "parentId"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialCategory");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getMaterialBasePropWithMaterialBasePropParams (Map<String, Object> params) {
        String[] keyList = {"id", "materialCatId", "category", "label", "name", "range", "sort"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialBaseProp");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getMaterialBasePropValWithMaterialBasePropValParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "materialCode", "materialBasePropId", "value"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialBasePropVal");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getFilesWithFilesParams (Map<String, Object> params) {
        String[] keyList = {"id", "materialBaseId", "fileId"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialFiles");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getCtrlPropWithCtrlPropParams (Map<String, Object> params) {
        String[] keyList = {"id", "category", "name", "label"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialCtrlProp");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getCtrlPropValWithCtrlPropValParams (Map<String, Object> params) {
        String[] keyList = {"id", "versionId", "materialCtrlPropId", "value"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialCtrlPropVal");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    public String getCtrlPropValVerWithCtrlPropValVerParams (Map<String, Object> params) {
        String[] keyList = {"id", "version", "origanizationCode", "materialCatId", "spuCode", "startDate", "endDate"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialCtrlPropValVer");
                for (String key : keyList) {
                    if (params.get(key) != null) {
                        WHERE(key + "=#{" + key + "}");
                    }
                }
            }
        }.toString();
    }

    // ---------------------------------------- 添加物料基本信息部分 ----------------------------------------
    public String createBaseWithSpuCode (String spuCode) {
        return new SQL() {
            {
                INSERT_INTO("materialBase");
                VALUES("spuCode", "'" + spuCode + "'");
                VALUES("spuName", "spuName");
                VALUES("category", "1");
                VALUES("defaultUnitId", "0");
                VALUES("materialCatId", "0");
            }
        }.toString();
    }

    public String insertMaterialWithSpuCodeAndParams (String spuCode, int materialBaseId, Map<String, Object> params) {
        String[] stringList = {"materialCode", "materialName", "oldMaterialCode", "barCode"};
        String[] intList = {"materialBaseId", "purchasePrice", "sellingPrice"};
        return new SQL() {
            {
                INSERT_INTO("material");
                VALUES("spuCode", "'" + spuCode + "'");
                for (String key : stringList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, "'" + value + "'");
                    }
                }
                VALUES("materialBaseId", Integer.toString(materialBaseId));
                for (String key : intList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, value);
                    }
                }
            }
        }.toString();
    }

    public String insertMaterialSkuWithSpuCodeAndParams (String spuCode, Map<String, Object> params) {
        String[] stringList = {"skuCode", "description"};
        String[] intList = {"unitId", "purchasePrice", "sellingPrice", "materialId"};
        return new SQL() {
            {
                INSERT_INTO("materialSku");
                VALUES("spuCode", "'" + spuCode + "'");
                for (String key : stringList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, "'" + value + "'");
                    }
                }
                for (String key : intList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, value);
                    }
                }
            }
        }.toString();
    }

    public String insertCtrlPropValWithCtrlPropValParams (int versionId, int ctrlPropId, String value) {
        return new SQL() {
            {
                INSERT_INTO("materialCtrlPropVal");
                VALUES("value", "'" + value + "'");
                VALUES("versionId", Integer.valueOf(versionId).toString());
                VALUES("materialCtrlPropId", Integer.valueOf(ctrlPropId).toString());
            }
        }.toString();
    }

    public String insertCtrlPropValVerWithCtrlPropValVerParams (Map<String, Object> params) {
        String[] stringList = {"version", "organizationCode", "spuCode", "startDate", "endDate"};
        String[] intList = {"materialCatId"};
        return new SQL() {
            {
                INSERT_INTO("materialCtrlPropValVer");
                for (String key : stringList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, "'" + value + "'");
                    }
                }
                for (String key : intList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, value);
                    }
                }
            }
        }.toString();
    }

    // ---------------------------------------- 更新物料基本信息部分 ----------------------------------------
    public String updateBaseInfoWithBaseInfoParams (String spuCode, String name, String value) {
        String[] stringList = {"spuCode", "mnemonic", "spuName", "description", "designCode", "designVersion",
                "source", "usage", "note"};
        return new SQL() {
            {
                UPDATE("materialBase");
                boolean stringFlag = false;
                for (String key : stringList) {
                    if (key.equals(name)) {
                        stringFlag = true;
                        break;
                    }
                }
                if (stringFlag) {
                    SET(name + "='" + value + "'");
                } else {
                    SET(name + "=" + value);
                }
                WHERE("spuCode = " + spuCode);
            }
        }.toString();
    }

    public String updateupdateBaseInfoWithBaseInfoParamsArray (String spuCode, String[] names, String[] values) {
        // 必须保证names长度和values长度一致！
        return new SQL() {
            {
                UPDATE("materialBase");
                int len = names.length;
                for (int i = 0; i < len; ++i) {
                    SET(names[i] + "=" + values[i]);
                }
                WHERE("spuCode = " + spuCode);
            }
        }.toString();
    }

    public String updateMaterialSkuWithMaterialSkuParams (String spuCode, String name, String value) {
        return new SQL() {
            {
                UPDATE("materialSku");
                SET(name + "=" + value);
                WHERE("spuCode = " + spuCode);
            }
        }.toString();
    }

    public String updateMaterialSkuWithMaterialSkuParamsArray (String spuCode, String[] names, String[] values) {
        // 必须保证names长度和values长度一致！
        return new SQL() {
            {
                UPDATE("materialSku");
                int len = names.length;
                for (int i = 0; i < len; ++i) {
                    SET(names[i] + "=" + values[i]);
                }
                WHERE("spuCode = " + spuCode);
            }
        }.toString();
    }

    public String updateUnitWithUnitParams (int id, Map<String, Object> params) {
        return new SQL() {
            {
                UPDATE("unit");
                for (String key : params.keySet()) {
                    SET(key + "='" + params.get(key) + "'");
                }
                WHERE("id = " + id);
            }
        }.toString();
    }

    public String updateMaterialUnitWithMaterialUnitParams (int id, String name, String value) {
        return new SQL() {
            {
                UPDATE("materialUnit");
                SET(name + "=" + value);
                WHERE("id = " + id);
            }
        }.toString();
    }

    public String updateMaterialUnitWithMaterialUnitParamsArray (int id, String[] names, String[] values) {
        // 必须保证names长度和values长度一致！
        return new SQL() {
            {
                UPDATE("materialUnit");
                int len = names.length;
                for (int i = 0; i < len; ++i) {
                    SET(names[i] + "=" + values[i]);
                }
                WHERE("id = " + id);
            }
        }.toString();
    }

    public String updateMaterialFilesWithMaterialFilesParams (int materialBaseId, String name, String value) {
        return new SQL() {
            {
                UPDATE("materialFiles");
                SET(name + "=" + value);
                WHERE("materialBaseId = " + materialBaseId);
            }
        }.toString();
    }

    public String updateMaterialBaseProp (int id, String name, String value) {
        return new SQL() {
            {
                UPDATE("materialBaseProp");
                SET(name + "=" + value);
                WHERE("id = " + id);
            }
        }.toString();
    }

    public String updateMaterialBasePropVal (String spuCode, String materialCode, int materialBasePropId, String name, String value) {
        return new SQL() {
            {
                UPDATE("materialBasePropVal");
                SET(name + "='" + value + "'");
                WHERE("spuCode = " + spuCode);
                WHERE("materialCode = " + materialCode);
                WHERE("materialBasePropId = " + materialBasePropId);
            }
        }.toString();
    }

    public String updateMaterialFilesWithMaterialFilesParamsArray (int materialBaseId, String[] names, String[] values) {
        return new SQL() {
            {
                UPDATE("materialFiles");
                int len = names.length;
                for (int i = 0; i < len; ++i) {
                    SET(names[i] + "=" + values[i]);
                }
                WHERE("materialBaseId = " + materialBaseId);
            }
        }.toString();
    }

    public String updateCtrlPropWithCtrlPropParams (int versionId, int ctrlPropId, String value) {
        return new SQL() {
            {
                UPDATE("materialCtrlPropVal");
                SET("value" + "='" + value + "'");
                WHERE("versionId" + "=" + versionId);
                WHERE("materialCtrlPropId" + "=" + ctrlPropId);
            }
        }.toString();
    }

    // ---------------------------------------- 删除物料基本信息部分 ----------------------------------------
    public String deleteAllMaterialWithSpuCode (String spuCode) {
        return new SQL() {
            {
                DELETE_FROM("material");
                WHERE("spuCode = " + spuCode);
            }
        }.toString();
    }

    public String deleteAllMaterialSkuWithSpuCode (String spuCode) {
        return new SQL() {
            {
                DELETE_FROM("materialSku");
                WHERE("spuCode = " + spuCode);
            }
        }.toString();
    }

    // ---------------------------------------- 添加物料基本属性部分 ----------------------------------------
    public String insertMaterialBasePropWithMaterialBasePropParams (Map<String, Object> params) {
        String[] stringList = {"label", "name", "valueRange"};
        String[] intList = {"materialCatId", "category", "sort"};
        return new SQL() {
            {
                INSERT_INTO("materialBaseProp");
                for (String key : stringList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, "'" + value + "'");
                    }
                }
                for (String key : intList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, value);
                    }
                }
            }
        }.toString();
    }

    public String insertMaterialBasePropValWithMaterialBasePropValParams (Map<String, Object> params) {
        String[] stringList = {"spuCode", "materialCode", "value"};
        String[] intList = {"materialBasePropId"};
        return new SQL() {
            {
                INSERT_INTO("materialBasePropVal");
                for (String key : stringList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, "'" + value + "'");
                    }
                }
                for (String key : intList) {
                    if (params.containsKey(key)) {
                        String column = key;
                        String value = params.get(key).toString();
                        VALUES(column, value);
                    }
                }
            }
        }.toString();
    }

    // ---------------------------------------- 删除物料基本属性部分 ----------------------------------------
    public String deleteAllMaterialBasePropWithCatId (Integer catId) {
        return new SQL() {
            {
                DELETE_FROM("materialBaseProp");
                WHERE("materialCatId = " + catId.toString());
            }
        }.toString();
    }

    public String deleteAllMaterialBasePropValWithMaterialBasePropId (Integer materialBasePropId) {
        return new SQL() {
            {
                DELETE_FROM("materialBasePropVal");
                WHERE("materialBasePropId = " + materialBasePropId.toString());
            }
        }.toString();
    }
}
