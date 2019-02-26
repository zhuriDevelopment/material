package org.material.managementfacade.service.info;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.springframework.stereotype.Service;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@Service
public interface InfoModifyService {
    int updateMaterialInfo (MaterialInfoModifyRequest params);
}
