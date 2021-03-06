package org.material.managementweb.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.CatAddReq;
import org.material.managementfacade.model.requestmodel.CatDeleteReq;
import org.material.managementfacade.model.requestmodel.CatModifyNameReq;
import org.material.managementservice.general.MaterialCategoryErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.service.category.impl.CategoryModifyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料分类信息修改接口", tags = {"物料分类信息修改接口"})
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.POST},
             origins = "http://localhost:8081")
public class CategoryModifyController {
    @Autowired
    private CategoryModifyServiceImpl categoryModifyService;

    @PostMapping(value = "/addMaterialCategory")
    @ApiOperation(value = "增加物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int addMaterialCategory (@RequestBody @NotNull CatAddReq request) {
        if (!MaterialGeneral.isContainsEmpty(request) && request.getParentId() != -1) {
            return categoryModifyService.addMaterialCategory(request);
        } else {
            return MaterialCategoryErrCode.invalidAddCategoryRequest;
        }
    }

    @PostMapping(value = "/updateMaterialCategory")
    @ApiOperation(value = "根据物料分类oldName及parentId更新newName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialCategory (@RequestBody @NotNull CatModifyNameReq request) {
        if (!MaterialGeneral.isContainsEmpty(request) && request.getParentId() != -1) {
            return categoryModifyService.updateMaterialCategoryName(request);
        } else {
            return MaterialCategoryErrCode.invalidModifyCategoryRequest;
        }
    }

    @PostMapping(value = "/deleteMaterialCategory")
    @ApiOperation(value = "删除物料分类编码信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int deleteMaterialCategory (@RequestBody @NotNull CatDeleteReq request) {
        //必须获取全部属性值
        if (!MaterialGeneral.isContainsEmpty(request)
                && request.getId() != -1
                && request.getParentId() != -1) {
            // 数据合法，允许继续
            return categoryModifyService.deleteMaterialCategory(request);
        } else {
            return MaterialCategoryErrCode.invalidDeleteCategoryRequest;
        }
    }


}
