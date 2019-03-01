package org.material.managementfacade.service.category;

import org.material.managementfacade.model.requestmodel.CategoryAddRequest;
import org.material.managementfacade.model.requestmodel.CategoryDeleteRequest;
import org.material.managementfacade.model.requestmodel.CategoryModifyNameRequest;
import org.springframework.stereotype.Service;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@Service
public interface CategoryModifyService {
    int addMaterialCategory (CategoryAddRequest request);

    int updateMaterialCategoryName (CategoryModifyNameRequest request);

    int deleteMaterialCategory (CategoryDeleteRequest request);
}
