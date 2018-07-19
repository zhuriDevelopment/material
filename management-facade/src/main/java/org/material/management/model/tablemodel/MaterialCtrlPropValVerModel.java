package org.material.management.model.tablemodel;

import io.swagger.annotations.ApiModel;

import java.sql.Timestamp;

@ApiModel("物料控制属性值版本表")
public class MaterialCtrlPropValVerModel {
    private int id;
    private String version;
    private String organizationCode;
    private int materialCatId;
    private int materialId;
    private Timestamp startDate;
    private Timestamp endDate;

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

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
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
