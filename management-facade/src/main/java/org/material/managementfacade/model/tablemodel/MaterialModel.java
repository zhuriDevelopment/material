package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料信息表")
public class MaterialModel {
    private int id;
    private String spuCode;
    private String materialCode;
    private String materialName;
    private String oldMaterialCode;
    private String barCode;
    private int materialBaseId;
//    private int purchasePrice;
//    private int sellingPrice;

    public MaterialModel () {
        this.id = -1;
        this.spuCode = null;
        this.materialCode = null;
        this.materialName = null;
        this.oldMaterialCode = null;
        this.barCode = null;
        this.materialBaseId = -1;
//        this.purchasePrice = -1;
//        this.sellingPrice = -1;
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

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName () {
        return materialName;
    }

    public void setMaterialName (String materialName) {
        this.materialName = materialName;
    }

    public String getOldMaterialCode () {
        return oldMaterialCode;
    }

    public void setOldMaterialCode (String oldMaterialCode) {
        this.oldMaterialCode = oldMaterialCode;
    }

    public String getBarCode () {
        return barCode;
    }

    public void setBarCode (String barCode) {
        this.barCode = barCode;
    }

    public int getMaterialBaseId () {
        return materialBaseId;
    }

    public void setMaterialBaseId (int materialBaseId) {
        this.materialBaseId = materialBaseId;
    }

}
