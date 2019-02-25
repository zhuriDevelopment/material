package org.material.managementfacade.model.responsemodel.MaterialInfo;

import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.tablemodel.*;

import java.util.List;

/**
 * @author cplayer on 2019-02-25 20:41
 * @version 1.0
 * getMaterialInfo方法的回复类
 */

public class MaterialInfoResponse {
    private List<MaterialBaseModel> baseInfos;
    private List<MaterialModel> materialInfos;
    private List<MaterialSkuModel> skuInfos;
    private List<MaterialFilesModel> baseInfoForFiles;
    private MaterialInfoUnitResponse units;
    private MaterialInfoBasePropResponse basePropInfos;
    private MaterialInfoStandardResponse standardInfos;
    private List<ControlPropertyBean> purchaseAndStoreInfos;
    private List<ControlPropertyBean> planInfos;
    private List<ControlPropertyBean> salesInfos;
    private List<ControlPropertyBean> qualifyInfos;
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

    public MaterialInfoUnitResponse getUnits () {
        return units;
    }

    public void setUnits (MaterialInfoUnitResponse units) {
        this.units = units;
    }

    public MaterialInfoBasePropResponse getBasePropInfos () {
        return basePropInfos;
    }

    public void setBasePropInfos (MaterialInfoBasePropResponse basePropInfos) {
        this.basePropInfos = basePropInfos;
    }

    public MaterialInfoStandardResponse getStandardInfos () {
        return standardInfos;
    }

    public void setStandardInfos (MaterialInfoStandardResponse standardInfos) {
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

    public List<ControlPropertyBean> getQualifyInfos () {
        return qualifyInfos;
    }

    public void setQualifyInfos (List<ControlPropertyBean> qualifyInfos) {
        this.qualifyInfos = qualifyInfos;
    }

    public List<ControlPropertyBean> getFinanceInfos () {
        return financeInfos;
    }

    public void setFinanceInfos (List<ControlPropertyBean> financeInfos) {
        this.financeInfos = financeInfos;
    }
}
