package org.material.management.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料基本属性表")
public class MaterialBasePropModel {
    private int id;
    private int materialCatId;
    private int type;
    private String label;
    private String name;
    private String range;
    private int sort;

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
