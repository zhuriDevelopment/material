package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

/**
 * @author cplayer on 2019-02-25 15:05
 * @version 1.0
 * 物料关键属性表
 */

@ApiModel("物料关键属性表")
public class MaterialKeyPropModel {
    private int id;
    private String value;
    private String name;
    private String note;
    private String spuCode;

    public MaterialKeyPropModel () {
        this.id = -1;
        this.value = null;
        this.name = null;
        this.note = null;
        this.spuCode = null;
    }

    public int getId () {
        return id;
    }

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getValue () {
        return value;
    }

    public void setValue (String value) {
        this.value = value;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getNote () {
        return note;
    }

    public void setNote (String note) {
        this.note = note;
    }
}
