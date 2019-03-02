package org.material.managementfacade.model.requestmodel;

import java.util.List;

/**
 * @author cplayer on 2019-03-03 01:39.
 * @version 1.0
 * 根据spu编码和物料编码获取物料基本属性请求封装类
 */

public class MaterialBaseObtainBySpuAndMatCodeRequest {
    private String spuCode;
    private List<String> materialCodes;
    private int propertyType;

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

    public int getPropertyType () {
        return propertyType;
    }

    public void setPropertyType (int propertyType) {
        this.propertyType = propertyType;
    }
}
