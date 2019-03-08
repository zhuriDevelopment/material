package org.material.managementweb.info;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.MaterialBaseModifyBySpuAndMatCodeRequest;
import org.material.managementfacade.model.requestmodel.InfoModifyByCatCodeAndNameReq;
import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.responsemodel.InfoModifyByCatCodeAndNameResp;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.service.info.impl.InfoModifyServiceImpl;
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
        if (MaterialGeneral.isAllEmpty(params)) {
            return MaterialInfoErrCode.errCodeClassIsEmpty;
        }
        return infoModifyService.updateMaterialInfo(params);
    }

    @PutMapping(value = "/updateMaterialInfoWithCatCodeAndCatName")
    @ApiOperation(value = "根据物料分类id和物料名称更新物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public InfoModifyByCatCodeAndNameResp updateMaterialInfoWithCatCodeAndCatName
            (@RequestBody @NotNull InfoModifyByCatCodeAndNameReq params) {
        if (MaterialGeneral.isContainsEmpty(params)) {
            InfoModifyByCatCodeAndNameResp result = new InfoModifyByCatCodeAndNameResp();
            result.setErrCode(MaterialInfoErrCode.errorParamInUpdatingInfoWithCatIdAndName);
            return result;
        } else {
            return infoModifyService.updateMaterialInfoWithCatCodeAndCatName(params);
        }
    }

    @PutMapping(value = "/updateMaterialBasePropsBySpuCodeAndMaterialCodes")
    @ApiOperation(value = "根据spu编码和物料编码更新物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialBasePropsBySpuCodeAndMaterialCodes (@RequestBody @NotNull MaterialBaseModifyBySpuAndMatCodeRequest params) {
        if (!MaterialGeneral.isContainsEmpty(params)) {
            return MaterialInfoErrCode.failedUpdateMaterialBaseWithSpuAndCatCode;
        } else {
            return infoModifyService.updateMaterialBasePropsBySpuCodeAndMaterialCodes(params);
        }
    }
}
