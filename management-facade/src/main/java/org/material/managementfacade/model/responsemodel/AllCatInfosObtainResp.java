package org.material.managementfacade.model.responsemodel;

import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;

/**
 * @author cplayer on 2019-03-10 01:08.
 * @version 1.0
 * 获取所有物料分类信息的单条请求封装类
 */

public class AllCatInfosObtainResp {
    private int value;
    private String label;

    public AllCatInfosObtainResp () {
        this.value = -1;
        this.label = null;
    }

    public AllCatInfosObtainResp (MaterialCategoryModel category) {
        this.value = category.getId();
        this.label = category.getName();
    }

    public int getValue () {
        return value;
    }

    public void setValue (int value) {
        this.value = value;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }
}
