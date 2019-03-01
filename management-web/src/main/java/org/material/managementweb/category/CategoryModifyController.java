package org.material.managementweb.category;

import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.CategoryAddRequest;
import org.material.managementfacade.model.requestmodel.CategoryDeleteRequest;
import org.material.managementfacade.model.requestmodel.CategoryModifyNameRequest;
import org.material.managementservice.general.MaterialCategoryErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.service.category.impl.CategoryModifyServiceImpl;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public class CategoryModifyController {
    @Autowired
    private MaterialInfoServiceImpl materialInfoService;
    @Autowired
    private CategoryModifyServiceImpl categoryModifyService;

//    @PostMapping(value = "/addMaterialCategory")
//    @ApiOperation(value = "增加物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    //返回1为成功添加数据，返回0为失败
//    public int addMaterialCategory (@RequestBody Map<String, Object> params) {
//        //要求code,name,parentId信息全部获取
//        if (params.containsKey("code") && params.containsKey("name") && params.containsKey("parentId")) {
//            String code = (String) params.get("code");
//            String name = (String) params.get("name");
//            int parentId = (int) params.get("parentId");
//            return materialInfoService.addMaterialCategory(code, name, parentId);
//        } else {
//            return 0;
//        }
//    }
    @PostMapping(value = "/addMaterialCategory")
    @ApiOperation(value = "增加物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int addMaterialCategory (@RequestBody @NotNull CategoryAddRequest request) {
        if (!MaterialGeneral.isContainsEmpty(request) && request.getParentId() != -1) {
            return categoryModifyService.addMaterialCategory(request);
        } else {
            return MaterialCategoryErrCode.invalidAddCategoryRequest;
        }
    }

//    @PostMapping(value = "/updateMaterialCategory")
//    @ApiOperation(value = "根据物料oldName及parentId更新newName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    //返回1为成功更新数据，返回0为失败
//    public int updateMaterialCategory (@RequestBody Map<String, Object> params) {
//        //确保三个属性值全部获取
//        if (params.containsKey("newName") && params.containsKey("oldName") && params.containsKey("parentId")) {
//            String newName = (String) params.get("newName");
//            String oldName = (String) params.get("oldName");
//            int parentId = (int) params.get("parentId");
//            return materialInfoService.updateMaterialCategory(newName, oldName, parentId);
//        } else {
//            return 0;
//        }
//    }
    @PostMapping(value = "/updateMaterialCategory")
    @ApiOperation(value = "根据物料分类oldName及parentId更新newName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialCategory (@RequestBody @NotNull CategoryModifyNameRequest request) {
        if (!MaterialGeneral.isContainsEmpty(request) && request.getParentId() != -1) {
            return categoryModifyService.updateMaterialCategoryName(request);
        } else {
            return MaterialCategoryErrCode.invalidModifyCategoryRequest;
        }
    }

    @PostMapping(value = "/deleteMaterialCategory")
    @ApiOperation(value = "删除物料分类编码信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int deleteMaterialCategory (@RequestBody @NotNull CategoryDeleteRequest request) {
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
