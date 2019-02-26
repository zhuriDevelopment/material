package org.material.managementservice.service.info.impl.supplier.controlprop;

import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.propertymodel.finance.FinanceList;
import org.material.managementfacade.model.propertymodel.plan.PlanList;
import org.material.managementfacade.model.propertymodel.purchaseandstore.PurchaseAndStoreList;
import org.material.managementfacade.model.propertymodel.quality.QualityList;
import org.material.managementfacade.model.propertymodel.sales.SalesList;
import org.material.managementfacade.model.requestmodel.MaterialInfoRequest;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValVerModel;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.InfoObtainServiceSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    private InfoObtainServiceSupplier infoObtainServiceSupplier;

    /**
     * 根据属性名、组织编码以及spuCode查找对应的物料控制属性
     *
     * @author cplayer
     * @date 2019-02-26 22:15
     * @param propName 控制属性名
     *
     * @param organizationId 组织id
     *
     * @param spuCode spu编码
     *
     * @return org.material.managementfacade.model.propertymodel.ControlPropertyBean
     *
     */
    private ControlPropertyBean getControlPropByName (String propName, int organizationId, String spuCode) {
        // 获取物料分类id，不存在就返回空
        List<MaterialBaseModel> baseResult = infoObtainMapper.getBaseInfoWithSpuCode(spuCode);
        if (baseResult == null || baseResult.size() == 0) {
            return null;
        }
        // 此时应该只有一个分类id
        int materialCatId = infoObtainServiceSupplier.getInitElementOrFirstElement(baseResult, MaterialBaseModel.class)
                .getMaterialCatId();
        // 获取对应的物料控制属性值版本
        MaterialCtrlPropValVerModel paramPropValVer = new MaterialCtrlPropValVerModel();
        paramPropValVer.setMaterialCatId(materialCatId);
        paramPropValVer.setSpuCode(spuCode);
        paramPropValVer.setOrganizationCode(Integer.valueOf(organizationId).toString());
        List<MaterialCtrlPropValVerModel> ctrlVerResult = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(paramPropValVer);
        // 需要确保结果只有一个，若有多个，取第一个
        int versionId = infoObtainServiceSupplier.getInitElementOrFirstElement(ctrlVerResult, MaterialCtrlPropValVerModel.class)
                .getId();
        // 查找控制属性名对应的id
        MaterialCtrlPropModel paramProp = new MaterialCtrlPropModel();
        paramProp.setName(propName);
        // 获取对应的物料控制属性
        List<MaterialCtrlPropModel> ctrlPropResult = generalMapper.getCtrlPropWithCtrlPropParams(paramProp);
        if (ctrlPropResult == null || ctrlPropResult.size() == 0) {
            return null;
        }
        int ctrlPropId = infoObtainServiceSupplier.getInitElementOrFirstElement(ctrlPropResult, MaterialCtrlPropModel.class)
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
        String value = infoObtainServiceSupplier.getInitElementOrFirstElement(ctrlValResult, MaterialCtrlPropValModel.class)
                .getValue();
        result = new ControlPropertyBean(propName, value);
        return result;
    }

    /**
     * 根据属性名列表、组织编码以及spuCode返回所有对应的物料控制属性
     *
     * @author cplayer
     * @date 2019-02-26 22:39
     * @param propNameList 控制属性名列表
     *
     * @param organizationId 组织id
     *
     * @param spuCode spu编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
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
     * @author cplayer
     * @date 2019-02-26 22:49
     * @param index 控制属性名索引，即第index个控制属性，顺序由yml文件中的顺序决定
     *
     * @param propNameList 控制属性名数组
     *
     * @param organizationId 组织id
     *
     * @param spuCode spu编码
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
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
     * @author cplayer
     * @date 2019-02-26 22:51
     * @param index 采购和库存属性名索引，即第index个采购和库存属性，顺序由yml文件中的顺序决定
     *
     * @param params 物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     */
    public List<ControlPropertyBean> getPurchaseAndStoreProperties (int index, MaterialInfoRequest params) {
        return getControlPropListWithIndex(index,
                purchaseAndStoreList.getPurchaseAndStoreList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料计划类属性
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     * @param index 计划类属性名索引，即第index个计划类属性，顺序由yml文件中的顺序决定
     *
     * @param params 物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     */
    public List<ControlPropertyBean> getPlanProperties (int index, MaterialInfoRequest params) {
        return getControlPropListWithIndex(index,
                planList.getPlanList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料销售类属性
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     * @param index 销售类属性名索引，即第index个销售类属性，顺序由yml文件中的顺序决定
     *
     * @param params 物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     */
    public List<ControlPropertyBean> getSalesProperties (int index, MaterialInfoRequest params) {
        return getControlPropListWithIndex(index,
                salesList.getSalesList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料质量属性
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     * @param index 质量属性名索引，即第index个质量属性，顺序由yml文件中的顺序决定
     *
     * @param params 物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     */
    public List<ControlPropertyBean> getQualityProperties (int index, MaterialInfoRequest params) {
        return getControlPropListWithIndex(index,
                qualityList.getQualityList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }

    /**
     * 根据属性名索引、组织id以及spu编码获取所有对应的物料财务属性
     *
     * @author cplayer
     * @date 2019-02-26 22:51
     * @param index 财务属性名索引，即第index个财务属性，顺序由yml文件中的顺序决定
     *
     * @param params 物料信息参数
     *
     * @return java.util.List<org.material.managementfacade.model.propertymodel.ControlPropertyBean>
     *
     */
    public List<ControlPropertyBean> getFinanceProperties (int index, MaterialInfoRequest params) {
        return getControlPropListWithIndex(index,
                financeList.getFinanceList(),
                params.getOrganizationId(),
                params.getSpuCode());
    }
}
