package org.material.managementfacade.model.requestmodel;

import java.util.List;

/**
 * @author cplayer on 2019-03-03 01:39.
 * @version 1.0
 * 根据spu编码和物料编码获取物料基本属性请求封装类
 */

public class BaseObtainBySpuAndMatCodeReq {
    private String spuCode;
    private List<String> materialCodes;
    private Integer propertyType;

    public BaseObtainBySpuAndMatCodeReq () {
        this.spuCode = null;
        this.materialCodes = null;
        this.propertyType = null;
    }

    public BaseObtainBySpuAndMatCodeReq (String spuCode, List<String> materialCodes, Integer propertyType) {
        this.spuCode = spuCode;
        this.materialCodes = materialCodes;
        this.propertyType = propertyType;
    }

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public List<String> getMaterialCodes () {
        return materialCodes;
    }

    public void setMaterialCodes (List<String> materialCodes) {
        this.materialCodes = materialCodes;
    }

    public Integer getPropertyType () {
        return propertyType;
    }

    public void setPropertyType (Integer propertyType) {
        this.propertyType = propertyType;
    }
}
