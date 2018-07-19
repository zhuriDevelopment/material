package com.zhuri.material.materialmanagement.mapper.provider;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class MaterialInfoProvider {
    // 本类所有的方法均要求做到对全部参数可用
    // 根据所提供的Map动态生成对应的sql语言
    // params里面包含key-value对，value将直接用于sql语句的构造
    public String getBaseInfoWithBaseInfoParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "mnemonic", "spuName", "description", "type", "designCode", "designVersion", "defaultUnitId", "source",
                            "usage", "materialCatId"};
        return new SQL () {
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

    public String getMaterialCategoryWithMaterialCategoryParams (Map<String, Object> params) {
        String[] keyList = {"id", "code", "name", "parentId"};
        return new SQL () {
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

    public String getMaterialWithMaterialParams (Map<String, Object> params) {
        String[] keyList = {"id", "spuCode", "materialCode", "materialName", "oldMaterialCode", "barCode", "materialBaseId", "purchasePrice",
                            "sellingPrice"};
        return new SQL () {
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
        String[] keyList = {"id", "spuCode", "skuCode", "materialId", "unitId", "purchasePrice", "sellingPrice"};
        return new SQL() {
            {
                SELECT("*");
                FROM("materialsku");
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

    public String getCtrlPropValWithCtrlPropValParams (Map<String, Object> params) {
        String[] keyList = {"id", "versionId", "materialCtrlPropId", "value"};
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

    public String getCtrlPropValVerWithCtrlPropValVerParams (Map<String, Object> params) {
        String[] keyList = {"id", "version", "origanizationCode", "materialCatId", "materialId", "startDate", "endDate"};
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
}
