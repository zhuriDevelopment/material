package org.material.managementfacade.service.info;

import org.material.managementfacade.model.requestmodel.*;
import org.material.managementfacade.model.responsemodel.BaseInfoResponse;
import org.material.managementfacade.model.responsemodel.MaterialBaseObtainBySpuAndMatCodeResponse;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoResponse;
import org.material.managementfacade.model.responsemodel.MaterialInfoObtainByCatCodeAndNameResponse;
import org.material.managementfacade.model.responsemodel.MaterialInfoObtainByCategoryInfoResponse;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 * 物料信息获取服务接口
 */

@Service
public interface InfoObtainService {

    BaseInfoResponse getAllBaseInfo ();

    BaseInfoResponse getBaseInfoByParams (BaseInfoRequest params);

    MaterialInfoResponse getMaterialInfoByParams (MaterialInfoRequest params);

    MaterialInfoObtainByCategoryInfoResponse getAllMaterialBaseByCategoryInfos (MaterialInfoObtainByCategoryInfoRequest params);

    MaterialInfoObtainByCatCodeAndNameResponse getMaterialInfoWithCatCodeAndCatName (MaterialInfoObtainByCatCodeAndNameRequest params);

    MaterialBaseObtainBySpuAndMatCodeResponse getMaterialBasePropsBySpuCodeAndMaterialCodes (MaterialBaseObtainBySpuAndMatCodeRequest params);

    List<MaterialBasePropModel> getMaterialBaseByCatIdAndType (BasePropObtainByCatIdAndTypeRequest params);
}
