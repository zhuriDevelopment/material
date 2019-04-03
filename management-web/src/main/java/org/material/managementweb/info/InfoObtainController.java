package org.material.managementweb.info;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.requestmodel.*;
import org.material.managementfacade.model.responsemodel.*;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MatInfoResp;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.service.info.impl.InfoObtainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料信息获取接口", tags = {"物料信息获取接口"})
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.GET},
             origins = "http://localhost:8081")
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
    public BaseInfoResp getBaseInfo
            (Integer materialCatId, String materialName, String skuCode, Date startDate, Date endDate,
             String spuCode, String designCode, String designVersion, String source) {
        BaseInfoReq params = new BaseInfoReq(materialCatId, materialName, skuCode, startDate, endDate,
                spuCode, designCode, designVersion, source);
        if (MaterialGeneral.isAllEmpty(params)) {
            // 按获取所有物料基本信息处理
            return infoObtainService.getAllBaseInfo();
        }
        // 若非空，返回正常结果
        return infoObtainService.getBaseInfoByParams(params);
    }

    @GetMapping(value = "/getMaterialInfo")
    @ApiOperation(value = "根据给定参数查询物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MatInfoResp getMaterialInfo
            (@RequestParam("spuCode") String spuCode,
             @RequestParam("typeArr") List<Integer> typeArr,
             Integer organizationId) {
        MatInfoReq params = new MatInfoReq(spuCode, typeArr, organizationId);
        if (MaterialGeneral.isAllEmpty(params)) {
            MatInfoResp response = new MatInfoResp();
            response.setErrCode(MaterialInfoErrCode.errCodeClassIsEmpty);
            return response;
        }
        params.setOrganizationId(MaterialGeneral.generalOrganizationId);
        return infoObtainService.getMaterialInfoByParams(params);
    }

    @GetMapping(value = "/getAllMaterialBaseByCategoryInfos")
    @ApiOperation(value = "根据物料分类信息获取所有物料基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MatInfoObtainByCatInfoResp getAllMaterialBaseByCategoryInfos
            (@RequestParam("id") Integer id) {
        if (id != -1) {
            return infoObtainService.getAllMaterialBaseByCategoryInfos(id);
        } else {
            // 返回空对象
            MatInfoObtainByCatInfoResp result = new MatInfoObtainByCatInfoResp();
            result.setErrCode(MaterialInfoErrCode.invalidParams);
            return result;
        }
    }

    @GetMapping(value = "/getMaterialInfoWithCatCodeAndCatName")
    @ApiOperation(value = "根据物料分类编码和物料分类名称获取所有物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MatInfoObtainByCatCodeAndNameResp getMaterialInfoWithCatCodeAndCatName
            (@RequestParam("code") @NotNull String code,
             @RequestParam("name") @NotNull String name,
             @RequestParam("typeArr") @NotNull List<Integer> typeArr) {
        // 简化参数，封装成一个类
        MatInfoObtainByCatCodeAndNameReq params = new MatInfoObtainByCatCodeAndNameReq(code, name, typeArr);
        return infoObtainService.getMaterialInfoWithCatCodeAndCatName(params);
    }

    @GetMapping(value = "/getMaterialBasePropsBySpuCodeAndMaterialCodes")
    @ApiOperation(value = "根据spu编码和物料编码获取物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseObtainBySpuAndMatCodeResp getMaterialBasePropsBySpuCodeAndMaterialCodes
            (String spuCode, List<String> materialCodes, Integer propertyType) {
        BaseObtainBySpuAndMatCodeReq params = new BaseObtainBySpuAndMatCodeReq(
                spuCode, materialCodes, propertyType);
        if (MaterialGeneral.isContainsEmpty(params)) {
            BaseObtainBySpuAndMatCodeResp response = new BaseObtainBySpuAndMatCodeResp();
            response.setErrCode(MaterialInfoErrCode.invalidParamWhenObtainBaseBySpuAndMatCode);
            return response;
        } else {
            return infoObtainService.getMaterialBasePropsBySpuCodeAndMaterialCodes(params);
        }
    }

    @GetMapping(value = "/getMaterialBaseByCatIdAndType")
    @ApiOperation(value = "根据物料分类id和属性类型获取物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<MaterialBasePropModel> getMaterialBaseByCatIdAndType
            (@RequestParam("catId") Integer catId,
             @RequestParam("propertyType") Integer propertyType) {
        BasePropObtainByCatIdAndTypeReq params = new BasePropObtainByCatIdAndTypeReq(catId, propertyType);
        return infoObtainService.getMaterialBaseByCatIdAndType(params);
    }

    @GetMapping(value = "/getAllUnitInfos")
    @ApiOperation(value = "获取所有计量单位属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<AllUnitInfosObtainResp> getAllUnitInfos () {
        return infoObtainService.getAllUnitInfos();
    }

}
