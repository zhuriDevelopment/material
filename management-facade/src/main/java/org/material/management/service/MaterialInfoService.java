package org.material.management.service;

import java.util.*;

import org.material.management.model.tablemodel.*;
import org.springframework.stereotype.Service;

@Service
public interface MaterialInfoService {

    List<MaterialBaseModel> getBaseInfoByParams (Map<String, Object> params);

    List<Object> getMaterialInfo (String spuCode, String spuName, List<Integer> types, int orgnizationId);

    //用于获取整个物料分类信息树
    class MaterialCategoryTree{
        private int id;
        private String name;
        private int parentId;
        private int level;
        private List<MaterialCategoryTree> childrenList;

        public MaterialCategoryTree(int id, String name, int parentId, int level) {
            this.id = id;
            this.name = name;
            this.parentId = parentId;
            this.level = level;
            this.childrenList = new ArrayList<MaterialCategoryTree>();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getParentId() {
            return parentId;
        }

        public int getLevel() {
            return level;
        }

        public List<MaterialCategoryTree> getChildrenList() {
            return childrenList;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public void addChild(MaterialCategoryTree T) {
            childrenList.add(T);
        }
    }

    MaterialCategoryTree getMaterialCategory();

    int addMaterialCategory (String code,String name,int parentId);

    int updateMaterialCategory (String newName, String oldName, int parentId);

    int deleteMaterialCategory (String name, int parentId);
}
