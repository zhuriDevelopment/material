package com.zhuri.material.materialmanagement.bean.tablebean;

public class MaterialCtrlPropValBean {
    private int id;
    private int versionId;
    private int materialCtrlPropId;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getMaterialCtrlPropId() {
        return materialCtrlPropId;
    }

    public void setMaterialCtrlPropId(int materialCtrlPropId) {
        this.materialCtrlPropId = materialCtrlPropId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
