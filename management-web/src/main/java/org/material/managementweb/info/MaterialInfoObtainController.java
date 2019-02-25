package org.material.managementweb.info;

import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.BaseInfoRequest;
import org.material.managementfacade.model.responsemodel.BaseInfoResponse;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.general.MaterialInfoGeneral;
import org.material.managementservice.service.info.impl.MaterialInfoObtainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public class MaterialInfoObtainController {

    @Autowired
    private MaterialInfoObtainServiceImpl materialInfoService;

    @PostMapping(value = "/getAllBaseInfo")
    @ApiOperation(value = "获取所有的物料基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseInfoResponse getAllBaseInfo () {
        return materialInfoService.getAllBaseInfo();
    }

    @PostMapping(value = "/getBaseInfo")
    @ApiOperation(value = "根据给定参数查询基础信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getBaseInfo (@RequestBody BaseInfoRequest params) {
        // 参数必须非空！
        if (MaterialInfoGeneral.isEmpty(params)) {
            // 否则传回空类错误码
            return MaterialInfoErrCode.errCodeClassIsEmpty;
        }
        // 若非空，返回正常结果
        BaseInfoResponse result = materialInfoService.getBaseInfoByParams(params);
        return result;
    }


}
