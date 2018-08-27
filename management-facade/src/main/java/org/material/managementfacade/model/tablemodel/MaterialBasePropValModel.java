package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料基本属性值表")
public class MaterialBasePropValModel {
    private int id;
    private String spuCode;
    private String materialCode;
    private int materialBasePropId;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public int getMaterialBasePropId() {
        return materialBasePropId;
    }

    public void setMaterialBasePropId(int materialBasePropId) {
        this.materialBasePropId = materialBasePropId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
