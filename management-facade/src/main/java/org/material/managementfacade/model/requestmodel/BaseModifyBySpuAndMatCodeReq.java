package org.material.managementfacade.model.requestmodel;

import java.util.List;

/**
 * @author cplayer on 2019-03-03 04:58.
 * @version 1.0
 * 根据spu编码和物料编码更新物料基本属性请求封装类
 */

public class BaseModifyBySpuAndMatCodeReq {
    private String spuCode;
    private int propertyType;
    private List<BaseModifyBySpuAndMatCodeUpdateProps> updateValues;

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public int getPropertyType () {
        return propertyType;
    }

    public void setPropertyType (int propertyType) {
        this.propertyType = propertyType;
    }

    public List<BaseModifyBySpuAndMatCodeUpdateProps> getUpdateValues () {
        return updateValues;
    }

    public void setUpdateValues (List<BaseModifyBySpuAndMatCodeUpdateProps> updateValues) {
        this.updateValues = updateValues;
    }
}

