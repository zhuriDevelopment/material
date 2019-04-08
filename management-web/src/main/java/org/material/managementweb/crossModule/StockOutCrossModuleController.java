package org.material.managementweb.crossModule;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementservice.service.crossModule.impl.StockOutCrossModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cplayer on 2019-04-03 23:59.
 * @version 1.0
 * 出库部分的跨模块调用
 */

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "出库跨模块获取物料信息接口", tags = {"出库跨模块获取物料信息接口"})
public class StockOutCrossModuleController {
    @Autowired
    private StockOutCrossModuleServiceImpl stockOutCrossModuleService;

    @RequestMapping(value = "/getFormatByMaterialCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据物料编码获取物料规格")
    public List<String> getFormatByMaterialCode (@RequestParam("materialCodes") List<String> materialCodes) {
        return stockOutCrossModuleService.getFormatByMaterialCode(materialCodes);
    }

    @RequestMapping(value = "/getUnitNameByUnitId", method = RequestMethod.GET)
    @ApiOperation(value = "根据计量单位id获取计量单位名称")
    public String getUnitNameByUnitId (@RequestParam("unitId") int unitId) {
        return stockOutCrossModuleService.getUnitNameByUnitId(unitId);
    }

    @GetMapping(value = "/getMaterialCodeByCrossModuleParams")
    @ApiOperation(value = "根据跨模块参数获取物料编码列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<String> getMaterialCodeByCrossModuleParams
            (String materialCode, String mnemonic,
             String spuCode, String spuName,
             String materialCategory, String description) {
        if (materialCode == null && mnemonic == null &&
                spuCode == null && spuName == null &&
                materialCategory == null && description == null) {
            // 返回空集合
            return new ArrayList<>();
        } else {
            return stockOutCrossModuleService.getMaterialCodeByCrossModuleParams(materialCode, mnemonic, spuCode,
                    spuName, materialCategory, description);
        }
    }

    @GetMapping(value = "/getMaterialNamesByMaterialCodes")
    @ApiOperation(value = "根据物料编码获取物料名称")
    public List<String> getMaterialNamesByMaterialCodes (@RequestParam("materialCodes") List<String> materialCodes) {
        return stockOutCrossModuleService.getMaterialNamesByMaterialCodes(materialCodes);
    }
}
