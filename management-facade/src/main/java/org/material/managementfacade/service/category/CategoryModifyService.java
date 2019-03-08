package org.material.managementfacade.service.category;

import org.material.managementfacade.model.requestmodel.CatAddReq;
import org.material.managementfacade.model.requestmodel.CatDeleteReq;
import org.material.managementfacade.model.requestmodel.CategoryModifyNameRequest;
import org.springframework.stereotype.Service;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@Service
public interface CategoryModifyService {
    int addMaterialCategory (CatAddReq request);

    int updateMaterialCategoryName (CategoryModifyNameRequest request);

    int deleteMaterialCategory (CatDeleteReq request);
}
