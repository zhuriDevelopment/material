package org.material.management.model.processmodel;

import java.util.ArrayList;
import java.util.List;

// 用于获取整个物料分类信息树
public class MaterialCategoryTree {
    private int id;
    private String name;
    private int parentId;
    private int level;
    private List<MaterialCategoryTree> childrenList;

    public MaterialCategoryTree (int id, String name, int parentId, int level) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.childrenList = new ArrayList<>();
    }

    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public int getParentId () {
        return parentId;
    }

    public int getLevel () {
        return level;
    }

    public List<MaterialCategoryTree> getChildrenList () {
        return childrenList;
    }

    public void setParentId (int parentId) {
        this.parentId = parentId;
    }

    public void addChild (MaterialCategoryTree T) {
        childrenList.add(T);
    }
}
