package org.material.managementfacade.service.info;

import org.material.managementfacade.model.requestmodel.*;
import org.material.managementfacade.model.responsemodel.*;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MatInfoResp;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MatInfoRespParams;
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

    BaseInfoResp getAllBaseInfo ();

    BaseInfoResp getBaseInfoByParams (BaseInfoReq params);

    MatInfoResp getMaterialInfoByParams (MatInfoReq params);

    MaterialInfoObtainByCategoryInfoResponse getAllMaterialBaseByCategoryInfos (MaterialInfoObtainByCategoryInfoRequest params);

    MaterialInfoObtainByCatCodeAndNameResponse getMaterialInfoWithCatCodeAndCatName (MaterialInfoObtainByCatCodeAndNameRequest params);

    MaterialBaseObtainBySpuAndMatCodeResponse getMaterialBasePropsBySpuCodeAndMaterialCodes (MaterialBaseObtainBySpuAndMatCodeRequest params);

    List<MaterialBasePropModel> getMaterialBaseByCatIdAndType (BasePropObtainByCatIdAndTypeRequest params);
}
