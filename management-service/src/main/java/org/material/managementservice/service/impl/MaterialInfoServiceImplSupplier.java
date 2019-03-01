package org.material.managementservice.service.impl;

import java.util.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.propertymodel.purchaseandstore.PurchaseAndStoreList;
import org.material.managementfacade.model.propertymodel.sales.SalesList;
import org.material.managementfacade.model.propertymodel.plan.PlanList;
import org.material.managementfacade.model.propertymodel.quality.QualityList;
import org.material.managementfacade.model.propertymodel.finance.FinanceList;
import org.material.managementservice.mapper.MaterialInfoMapper;
import org.material.managementfacade.model.tablemodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 作为ServiceImpl的补充类，以防止ServiceImpl类过于复杂掩盖逻辑

@Service
public class MaterialInfoServiceImplSupplier {
    @Autowired
    private PurchaseAndStoreList purchaseAndStoreList;
    @Autowired
    private PlanList planList;
    @Autowired
    private SalesList salesList;
    @Autowired
    private QualityList qualityList;
    @Autowired
    private FinanceList financeList;
    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    // ---------------------------------------- 生成参数Map部分 ----------------------------------------
    // 根据提供的参数生成对应的参数Map的方法
    // 通用子方法
    private Map<String, Object> chooseParams (Map<String, Object> params, String[] keyList, String[] targetList) {
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
    Map<String, Object> splitBaseInfoParams (
            Map<String, Object> params,
            String[] keyList,
            String[] targetList) {
        // 根据所给定的参数进行筛选
        return chooseParams(params, keyList, targetList);
    }

    // 对materialCategory表
    Map<String, Object> splitCategoryParams (
            Map<String, Object> params,
            String[] keyList,
            String[] targetList) {
        return chooseParams(params, keyList, targetList);
    }

    // 对material表
    Map<String, Object> splitMaterialParams (
            Map<String, Object> params,
            String[] keyList,
            String[] targetList) {
        return chooseParams(params, keyList, targetList);
    }

    // ---------------------------------------- 物料定义部分 ----------------------------------------
    int updateMaterialWithMaterialList (String spuCode, List<Map<String, Object>> updateValue) {
        int result = 0;
        // 先删除已有的所有记录
        result = materialInfoMapper.deleteAllMaterialWithSpuCode(spuCode);
        logger.debug("删除所有的物料记录，删除了" + result + "条。");
        // 再完全重新插入
        int dataLen = updateValue.size();
        List<MaterialBaseModel> baseInfoForFiles = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        int materialBaseId;
        if (baseInfoForFiles != null && baseInfoForFiles.size() > 0) {
            // 有对应的记录
            materialBaseId = baseInfoForFiles.get(0).getId();
        } else {
            // 若没有采用-1
            materialBaseId = -1;
        }
        logger.debug("spuCode = " + spuCode + ", materialBaseId = " + materialBaseId);
        result = 0;
        for (int i = 0; i < dataLen; ++i) {
            int updateId;
            updateId = materialInfoMapper.insertMaterialWithSpuCodeAndParams(spuCode, materialBaseId, updateValue.get(i));
            if (updateId >= 0) {
                result++;
            }
        }
        return result;
    }

    // ---------------------------------------- 物料sku信息部分 ----------------------------------------

    List<Object> getMaterialSkuWithMaterialSkuParams (Map<String, Object> params) {
        List<MaterialSkuModel> skuModels = materialInfoMapper.getMaterialSkuWithMaterialSkuParams(params);
        List<Object> result = new ArrayList<>();
        if (skuModels != null && skuModels.size() > 0) {
            result.add(skuModels);
            Map<String, Object> param = new HashMap<>(16);
            List<MaterialModel> materialModels = new ArrayList<>();
            List<UnitModel> unitModels = new ArrayList<>();
            for (MaterialSkuModel element : skuModels) {
                param.clear();
                // param.put("id", element.getMaterialId());
                param.put("id", element.getMaterialCode());
                List<MaterialModel> tmpModels = materialInfoMapper.getMaterialWithMaterialParams(params);
                if (tmpModels != null && tmpModels.size() > 0) {
                    // 取第一个
                    materialModels.add(tmpModels.get(0));
                } else {
                    materialModels.add(new MaterialModel());
                }
                param.clear();
                param.put("id", element.getUnitId());
                List<UnitModel> tmpUnit = materialInfoMapper.getUnitWithUnitParams(params);
                if (tmpUnit != null && tmpUnit.size() > 0) {
                    // 同取第一个
                    unitModels.add(tmpUnit.get(0));
                } else {
                    unitModels.add(new UnitModel());
                }
            }
            result.add(materialModels);
            result.add(unitModels);
        } else {
            result.add(new ArrayList<>());
        }
        return result;
    }

    int updateMaterialSkuWithMaterialSkuList (String spuCode, List<Map<String, Object>> updateValue) {
        int result = 0;
        result = materialInfoMapper.deleteAllMaterialSkuWithSpuCode(spuCode);
        logger.debug("删除所有的物料sku记录，删除了" + result + "条。");
        int dataLen = updateValue.size();
        result = 0;
        Map<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < dataLen; ++i) {
            int updateId;
            logger.debug("spuCode = " + spuCode);
            // 填充unitId
            paramsMap.clear();
            paramsMap.put("name", updateValue.get(i).get("unit"));
            List<UnitModel> unitResult = materialInfoMapper.getUnitWithUnitParams(paramsMap);
            int unitId;
            if (unitResult != null && unitResult.size() > 0) {
                // 有对应的记录
                unitId = unitResult.get(0).getId();
            } else {
                unitId = -1;
            }
            updateValue.get(i).put("unitId", unitId);
            logger.debug("unitId = " + unitId);
            // 填充materialId
            paramsMap.clear();
            paramsMap.put("materialCode", updateValue.get(i).get("materialCode"));
            List<MaterialModel> materialResult = materialInfoMapper.getMaterialWithMaterialParams(paramsMap);
            int materialId;
            if (materialResult != null && materialResult.size() > 0) {
                // 存在对应记录
                materialId = materialResult.get(0).getId();
            } else {
                materialId = -1;
            }
            updateValue.get(i).put("materialId", materialId);
            logger.debug("materialId = " + materialId);
            updateId = materialInfoMapper.insertMaterialSkuWithSpuCodeAndParams(spuCode, updateValue.get(i));
            if (updateId >= 0) {
                result++;
            }
        }
        return result;
    }

