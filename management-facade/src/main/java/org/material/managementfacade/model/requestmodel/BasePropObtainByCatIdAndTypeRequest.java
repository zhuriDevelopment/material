package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-03 05:50.
 * @version 1.0
 * 根据物料分类id和属性类型获取物料基本属性请求封装类
 */

public class BasePropObtainByCatIdAndTypeRequest {
    private int catId;
    private int propertyType;

    public int getCatId () {
        return catId;
    }

    public void setCatId (int catId) {
        this.catId = catId;
    }

    public int getPropertyType () {
        return propertyType;
    }

    public void setPropertyType (int propertyType) {
        this.propertyType = propertyType;
    }
}
