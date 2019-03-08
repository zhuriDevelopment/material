package org.material.managementfacade.service.info;

import org.material.managementfacade.model.requestmodel.MaterialBaseModifyBySpuAndMatCodeRequest;
import org.material.managementfacade.model.requestmodel.InfoModifyByCatCodeAndNameReq;
import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.responsemodel.InfoModifyByCatCodeAndNameResp;
import org.springframework.stereotype.Service;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@Service
public interface InfoModifyService {
    int updateMaterialInfo (MaterialInfoModifyRequest params);

    InfoModifyByCatCodeAndNameResp updateMaterialInfoWithCatCodeAndCatName
            (InfoModifyByCatCodeAndNameReq params);

    int updateMaterialBasePropsBySpuCodeAndMaterialCodes
            (MaterialBaseModifyBySpuAndMatCodeRequest params);
}
