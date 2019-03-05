package org.material.managementweb.info;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.*;
import org.material.managementfacade.model.responsemodel.*;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.service.info.impl.InfoObtainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料信息获取接口", description = "物料信息获取接口")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.GET},
             origins = "*")
public class InfoObtainController {

    @Autowired
    private InfoObtainServiceImpl infoObtainService;

    @GetMapping(value = "/getAllBaseInfo")
    @ApiOperation(value = "获取所有的物料基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseInfoResp getAllBaseInfo () {
        return infoObtainService.getAllBaseInfo();
    }

    @GetMapping(value = "/getBaseInfo")
    @ApiOperation(value = "根据给定参数查询基础信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseInfoResp getBaseInfo (@RequestBody BaseInfoReq params) {
        // 参数必须非空！
        if (MaterialGeneral.isAllEmpty(params)) {
            // 否则传回空类错误码
            BaseInfoResp result = new BaseInfoResp();
            result.setErrCode(MaterialInfoErrCode.errCodeClassIsEmpty);
            return result;
        }
        // 若非空，返回正常结果
        return infoObtainService.getBaseInfoByParams(params);
    }

    @GetMapping(value = "/getMaterialInfo")
    @ApiOperation(value = "根据给定参数查询物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getMaterialInfo (@RequestBody MaterialInfoRequest params) {
        if (MaterialGeneral.isAllEmpty(params)) {
            return MaterialInfoErrCode.errCodeClassIsEmpty;
        }
        // 在没有权限的情况下，统一把组织编码固定下来，无论前端是否传了组织id
        params.setOrganizationId(1);
        return infoObtainService.getMaterialInfoByParams(params);
    }

    @GetMapping(value = "/getAllMaterialBaseByCategoryInfos")
    @ApiOperation(value = "根据物料分类信息获取所有物料基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MaterialInfoObtainByCategoryInfoResponse getAllMaterialBaseByCategoryInfos (@RequestBody MaterialInfoObtainByCategoryInfoRequest params) {
        int id = params.getId();
        if (id != -1) {
            return infoObtainService.getAllMaterialBaseByCategoryInfos(params);
        } else {
            // 返回空对象
            return new MaterialInfoObtainByCategoryInfoResponse();
        }
    }

    @GetMapping(value = "/getMaterialInfoWithCatCodeAndCatName")
    @ApiOperation(value = "根据物料分类编码和物料分类名称获取所有物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MaterialInfoObtainByCatCodeAndNameResponse getMaterialInfoWithCatCodeAndCatName (@RequestBody @NotNull MaterialInfoObtainByCatCodeAndNameRequest params) {
        return infoObtainService.getMaterialInfoWithCatCodeAndCatName(params);
    }

    @GetMapping(value = "/getMaterialBasePropsBySpuCodeAndMaterialCodes")
    @ApiOperation(value = "根据spu编码和物料编码获取物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MaterialBaseObtainBySpuAndMatCodeResponse getMaterialBasePropsBySpuCodeAndMaterialCodes (@RequestBody @NotNull MaterialBaseObtainBySpuAndMatCodeRequest params) {
        if (MaterialGeneral.isContainsEmpty(params)) {
            MaterialBaseObtainBySpuAndMatCodeResponse response = new MaterialBaseObtainBySpuAndMatCodeResponse();
            response.setErrCode(MaterialInfoErrCode.invalidParamWhenObtainBaseBySpuAndMatCode);
            return response;
        } else {
            return infoObtainService.getMaterialBasePropsBySpuCodeAndMaterialCodes(params);
        }
    }

    @GetMapping(value = "/getMaterialBaseByCatIdAndType")
    @ApiOperation(value = "根据物料分类id和属性类型获取物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<MaterialBasePropModel> getMaterialBaseByCatIdAndType(@RequestBody @NotNull BasePropObtainByCatIdAndTypeRequest params) {
        return infoObtainService.getMaterialBaseByCatIdAndType(params);
    }

}
