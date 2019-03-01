package org.material.managementweb.category;

import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.material.managementfacade.model.requestmodel.MaterialCategoryObtainByIdRequest;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementservice.service.category.impl.CategoryObtainServiceImpl;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public class CategoryObtainController {
    @Autowired
    private CategoryObtainServiceImpl categoryObtainService;

    @GetMapping(value = "/getMaterialCategory")
    @ApiOperation(value = "获取当前物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MaterialCategoryTree getMaterialCategory () {
        return categoryObtainService.getMaterialCategory();
    }

    @GetMapping(value = "/getMaterialCategoryInfosWithId")
    @ApiOperation(value = "根据物料分类id获取所有物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<MaterialCategoryModel> getMaterialCategoryInfosWithId (@RequestBody MaterialCategoryObtainByIdRequest params) {
        if (params.getId() != -1) {
            return categoryObtainService.getMaterialCategoryInfosWithId(params);
        } else {
            return new ArrayList<>();
        }
    }
}
