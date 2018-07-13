package com.zhuri.material.materialmanagement.service;

import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuri.material.materialmanagement.bean.*;
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
                    String[] catBaseTargetList = {"materialCatId"};
                    Map<String, Object> categoryBaseMap = MaterialInfoServiceSupplier.splitBaseInfoParams(params, catBaseKeyList, catBaseTargetList);
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
            String[] materialTargetList = {"materialCode"};
            Map<String, Object> materialMap = MaterialInfoServiceSupplier.splitMaterialParams(params, materialKeyList, materialTargetList);
            List<String> spuCodeFromMaterial = null;
            if (materialMap.size() > 0) {
                List<MaterialBean> materialResult = materialInfoMapper.getMaterialWithMaterialParams(materialMap);
                spuCodeFromMaterial = materialResult.stream()
                                        .map(material -> material.getSpuCode())
                                        .distinct()
                                        .collect(Collectors.toList());
            }
            // 对spuCode去重
            HashSet<String> spuCodes = new HashSet<>();
            spuCodes.clear();
            if (spuCodeFromBase != null) { spuCodes.addAll(spuCodeFromBase); }
            if (spuCodeFromCategory != null) { spuCodes.addAll(spuCodeFromCategory); }
            if (spuCodeFromMaterial != null) { spuCodes.addAll(spuCodeFromMaterial); }
            List<MaterialBaseBean> result = new ArrayList<>();
            result.clear();
            for (String spuCode : spuCodes) {
                result.addAll(materialInfoMapper.getBaseInfoWithSpuCode(spuCode));
            }
            result.sort((o1, o2) -> {
                return o1.getId() - o2.getId();
            });
            return result;
        }
    }
}
