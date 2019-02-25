package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料基本属性表")
public class MaterialBasePropModel {
    private int id;
    private int materialCatId;
    private int type;
    private String label;
    private String name;
    private String valueRange;
    private int sort;

    public MaterialBasePropModel () {
        this.id = -1;
        this.materialCatId = -1;
        this.type = -1;
        this.label = null;
        this.name = null;
        this.valueRange = null;
        this.sort = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialCatId() {
        return materialCatId;
    }

    public void setMaterialCatId(int materialCatId) {
        this.materialCatId = materialCatId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
