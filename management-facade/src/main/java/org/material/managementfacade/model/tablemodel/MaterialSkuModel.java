package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料sku信息表")
public class MaterialSkuModel {
    private int id;
    private String spuCode;
    private String skuCode;
    private String skuName;
    private String materialCode;
    private String barCode;
    private int unitId;
    private int purchasePrice;
    private int sellingPrice;
    private String description;

    public MaterialSkuModel () {
        this.id = -1;
        this.spuCode = null;
        this.skuCode = null;
        this.skuName = null;
        this.materialCode = null;
        this.barCode = null;
        this.unitId = -1;
        this.purchasePrice = -1;
        this.sellingPrice = -1;
        this.description = null;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public String getSkuCode () {
        return skuCode;
    }

    public void setSkuCode (String skuCode) {
        this.skuCode = skuCode;
    }

    public int getUnitId () {
        return unitId;
    }

    public void setUnitId (int unitId) {
        this.unitId = unitId;
    }

    public int getPurchasePrice () {
        return purchasePrice;
    }

    public void setPurchasePrice (int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getSellingPrice () {
        return sellingPrice;
    }

    public void setSellingPrice (int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getSkuName () {
        return skuName;
    }

    public void setSkuName (String skuName) {
        this.skuName = skuName;
    }

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }

    public String getBarCode () {
        return barCode;
    }

    public void setBarCode (String barCode) {
        this.barCode = barCode;
    }
}
