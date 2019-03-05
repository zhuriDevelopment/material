package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料控制属性表")
public class MaterialCtrlPropModel {
    private int id;
    private int type;
    private String name;
    private int label;

    public MaterialCtrlPropModel () {
        this.id = -1;
        this.type = -1;
        this.name = null;
        this.label = -1;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getLabel () {
        return label;
    }

    public void setLabel (int label) {
        this.label = label;
    }
}