    // ---------------------------------------- 获取物料单位部分 ----------------------------------------

    List<Object> getAllUnitsBySpuCode (String spuCode) {
        // 先获取所有的物料单位记录
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("spuCode", spuCode);
        List<MaterialBaseModel> materialBases = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        int defaultUnitId = materialBases.get(0).getDefaultUnitId();
        List<MaterialUnitModel> materialUnits = materialInfoMapper.getMaterialUnitWithMaterialUnitParams(params);
        List<Object> result = new ArrayList<>();
        List<UnitModel> allUnit = new ArrayList<>();
        List<UnitModel> defaultUnit = new ArrayList<>();
        result.clear();
        allUnit.clear();
        defaultUnit.clear();
        // 对于每个物料单位记录逐个查询，以覆盖转换系数和排序号
        for (MaterialUnitModel element : materialUnits) {
            int unitId = element.getUnitId();
            params.clear();
            params.put("id", unitId);
            List<UnitModel> tmp = materialInfoMapper.getUnitWithUnitParams(params);
            if (tmp != null && tmp.size() > 0) {
                // 这里应该只有第一个为有效
                double conversionFactor = element.getConversionFactor();
                if (conversionFactor >= 0) {
                    tmp.get(0).setConversionFactor(conversionFactor);
                }
                int sort = element.getSort();
                if (sort > 0) {
                    tmp.get(0).setSort(sort);
                }
                allUnit.addAll(tmp);
                if (unitId == defaultUnitId) {
                    defaultUnit.addAll(tmp);
                }
            }
        }
        result.add(defaultUnit);
        result.add(allUnit);
        return result;
    }

    // ---------------------------------------- 更新物料单位部分 ----------------------------------------

