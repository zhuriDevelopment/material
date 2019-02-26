package org.material.managementweb.info;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.material.managementservice.service.info.impl.InfoModifyServiceImpl;
import org.material.managementservice.service.info.impl.InfoObtainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料信息修改接口", description = "物料信息修改接口")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.PUT},
             origins = "*")
public class InfoModifyController {
    @Autowired
    private InfoModifyServiceImpl infoModifyService;

    @PutMapping(value = "/updateMaterialInfo")
    @ApiOperation(value = "根据给定参数更新物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialInfo (@RequestBody MaterialInfoModifyRequest params) {
        if (MaterialGeneral.isEmpty(params)) {
            return MaterialErrCode.errCodeClassIsEmpty;
        }
        return infoModifyService.updateMaterialInfo(params);
    }
}
