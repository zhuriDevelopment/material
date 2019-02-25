package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.requestmodel.BaseInfoRequest;
import org.material.managementfacade.model.requestmodel.MaterialInfoRequest;
import org.material.managementfacade.model.responsemodel.BaseInfoResponse;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoResponse;
import org.material.managementfacade.model.tablemodel.*;
import org.material.managementfacade.service.info.InfoObtainService;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.InfoObtainServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.baseinfo.BaseInfoObtainServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.materialinfo.MaterialInfoObtainServiceSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author cplayer on 2019-02-25 02:37.
* @version 1.0 
*        
* 物料信息获取的服务实现接口
*
*/

@Component
public class InfoObtainServiceImpl implements InfoObtainService {
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainServiceSupplier infoObtainServiceSupplier;
    @Autowired
    private BaseInfoObtainServiceSupplier baseInfoObtainServiceSupplier;
    @Autowired
    private MaterialInfoObtainServiceSupplier materialInfoObtainServiceSupplier;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    public Object test () {
        List<MaterialBaseModel> list1 = new ArrayList<>();
        MaterialBaseModel ele1 = new MaterialBaseModel();
        MaterialBaseModel ele2 = new MaterialBaseModel();
        MaterialBaseModel ele3 = new MaterialBaseModel();
        ele1.setSpuCode("10001");
        list1.add(ele1);
        ele2.setSpuCode("10003");
        list1.add(ele2);
        List<MaterialBaseModel> list2 = new ArrayList<>();
        ele3.setSpuCode("10001");
        list2.add(ele3);
        return infoObtainServiceSupplier.addAllListSpuCode(list1, list2);
    }

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
        List<MaterialBaseModel> baseResult = infoObtainMapper.getAllBaseInfo();
        return baseInfoObtainServiceSupplier.getBaseInfoAllParams(baseResult);
    }

    /**
     * 根据给定参数获取物料基本信息
     *
     * materialBase表：物料分类、spu编码、设计图号、设计版本、来源、起始时间、终止时间
     * material表：物料名称
     * materialSku表：sku编码
     *
     * @author cplayer
     * @date 2019-02-25 03:25
     * @param params 所给的基本信息参数
     *               包含：
     *               物料分类（以id的形式传过来）
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
        // 针对materialBase表，采取两步：
        // 第一步根据时间以外的标准筛选
        MaterialBaseModel param_first = new MaterialBaseModel();
        param_first.setMaterialCatId(params.getMaterialCatId());
        param_first.setSpuCode(params.getSpuCode());
        param_first.setDesignCode(params.getDesignCode());
        param_first.setDesignVersion(params.getDesignVersion());
        param_first.setSource(params.getSource());
        List<MaterialBaseModel> baseModel_first = generalMapper.getMaterialBaseWithMaterialBaseParams(param_first);
        // 第二步根据时间筛选
        List<MaterialBaseModel> baseModel_second =
                infoObtainMapper.getBaseInfoWithDateRange(params.getStartDate(), params.getEndDate());
        // 针对material表，根据物料名称获取spuCode，进而获取materialBase表信息
        // 若参数为空，则不考虑此步
        List<MaterialModel> materialModelList = null;
        if (params.getMaterialName() != null) {
            MaterialModel paramMaterial = new MaterialModel();
            paramMaterial.setMaterialName(params.getMaterialName());
            materialModelList = generalMapper.getMaterialWithMaterialParams(paramMaterial);
        }
        // 针对materialSku表，根据sku编码获取spu编码，进而获取materialBase表信息
        // 若参数为空，则不考虑此步
        List<MaterialSkuModel> materialSkuModelList = null;
        if (params.getSkuCode() != null) {
            MaterialSkuModel paramMaterialSku = new MaterialSkuModel();
            paramMaterialSku.setSkuCode(params.getSkuCode());
            materialSkuModelList = generalMapper.getMaterialSkuWithMaterialSkuParams(paramMaterialSku);
        }
        // 接下来实现对spuCode的取交集
        // 先获取所有出现的spuCode
        HashSet<String> spuCode_distinct =
                infoObtainServiceSupplier.addAllListSpuCode(baseModel_first,
                        baseModel_second,
                        materialModelList,
                        materialSkuModelList);
        // 接着只需要在第一步所得到的列表里筛选出对应的MaterialBaseModel对象即可
        List<MaterialBaseModel> finalResult = baseModel_first.stream()
                .filter(ele -> spuCode_distinct.contains(ele.getSpuCode()))
                .collect(Collectors.toList());
        // 最后调用工具函数返回对应的值
        return baseInfoObtainServiceSupplier.getBaseInfoAllParams(finalResult);
    }

    /**
     * 根据参数获取物料信息的接口
     * typeArr中对应值如下：
     * 物料基本信息：1
     * 物料定义：2
     * SKU定义：3
     * 附件管理：4（暂时为空，依赖于物料基本信息id）
     * 采购和库存属性：5
     * 计划类属性：6
     * 销售类属性：7
     * 质量类属性：8
     * 财务类属性：9
     * 计量单位：10
     * 全部基础信息：11
     * 规格信息：12
     *
     * @author cplayer
     * @date 2019-02-25 20:47
     * @param params 物料信息参数，详细定义见类
     *
     * @return org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoResponse
     *
     */
    @Override
    public MaterialInfoResponse getMaterialInfoByParams (MaterialInfoRequest params) {
        MaterialInfoResponse result = new MaterialInfoResponse();
        for (int type : params.getTypeArr()) {
            switch (type) {
                case 1:
                    // 处理物料基本信息
                    result.setBaseInfos(materialInfoObtainServiceSupplier.getMaterialInfoForBaseInfo(params));
                    break;
                case 2:
                    // 处理物料定义
                    result.setMaterialInfos(materialInfoObtainServiceSupplier.getMaterialInfoForMaterialInfo(params));
                    break;
                case 3:
                    // 处理sku定义
                    result.setSkuInfos(materialInfoObtainServiceSupplier.getMaterialInfoForMaterialSkuInfo(params));
                    break;
                case 4:
                    // 处理附件管理
                    result.setBaseInfoForFiles(materialInfoObtainServiceSupplier.getMaterialInfoForFileInfo(params));
                    break;
                case 5:
                    // 处理采购和库存属性
                    break;
                case 6:
                    // 处理计划类属性
                    break;
                case 7:
                    // 处理销售类属性
                    break;
                case 8:
                    // 处理质量类属性
                    break;
                case 9:
                    // 处理财务类属性
                    break;
                case 10:
                    // 处理计量单位
                    result.setUnits(materialInfoObtainServiceSupplier.getMaterialInfoForUnitInfo(params));
                    break;
                case 11:
                    // 处理全部基础信息
                    break;
                case 12:
                    // 处理规格信息
                    break;
            }
        }
        return result;
    }


