package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

import java.sql.Timestamp;

@ApiModel("物料控制属性值版本表")
public class MaterialCtrlPropValVerModel {
    private int id;
    private String version;
    private String organizationCode;
    private int materialCatId;
    private String spuCode;
    private Timestamp startDate;
    private Timestamp endDate;

    public MaterialCtrlPropValVerModel () {
        this.id = -1;
        this.materialCatId = -1;
        this.version = null;
        this.organizationCode = null;
        this.spuCode = null;
        this.startDate = null;
        this.endDate = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public int getMaterialCatId() {
        return materialCatId;
    }

    public void setMaterialCatId(int materialCatId) {
        this.materialCatId = materialCatId;
    }

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
