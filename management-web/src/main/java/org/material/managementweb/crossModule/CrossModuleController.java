package org.material.managementweb.crossModule;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementservice.service.crossModule.impl.CrossModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "跨模块获取物料信息接口", description = "跨模块获取物料信息接口")
public class CrossModuleController {
    @Autowired
    private CrossModuleServiceImpl crossModuleServiceImpl;

    @RequestMapping(value = "/getMaterialByMaterialCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据物料编码获取物料信息")
    public List<MaterialModel> getMaterialByMaterialCode (@RequestParam String materialCode) {
        return crossModuleServiceImpl.getMaterialByMaterialCode(materialCode);
    }

    @RequestMapping(value = "/getMaterialByMaterialName", method = RequestMethod.GET)
    @ApiOperation(value = "根据物料名称获取物料信息")
    public List<MaterialModel> getMaterialByMaterialName (@RequestParam String materialName) {
        try {
            materialName = java.net.URLDecoder.decode(materialName, "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }
        return crossModuleServiceImpl.getMaterialByMaterialName(materialName);
    }

    @RequestMapping(value = "/getMaterialBySpuCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据spu编码获取物料信息")
    public List<MaterialModel> getMaterialBySpuCode (@RequestParam String spuCode) {
        return crossModuleServiceImpl.getMaterialBySpuCode(spuCode);
    }

    @RequestMapping(value = "/getMaterialBaseBySpuCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据spu编码获取物料基本信息")
    public List<MaterialBaseModel> getMaterialBaseBySpuCode (@RequestParam String spuCode) {
        return crossModuleServiceImpl.getMaterialBaseBySpuCode(spuCode);
    }

    @RequestMapping(value = "/getMaterialBaseBySpuName", method = RequestMethod.GET)
    @ApiOperation(value = "根据spu名称获取物料基本信息")
    public List<MaterialBaseModel> getMaterialBaseBySpuName (@RequestParam String spuName) {
        try {
            spuName = java.net.URLDecoder.decode(spuName, "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }
        return crossModuleServiceImpl.getMaterialBaseBySpuName(spuName);
    }

    @RequestMapping(value = "/getMaterialBasePropByMaterialCatId", method = RequestMethod.GET)
    @ApiOperation(value = "根据materialCatId获取物料基本属性")
    public List<MaterialBasePropModel> getMaterialBasePropByMaterialCatId (@RequestParam int materialCatId) {
        return crossModuleServiceImpl.getMaterialBasePropByMaterialCatId(materialCatId);
    }
}
