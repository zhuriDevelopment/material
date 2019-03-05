package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料控制属性值表")
public class MaterialCtrlPropValModel {
    private int id;
    private int versionId;
    private int materialCtrlPropId;
    private String value;

    public MaterialCtrlPropValModel () {
        this.id = -1;
        this.versionId = -1;
        this.materialCtrlPropId = -1;
        this.value = null;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getVersionId () {
        return versionId;
    }

    public void setVersionId (int versionId) {
        this.versionId = versionId;
    }

    public int getMaterialCtrlPropId () {
        return materialCtrlPropId;
    }

    public void setMaterialCtrlPropId (int materialCtrlPropId) {
        this.materialCtrlPropId = materialCtrlPropId;
    }

    public String getValue () {
        return value;
    }

    public void setValue (String value) {
        this.value = value;
    }
}
