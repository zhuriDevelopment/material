package org.material.managementweb.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.material.managementfacade.model.responsemodel.AllCatInfosObtainResp;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementservice.service.category.impl.CategoryObtainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料分类信息获取接口", tags = {"物料分类信息获取接口"})
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.GET},
             origins = "http://localhost:8081")
public class CategoryObtainController {
    @Autowired
    private CategoryObtainServiceImpl categoryObtainService;

    @GetMapping(value = "/getMaterialCategory")
    @ApiOperation(value = "获取所有物料分类信息 —— 树", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<MaterialCategoryTree> getMaterialCategory () {
        return categoryObtainService.getMaterialCategory();
    }

    @GetMapping(value = "/getMaterialCategoryInfosWithId")
    @ApiOperation(value = "根据物料分类id获取所有物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<MaterialCategoryModel> getMaterialCategoryInfosWithId (@RequestParam("id") int id) {
        if (id != -1) {
            return categoryObtainService.getMaterialCategoryInfosWithId(id);
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping(value = "/getAllMaterialCategoryInfos")
    @ApiOperation(value = "获取所有物料分类信息 —— 列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AllCatInfosObtainResp> getAllMaterialCategoryInfos () {
        return categoryObtainService.getAllMaterialCategoryInfos();
    }
}
