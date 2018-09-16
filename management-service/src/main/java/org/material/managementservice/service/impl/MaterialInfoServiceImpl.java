package org.material.managementservice.service.impl;

import org.material.managementservice.mapper.MaterialInfoMapper;
import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.tablemodel.*;
import org.material.managementfacade.service.MaterialInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MaterialInfoServiceImpl implements MaterialInfoService {
    @Autowired
    private MaterialInfoMapper materialInfoMapper;
    @Autowired
    private MaterialInfoServiceImplSupplier materialInfoServiceImplSupplier;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    @Override
    public List<Object> getAllBaseInfo () {
        List<MaterialBaseModel> baseResult = materialInfoMapper.getAllBaseInfo();
        List<Object> result = new ArrayList<>();
        result.add(baseResult);
        List<MaterialCategoryModel> catResult = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        catResult.clear();
        List<UnitModel> unitResult = new ArrayList<>();
        unitResult.clear();
        for (MaterialBaseModel element : baseResult) {
            List<MaterialCategoryModel> tmp;
            List<UnitModel> tmpUnit;
            params.clear();
            params.put("id", element.getMaterialCatId());
            tmp = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(params);
            if (tmp.size() > 0) {
                catResult.addAll(tmp);
            } else {
                catResult.add(new MaterialCategoryModel());
            }
            params.clear();
            params.put("id", element.getDefaultUnitId());
            tmpUnit = materialInfoMapper.getUnitWithUnitParams(params);
            if (tmpUnit.size() > 0) {
                unitResult.addAll(tmpUnit);
            } else {
                unitResult.add(new UnitModel());
            }
        }
        result.add(catResult);
        result.add(unitResult);
        return result;
    }

    @Override
    public List<Object> getBaseInfoByParams (Map<String, Object> params) {
        String checkKey = "spuCode";
        List<MaterialBaseModel> baseResult;
        if (params.containsKey(checkKey)) {
            String spuCode = params.get("spuCode").toString();
            baseResult = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        } else {
            // 物料类型，助记码，SPU名称，物料描述，得到一个SPU Code List
            String[] baseInfoKeyList = {"materialType", "mnemonic", "spuName", "description"};
            String[] baseInfoTargetList = {"type", "mnemonic", "spuName", "description"};
            Map<String, Object> baseInfoMap = materialInfoServiceImplSupplier.splitBaseInfoParams(params, baseInfoKeyList, baseInfoTargetList);
            List<String> spuCodeFromBase = null;
            if (baseInfoMap.size() > 0) {
                List<MaterialBaseModel> baseInfoResult = materialInfoMapper.getBaseInfoWithBaseInfoParams(baseInfoMap);
                spuCodeFromBase = baseInfoResult.stream()
                        .map(base -> base.getSpuCode())
                        .distinct()
                        .collect(Collectors.toList());
            }
            // 对于可能的物料分类名称，先查出对应的物料分类ID
            String[] categoryKeyList = {"materialCategory"};
            String[] categoryTargetList = {"name"};
            Map<String, Object> categoryMap = materialInfoServiceImplSupplier.splitCategoryParams(params, categoryKeyList, categoryTargetList);
            List<Integer> materialCatIdList = null;
            if (categoryMap.size() > 0) {
                List<MaterialCategoryModel> categoryResult = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap);
                materialCatIdList = categoryResult.stream()
                        .map(cate -> cate.getId())
                        .distinct()
                        .collect(Collectors.toList());
            }
            // 再获取对应的spuCode
            List<String> spuCodeFromCategory = null;
            if (materialCatIdList != null) {
                // 必须保证非空
                if (materialCatIdList.size() > 1) {
                    // 有重复记录报错
                    return null;
                } else {
                    // 否则正常处理
                    String[] catBaseKeyList = {"materialCatId"};
                    Map<String, Object> categoryBaseMap = materialInfoServiceImplSupplier.splitBaseInfoParams(params, catBaseKeyList, catBaseKeyList);
                    if (categoryBaseMap.size() > 0) {
                        List<MaterialBaseModel> cateInfoResult = materialInfoMapper.getBaseInfoWithBaseInfoParams(categoryBaseMap);
                        spuCodeFromCategory = cateInfoResult.stream()
                                .map(base -> base.getSpuCode())
                                .distinct()
                                .collect(Collectors.toList());
                    }
                }
            }

            // 对于物料编码，先去material表中查询
            String[] materialKeyList = {"materialCode"};
            Map<String, Object> materialMap = materialInfoServiceImplSupplier.splitMaterialParams(params, materialKeyList, materialKeyList);
            List<String> spuCodeFromMaterial = null;
            if (materialMap.size() > 0) {
                List<MaterialModel> materialResult = materialInfoMapper.getMaterialWithMaterialParams(materialMap);
                spuCodeFromMaterial = materialResult.stream()
                        .map(material -> material.getSpuCode())
                        .distinct()
                        .collect(Collectors.toList());
            }

            // 最后对spuCode去重
            HashSet<String> spuCodes = new HashSet<>();
            List<List<String>> spuCodesLists = new ArrayList<>();
            spuCodes.clear();
            spuCodesLists.clear();
            if (spuCodeFromBase != null) {
                spuCodesLists.add(spuCodeFromBase);
            }
            if (spuCodeFromCategory != null) {
                spuCodesLists.add(spuCodeFromCategory);
            }
            if (spuCodeFromMaterial != null) {
                spuCodesLists.add(spuCodeFromMaterial);
            }
            baseResult = new ArrayList<>();
            baseResult.clear();
            // 一般不可能出现三个均为空的情况，但是保险起见，若为空则返回空列表
            if (spuCodesLists.size() > 0) {
                spuCodes.addAll(spuCodesLists.get(0));
                for (int i = 1; i < spuCodesLists.size(); ++i) {
                    spuCodes.retainAll(spuCodesLists.get(i));
                }
                for (String spuCode : spuCodes) {
                    baseResult.addAll(materialInfoMapper.getBaseInfoWithSpuCode(spuCode));
                }
                baseResult.sort(Comparator.comparingInt(MaterialBaseModel::getId));
            }
        }
        List<Object> result = new ArrayList<>();
        result.add(baseResult);
        List<MaterialCategoryModel> catResult = new ArrayList<>();
        catResult.clear();
        List<UnitModel> unitResult = new ArrayList<>();
        unitResult.clear();
        for (MaterialBaseModel element : baseResult) {
            List<MaterialCategoryModel> tmp;
            List<UnitModel> tmpUnit;
            params.clear();
            params.put("id", element.getMaterialCatId());
            tmp = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(params);
            if (tmp.size() > 0) {
                catResult.addAll(tmp);
            } else {
                catResult.add(new MaterialCategoryModel());
            }
            params.clear();
            params.put("id", element.getDefaultUnitId());
            tmpUnit = materialInfoMapper.getUnitWithUnitParams(params);
            if (tmpUnit.size() > 0) {
                unitResult.addAll(tmpUnit);
            } else {
                unitResult.add(new UnitModel());
            }
        }
        result.add(catResult);
        result.add(unitResult);
        return result;
    }

    /*
        物料基本信息：1
        物料定义：2
        SKU定义：3
        附件管理：4 （依赖于物料基本信息Id）
        采购和库存属性：5
        计划类属性：6
        销售类属性：7
        质量类属性：8
        财务类属性：9
        计量单位：10
        全部基础信息：11
        规格信息：12
    */
    @Override
    public List<Object> getMaterialInfo (String spuCode, String spuName, List<Integer> types, int organizationId) {
        int maxTypeNum = 11;
        int[] flag = new int[maxTypeNum + 1];
        Arrays.fill(flag, 0);
        for (int type : types) {
            flag[type] = 1;
        }
        List<Object> result = new ArrayList<>();
        Map<String, Object> paramsMap;
        // 缓存物料基本信息id
        int materialBaseId = -1;
        for (int i = 1; i <= maxTypeNum; ++i) {
            if (flag[i] == 0) {
                continue;
            }
            switch (i) {
                case 1:
                    List<MaterialBaseModel> baseInfos = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
                    // 预设SPU编码必须唯一！
                    if (baseInfos != null && baseInfos.size() > 0) {
                        materialBaseId = baseInfos.get(0).getId();
                        result.add(baseInfos);
                    }
                    break;
                case 2:
                    // 物料定义针对物料信息表
                    // TODO: 对于物料定义中的物料规格暂时没得到答复，暂时忽略规格信息，全部以空信息返回
                    paramsMap = new HashMap<>(16);
                    paramsMap.put("spuCode", spuCode);
                    List<MaterialModel> materialInfos = materialInfoMapper.getMaterialWithMaterialParams(paramsMap);
                    if (materialInfos != null && materialInfos.size() > 0) {
                        result.add(materialInfos);
                    } else {
                        result.add(new ArrayList<>());
                    }
                    break;
                case 3:
                    paramsMap = new HashMap<>(16);
                    paramsMap.put("spuCode", spuCode);
                    List<Object> skuInfos = materialInfoServiceImplSupplier.getMaterialSkuWithMaterialSkuParams(paramsMap);
                    if (skuInfos != null && skuInfos.size() > 0) {
                        result.add(skuInfos);
                    } else {
                        result.add(new ArrayList<>());
                    }
                    break;
                case 4:
                    if (materialBaseId == -1) {
                        // 没有查到过物料信息ID
                        List<MaterialBaseModel> baseInfoForFiles = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
                        if (baseInfoForFiles != null && baseInfoForFiles.size() > 0) {
                            // 有对应的记录
                            materialBaseId = baseInfoForFiles.get(0).getId();
                        } else {
                            // 若没有返回空数组
                            result.add(new ArrayList<>());
                            break;
                        }
                    }
                    // 根据物料基本信息id进行查找
                    paramsMap = new HashMap<>(16);
                    paramsMap.put("materialBaseId", materialBaseId);
                    List<MaterialFilesModel> fileInfos = materialInfoMapper.getFilesWithFilesParams(paramsMap);
                    if (fileInfos != null && fileInfos.size() > 0) {
                        result.add(fileInfos);
                    } else {
                        result.add(new ArrayList<>());
                    }
                    break;
                case 10:
                    // 计量单位
                    // 包括默认计量单位
                    List<Object> fileUnits = materialInfoServiceImplSupplier.getAllUnitsBySpuCode(spuCode);
                    if (fileUnits != null && fileUnits.size() > 0) {
                        result.add(fileUnits);
                    } else {
                        result.add(new ArrayList<>());
                    }
                    break;
                case 11:
                    // 基本属性信息
                    List<Object> baseProperty = materialInfoServiceImplSupplier.getAllMaterialBaseProp(spuCode);
                    // List<Object> standardProperty = materialInfoServiceImplSupplier.getMaterialBasePropBySpuCodeAndType(spuCode, 4);
                    if (baseProperty != null && baseProperty.size() > 0) {
                        result.add(baseProperty);
                    } else {
                        result.add(new ArrayList<>());
                    }
                    break;
                case 12:
                    // 规格信息
                    List<Object> standardProperty = materialInfoServiceImplSupplier.getMaterialBasePropBySpuCodeAndType(spuCode, 4);
                    if (standardProperty != null && standardProperty.size() > 0) {
                        result.add(standardProperty);
                    } else {
                        result.add(new ArrayList<>());
                    }
                    break;
                default:
                    List<ControlPropertyBean> controlProps = materialInfoServiceImplSupplier.getAllControlPropertyByType(i, organizationId, spuCode);
                    if (controlProps.size() > 0) {
                        result.add(controlProps);
                    }
                    break;
            }
        }
        return result;
    }

    /*
        物料基本信息：1
        物料定义：2
        SKU定义：3
        附件管理：4 （依赖于物料基本信息Id）
        采购和库存属性：5
        计划类属性：6
        销售类属性：7
        质量类属性：8
        财务类属性：9
        计量单位：10
        规格信息：11
    */
    @Override
    public int updateMaterialInfo (String spuCode, String spuName, List<Object> data) {
        int result = 1;
        int tmpresult = 0;
        for (Object element : data) {
            Map<String, Object> needUpdate = (Map<String, Object>) element;
            int propertyType = Integer.parseInt(needUpdate.get("propertyType").toString());
            logger.debug("propertyType = " + propertyType);
            if (propertyType == 11) {
                List<Object> updateValue = (List<Object>) needUpdate.get("updateValue");
                tmpresult = materialInfoServiceImplSupplier.updateMaterialBasePropBySpuCode(spuCode, 4, updateValue);
            } else if (propertyType == 10) {
                // 计量单位
                // 查询当前的更新信息是否在unit表中有对应记录
                List<Object> updateValue = (List<Object>) needUpdate.get("updateValue");
                tmpresult = materialInfoServiceImplSupplier.updateUnitsBySpuCode(spuCode, updateValue);
            } else {
                List<Map<String, Object>> updateValue = (List<Map<String, Object>>) needUpdate.get("updateValue");
                for (Map<String, Object> kvPairs : updateValue) {
                    String name = null, value = null;
                    if (kvPairs.containsKey("name")) {
                        name = kvPairs.get("name").toString();
                        logger.debug("name = " + name);
                    }
                    if (kvPairs.containsKey("value")) {
                        value = kvPairs.get("value").toString();
                        logger.debug("value = " + value);
                    }
                    switch (propertyType) {
                        case 1:
                            // 物料基本信息
                            List<MaterialBaseModel> baseTmp = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
                            if (!(baseTmp != null && !baseTmp.isEmpty())) {
                                tmpresult = materialInfoMapper.createBaseWithSpuCode(spuCode);
                                logger.info("spuCode = " + spuCode + "的默认记录由于不存在被创建。");
                            }
                            tmpresult = materialInfoMapper.updateBaseInfoWithBaseInfoParams(spuCode, name, value);
                            break;
                        case 2:
                            // 物料定义
                            tmpresult = materialInfoServiceImplSupplier.updateMaterialWithMaterialList(spuCode, updateValue);
                            break;
                        case 3:
                            // SKU定义
                            tmpresult = materialInfoServiceImplSupplier.updateMaterialSkuWithMaterialSkuList(spuCode, updateValue);
                            break;
                        case 4:
                            // 附件信息
                            List<MaterialBaseModel> baseModels = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
                            int baseId = baseModels.get(0).getId();
                            tmpresult = materialInfoMapper.updateMaterialFilesWithMaterialFilesParams(baseId, name, value);
                            break;
                        default:
                            // 5 - 9 控制信息
                            // 先获取materialBaseId
                            if (needUpdate.containsKey("organizationCode")) {
                                int organizationCode = Integer.parseInt(needUpdate.get("organizationCode").toString());
                                tmpresult = materialInfoServiceImplSupplier.updateControlPropertyByTypeAndValue(propertyType, organizationCode, spuCode, name, value);
                            } else {
                                tmpresult = 0;
                            }
                            break;
                    }
                    logger.debug("此次操作更新了" + tmpresult + "行。");
                    result = Math.min(result, result * tmpresult);
                }
            }
        }
        return result;
    }

    @Override
    public MaterialCategoryTree getMaterialCategory () {
        // 构造一个HashMap用于通过id获取对象
        Map<Integer, MaterialCategoryTree> idCategoryMap = new HashMap<>();
        idCategoryMap.clear();
        MaterialCategoryTree root = new MaterialCategoryTree(0, "物料总分类", -1, 0);
        idCategoryMap.put(root.getId(), root);
        Map<String, Object> categoryMap = new HashMap<String, Object>();
        categoryMap.clear();
        List<MaterialCategoryModel> materialCategoryList = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap);
        for (MaterialCategoryModel materialCategory : materialCategoryList) {
            int id = materialCategory.getId();
            String name = materialCategory.getName();
            int parentId = materialCategory.getParentId();
            MaterialCategoryTree parent = idCategoryMap.get(parentId);
            int level = parent.getLevel() + 1;
            MaterialCategoryTree node = new MaterialCategoryTree(id, name, parentId, level);
            idCategoryMap.put(id, node);
            parent.addChild(node);
        }
        return root;
    }

    @Override
    public int addMaterialCategory (String code, String name, int parentId) {
        //确保原数据表中无当前添加的code
        int count = 0;
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.clear();
        categoryMap.put("code", code);
        count = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap).size();
        if (count != 0) {
            return 0;
        } else {
            categoryMap.clear();
        }
        //parentId必须为0或数据库中已有id
        if (parentId != 0) {
            categoryMap.put("id", parentId);
            count = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap).size();
            if (count <= 0) {
                return 0;
            } else {
                categoryMap.clear();
            }
        }
        //name不可与父类目录下其他记录name值相同
        categoryMap.put("name", name);
        categoryMap.put("parentId", parentId);
        count = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap).size();
        if (count != 0) {
            return 0;
        } else {
            return materialInfoMapper.addMaterialCategory(code, name, parentId);
        }
    }

    @Override
    public int updateMaterialCategory (String newName, String oldName, int parentId) {
        //根据原物料名称及父id，确认唯一一条记录
        Map<String, Object> categoryMap = new HashMap<String, Object>();
        categoryMap.clear();
        categoryMap.put("name", oldName);
        categoryMap.put("parentId", parentId);
        int count = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap).size();
        if (count != 1) {
            return 0;
        } else {
            categoryMap.clear();
        }
        //根据新物料分类名称和父id，确认无重复记录
        categoryMap.put("name", newName);
        categoryMap.put("parentId", parentId);
        count = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap).size();
        if (count != 0) {
            return 0;
        } else {
            return materialInfoMapper.updateMaterialCategory(newName, oldName, parentId);
        }
    }

    @Override
    public int deleteMaterialCategory (int id, String code, String name, int parentId) {
        //传入值必须唯一确定一条记录
        //根据id与parentId关系将对应记录及其子类记录全部删除
        int index = 0;
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.clear();
        categoryMap.put("id", id);
        categoryMap.put("code", code);
        categoryMap.put("name", name);
        categoryMap.put("parentId", parentId);
        List<MaterialCategoryModel> materialCategoryList = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap);
        if (materialCategoryList.size() == 1) {
            categoryMap.remove("id");
            categoryMap.remove("code");
            categoryMap.remove("name");
            while (index < materialCategoryList.size()) {
                MaterialCategoryModel tmp = materialCategoryList.get(index);
                categoryMap.replace("parentId", tmp.getId());
                materialCategoryList.addAll(materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap));
                materialInfoMapper.deleteMaterialCategoryByNameAndParentId(tmp.getName(), tmp.getParentId());
                index++;
            }
        }
        return index;
    }

    @Override
    public List<Object> getAllMaterialBaseByCategoryInfos (int id) {
        Map<String, Object> paramsMap = new HashMap<>(16);
        paramsMap.clear();
        paramsMap.put("materialCatId", id);
        List<MaterialBaseModel> materialBaseResult = materialInfoMapper.getBaseInfoWithBaseInfoParams(paramsMap);
        paramsMap.clear();
        paramsMap.put("id", id);
        List<MaterialCategoryModel> catResult = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(paramsMap);
        List<Object> result = new ArrayList<>();
        result.add(materialBaseResult);
        result.add(catResult);
        return result;
    }

    @Override
    public List<Object> getMaterialCategoryInfosWithId (int id) {
        List<Object> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("id", new Integer(id).toString());
        List<MaterialCategoryModel> materialCategoryResult = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(params);
        result.add(materialCategoryResult);
        return result;
    }

    /*
        采购和库存属性：5
        计划类属性：6
        销售类属性：7
        质量类属性：8
        财务类属性：9
        全部基础信息：11
    */
    @Override
    public List<Object> getMaterialInfoWithCatCodeAndCatName (int catCode, String catName, List<Integer> types, int organizationId) {
        List<Object> result = new ArrayList<>();
        result.clear();
        Map<String, Object> params = new HashMap<>();
        int maxTypeNum = 11;
        int catId = 0;
        int[] flag = new int[maxTypeNum + 1];
        Arrays.fill(flag, 0);
        for (int type : types) {
            flag[type] = 1;
        }
        params.clear();
        params.put("code", catCode);
        List<MaterialCategoryModel> materialBaseTmp = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(params);
        if (materialBaseTmp != null && materialBaseTmp.size() > 0) {
            catId = materialBaseTmp.get(0).getId();
            logger.info("物料分类编码为" + catCode + "的记录对应的物料分类id为：" + catId);
            for (int i = 5; i <= maxTypeNum; ++i) {
                if (flag[i] == 0) {
                    continue;
                }
                switch (i) {
                    case 11:
                        // 基础信息
                        List<Object> baseProp = materialInfoServiceImplSupplier.getAllMaterialBaseByCatId(catId);
                        if (baseProp != null && baseProp.size() > 0) {
                            result.add(baseProp);
                            logger.info("成功找到物料基本属性！");
                        } else {
                            logger.info("寻找物料基本属性失败！");
                            result.add(new ArrayList<>());
                        }
                        break;
                    default:
                        // 6-10
                        // 控制类属性
                        List<ControlPropertyBean> controlProps = materialInfoServiceImplSupplier.getControlPropsByCatIdAndType(catId, i, organizationId);
                        if (controlProps != null && controlProps.size() > 0) {
                            result.add(controlProps);
                        }
                        break;
                }
            }
        } else {
            logger.info("物料分类编码为" + catCode + "的记录在数据库中不存在对应的数据！");
            for (int i = 5; i <= maxTypeNum; ++i) {
                if (flag[i] == 0) {
                    continue;
                }
                result.add(new ArrayList<>());
            }
        }
        return result;
    }

    @Override
    public int updateMaterialInfoWithCatCodeAndCatName (String catCode, String catName, List<Object> data) {
        int result = 1;
        int tmpresult = 0;
        int catId = 0;
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("code", catCode);
        List<MaterialCategoryModel> materialBaseTmp = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(params);
        if (materialBaseTmp != null && materialBaseTmp.size() > 0) {
            catId = materialBaseTmp.get(0).getId();
            logger.info("物料分类编码为" + catCode + "的记录对应的物料分类id为：" + catId);
            for (Object element : data) {
                Map<String, Object> needUpdate = (Map<String, Object>) element;
                int propertyType = Integer.parseInt(needUpdate.get("propertyType").toString());
                logger.debug("propertyType = " + propertyType);
                if (propertyType == 11) {
                    List<Object> updateValue = (List<Object>) needUpdate.get("updateValue");
                    tmpresult = materialInfoServiceImplSupplier.updateMaterialBasePropByCatId(catId, updateValue);
                } else {
                    List<Map<String, Object>> updateValue = (List<Map<String, Object>>) needUpdate.get("updateValue");
                    for (Map<String, Object> kvPairs : updateValue) {
                        String name = null, value = null;
                        if (kvPairs.containsKey("name")) {
                            name = kvPairs.get("name").toString();
                            logger.debug("name = " + name);
                        }
                        if (kvPairs.containsKey("value")) {
                            value = kvPairs.get("value").toString();
                            logger.debug("value = " + value);
                        }
                        if (needUpdate.containsKey("organizationCode")) {
                            int organizationCode = Integer.parseInt(needUpdate.get("organizationCode").toString());
                            tmpresult = materialInfoServiceImplSupplier.updateControlPropertyByCatIdAndTypeAndValue(propertyType, organizationCode, catId, name, value);
                        } else {
                            tmpresult = 0;
                        }
                    }
                    logger.debug("此次操作类型为：" + propertyType + "，物料分类编码为：" + catCode + "，返回结果为：" + tmpresult + "。");
                }
                result = Math.min(result, result * tmpresult);
            }
        } else {
            logger.info("物料分类编码为" + catCode + "的记录在数据库中不存在对应的数据！");
            result = 0;
        }
        return result;
    }
}