    public int updateUnitsBySpuCode (String spuCode, List<Object> updateValueLists) {
        Map<String, Object> params = new HashMap<>();
        params.clear();
        try {
            String defaultUnitName = updateValueLists.get(0).toString();
            List<Object> updateValue = (List<Object>) updateValueLists.get(1);
            for (Object element : updateValue) {
                Map<String, Object> unitElement = (Map<String, Object>) element;
                params.put("label", unitElement.get("label"));
                params.put("name", unitElement.get("name"));
                params.put("englishName", unitElement.get("englishName"));
                List<UnitModel> unitResults = materialInfoMapper.getUnitWithUnitParams(params);
                int unitId = 0;
                if (unitResults != null && unitResults.size() > 0) {
                    // 存在
                    unitId = unitResults.get(0).getId();
                } else {
                    // 不存在
                    materialInfoMapper.addUnit(unitElement.get("label").toString(), unitElement.get("name").toString(),
                            unitElement.get("englishName").toString(), 0, 1, 1);
                    List<UnitModel> unitTmp = materialInfoMapper.getUnitWithUnitParams(params);
                    // 刚刚存进去，不可能为空
                    unitId = unitTmp.get(0).getId();
                    params.clear();
                    params.put("relatedId", unitId);
                    materialInfoMapper.updateUnitWithUnitParams(unitId, params);
                }
                logger.info("查找/更新unit表结束，所对应的unitId为：" + unitId);
                // 根据unitId来更新materialUnit表
                params.put("spuCode", spuCode);
                params.put("unitId", unitId);
                List<MaterialUnitModel> materialUnitResults = materialInfoMapper.getMaterialUnitWithMaterialUnitParams(params);
                int materialUnitId = 0;
                if (materialUnitResults != null && materialUnitResults.size() > 0) {
                    // 存在
                    materialUnitId = materialUnitResults.get(0).getId();
                    materialInfoMapper.updateMaterialUnitWithMaterialUnitParams(materialUnitId, "conversionFactor", unitElement.get("conversionFactor").toString());
                    materialInfoMapper.updateMaterialUnitWithMaterialUnitParams(materialUnitId, "sort", unitElement.get("sort").toString());
                } else {
                    // 不存在
                    materialInfoMapper.addMaterialUnit(spuCode, unitId, unitId,
                            Double.parseDouble(unitElement.get("conversionFactor").toString()),
                            Integer.parseInt(unitElement.get("sort").toString()));
                    params.clear();
                    params.put("spuCode", spuCode);
                    params.put("unitId", unitId);
                    List<MaterialUnitModel> materialUnitTmp = materialInfoMapper.getMaterialUnitWithMaterialUnitParams(params);
                    materialUnitId = materialUnitTmp.get(0).getId();
                }
                logger.info("查找/更新materialUnit表结束，所对应的materialUnitId为：" + materialUnitId);
            }
            logger.info("现在开始更新所有materialUnit表记录的id为默认计量单位id。");
            params.clear();
            // 此处查询的依据是中文名字！
            params.put("name", defaultUnitName);
            List<UnitModel> unitTmp = materialInfoMapper.getUnitWithUnitParams(params);
            int defaultUnitId = unitTmp.get(0).getId();
            params.clear();
            params.put("spuCode", spuCode);
            List<MaterialUnitModel> materialUnitTmp = materialInfoMapper.getMaterialUnitWithMaterialUnitParams(params);
            for (MaterialUnitModel element : materialUnitTmp) {
                if (element.getId() != defaultUnitId) {
                    materialInfoMapper.updateMaterialUnitWithMaterialUnitParams(element.getId(), "relatedId", new Integer(defaultUnitId).toString());
                }
            }
            // 更新对应spuCode的默认计量单位id
            materialInfoMapper.updateBaseInfoWithBaseInfoParams(spuCode, "defaultUnitId", new Integer(defaultUnitId).toString());
            logger.info("更新对应spuCode的默认计量单位id成功。");

        } catch (ClassCastException e) {
            logger.error("发生类转换错误，函数：updateUnitsBySpuCode");
        }

        return 0;
    }

    // ---------------------------------------- 物料基本属性部分 ----------------------------------------
    /*
        1:关键属性
        2:非关键属性
        3:批号属性
        4:规格属性
    */
    // ---------------------------------------- 获取物料基本属性部分 ----------------------------------------

    List<Object> getAllMaterialBaseProp (String spuCode) throws ClassCastException {
        List<Object> result = new ArrayList<>();
        result.clear();
        try {
            List<MaterialBasePropValModel> valList = new ArrayList<>();
            List<MaterialBasePropModel> propList = new ArrayList<>();
            for (int i = 1; i <= 4; ++i) {
                List<Object> tmpResult = getMaterialBasePropBySpuCodeAndType(spuCode, i);
                if (tmpResult != null && tmpResult.size() > 0) {
                    List<MaterialBasePropModel> propTmp = (List<MaterialBasePropModel>) tmpResult.get(1);
                    List<MaterialBasePropValModel> valTmp = (List<MaterialBasePropValModel>) tmpResult.get(0);
                    valList.addAll(valTmp);
                    propList.addAll(propTmp);
                }
            }
            int[] sortArray = new int[propList.size() + 1];
            for (int i = 0; i < propList.size(); ++i) {
                sortArray[propList.get(i).getSort()] = i;
            }
            List<MaterialBasePropValModel> valSort = new ArrayList<>();
            List<MaterialBasePropModel> propSort = new ArrayList<>();
            for (int i = 1; i <= propList.size(); ++i) {
                valSort.add(valList.get(sortArray[i]));
                propSort.add(propList.get(sortArray[i]));
            }
            result.add(valSort);
            result.add(propSort);
        } catch (ClassCastException e) {
            logger.error("发生转换异常，函数：getAllMaterialBaseProp。");
        }
        return result;
    }

