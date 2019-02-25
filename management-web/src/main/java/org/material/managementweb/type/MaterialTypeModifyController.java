package org.material.managementweb.type;

import io.swagger.annotations.ApiOperation;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public class MaterialTypeModifyController {
    @Autowired
    private MaterialInfoServiceImpl materialInfoService;

    @PostMapping(value = "/addMaterialCategory")
    @ApiOperation(value = "增加物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //返回1为成功添加数据，返回0为失败
    public int addMaterialCategory (@RequestBody Map<String, Object> params) {
        //要求code,name,parentId信息全部获取
        if (params.containsKey("code") && params.containsKey("name") && params.containsKey("parentId")) {
            String code = (String) params.get("code");
            String name = (String) params.get("name");
            int parentId = (int) params.get("parentId");
            return materialInfoService.addMaterialCategory(code, name, parentId);
        } else {
            return 0;
        }
    }
}
