package org.material.managementfacade.model.requestmodel;


import java.util.Date;

/**
 * @author cplayer on 2019-02-25 02:46.
 * @version 1.0
 * getBaseInfo接口的请求类
 */

public class BaseInfoReq {
    private Integer materialCatId;
    private String materialName;
    private String skuCode;
    private Date startDate;
    private Date endDate;
    private String spuCode;
    private String designCode;
    private String designVersion;
    private String source;

    public BaseInfoReq () {
        this.materialCatId = -1;
        this.materialName = null;
        this.skuCode = null;
        this.startDate = null;
        this.endDate = null;
        this.spuCode = null;
        this.designCode = null;
        this.designVersion = null;
        this.source = null;
    }

    public BaseInfoReq (Integer materialCatId, String materialName, String skuCode, Date startDate, Date endDate, String spuCode, String designCode, String designVersion, String source) {
        this.materialCatId = materialCatId;
        this.materialName = materialName;
        this.skuCode = skuCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.spuCode = spuCode;
        this.designCode = designCode;
        this.designVersion = designVersion;
        this.source = source;
    }

    public Integer getMaterialCatId () {
        return materialCatId;
    }

    public void setMaterialCatId (Integer materialCatId) {
        this.materialCatId = materialCatId;
    }

    public String getMaterialName () {
        return materialName;
    }

    public void setMaterialName (String materialName) {
        this.materialName = materialName;
    }

    public String getSkuCode () {
        return skuCode;
    }

    public void setSkuCode (String skuCode) {
        this.skuCode = skuCode;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
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
