package org.material.managementweb.info;

import io.swagger.annotations.ApiOperation;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public class InfoModifyController {

    @Autowired
    private MaterialInfoServiceImpl materialInfoService;

    @PostMapping(value = "/updateMaterialInfo")
    @ApiOperation(value = "根据给定参数更新物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialInfo (@RequestBody Map<Object, Object> params) {
        try {
            String spuCode = (String) params.get("spuCode");
            String spuName = (String) params.get("spuName");
            List<Object> data = (List<Object>) params.get("data");
            return materialInfoService.updateMaterialInfo(spuCode, spuName, data);
        } catch (ClassCastException e) {
            return -1;
        }
    }
}