    List<Object> getMaterialBasePropBySpuCodeAndType (String spuCode, int propertyType) {
        List<Object> result = new ArrayList<>();
        result.clear();
        // 包含两个list，第一个list存放了对应的属性值，第二个list存放了对应的基本属性内容
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("spuCode", spuCode);
        // 查询所有此spu编码下的不同物料的默认值
        params.put("materialCode", "-1");
        List<MaterialBasePropValModel> valResult = materialInfoMapper.getMaterialBasePropValWithMaterialBasePropValParams(params);
        List<MaterialBasePropModel> propResult = new ArrayList<>();
        propResult.clear();
        for (MaterialBasePropValModel element : valResult) {
            params.clear();
            int id = element.getMaterialBasePropId();
            params.put("id", id);
            params.put("type", propertyType);
            List<MaterialBasePropModel> tmp = materialInfoMapper.getMaterialBasePropWithMaterialBasePropParams(params);
            if (tmp != null && tmp.size() > 0) {
                propResult.addAll(tmp);
            } else {
                // id为-1的对应记录说明出错了，但为了保持对应长度相等故存放无效对象进去
                MaterialBasePropModel emptyModel = new MaterialBasePropModel();
                emptyModel.setId(-1);
                propResult.add(emptyModel);
            }
        }
        List<MaterialBasePropValModel> valFilter = new ArrayList<>();
        List<MaterialBasePropModel> propFilter = new ArrayList<>();
        for (int i = 0; i < valResult.size(); ++i) {
            if (propResult.get(i).getId() != -1) {
                valFilter.add(valResult.get(i));
                propFilter.add(propResult.get(i));
            }
        }
        result.add(valFilter);
        result.add(propFilter);
        return result;
    }

    List<Object> getAllMaterialBaseByCatId (int catId) {
        List<Object> result = new ArrayList<>();
        result.clear();
        logger.info("获取物料基本属性，物料分类id为：" + catId);
        for (int i = 1; i <= 4; ++i) {
            List<Object> tmpResult = getMaterialBaseByCatIdAndType(catId, i);
            logger.info("获取物料基本属性中，类别为：" + i);
            if (tmpResult != null && tmpResult.size() > 0) {
                result.addAll(tmpResult);
                logger.info("获取了物料基本属性！");
            }
        }
        try {
            result.sort(new Comparator<Object>() {
                @Override
                public int compare (Object a, Object b) {
                    MaterialBasePropModel tmpA = (MaterialBasePropModel) a;
                    MaterialBasePropModel tmpB = (MaterialBasePropModel) b;
                    return tmpA.getSort() - tmpB.getSort();
                }
            });
        } catch (ClassCastException e) {
            e.printStackTrace();
            logger.error("在getAllMaterialBaseByCatId中返回了不应该返回的值，返回的类不为MaterialBasePropModel！");
        }
        return result;
    }

    List<Object> getMaterialBaseByCatIdAndType (int catId, int propertyType) {
        List<Object> result = new ArrayList<>();
        result.clear();
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", catId);
        params.put("type", propertyType);
        List<MaterialBasePropModel> materialBasePropResult = materialInfoMapper.getMaterialBasePropWithMaterialBasePropParams(params);
        result.addAll(materialBasePropResult);
        return result;
    }

    // ---------------------------------------- 更新物料基本属性部分 ----------------------------------------

    int updateMaterialBasePropBySpuCode (String spuCode, int propertyType, List<Object> updateValue) {
        List<Object> basePropVals = (List<Object>) updateValue.get(0);
        List<Object> baseProps = (List<Object>) updateValue.get(1);
        int statusCode = 0;
        for (int i = 0; i < baseProps.size(); ++i) {
            int eachStatusCode = 0;
            Map<String, Object> curBaseProp = (Map<String, Object>) baseProps.get(i);
            Map<String, Object> curBasePropVal = (Map<String, Object>) basePropVals.get(i);
            List<MaterialBaseModel> materialBase = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
            int materialCatId = materialBase.get(0).getMaterialCatId();
            Map<String, Object> params = new HashMap<>();
            params.clear();
            params.put("type", curBaseProp.get("category"));
            params.put("label", curBaseProp.get("label"));
            params.put("name", curBaseProp.get("name"));
            params.put("materialCatId", materialCatId);
            List<MaterialBasePropModel> materialBasePropResult = materialInfoMapper.getMaterialBasePropWithMaterialBasePropParams(params);
            int materialBasePropId = 0;
            String rangeVal = curBaseProp.get("range").toString();
            rangeVal = rangeVal.replace("\"", "\\\"");
            logger.info("range字段的值为：" + rangeVal);
            if (materialBasePropResult != null && materialBasePropResult.size() > 0) {
                // 存在即更新
                materialBasePropId = materialBasePropResult.get(0).getId();
                int updateResult = 0;
                rangeVal = "\'" + rangeVal + "\'";
                updateResult = materialInfoMapper.updateMaterialBasePropWithMaterialBaseProp(materialBasePropId, "valueRange", rangeVal);
                logger.info("更新range字段，值为：" + curBaseProp.get("range") + "； 结果为：" + updateResult);
                updateResult = materialInfoMapper.updateMaterialBasePropWithMaterialBaseProp(materialBasePropId, "sort", curBaseProp.get("sort").toString());
                logger.info("更新sort字段，值为：" + curBaseProp.get("sort") + "； 结果为：" + updateResult);
            } else {
                // 不存在即添加
                params.put("valueRange", rangeVal);
                params.put("sort", curBaseProp.get("sort"));
                materialBasePropId = materialInfoMapper.insertMaterialBasePropWithMaterialBasePropParams(params);
                List<MaterialBasePropModel> materialBasePropTmp = materialInfoMapper.getMaterialBasePropWithMaterialBasePropParams(params);
                materialBasePropId = materialBasePropTmp.get(0).getId();
                logger.info("添加之后的id为：" + materialBasePropId);
            }
            // 获取materialBasePropId之后，更新materialBasePropVal表
            params.clear();
            params.put("spuCode", spuCode);
            params.put("materialCode", "-1");
            params.put("materialBasePropId", materialBasePropId);
            List<MaterialBasePropValModel> materialBasePropValResult = materialInfoMapper.getMaterialBasePropValWithMaterialBasePropValParams(params);
            int materialBasePropValId = 0;
            if (materialBasePropValResult != null && materialBasePropValResult.size() > 0) {
                // 存在即更新
                int updateResult = 0;
                materialBasePropValId = materialBasePropValResult.get(0).getId();
                updateResult = materialInfoMapper.updateMaterialBasePropValWithMaterialBasePropValParams(spuCode, "-1", materialBasePropId, "value", curBasePropVal.get("value").toString());
                logger.info("更新value字段，值为：" + curBasePropVal.get("value") + "； 结果为：" + updateResult);
            } else {
                // 不存在即添加
                params.put("value", curBasePropVal.get("value"));
                int insertResult = 0;
                insertResult = materialInfoMapper.insertMaterialBasePropValWithMaterialBasePropValParams(params);
                materialBasePropValId = insertResult;
                logger.info("添加之后的id为：" + insertResult);
            }
            eachStatusCode = materialBasePropId * materialBasePropValId;
            if (eachStatusCode > 0) {
                eachStatusCode = 1;
            }
            statusCode += eachStatusCode;
        }
        return statusCode;
    }

