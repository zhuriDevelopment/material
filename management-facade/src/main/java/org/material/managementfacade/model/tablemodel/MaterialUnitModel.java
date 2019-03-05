package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

import java.util.Objects;

@ApiModel("物料单位表")
public class MaterialUnitModel {
    private int id;
    private String spuCode;
    private int unitId;
    private int relatedId;
    private double conversionFactor;
    private int sort;

    public MaterialUnitModel () {
        this.id = -1;
        this.spuCode = null;
        this.unitId = -1;
        this.relatedId = -1;
        this.conversionFactor = -1;
        this.sort = -1;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MaterialUnitModel that = (MaterialUnitModel) o;
        return id == that.id &&
                unitId == that.unitId &&
                relatedId == that.relatedId &&
                Double.compare(that.conversionFactor, conversionFactor) == 0 &&
                sort == that.sort &&
                spuCode.equals(that.spuCode);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, spuCode, unitId, relatedId, conversionFactor, sort);
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

    public int getUnitId () {
        return unitId;
    }

    public void setUnitId (int unitId) {
        this.unitId = unitId;
    }

    public int getRelatedId () {
        return relatedId;
    }

    public void setRelatedId (int relatedId) {
        this.relatedId = relatedId;
    }

    public double getConversionFactor () {
        return conversionFactor;
    }

    public void setConversionFactor (double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public int getSort () {
        return sort;
    }

    public void setSort (int sort) {
        this.sort = sort;
    }
}
