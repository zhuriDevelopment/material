package org.material.managementservice.service.category.impl;

import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.material.managementfacade.model.requestmodel.MaterialCategoryObtainByIdRequest;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.service.category.CategoryObtainService;
import org.material.managementservice.mapper.category.CategoryObtainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@Component
public class CategoryObtainServiceImpl implements CategoryObtainService {
    @Autowired
    private CategoryObtainMapper categoryObtainMapper;

    /**
     * 获取物料分类信息树的函数
     *
     * @author cplayer
     * @date 2019-03-02 06:03
     *
     * @return org.material.managementfacade.model.processmodel.MaterialCategoryTree
     *
     */
    @Override
    public MaterialCategoryTree getMaterialCategory () {
        // 构造一个HashMap用于通过id获取对象
        Map<Integer, MaterialCategoryTree> idCategoryMap = new HashMap<>();
        idCategoryMap.clear();
        MaterialCategoryTree root = new MaterialCategoryTree(0, "物料总分类", -1, 0, "-1");
        idCategoryMap.put(root.getId(), root);
        List<MaterialCategoryModel> materialCategoryList = categoryObtainMapper.getAllMaterialCategory();
        for (MaterialCategoryModel materialCategory : materialCategoryList) {
            int id = materialCategory.getId();
            String name = materialCategory.getName();
            int parentId = materialCategory.getParentId();
            String code = materialCategory.getCode();
            MaterialCategoryTree parent = idCategoryMap.get(parentId);
            int level = parent.getLevel() + 1;
            MaterialCategoryTree node = new MaterialCategoryTree(id, name, parentId, level, code);
            idCategoryMap.put(id, node);
            parent.addChild(node);
        }
        return root;
    }

    /**
     * 根据物料分类id获取所有物料分类信息的实现函数
     *
     * @author cplayer
     * @date 2019-03-02 06:01
     * @param params 请求参数，其中包括物料分类id
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialCategoryModel>
     *
     */
    @Override
    public List<MaterialCategoryModel> getMaterialCategoryInfosWithId (MaterialCategoryObtainByIdRequest params) {
        return categoryObtainMapper.getMaterialCategoryById(params.getId());
    }
}