    int updateMaterialBasePropByCatId (int catId, List<Object> updateValue) {
        int statusCode = 0;
        int tmpresult = 0;
        Map<String, Object> params = new HashMap<>();
        try {
            // 由于可能物料基础属性可能全部变更，故需要先删除所有已存在的记录，然后全部重新插入
            // 删除的时候要注意要同时从materialBaseProp表和materialBasePropVal表中一起删除，以防在materialBasePropVal表中出现无效数据
            params.clear();
            params.put("materialCatId", catId);
            List<MaterialBasePropModel> materialBasePropTmp = materialInfoMapper.getMaterialBasePropWithMaterialBasePropParams(params);
            if (materialBasePropTmp != null && materialBasePropTmp.size() > 0) {
                for (MaterialBasePropModel element : materialBasePropTmp) {
                    tmpresult = materialInfoMapper.deleteAllMaterialBasePropValWithMaterialBasePropId(new Integer(element.getId()));
                    logger.info("从materialBasePropVal表中删除materialBasePropId = " + element.getId() + "的记录返回结果为：" + tmpresult);
                }
                tmpresult = materialInfoMapper.deleteAllMaterialBasePropWithCatId(new Integer(catId));
                logger.info("从materialBaseProp表中删除所有materialCatId = " + catId + "的记录返回结果为：" + tmpresult);
            } else {
                logger.info("原数据库没有materialCatId = " + catId + "的记录，无需删除！");
            }
            // 再添加所有新的数据
            for (Object element : updateValue) {
                Map<String, Object> record = (Map<String, Object>) element;
                // 每一个元素都是一条数据库记录
                params.clear();
                params.put("materialCatId", catId);
                params.put("type", record.get("category"));
                params.put("label", record.get("label"));
                params.put("name", record.get("name"));
                params.put("valueRange", record.get("range"));
                params.put("sort", record.get("sort"));
                tmpresult = materialInfoMapper.insertMaterialBasePropWithMaterialBasePropParams(params);
                logger.info("在materialBaseProp中添加记录，返回值为：" + tmpresult);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            logger.info("更新物料基本属性时出现转换错误，请检查输入格式是否正确！");
            statusCode = -1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.info("更新物料基本属性时出现空指针错误，请检查数据库是否有此记录，或者上传的数据是否正确！");
            statusCode = -2;
        }
        return statusCode;
    }

    // ---------------------------------------- 获取控制信息部分 ----------------------------------------

    public List<ControlPropertyBean> getAllControlPropByArray (String[] propNames, int organizationId, String spuCode) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) {
            return null;
        }
        // 此时应该只有一个分类id
        int materialCatId = baseResult.get(0).getMaterialCatId();
        // 确认版本号
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", materialCatId);
        params.put("spuCode", spuCode);
        params.put("organizationCode", organizationId);
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
            if (ctrlValResult == null) {
                return null;
            }
            String value = ctrlValResult.get(0).getValue();
            result.add(new ControlPropertyBean(propName, value));
        }
        return result;
    }

    private List<ControlPropertyBean> getControlPropByName (String propName, int organizationId, String spuCode) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) {
            return null;
        }
        // 此时应该只有一个分类id
        int materialCatId = baseResult.get(0).getMaterialCatId();
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", materialCatId);
        params.put("spuCode", spuCode);
        params.put("organizationCode", organizationId);
        List<MaterialCtrlPropValVerModel> ctrlVerResult = materialInfoMapper.getCtrlPropValVerWithCtrlPropValVerParams(params);
        // 需要确保结果只有一个，若有多个，取第一个
        int versionId = ctrlVerResult.get(0).getId();
        // 查找控制属性名对应的id
        params.clear();
        params.put("name", propName);
        List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropParams(params);
        if (ctrlPropResult == null || ctrlPropResult.size() == 0) {
            return null;
        }
        int ctrlPropId = ctrlPropResult.get(0).getId();
        // 查找对应的属性值，根据版本号和属性名id
        params.clear();
        params.put("versionId", versionId);
        params.put("materialCtrlPropId", ctrlPropId);
        List<MaterialCtrlPropValModel> ctrlValResult = materialInfoMapper.getCtrlPropValWithCtrlPropValParams(params);
        if (ctrlValResult == null) {
            return null;
        }
        List<ControlPropertyBean> result = new ArrayList<>();
        result.clear();
        String value = ctrlValResult.get(0).getValue();
        result.add(new ControlPropertyBean(propName, value));
        return result;
    }

    private List<ControlPropertyBean> getPurchaseAndStoreProperties (int index, int organizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] purchasePropertiesList = purchaseAndStoreList.getPurchaseAndStoreList();
            for (String purchaseProperty : purchasePropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(purchaseProperty, organizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(purchaseProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(purchaseAndStoreList.getPurchaseAndStoreList()[index], organizationId, spuCode);
        }
    }

    private List<ControlPropertyBean> getPlanProperties (int index, int organizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] planPropertiesList = planList.getPlanList();
            for (String planProperty : planPropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(planProperty, organizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(planProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(planList.getPlanList()[index], organizationId, spuCode);
        }
    }

    private List<ControlPropertyBean> getSalesProperties (int index, int organizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] salesPropertiesList = salesList.getSalesList();
            for (String salesProperty : salesPropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(salesProperty, organizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(salesProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(salesList.getSalesList()[index], organizationId, spuCode);
        }
    }

    private List<ControlPropertyBean> getQualityProperties (int index, int organizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] qualityPropertiesList = qualityList.getQualityList();
            for (String qualityProperty : qualityPropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(qualityProperty, organizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(qualityProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(qualityList.getQualityList()[index], organizationId, spuCode);
        }
    }

    private List<ControlPropertyBean> getFinanceProperties (int index, int organizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] financePropertiesList = financeList.getFinanceList();
            for (String financeProperty : financePropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByName(financeProperty, organizationId, spuCode);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(financeProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByName(financeList.getFinanceList()[index], organizationId, spuCode);
        }
    }

    // 获取控制属性
    List<ControlPropertyBean> getAllControlPropertyByType (int type, int organizationId, String spuCode) {
        switch (type) {
            case 5:
                // 采购和库存属性：5
                return getPurchaseAndStoreProperties(-1, organizationId, spuCode);
            case 6:
                // 计划类属性：6
                return getPlanProperties(-1, organizationId, spuCode);
            case 7:
                // 销售类属性：7
                return getSalesProperties(-1, organizationId, spuCode);
            case 8:
                // 质量类属性：8
                return getQualityProperties(-1, organizationId, spuCode);
            case 9:
                // 财务类属性：9
                return getFinanceProperties(-1, organizationId, spuCode);
            default:
                return null;
        }
    }

    private List<ControlPropertyBean> getControlPropByCatIdAndName (String propName, int organizationCode, int catId) {
        // 先查询基于某个物料分类下通用的控制属性
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", catId);
        params.put("spuCode", "-1");
        params.put("organizationCode", organizationCode);
        List<MaterialCtrlPropValVerModel> ctrlVerResult = materialInfoMapper.getCtrlPropValVerWithCtrlPropValVerParams(params);
        // 需要确保结果只有一个，若有多个，取第一个
        int versionId = ctrlVerResult.get(0).getId();
        // 查找控制属性名对应的id
        params.clear();
        params.put("name", propName);
        List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropParams(params);
        if (ctrlPropResult == null || ctrlPropResult.size() == 0) {
            return null;
        }
        int ctrlPropId = ctrlPropResult.get(0).getId();
        // 查找对应的属性值，根据版本号和属性名id
        params.clear();
        params.put("versionId", versionId);
        params.put("materialCtrlPropId", ctrlPropId);
        List<MaterialCtrlPropValModel> ctrlValResult = materialInfoMapper.getCtrlPropValWithCtrlPropValParams(params);
        if (ctrlValResult == null) {
            return null;
        }
        List<ControlPropertyBean> result = new ArrayList<>();
        result.clear();
        String value = ctrlValResult.get(0).getValue();
        result.add(new ControlPropertyBean(propName, value));
        return result;
    }

    private List<ControlPropertyBean> getPurchaseAndStorePropertiesWithCatId (int index, int organizationId, int catId) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] purchasePropertiesList = purchaseAndStoreList.getPurchaseAndStoreList();
            for (String purchaseProperty : purchasePropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByCatIdAndName(purchaseProperty, organizationId, catId);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(purchaseProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByCatIdAndName(purchaseAndStoreList.getPurchaseAndStoreList()[index], organizationId, catId);
        }
    }

    private List<ControlPropertyBean> getPlanPropertiesWithCatId (int index, int organizationId, int catId) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] planPropertiesList = planList.getPlanList();
            for (String planProperty : planPropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByCatIdAndName(planProperty, organizationId, catId);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(planProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByCatIdAndName(planList.getPlanList()[index], organizationId, catId);
        }
    }

    private List<ControlPropertyBean> getSalesPropertiesWithCatId (int index, int organizationId, int catId) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] salesPropertiesList = salesList.getSalesList();
            for (String salesProperty : salesPropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByCatIdAndName(salesProperty, organizationId, catId);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(salesProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByCatIdAndName(salesList.getSalesList()[index], organizationId, catId);
        }
    }

    private List<ControlPropertyBean> getQualityPropertiesWithCatId (int index, int organizationId, int catId) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] qualityPropertiesList = qualityList.getQualityList();
            for (String qualityProperty : qualityPropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByCatIdAndName(qualityProperty, organizationId, catId);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(qualityProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByCatIdAndName(qualityList.getQualityList()[index], organizationId, catId);
        }
    }

    private List<ControlPropertyBean> getFinancePropertiesWithCatId (int index, int organizationId, int catId) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            String[] financePropertiesList = financeList.getFinanceList();
            for (String financeProperty : financePropertiesList) {
                List<ControlPropertyBean> tmpResult = getControlPropByCatIdAndName(financeProperty, organizationId, catId);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(financeProperty, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByCatIdAndName(financeList.getFinanceList()[index], organizationId, catId);
        }
    }

    // 获取通用控制属性
    List<ControlPropertyBean> getControlPropsByCatIdAndType (int catId, int type, int organizationId) {
        switch (type) {
            case 5:
                return getPurchaseAndStorePropertiesWithCatId(-1, organizationId, catId);
            case 6:
                // 计划类属性：6
                return getPlanPropertiesWithCatId(-1, organizationId, catId);
            case 7:
                // 销售类属性：7
                return getSalesPropertiesWithCatId(-1, organizationId, catId);
            case 8:
                // 质量类属性：8
                return getQualityPropertiesWithCatId(-1, organizationId, catId);
            case 9:
                // 财务类属性：9
                return getFinancePropertiesWithCatId(-1, organizationId, catId);
            default:
                return null;
        }
    }

    // ---------------------------------------- 更新控制信息部分 ----------------------------------------

    private int checkList (String[] keyList, String key) {
        for (int i = 0; i < keyList.length; ++i) {
            if (keyList[i].equals(key)) {
                return 1;
            }
        }
        return 0;
    }

    public int updatePurchaseAndStoreProperties (int versionId, int ctrlPropId, String name, String value) {
        String[] keyList = purchaseAndStoreList.getPurchaseAndStoreList();
        int flag = checkList(keyList, name);
        if (flag == 0) {
            return -1;
        }
        return materialInfoMapper.updateCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
    }

    public int updatePlanProperties (int versionId, int ctrlPropId, String name, String value) {
        String[] keyList = planList.getPlanList();
        int flag = checkList(keyList, name);
        if (flag == 0) {
            return -1;
        }
        return materialInfoMapper.updateCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
    }

    public int updateSalesProperties (int versionId, int ctrlPropId, String name, String value) {
        String[] keyList = salesList.getSalesList();
        int flag = checkList(keyList, name);
        if (flag == 0) {
            return -1;
        }
        return materialInfoMapper.updateCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
    }

    public int updateQualityProperties (int versionId, int ctrlPropId, String name, String value) {
        String[] keyList = qualityList.getQualityList();
        int flag = checkList(keyList, name);
        if (flag == 0) {
            return -1;
        }
        return materialInfoMapper.updateCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
    }

    public int updateFinanceProperties (int versionId, int ctrlPropId, String name, String value) {
        String[] keyList = financeList.getFinanceList();
        int flag = checkList(keyList, name);
        if (flag == 0) {
            return -1;
        }
        return materialInfoMapper.updateCtrlPropWithCtrlPropParams(versionId, ctrlPropId, value);
    }

    int updateControlPropertyByTypeAndValue (int type, int organizationCode, String spuCode, String name, String value) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) {
            return 0;
        }
        // 此时应该只有一个分类id
        int materialCatId = baseResult.get(0).getMaterialCatId();
        // 获取物料id
        // List<MaterialModel> materialResult = materialInfoMapper.getMaterialWithSpuCode(spuCode);
        // if (materialResult == null || materialResult.size() == 0) {
        //     return -1;
        // }
        // int materialId = materialResult.get(0).getId();
        // 确认版本号
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", materialCatId);
        // params.put("materialId", materialId);
        params.put("spuCode", spuCode);
        params.put("organizationCode", organizationCode);
        List<MaterialCtrlPropValVerModel> ctrlVerResult = materialInfoMapper.getCtrlPropValVerWithCtrlPropValVerParams(params);
        // 需要确保结果只有一个，若有多个，取第一个
        if (ctrlVerResult == null || ctrlVerResult.size() == 0) {
            // 添加流程
            params.put("version", "B" + (int) (Math.random() * (100000)));
            // 默认间隔10年
            params.put("startDate", new java.sql.Timestamp(System.currentTimeMillis()).toString());
            params.put("endDate", new java.sql.Timestamp(System.currentTimeMillis() + 315360000000L).toString());
            int insertCtrlVerResult = materialInfoMapper.insertCtrlPropValVerWithCtrlPropValVerParams(params);
            logger.info("新添加了属性于materialCtrlPropValVer表，返回结果为：" + insertCtrlVerResult);
            params.clear();
            params.put("materialCatId", materialCatId);
            params.put("spuCode", spuCode);
            params.put("organizationCode", organizationCode);
            ctrlVerResult = materialInfoMapper.getCtrlPropValVerWithCtrlPropValVerParams(params);
        }
        int versionId = ctrlVerResult.get(0).getId();
        // 查找控制属性名对应的id
        params.clear();
        params.put("name", name);
        List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropParams(params);
        int ctrlPropId = ctrlPropResult.get(0).getId();
        params.clear();
        params.put("versionId", versionId);
        params.put("materialCtrlPropId", ctrlPropId);
        List<MaterialCtrlPropValModel> ctrlPropValResult = materialInfoMapper.getCtrlPropValWithCtrlPropValParams(params);
        if (ctrlPropValResult != null && ctrlPropValResult.size() > 0) {
            switch (type) {
                case 5:
                    // 采购和库存属性：5
                    return updatePurchaseAndStoreProperties(versionId, ctrlPropId, name, value);
                case 6:
                    // 计划类属性：6
                    return updatePlanProperties(versionId, ctrlPropId, name, value);
                case 7:
                    // 销售类属性：7
                    return updateSalesProperties(versionId, ctrlPropId, name, value);
                case 8:
                    // 质量类属性：8
                    return updateQualityProperties(versionId, ctrlPropId, name, value);
                case 9:
                    // 财务类属性：9
                    return updateFinanceProperties(versionId, ctrlPropId, name, value);
                default:
                    return 0;
            }
        } else {
            int insertCtrlPropValResult = materialInfoMapper.insertCtrlPropValWithCtrlPropValParams(versionId, ctrlPropId, value);
            logger.info("新添加了属性于materialCtrlPropVal表，返回结果为：" + insertCtrlPropValResult);
            return 0;
        }
    }

    int updateControlPropertyByCatIdAndTypeAndValue (int type, int organizationCode, int catId, String name, String value) {
        // 先查询基于某个物料分类下通用的控制属性
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("materialCatId", catId);
        params.put("spuCode", "-1");
        params.put("organizationCode", organizationCode);
        List<MaterialCtrlPropValVerModel> ctrlVerResult = materialInfoMapper.getCtrlPropValVerWithCtrlPropValVerParams(params);
        // 需要确保结果只有一个，若有多个，取第一个
        int versionId = ctrlVerResult.get(0).getId();
        // 查找控制属性名对应的id
        params.clear();
        params.put("name", name);
        List<MaterialCtrlPropModel> ctrlPropResult = materialInfoMapper.getCtrlPropWithCtrlPropParams(params);
        int ctrlPropId = ctrlPropResult.get(0).getId();
        switch (type) {
            case 5:
                // 采购和库存属性：5
                return updatePurchaseAndStoreProperties(versionId, ctrlPropId, name, value);
            case 6:
                // 计划类属性：6
                return updatePlanProperties(versionId, ctrlPropId, name, value);
            case 7:
                // 销售类属性：7
                return updateSalesProperties(versionId, ctrlPropId, name, value);
            case 8:
                // 质量类属性：8
                return updateQualityProperties(versionId, ctrlPropId, name, value);
            case 9:
                // 财务类属性：9
                return updateFinanceProperties(versionId, ctrlPropId, name, value);
            default:
                return 0;
        }
    }
}
