package org.material.managementservice.service.info.impl.supplier.controlprop;

import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.propertymodel.finance.FinanceList;
import org.material.managementfacade.model.propertymodel.plan.PlanList;
import org.material.managementfacade.model.propertymodel.purchaseandstore.PurchaseAndStoreList;
import org.material.managementfacade.model.propertymodel.quality.QualityList;
import org.material.managementfacade.model.propertymodel.sales.SalesList;
import org.material.managementfacade.model.requestmodel.MatInfoReq;
import org.material.managementfacade.model.tablemodel.*;
import org.material.managementservice.general.MaterialCategoryErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cplayer on 2019-02-26 22:58
 * @version 1.0
 * 物料控制属性获取的辅助类，存放关于物料控制属性获取的函数
 */

@Component
public class ControlPropObtainServiceSupplier {
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
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    
    /**
     * 根据spu编码查找对应的版本id的函数
     *
     * @author cplayer
     * @date 2019-04-03 21:26
     * @param spuCode spu编码
     * @param organizationCode 组织编码
     *
     * @return versionId 对应的版本id
     *
     */
    public int findOrInsertCtrlPropValVerBySpuCodeAndOrgCode (String spuCode, String organizationCode) {
        MaterialCtrlPropValVerModel param = new MaterialCtrlPropValVerModel();
        param.setSpuCode(spuCode);
        param.setOrganizationCode(organizationCode);
        List<MaterialCtrlPropValVerModel> searchRes = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(param);
        MaterialCtrlPropValVerModel res = MaterialGeneral.getInitElementOrFirstElement(searchRes,
                MaterialCtrlPropValVerModel.class);
        if (res.getId() == -1) {
            // 插入
            // 填写版本号，组织编码，物料分类id，spu编码，起始时间，终止时间
            // 获取物料分类id，不存在就返回空
            List<MaterialBaseModel> baseResult = infoObtainMapper.getBaseInfoWithSpuCode(spuCode);
            if (baseResult == null || baseResult.size() == 0) {
                return -1;
            }
            // 此时应该只有一个分类id
            int materialCatId = MaterialGeneral.getInitElementOrFirstElement(baseResult, MaterialBaseModel.class)
                    .getMaterialCatId();
            // 默认版本号
            String version = "ver-" + "0100-T-" + Integer.valueOf(materialCatId).toString();
            // 填写参数
            MaterialCtrlPropValVerModel paramInsert = new MaterialCtrlPropValVerModel();
            paramInsert.setOrganizationCode(organizationCode);
            paramInsert.setMaterialCatId(materialCatId);
            paramInsert.setSpuCode(MaterialGeneral.generalSpuCode);
            paramInsert.setVersion(version);
            paramInsert.setStartDate(new Timestamp(System.currentTimeMillis()));
            paramInsert.setEndDate(new Timestamp(System.currentTimeMillis() + 315360000000L));
        }
        return res.getId();
    }
    
    /**
     * 根据物料分类id、spu编码和组织编码查找对应的版本id的函数
     *
     * @author cplayer
     * @date 2019-04-03 14:42
     * @param spuCode spu编码
     * @param organizationCode 组织编码
     * @param materialCatId 物料分类id
     *
     * @return versionId 对应的版本id
     *
     */
    public int getVersionIdByParams (String spuCode, String organizationCode, int materialCatId) {
        MaterialCtrlPropValVerModel param = new MaterialCtrlPropValVerModel();
        param.setSpuCode(spuCode);
        param.setOrganizationCode(organizationCode);
        param.setMaterialCatId(materialCatId);
        List<MaterialCtrlPropValVerModel> searchRes = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(param);
        MaterialCtrlPropValVerModel res = MaterialGeneral.getInitElementOrFirstElement(searchRes, MaterialCtrlPropValVerModel.class);
        return res.getId();
    }

