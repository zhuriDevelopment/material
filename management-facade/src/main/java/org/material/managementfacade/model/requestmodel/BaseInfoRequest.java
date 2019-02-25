package org.material.managementfacade.model.requestmodel;

import java.lang.reflect.Field;

/**
 * @author cplayer on 2019-02-25 02:46.
 * @version 1.0
 * getBaseInfo接口的请求类
 */

public class BaseInfoRequest {
    private int materialType;
    private String materialCode;
    private String skuCode;
    private String startDate;
    private String endDate;
    private String spuCode;
    private String designCode;
    private String designVersion;
    private String source;

    public int getMaterialType () {
        return materialType;
    }

    public void setMaterialType (int materialType) {
        this.materialType = materialType;
    }

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }

    public String getSkuCode () {
        return skuCode;
    }

    public void setSkuCode (String skuCode) {
        this.skuCode = skuCode;
    }

    public String getStartDate () {
        return startDate;
    }

    public void setStartDate (String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate () {
        return endDate;
    }

    public void setEndDate (String endDate) {
        this.endDate = endDate;
    }

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public String getDesignCode () {
        return designCode;
    }

    public void setDesignCode (String designCode) {
        this.designCode = designCode;
    }

    public String getDesignVersion () {
        return designVersion;
    }

    public void setDesignVersion (String designVersion) {
        this.designVersion = designVersion;
    }

    public String getSource () {
        return source;
    }

    public void setSource (String source) {
        this.source = source;
    }
}
