package org.material.managementfacade.service.category;

import org.material.managementfacade.model.processmodel.MaterialCategoryTree;
import org.material.managementfacade.model.requestmodel.MaterialCategoryObtainByIdRequest;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@Service
public interface CategoryObtainService {

    MaterialCategoryTree getMaterialCategory ();

    List<MaterialCategoryModel> getMaterialCategoryInfosWithId (MaterialCategoryObtainByIdRequest params);
}
