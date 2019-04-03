package org.material.managementweb.info;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.service.info.impl.InfoDeleteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cplayer on 2019-04-03 15:59
 * @version 1.0
 * 物料信息删除的接口类
 */

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料信息删除接口", tags = {"物料信息删除接口"})
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
             methods = {RequestMethod.DELETE},
             origins = "http://localhost:8081")
public class InfoDeleteController {
    @Autowired
    private InfoDeleteServiceImpl infoDeleteService;

    @DeleteMapping(value = "/deleteInfoBySpuCode")
    @ApiOperation(value = "根据spu编码删除物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int deleteInfoBySpuCode (@RequestParam("spuCode") @NotNull String spuCode) {
        return infoDeleteService.deleteInfoBySpuCode(spuCode);
    }

    @DeleteMapping(value = "/deleteInfoBySpuCodes")
    @ApiOperation(value = "根据spu编码数组删除物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int deleteInfoBySpuCodes (@RequestParam("spuCodes") @NotNull List<String> spuCodes) {
        return infoDeleteService.deleteInfoBySpuCodes(spuCodes);
    }
}
