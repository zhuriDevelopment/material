package org.material.managementfacade.model.responsemodel.MaterialInfo;

import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialFilesModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementfacade.model.tablemodel.MaterialSkuModel;

import java.util.List;

/**
 * @author cplayer on 2019-02-25 20:41
 * @version 1.0
 * getMaterialInfo方法的回复类中的参数
 */

public class MatInfoRespParams {
    // 物料基本信息
    private List<MaterialBaseModel> baseInfos;
    // 物料信息
    private List<MaterialModel> materialInfos;
    // SKU信息
    private List<MaterialSkuModel> skuInfos;
    // 附件管理（保留接口）
    private List<MaterialFilesModel> baseInfoForFiles;
    // 计量单位
    private MatInfoUnitResp units;
    // 全部基础信息
    private MatInfoBasePropResp basePropInfos;
    // 规格信息
    private MatInfoBasePropRespClass standardInfos;
    // 采购和库存属性
    private List<ControlPropertyBean> purchaseAndStoreInfos;
    // 计划类属性
    private List<ControlPropertyBean> planInfos;
    // 销售类属性
    private List<ControlPropertyBean> salesInfos;
    // 质量类属性
    private List<ControlPropertyBean> qualityInfos;
    // 财务类属性
    private List<ControlPropertyBean> financeInfos;

    public List<MaterialBaseModel> getBaseInfos () {
        return baseInfos;
    }

    public void setBaseInfos (List<MaterialBaseModel> baseInfos) {
        this.baseInfos = baseInfos;
    }

    public List<MaterialModel> getMaterialInfos () {
        return materialInfos;
    }

    public void setMaterialInfos (List<MaterialModel> materialInfos) {
        this.materialInfos = materialInfos;
    }

    public List<MaterialSkuModel> getSkuInfos () {
        return skuInfos;
    }

    public void setSkuInfos (List<MaterialSkuModel> skuInfos) {
        this.skuInfos = skuInfos;
    }

    public List<MaterialFilesModel> getBaseInfoForFiles () {
        return baseInfoForFiles;
    }

    public void setBaseInfoForFiles (List<MaterialFilesModel> baseInfoForFiles) {
        this.baseInfoForFiles = baseInfoForFiles;
    }

    public MatInfoUnitResp getUnits () {
        return units;
    }

    public void setUnits (MatInfoUnitResp units) {
        this.units = units;
    }

    public MatInfoBasePropResp getBasePropInfos () {
        return basePropInfos;
    }

    public void setBasePropInfos (MatInfoBasePropResp basePropInfos) {
        this.basePropInfos = basePropInfos;
    }

    public MatInfoBasePropRespClass getStandardInfos () {
        return standardInfos;
    }

    public void setStandardInfos (MatInfoBasePropRespClass standardInfos) {
        this.standardInfos = standardInfos;
    }

    public List<ControlPropertyBean> getPurchaseAndStoreInfos () {
        return purchaseAndStoreInfos;
    }

    public void setPurchaseAndStoreInfos (List<ControlPropertyBean> purchaseAndStoreInfos) {
        this.purchaseAndStoreInfos = purchaseAndStoreInfos;
    }

    public List<ControlPropertyBean> getPlanInfos () {
        return planInfos;
    }

    public void setPlanInfos (List<ControlPropertyBean> planInfos) {
        this.planInfos = planInfos;
    }

    public List<ControlPropertyBean> getSalesInfos () {
        return salesInfos;
    }

    public void setSalesInfos (List<ControlPropertyBean> salesInfos) {
        this.salesInfos = salesInfos;
    }

    public List<ControlPropertyBean> getQualityInfos () {
        return qualityInfos;
    }

    public void setQualityInfos (List<ControlPropertyBean> qualityInfos) {
        this.qualityInfos = qualityInfos;
    }

    public List<ControlPropertyBean> getFinanceInfos () {
        return financeInfos;
    }

    public void setFinanceInfos (List<ControlPropertyBean> financeInfos) {
        this.financeInfos = financeInfos;
    }
}
