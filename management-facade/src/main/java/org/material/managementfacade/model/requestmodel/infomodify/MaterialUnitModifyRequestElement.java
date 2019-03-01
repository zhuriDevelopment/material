package org.material.managementfacade.model.requestmodel.infomodify;

/**
 * @author cplayer on 2019-03-01 22:17.
 * @version 1.0
 * updateMaterialInfo中计量单位修改请求的数组元素的封装类
 */

public class MaterialUnitModifyRequestElement {
    private int sort;
    private int unitId;
    private double conversionFactor;

    public int getSort () {
        return sort;
    }

    public void setSort (int sort) {
        this.sort = sort;
    }

    public int getUnitId () {
        return unitId;
    }

    public void setUnitId (int unitId) {
        this.unitId = unitId;
    }

    public double getConversionFactor () {
        return conversionFactor;
    }

    public void setConversionFactor (double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }
}
