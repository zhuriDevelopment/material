package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.requestmodel.*;
import org.material.managementfacade.model.responsemodel.*;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoResponse;
import org.material.managementfacade.model.tablemodel.*;
import org.material.managementfacade.service.info.InfoObtainService;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.InfoObtainServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.baseinfo.BaseInfoObtainServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.baseprop.BasePropObtainServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.controlprop.ControlPropObtainServiceSupplier;
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
    @Autowired
    private ControlPropObtainServiceSupplier controlPropObtainServiceSupplier;
    @Autowired
    private BasePropObtainServiceSupplier basePropObtainServiceSupplier;

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
    public BaseInfoResp getAllBaseInfo () {
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
     * @return org.material.managementfacade.model.responsemodel.BaseInfoRespParams
     *
     */
    @Override
    public BaseInfoResp getBaseInfoByParams (BaseInfoReq params) {
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
                    result.setPurchaseAndStoreInfos(controlPropObtainServiceSupplier.
                            getPurchaseAndStoreProperties(-1, params));
                    break;
                case 6:
                    // 处理计划类属性
                    result.setPlanInfos(controlPropObtainServiceSupplier.
                            getPlanProperties(-1, params));
                    break;
                case 7:
                    // 处理销售类属性
                    result.setSalesInfos(controlPropObtainServiceSupplier.getSalesProperties(-1, params));
                    break;
                case 8:
                    // 处理质量类属性
                    result.setQualifyInfos(controlPropObtainServiceSupplier.getQualityProperties(-1, params));
                    break;
                case 9:
                    // 处理财务类属性
                    result.setFinanceInfos(controlPropObtainServiceSupplier.getFinanceProperties(-1, params));
                    break;
                case 10:
                    // 处理计量单位
                    result.setUnits(materialInfoObtainServiceSupplier.getMaterialInfoForUnitInfo(params));
                    break;
                case 11:
                    // 处理全部基础信息
                    result.setBasePropInfos(materialInfoObtainServiceSupplier.getMaterialInfoForAllBasePropInfos(params));
                    break;
                case 12:
                    // 处理规格信息
                    result.setStandardInfos(materialInfoObtainServiceSupplier.getMaterialInfoForBasePropInfoWithType(params, 4));
                    break;
                default:
                    // 若出现其他的选项，什么都不做，也不允许出现其他选项
                    break;
            }
        }
        return result;
    }

    /**
     * 根据物料分类信息获取所有物料基本信息的函数
     *
     * @author cplayer
     * @date 2019-03-02 05:50
     * @param params 传上来的id
     *
     * @return org.material.managementfacade.model.responsemodel.MaterialInfoObtainByCategoryInfoResponse
     *
     */
    @Override
    public MaterialInfoObtainByCategoryInfoResponse getAllMaterialBaseByCategoryInfos (MaterialInfoObtainByCategoryInfoRequest params) {
        MaterialBaseModel paramBase = new MaterialBaseModel();
        MaterialCategoryModel paramCate = new MaterialCategoryModel();
        paramBase.setMaterialCatId(params.getId());
        paramCate.setId(params.getId());
        List<MaterialBaseModel> materialBaseResult = generalMapper.getMaterialBaseWithMaterialBaseParams(paramBase);
        List<MaterialCategoryModel> catResult = generalMapper.getMaterialCategoryWithMaterialCategoryParams(paramCate);
        MaterialInfoObtainByCategoryInfoResponse result = new MaterialInfoObtainByCategoryInfoResponse();
        result.setCatResult(catResult);
        result.setMaterialBaseResult(materialBaseResult);
        return result;
    }

    /**
     * 根据物料分类id和物料名称获取所有物料信息的函数
     *
     * 信息数组对应编码如下：
     * 采购和库存属性：5
     * 计划类属性：6
     * 销售类属性：7
     * 质量类属性：8
     * 财务类属性：9
     * 全部基础信息：11
     * （10空出来是为了和getMaterialInfoByParams接口的数据保持一致）
     *
     * @author cplayer
     * @date 2019-03-02 06:13
     * @param params 请求对应的参数，包括物料分类编码、名称以及需要获取的信息数组
     *
     * @return org.material.managementfacade.model.responsemodel.MaterialInfoObtainByCatCodeAndNameResponse
     *
     */
    @Override
    public MaterialInfoObtainByCatCodeAndNameResponse getMaterialInfoWithCatCodeAndCatName (MaterialInfoObtainByCatCodeAndNameRequest params) {
        String catCode = params.getCode();
        String catName = params.getName();
        List<Integer> typeArr = params.getTypeArr();
        int organizationId = 1;
        // 先查找对应的物料分类id
        MaterialCategoryModel param = new MaterialCategoryModel();
        param.setCode(catCode);
        MaterialCategoryModel searchResult = MaterialGeneral.getInitElementOrFirstElement(
                generalMapper.getMaterialCategoryWithMaterialCategoryParams(param),
                MaterialCategoryModel.class);
        MaterialInfoObtainByCatCodeAndNameResponse result = new MaterialInfoObtainByCatCodeAndNameResponse();
        if (searchResult.getId() != -1) {
            // 说明找到了，继续处理
            int catId = searchResult.getId();
            logger.info("物料分类编码为" + catCode + "的记录对应的物料分类id为：" + catId);
            for (Integer type : typeArr) {
                // 若是全部基础信息
                switch (type) {
                    case 5:
                        // 处理采购和库存属性
                        result.setPurchaseAndStoreInfos(controlPropObtainServiceSupplier.getPurchaseAndStorePropertiesWithCatId(-1, organizationId, catId));
                        break;
                    case 6:
                        // 处理计划类属性
                        result.setPlanInfos(controlPropObtainServiceSupplier.getPlanPropertiesWithCatId(-1, organizationId, catId));
                        break;
                    case 7:
                        // 处理销售类属性
                        result.setSalesInfos(controlPropObtainServiceSupplier.getSalesPropertiesWithCatId(-1, organizationId, catId));
                        break;
                    case 8:
                        // 处理质量类属性
                        result.setQualifyInfos(controlPropObtainServiceSupplier.getQualityPropertiesWithCatId(-1, organizationId, catId));
                        break;
                    case 9:
                        // 处理财务类属性
                        result.setFinanceInfos(controlPropObtainServiceSupplier.getFinancePropertiesWithCatId(-1, organizationId, catId));
                        break;
                    case 11:
                        List<MaterialBasePropModel> basePropList = basePropObtainServiceSupplier.getAllMaterialBasePropByCatId(catId);
                        if (basePropList.size() == 0) {
                            logger.info("待寻找的物料基本属性不存在！");
                        }
                        break;
                    default:
                        break;
                }
            }
            return result;
        } else {
            logger.error(String.format("根据物料分类id和物料名称获取所有物料信息时，分类编码 = %s的记录不存在。", catCode));
            result.setResultCode(MaterialInfoErrCode.notExistMaterialCateId);
            return result;
        }
    }

    /**
     * 根据spu编码和物料编码获取物料基本属性的实现函数
     *
     * @author cplayer
     * @date 2019-03-03 01:46
     * @param params 请求对应的参数
     *
     * @return org.material.managementfacade.model.responsemodel.MaterialBaseObtainBySpuAndMatCodeResponse
     *
     */
    @Override
    public MaterialBaseObtainBySpuAndMatCodeResponse getMaterialBasePropsBySpuCodeAndMaterialCodes (MaterialBaseObtainBySpuAndMatCodeRequest params) {
        MaterialBaseObtainBySpuAndMatCodeResponse response = new MaterialBaseObtainBySpuAndMatCodeResponse();
        // 先找所有通用的属性，记录下所有对应属性的id
        List<MaterialBasePropValModel> materialCommonBasePropResult = infoObtainMapper.getMaterialBasePropValWithSpuCodeAndMatCode(params.getSpuCode(), MaterialGeneral.generalMaterialCode);
        Map<Integer, MaterialBasePropModel> commonBaseProps = new HashMap<>(16);
        commonBaseProps.clear();
        // 记录下所有通用基本属性的信息
        for (MaterialBasePropValModel element : materialCommonBasePropResult) {
            int id = element.getMaterialBasePropId();
            List<MaterialBasePropModel> tmpResult = infoObtainMapper.getMaterialBasePropWithId(id);
            MaterialBasePropModel baseProp = MaterialGeneral.getInitElementOrFirstElement(tmpResult, MaterialBasePropModel.class);
            if (baseProp.getId() != -1) {
                // 非空，必须保证只有一个
                if (tmpResult.size() > 1) {
                    logger.info("在查找id = " + id + "的物料控制属性记录时出现了多个记录，请检查数据库！");
                }
                if (baseProp.getType() == params.getPropertyType()) {
                    commonBaseProps.put(id, tmpResult.get(0));
                }
            }
        }
        response.setBaseInfos(new ArrayList<>());
        // 接下来处理每个物料编码的信息
        for (String materialCode : params.getMaterialCodes()) {
            // 获取单个物料编码的所有基本属性
            MaterialBaseObtainBySpuAndMatCodeElement singleClass = basePropObtainServiceSupplier.getMateialBaseBySpuCodeAndSpecificMatCode(
                    params,
                    materialCode,
                    commonBaseProps
            );
            response.getBaseInfos().add(singleClass);
        }
        response.setErrCode(MaterialInfoErrCode.successObtainBaseBySpuAndMatCode);
        return response;
    }

    /**
     * 根据物料分类id和属性类型获取物料基本属性的实现函数
     *
     * @author cplayer
     * @date 2019-03-03 05:53
     * @param params 请求对应的参数
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialBasePropModel>
     *
     */
    @Override
    public List<MaterialBasePropModel> getMaterialBaseByCatIdAndType (BasePropObtainByCatIdAndTypeRequest params) {
        MaterialBasePropModel param = new MaterialBasePropModel();
        param.setMaterialCatId(params.getCatId());
        param.setType(params.getPropertyType());
        return generalMapper.getMaterialBasePropWithMaterialBasePropParams(param);
    }
}
