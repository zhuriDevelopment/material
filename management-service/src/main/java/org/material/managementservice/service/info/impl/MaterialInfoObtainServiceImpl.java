package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.requestmodel.BaseInfoRequest;
import org.material.managementfacade.model.responsemodel.BaseInfoResponse;
import org.material.managementfacade.model.tablemodel.*;
import org.material.managementfacade.service.info.MaterialInfoObtainService;
import org.material.managementservice.mapper.general.MaterialGeneralMapper;
import org.material.managementservice.mapper.info.MaterialInfoObtainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author cplayer on 2019-02-25 02:37.
* @version 1.0 
*        
* 物料信息获取的服务实现接口
*
*/
public class MaterialInfoObtainServiceImpl implements MaterialInfoObtainService {
    @Autowired
    private MaterialInfoObtainMapper materialInfoObtainMapper;
    @Autowired
    private MaterialGeneralMapper materialGeneralMapper;
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 获取所有物料基本信息
     *
     * @author cplayer
     * @date 2019-02-25 02:32
     * @return BaseInfoResponse类，包含所有物料基本信息
     *
     */
    @Override
    public BaseInfoResponse getAllBaseInfo () {
        // 获取所有物料基本信息
        List<MaterialBaseModel> baseResult = materialInfoObtainMapper.getAllBaseInfo();
        BaseInfoResponse result = new BaseInfoResponse();
        result.setBaseResult(baseResult);
        List<MaterialCategoryModel> catResult = new ArrayList<>();
        catResult.clear();
        List<UnitModel> unitResult = new ArrayList<>();
        unitResult.clear();
        // 获取每一条物料基本信息对应的物料分类信息和计量单位信息
        for (MaterialBaseModel element : baseResult) {
            List<MaterialCategoryModel> tmp;
            List<UnitModel> tmpUnit;
            MaterialCategoryModel paramCategory = new MaterialCategoryModel();
            paramCategory.setId(element.getMaterialCatId());
            tmp = materialGeneralMapper.getMaterialCategoryWithMaterialCategoryParams(paramCategory);
            if (tmp.size() > 0) {
                catResult.addAll(tmp);
            } else {
                catResult.add(new MaterialCategoryModel());
            }
            UnitModel paramUnit = new UnitModel();
            paramUnit.setId(element.getDefaultUnitId());
            tmpUnit = materialGeneralMapper.getUnitWithUnitParams(paramUnit);
            if (tmpUnit.size() > 0) {
                unitResult.addAll(tmpUnit);
            } else {
                unitResult.add(new UnitModel());
            }
        }
        result.setCatResult(catResult);
        result.setUnitResult(unitResult);
        return result;
    }

    /**
     * 根据给定参数获取物料基本信息
     *
     * @author cplayer
     * @date 2019-02-25 03:25
     * @param params 所给的基本信息参数
     *               包含：
     *               物料分类
     *               物料名称
     *               sku编码
     *               起始时间
     *               终止时间
     *               spu编码
     *               设计图号
     *               设计版本
     *               来源
     *
     * @return org.material.managementfacade.model.responsemodel.BaseInfoResponse
     *
     */
    @Override
    public BaseInfoResponse getBaseInfoByParams (BaseInfoRequest params) {
        BaseInfoResponse result = new BaseInfoResponse();
        List<MaterialBaseModel> baseResult;
        if (params.getSpuCode() != null) {
            // 若有spuCode，则按spuCode获取参数
            String spuCode = params.getSpuCode();
            baseResult = materialInfoObtainMapper.getBaseInfoWithSpuCode(spuCode);
        } else {
            // 物料类型，助记码，SPU名称，物料描述，得到一个SPU Code List
            MaterialBaseModel tmpParam = new MaterialBaseModel();
            tmpParam.setType(params.getMaterialType());


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

}
