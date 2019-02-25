package org.material.managementweb.info;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.BaseInfoRequest;
import org.material.managementfacade.model.requestmodel.MaterialInfoRequest;
import org.material.managementfacade.model.responsemodel.BaseInfoResponse;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoResponse;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.service.info.impl.InfoObtainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料信息获取接口", description = "物料信息获取接口")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST},
             origins = "*")
public class InfoObtainController {

    @Autowired
    private InfoObtainServiceImpl materialInfoService;

    @GetMapping(value = "/test")
    public Object test () {
        return materialInfoService.test();
    }

    @GetMapping(value = "/getAllBaseInfo")
    @ApiOperation(value = "获取所有的物料基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseInfoResponse getAllBaseInfo () {
        return materialInfoService.getAllBaseInfo();
    }

    @GetMapping(value = "/getBaseInfo")
    @ApiOperation(value = "根据给定参数查询基础信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getBaseInfo (@RequestBody BaseInfoRequest params) {
        // 参数必须非空！
        if (MaterialGeneral.isEmpty(params)) {
            // 否则传回空类错误码
            return MaterialErrCode.errCodeClassIsEmpty;
        }
        // 若非空，返回正常结果
        return materialInfoService.getBaseInfoByParams(params);
    }

    @GetMapping(value = "/getMaterialInfo")
    @ApiOperation(value = "根据给定参数查询物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getMaterialInfo (@RequestBody MaterialInfoRequest params) {
        if (MaterialGeneral.isEmpty(params)) {
            return MaterialErrCode.errCodeClassIsEmpty;
        }
        // 在没有权限的情况下，统一把组织编码固定下来，无论前端是否传了组织id
        params.setOrganizationId(1);
        return materialInfoService.getMaterialInfoByParams(params);
    }

}
