package org.material.managementfacade.service.crossModule;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cplayer on 2019-04-04.
 * @version 1.0
 * 出库跨模块服务
 */
@Service
public interface StockOutCrossModuleService {
    List<String> getFormatByMaterialCode (List<String> materialCodes);

    String getUnitNameByUnitId (int unitId);

    List<String> getMaterialCodeByCrossModuleParams (String materialCode, String mnemonic,
                                                     String spuCode, String spuName,
                                                     String materialCategory, String description);

    List<String> getMaterialNamesByMaterialCodes (List<String> materialCodes);
}
