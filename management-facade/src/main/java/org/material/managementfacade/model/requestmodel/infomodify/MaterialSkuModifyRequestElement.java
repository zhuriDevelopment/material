package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.Objects;

/**
 * @author cplayer on 2019-02-28 16:00
 * @version 1.0
 * 物料更新过程中sku定义更新元素的封装类
 */

public class MaterialSkuModifyRequestElement {
    private String skuCode;
    private String skuName;
    private String materialCode;
    private int unitId;
    private String barCode;
    private int purchasePrice;
    private int sellingPrice;
    private String description;

    public MaterialSkuModifyRequestElement () {
        this.skuCode = null;
        this.skuName = null;
        this.materialCode = null;
        this.barCode = null;
        this.unitId = -1;
        this.purchasePrice = -1;
        this.sellingPrice = -1;
        this.description = null;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialSkuModifyRequestElement that = (MaterialSkuModifyRequestElement) o;
        return unitId == that.unitId &&
                purchasePrice == that.purchasePrice &&
                sellingPrice == that.sellingPrice &&
                skuCode.equals(that.skuCode) &&
                skuName.equals(that.skuName) &&
                materialCode.equals(that.materialCode) &&
                barCode.equals(that.barCode) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode () {
        return Objects.hash(skuCode, skuName, materialCode, unitId, barCode, purchasePrice, sellingPrice, description);
    }

    public String getSkuCode () {
        return skuCode;
    }

    public void setSkuCode (String skuCode) {
        this.skuCode = skuCode;
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

    public int getUnitId () {
        return unitId;
    }

    public void setUnitId (int unitId) {
        this.unitId = unitId;
    }

    public String getBarCode () {
        return barCode;
    }

    public void setBarCode (String barCode) {
        this.barCode = barCode;
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
}
