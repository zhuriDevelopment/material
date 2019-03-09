package org.material.managementservice.service.category.impl;

import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.material.managementfacade.model.responsemodel.AllCatInfosObtainResp;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.service.category.CategoryObtainService;
import org.material.managementservice.mapper.category.CategoryObtainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * 注意需要返回数组以满足element tree控件的要求
     *
     * @return java.util.List<org.material.managementfacade.model.processmodel.MaterialCategoryTree>
     * @author cplayer
     * @date 2019-03-02 06:03
     */
    @Override
    public List<MaterialCategoryTree> getMaterialCategory () {
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
        List<MaterialCategoryTree> result = new ArrayList<>();
        result.add(root);
        return result;
    }

    /**
     * 根据物料分类id获取所有物料分类信息的实现函数
     *
     * @param id 物料分类id
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialCategoryModel>
     * @author cplayer
     * @date 2019-03-02 06:01
     */
    @Override
    public List<MaterialCategoryModel> getMaterialCategoryInfosWithId (int id) {
        return categoryObtainMapper.getMaterialCategoryById(id);
    }

    /**
     * 获取所有物料分类信息列表的函数
     *
     * @author cplayer
     * @date 2019-03-10 01:12
     *
     * @return java.util.List<org.material.managementfacade.model.responsemodel.AllCatInfosObtainResp>
     *
     */
    @Override
    public List<AllCatInfosObtainResp> getAllMaterialCategoryInfos () {
        return categoryObtainMapper.getAllMaterialCategory().stream()
                .map(AllCatInfosObtainResp::new)
                .collect(Collectors.toList());
    }
}
