package com.zhuri.material.materialmanagement.service;

import java.util.*;
import java.util.stream.Collectors;

import com.zhuri.material.materialmanagement.bean.tablebean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuri.material.materialmanagement.mapper.MaterialInfoMapper;
import com.zhuri.material.materialmanagement.service.supplier.MaterialInfoServiceSupplier;

@Service
public class MaterialInfoService {
    @Autowired
    MaterialInfoMapper materialInfoMapper;

    public List<MaterialBaseBean> getBaseInfoByParams (Map<String, Object> params) {
        String checkKey = "spuCode";
        if (params.containsKey(checkKey)) {
            String spuCode = params.get("spuCode").toString();
            return materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
        } else {
            // 物料类型，助记码，SPU名称，物料描述，得到一个SPU Code List
            String[] baseInfoKeyList = {"materialType", "mnemonic", "spuName", "description"};
            String[] baseInfoTargetList = {"type", "mnemonic", "spuName", "description"};
            Map<String, Object> baseInfoMap = MaterialInfoServiceSupplier.splitBaseInfoParams(params, baseInfoKeyList, baseInfoTargetList);
            List<String> spuCodeFromBase = null;
            if (baseInfoMap.size() > 0) {
                List<MaterialBaseBean> baseInfoResult = materialInfoMapper.getBaseInfoWithBaseInfoParams(baseInfoMap);
                spuCodeFromBase = baseInfoResult.stream()
                                        .map(base -> base.getSpuCode())
                                        .distinct()
                                        .collect(Collectors.toList());
            }
            // 对于可能的物料分类名称，先查出对应的物料分类ID
            String[] categoryKeyList = {"materialCategory"};
            String[] categoryTargetList = {"name"};
            Map<String, Object> categoryMap = MaterialInfoServiceSupplier.splitCategoryParams(params, categoryKeyList, categoryTargetList);
            List<Integer> materialCatIdList = null;
            if (categoryMap.size() > 0) {
                List<MaterialCategoryBean> categoryResult = materialInfoMapper.getMaterialCategoryWithMaterialCategoryParams(categoryMap);
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
                    Map<String, Object> categoryBaseMap = MaterialInfoServiceSupplier.splitBaseInfoParams(params, catBaseKeyList, catBaseKeyList);
                    if (categoryBaseMap.size() > 0) {
                        List<MaterialBaseBean> cateInfoResult = materialInfoMapper.getBaseInfoWithBaseInfoParams(categoryBaseMap);
                        spuCodeFromCategory = cateInfoResult.stream()
                                .map(base -> base.getSpuCode())
                                .distinct()
                                .collect(Collectors.toList());
                    }
                }
            }

            // 对于物料编码，先去material表中查询
            String[] materialKeyList = {"materialCode"};
            Map<String, Object> materialMap = MaterialInfoServiceSupplier.splitMaterialParams(params, materialKeyList, materialKeyList);
            List<String> spuCodeFromMaterial = null;
            if (materialMap.size() > 0) {
                List<MaterialBean> materialResult = materialInfoMapper.getMaterialWithMaterialParams(materialMap);
                spuCodeFromMaterial = materialResult.stream()
                                        .map(material -> material.getSpuCode())
                                        .distinct()
                                        .collect(Collectors.toList());
            }

            // 最后对spuCode去重
            HashSet<String> spuCodes = new HashSet<>();
            List<List<String>> spuCodesLists = new ArrayList<>();
            spuCodes.clear(); spuCodesLists.clear();
            if (spuCodeFromBase != null) { spuCodesLists.add(spuCodeFromBase); }
            if (spuCodeFromCategory != null) { spuCodesLists.add(spuCodeFromCategory); }
            if (spuCodeFromMaterial != null) { spuCodesLists.add(spuCodeFromMaterial); }
            List<MaterialBaseBean> result = new ArrayList<>();
            result.clear();
            // 一般不可能出现三个均为空的情况，但是保险起见，若为空则返回空列表
            if (spuCodesLists.size() > 0) {
                spuCodes.addAll(spuCodesLists.get(0));
                for (int i = 1; i < spuCodesLists.size(); ++i) {
                    spuCodes.retainAll(spuCodesLists.get(i));
                }
                for (String spuCode : spuCodes) {
                    result.addAll(materialInfoMapper.getBaseInfoWithSpuCode(spuCode));
                }
                result.sort(Comparator.comparingInt(MaterialBaseBean::getId));
            }
            return result;
        }
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
    */
    public List<Object> getMaterialInfo (String spuCode, String spuName, List<Integer> types) {
        int[] flag = new int[10];
        Arrays.fill(flag, 0);
        for (int type : types) {
            flag[type] = 1;
        }
        List<Object> result = new ArrayList<>();
        Map<String, Object> paramsMap;
        // 缓存物料基本信息id
        int materialBaseId = -1;
        for (int i = 1; i < 10; ++i) {
            switch (i) {
                case 1:
                    List<MaterialBaseBean> baseInfos = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
                    // 预设SPU编码必须唯一！
                    if (baseInfos != null && baseInfos.size() > 0) {
                        materialBaseId = baseInfos.get(0).getMaterialCatId();
                        result.add(baseInfos);
                    }
                    break;
                case 2:
                    // 物料定义针对物料信息表
                    // TODO: 对于物料定义中的物料规格暂时没得到答复，暂时忽略规格信息，全部以空信息返回
                    paramsMap = new HashMap<>(16);
                    paramsMap.put("spuCode", 1);
                    List<MaterialBean> materialInfos = materialInfoMapper.getMaterialWithMaterialParams(paramsMap);
                    if (materialInfos != null && materialInfos.size() > 0) {
                        result.add(materialInfos);
                    }
                    break;
                case 3:
                    paramsMap = new HashMap<>(16);
                    paramsMap.put("spuCode", 1);
                    List<MaterialSkuBean> skuInfos = materialInfoMapper.getMaterialSkuWithMaterialSkuParams(paramsMap);
                    if (skuInfos != null && skuInfos.size() > 0) {
                        result.add(skuInfos);
                    }
                    break;
                case 4:
                    if (materialBaseId == -1) {
                        // 没有查到过物料信息ID
                        List<MaterialBaseBean> baseInfoForFiles = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
                        if (baseInfoForFiles != null && baseInfoForFiles.size() > 0) {
                            // 有对应的记录
                            materialBaseId = baseInfoForFiles.get(0).getMaterialCatId();
                            // 根据物料基本信息id进行查找
                            paramsMap = new HashMap<>(16);
                            paramsMap.put("materialBaseId", 1);
                            List<MaterialFilesBean> fileInfos = materialInfoMapper.getFilesWithFilesParams(paramsMap);
                            if (fileInfos != null && fileInfos.size() > 0) {
                                result.add(fileInfos);
                            }
                        } else {
                            // 若没有返回空数组
                            result.add(new ArrayList<>());
                        }
                    }
                    break;
                case 5:
                    
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