//    @Override
//    public List<Object> getMaterialInfo (String spuCode, String spuName, List<Integer> types, int organizationId) {
//        int maxTypeNum = 12 + 1;
//        int[] flag = new int[maxTypeNum + 1];
//        Arrays.fill(flag, 0);
//        for (int type : types) {
//            flag[type] = 1;
//        }
//        List<Object> result = new ArrayList<>();
//        Map<String, Object> paramsMap;
//        // 缓存物料基本信息id
//        int materialBaseId = -1;
//        for (int i = 1; i <= maxTypeNum; ++i) {
//            if (flag[i] == 0) {
//                continue;
//            }
//            switch (i) {
//                case 1:
//                    List<MaterialBaseModel> baseInfos = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
//                    // 预设SPU编码必须唯一！
//                    if (baseInfos != null && baseInfos.size() > 0) {
//                        materialBaseId = baseInfos.get(0).getId();
//                        result.add(baseInfos);
//                    }
//                    break;
//                case 2:
//                    // 物料定义针对物料信息表
//                    paramsMap = new HashMap<>(16);
//                    paramsMap.put("spuCode", spuCode);
//                    List<MaterialModel> materialInfos = materialInfoMapper.getMaterialWithMaterialParams(paramsMap);
//                    if (materialInfos != null && materialInfos.size() > 0) {
//                        result.add(materialInfos);
//                    } else {
//                        result.add(new ArrayList<>());
//                    }
//                    break;
//                case 3:
//                    paramsMap = new HashMap<>(16);
//                    paramsMap.put("spuCode", spuCode);
//                    List<Object> skuInfos = materialInfoServiceImplSupplier.getMaterialSkuWithMaterialSkuParams(paramsMap);
//                    if (skuInfos != null && skuInfos.size() > 0) {
//                        result.add(skuInfos);
//                    } else {
//                        result.add(new ArrayList<>());
//                    }
//                    break;
//                case 4:
//                    if (materialBaseId == -1) {
//                        // 没有查到过物料信息ID
//                        List<MaterialBaseModel> baseInfoForFiles = materialInfoMapper.getBaseInfoWithSpuCode(spuCode);
//                        if (baseInfoForFiles != null && baseInfoForFiles.size() > 0) {
//                            // 有对应的记录
//                            materialBaseId = baseInfoForFiles.get(0).getId();
//                        } else {
//                            // 若没有返回空数组
//                            result.add(new ArrayList<>());
//                            break;
//                        }
//                    }
//                    // 根据物料基本信息id进行查找
//                    paramsMap = new HashMap<>(16);
//                    paramsMap.put("materialBaseId", materialBaseId);
//                    List<MaterialFilesModel> fileInfos = materialInfoMapper.getFilesWithFilesParams(paramsMap);
//                    if (fileInfos != null && fileInfos.size() > 0) {
//                        result.add(fileInfos);
//                    } else {
//                        result.add(new ArrayList<>());
//                    }
//                    break;
//                case 10:
//                    // 计量单位
//                    // 包括默认计量单位
//                    List<Object> fileUnits = materialInfoServiceImplSupplier.getAllUnitsBySpuCode(spuCode);
//                    if (fileUnits != null && fileUnits.size() > 0) {
//                        result.add(fileUnits);
//                    } else {
//                        result.add(new ArrayList<>());
//                    }
//                    break;
//                case 11:
//                    // 基本属性信息
//                    List<Object> baseProperty = materialInfoServiceImplSupplier.getAllMaterialBaseProp(spuCode);
//                    // List<Object> standardProperty = materialInfoServiceImplSupplier.getMaterialBasePropBySpuCodeAndType(spuCode, 4);
//                    if (baseProperty != null && baseProperty.size() > 0) {
//                        result.add(baseProperty);
//                    } else {
//                        result.add(new ArrayList<>());
//                    }
//                    break;
//                case 12:
//                    // 规格信息
//                    List<Object> standardProperty = materialInfoServiceImplSupplier.getMaterialBasePropBySpuCodeAndType(spuCode, 4);
//                    if (standardProperty != null && standardProperty.size() > 0) {
//                        result.add(standardProperty);
//                    } else {
//                        result.add(new ArrayList<>());
//                    }
//                    break;
//                default:
//                    List<ControlPropertyBean> controlProps = materialInfoServiceImplSupplier.getAllControlPropertyByType(i, organizationId, spuCode);
//                    if (controlProps.size() > 0) {
//                        result.add(controlProps);
//                    }
//                    break;
//            }
//        }
//        return result;
//    }
}
