package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-03 05:50.
 * @version 1.0
 * 根据物料分类id和属性类型获取物料基本属性请求封装类
 */

public class BasePropObtainByCatIdAndTypeReq {
    private Integer catId;
    private Integer propertyType;

    public BasePropObtainByCatIdAndTypeReq (Integer catId, Integer propertyType) {
        this.catId = catId;
        this.propertyType = propertyType;
    }

    public BasePropObtainByCatIdAndTypeReq () {
        this.catId = -1;
        this.propertyType = -1;
    }

    public Integer getCatId () {
        return catId;
    }

    public void setCatId (Integer catId) {
        this.catId = catId;
    }

    public Integer getPropertyType () {
        return propertyType;
    }

    public void setPropertyType (Integer propertyType) {
        this.propertyType = propertyType;
    }
}