    /**
     * 根据属性名、组织编码以及spuCode查找对应的物料控制属性
     *
     * @param propName
     *         控制属性名
     * @param organizationId
     *         组织id
     * @param spuCode
     *         spu编码
     *
     * @return org.material.managementfacade.model.propertymodel.ControlPropertyBean
     *
     * @author cplayer
     * @date 2019-02-26 22:15
     */
    private ControlPropertyBean getControlPropByName (String propName, int organizationId, String spuCode) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = infoObtainMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) {
            return null;
        }
        // 此时应该只有一个分类id
        int materialCatId = MaterialGeneral.getInitElementOrFirstElement(baseResult, MaterialBaseModel.class)
                .getMaterialCatId();
        // 获取对应的物料控制属性值版本
        MaterialCtrlPropValVerModel paramPropValVer = new MaterialCtrlPropValVerModel();
        paramPropValVer.setMaterialCatId(materialCatId);
        paramPropValVer.setSpuCode(spuCode);
        paramPropValVer.setOrganizationCode(Integer.valueOf(organizationId).toString());
        List<MaterialCtrlPropValVerModel> ctrlVerResult = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(paramPropValVer);
        // 需要确保结果只有一个，若有多个，取第一个
        int versionId = MaterialGeneral.getInitElementOrFirstElement(ctrlVerResult, MaterialCtrlPropValVerModel.class)
                .getId();
        // 查找控制属性名对应的id
        MaterialCtrlPropModel paramProp = new MaterialCtrlPropModel();
        paramProp.setName(propName);
        // 获取对应的物料控制属性
        List<MaterialCtrlPropModel> ctrlPropResult = generalMapper.getCtrlPropWithCtrlPropParams(paramProp);
        if (ctrlPropResult == null || ctrlPropResult.size() == 0) {
            return null;
        }
        int ctrlPropId = MaterialGeneral.getInitElementOrFirstElement(ctrlPropResult, MaterialCtrlPropModel.class)
                .getId();
        // 查找对应的物料控制属性值，根据版本号和属性名id
        MaterialCtrlPropValModel paramPropVal = new MaterialCtrlPropValModel();
        paramPropVal.setVersionId(versionId);
        paramPropVal.setMaterialCtrlPropId(ctrlPropId);
        List<MaterialCtrlPropValModel> ctrlValResult = generalMapper.getCtrlPropValWithCtrlPropValParams(paramPropVal);
        if (ctrlValResult == null || ctrlValResult.size() == 0) {
            return null;
        }
        ControlPropertyBean result;
        String value = MaterialGeneral.getInitElementOrFirstElement(ctrlValResult, MaterialCtrlPropValModel.class)
                .getValue();
        result = new ControlPropertyBean(propName, value);
        return result;
    }

    /**
     * 根据属性名列表、组织编码以及spuCode返回所有对应的物料控制属性
     *
     * @param propNameList
     *         控制属性名列表
     * @param organizationId
     *         组织id
     * @param spuCode
     *         spu编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:39
     */
    private List<ControlPropertyBean> getControlPropList (String[] propNameList, int organizationId, String spuCode) {
        List<ControlPropertyBean> result = new ArrayList<>();
        for (String propName : propNameList) {
            ControlPropertyBean tmpResult = getControlPropByName(propName, organizationId, spuCode);
            if (tmpResult != null) {
                result.add(tmpResult);
            } else {
                result.add(new ControlPropertyBean(propName, ""));
            }
        }
        return result;
    }


    /**
     * 根据属性索引、属性名列表、组织id以及spu编码获取所有对应的物料控制属性
     *
     * @param index
     *         控制属性名索引，即第index个控制属性，顺序由yml文件中的顺序决定
     * @param propNameList
     *         控制属性名数组
     * @param organizationId
     *         组织id
     * @param spuCode
     *         spu编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:49
     */
    private List<ControlPropertyBean> getControlPropListWithIndex (int index, String[] propNameList, int organizationId, String spuCode) {
        if (index < -1) {
            return null;
        }
        if (index == -1) {
            return getControlPropList(propNameList, organizationId, spuCode);
        } else {
            ControlPropertyBean result = getControlPropByName(propNameList[index],
                    organizationId,
                    spuCode);
            List<ControlPropertyBean> resultList = new ArrayList<>();
            resultList.add(result);
            return resultList;
        }
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料采购和库存属性
     *
     * @param index
     *         采购和库存属性名索引，即第index个采购和库存属性，顺序由yml文件中的顺序决定
     * @param params
     *         物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     */
    public List<ControlPropertyBean> getPurchaseAndStoreProperties (int index, MatInfoReq params) {
        return getControlPropListWithIndex(index,
                purchaseAndStoreList.getPurchaseAndStoreList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料计划类属性
     *
     * @param index
     *         计划类属性名索引，即第index个计划类属性，顺序由yml文件中的顺序决定
     * @param params
     *         物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     */
    public List<ControlPropertyBean> getPlanProperties (int index, MatInfoReq params) {
        return getControlPropListWithIndex(index,
                planList.getPlanList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料销售类属性
     *
     * @param index
     *         销售类属性名索引，即第index个销售类属性，顺序由yml文件中的顺序决定
     * @param params
     *         物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     */
    public List<ControlPropertyBean> getSalesProperties (int index, MatInfoReq params) {
        return getControlPropListWithIndex(index,
                salesList.getSalesList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料质量属性
     *
     * @param index
     *         质量属性名索引，即第index个质量属性，顺序由yml文件中的顺序决定
     * @param params
     *         物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     */
    public List<ControlPropertyBean> getQualityProperties (int index, MatInfoReq params) {
        return getControlPropListWithIndex(index,
                qualityList.getQualityList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料财务属性
     *
     * @param index
     *         财务属性名索引，即第index个财务属性，顺序由yml文件中的顺序决定
     * @param params
     *         物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     */
    public List<ControlPropertyBean> getFinanceProperties (int index, MatInfoReq params) {
        return getControlPropListWithIndex(index,
                financeList.getFinanceList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性索引，组织编码，物料分类id获取所有的对应的物料采购和库存属性
     *
     * @param index
     *         采购和库存属性名索引，即第index个采购和库存属性，顺序由yml文件中的顺序决定
     * @param versionId
     *         版本id
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 06:52
     */
    public List<ControlPropertyBean> getPurchaseAndStorePropertiesWithCatId (int index, int versionId) {
        return getControlPropListWithIndexAndCatId(index, purchaseAndStoreList.getPurchaseAndStoreList(), versionId);
    }

    /**
     * 根据属性索引，组织编码，物料分类id获取所有的对应的物料计划类属性
     *
     * @param index
     *         计划类属性名索引，即第index个计划类属性，顺序由yml文件中的顺序决定
     * @param organizationId
     *         组织编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 06:52
     */
    public List<ControlPropertyBean> getPlanPropertiesWithCatId (int index, int organizationId) {
        return getControlPropListWithIndexAndCatId(index, planList.getPlanList(), organizationId);
    }

    /**
     * 根据属性索引，组织编码，物料分类id获取所有的对应的物料销售类属性
     *
     * @param index
     *         销售类属性名索引，即第index个销售类属性，顺序由yml文件中的顺序决定
     * @param organizationId
     *         组织编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 06:52
     */
    public List<ControlPropertyBean> getSalesPropertiesWithCatId (int index, int organizationId) {
        return getControlPropListWithIndexAndCatId(index, salesList.getSalesList(), organizationId);
    }

    /**
     * 根据属性索引，组织编码，物料分类id获取所有的对应的物料质量类属性
     *
     * @param index
     *         质量类属性名索引，即第index个质量类属性，顺序由yml文件中的顺序决定
     * @param organizationId
     *         组织编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 06:52
     */
    public List<ControlPropertyBean> getQualityPropertiesWithCatId (int index, int organizationId) {
        return getControlPropListWithIndexAndCatId(index, qualityList.getQualityList(), organizationId);
    }

    /**
     * 根据属性索引，组织编码，物料分类id获取所有的对应的物料财务类属性
     *
     * @param index
     *         财务类属性名索引，即第index个财务类属性，顺序由yml文件中的顺序决定
     * @param organizationId
     *         组织编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 06:52
     */
    public List<ControlPropertyBean> getFinancePropertiesWithCatId (int index, int organizationId) {
        return getControlPropListWithIndexAndCatId(index, financeList.getFinanceList(), organizationId);
    }

    /**
     * 根据属性名称，组织编码以及物料分类id获取物料分类下的控制属性
     *
     * @param propName
     *         属性名称
     * @param versionId
     *         版本id
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 06:45
     */
    private List<ControlPropertyBean> getControlPropByVersionIdAndName (String propName, int versionId) {
        // 查找控制属性名对应的id
        MaterialCtrlPropModel paramCtrProp = new MaterialCtrlPropModel();
        paramCtrProp.setName(propName);
        List<MaterialCtrlPropModel> ctrlPropResult = generalMapper.getCtrlPropWithCtrlPropParams(paramCtrProp);
        if (ctrlPropResult == null || ctrlPropResult.size() == 0) {
            return null;
        }
        int ctrlPropId = MaterialGeneral.getInitElementOrFirstElement(ctrlPropResult, MaterialCtrlPropModel.class).getId();
        // 查找对应的属性值，根据版本号和属性名id
        MaterialCtrlPropValModel paramCtrPropVal = new MaterialCtrlPropValModel();
        paramCtrPropVal.setVersionId(versionId);
        paramCtrPropVal.setMaterialCtrlPropId(ctrlPropId);
        List<MaterialCtrlPropValModel> ctrlValResult = generalMapper.getCtrlPropValWithCtrlPropValParams(paramCtrPropVal);
        if (ctrlValResult == null) {
            return null;
        }
        List<ControlPropertyBean> result = new ArrayList<>();
        result.clear();
        String value = ctrlValResult.get(0).getValue();
        result.add(new ControlPropertyBean(propName, value));
        return result;
    }


    /**
     * 根据物料控制属性索引、物料控制属性名列表、组织编码以及物料分类id获取对应的控制属性
     *
     * @param index
     *         属性名索引
     * @param propNameList
     *         属性名列表
     * @param versionId
     *         版本id
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     * @author cplayer
     * @date 2019-03-02 07:01
     */
    private List<ControlPropertyBean> getControlPropListWithIndexAndCatId (int index, String[] propNameList, int versionId) {
        // 索引不能非法
        if (index < -1) {
            return null;
        } else if (index == -1) {
            List<ControlPropertyBean> result = new ArrayList<>();
            for (String property : propNameList) {
                List<ControlPropertyBean> tmpResult = getControlPropByVersionIdAndName(property, versionId);
                if (tmpResult != null && !tmpResult.isEmpty()) {
                    result.addAll(tmpResult);
                } else {
                    List<ControlPropertyBean> emptyResult = new ArrayList<>();
                    emptyResult.add(new ControlPropertyBean(property, ""));
                    result.addAll(emptyResult);
                }
            }
            return result;
        } else {
            return getControlPropByVersionIdAndName(propNameList[index], versionId);
        }
    }
}
