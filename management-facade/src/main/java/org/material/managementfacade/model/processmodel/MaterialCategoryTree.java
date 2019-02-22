package org.material.managementfacade.model.processmodel;

import java.util.ArrayList;
import java.util.List;

// 用于获取整个物料分类信息树
public class MaterialCategoryTree {
    private int id;
    private String label;
    private int parentId;
    private int level;
    private String code;
    private List<MaterialCategoryTree> children;

    public MaterialCategoryTree (int id, String label, int parentId, int level, String code) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.level = level;
        this.children = new ArrayList<>();
        this.code = code;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public int getParentId () {
        return parentId;
    }

    public void setParentId (int parentId) {
        this.parentId = parentId;
    }

    public int getLevel () {
        return level;
    }

    public void setLevel (int level) {
        this.level = level;
    }

    public List<MaterialCategoryTree> getChildren () {
        return children;
    }

    public void setChildren (List<MaterialCategoryTree> children) {
        this.children = children;
    }

    public void addChild (MaterialCategoryTree T) {
        children.add(T);
    }
}
