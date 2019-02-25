package org.material.managementfacade.service.info;

import org.material.managementfacade.model.requestmodel.BaseInfoRequest;
import org.material.managementfacade.model.responsemodel.BaseInfoResponse;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

public interface MaterialInfoObtainService {
    public BaseInfoResponse getAllBaseInfo ();

    public BaseInfoResponse getBaseInfoByParams (BaseInfoRequest params);
}
