package org.material.managementfacade.service.info;

import org.material.managementfacade.model.requestmodel.BaseModifyBySpuAndMatCodeReq;
import org.material.managementfacade.model.requestmodel.InfoModifyByCatCodeAndNameReq;
import org.material.managementfacade.model.requestmodel.InfoModifyReq;
import org.material.managementfacade.model.responsemodel.InfoModifyByCatCodeAndNameResp;
import org.springframework.stereotype.Service;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@Service
public interface InfoModifyService {
    int updateMaterialInfo (InfoModifyReq params);

    InfoModifyByCatCodeAndNameResp updateMaterialInfoWithCatCodeAndCatName
            (InfoModifyByCatCodeAndNameReq params);

    int updateMaterialBasePropsBySpuCodeAndMaterialCodes
            (BaseModifyBySpuAndMatCodeReq params);
}
