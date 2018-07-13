package com.zhuri.material.materialmanagement.mapper.provider;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class MaterialInfoProvider {
    // 本类所有的方法均要求做到对全部参数可用
    // 动态生成对应的sql语言
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
}
