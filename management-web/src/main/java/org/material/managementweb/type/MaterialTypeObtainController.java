package org.material.managementweb.type;

import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public class MaterialTypeObtainController {
    @Autowired
    private MaterialInfoServiceImpl materialInfoService;

    @PostMapping(value = "/getMaterialCategory")
    @ApiOperation(value = "获取当前物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MaterialCategoryTree getMaterialCategory () {
        return materialInfoService.getMaterialCategory();
    }
}
