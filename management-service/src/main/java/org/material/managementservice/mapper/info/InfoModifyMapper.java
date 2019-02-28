package org.material.managementservice.mapper.info;

import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValModel;
import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValVerModel;
import org.material.managementfacade.model.tablemodel.MaterialSkuModel;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
* @author cplayer on 2019-02-25 02:22.
* @version 1.0 
* 物料信息修改Mapper类
*/
@Repository
public interface InfoModifyMapper {
    int countBaseBySpuCode (String spuCode);

    int deleteAllMaterialBySpuCode (String spuCode);

    int countMaterialBasePropValByParams (int materialBasePropId, String spuCode, String materialCode);

    int insertMaterialBasePropValByParams (MaterialBasePropValModel param);

    int updateMaterialBasePropValByParams (MaterialBasePropValModel param);

    int insertMaterialSkuByParams (MaterialSkuModel param);

    int deleteMaterialSkuByParams (MaterialSkuModel param);

    List<MaterialCtrlPropValVerModel> getCtrlPropValVerWithCtrlPropValVerParams (MaterialCtrlPropValVerModel param, Timestamp timestamp);

    int insertCtrlPropValVerByParams (MaterialCtrlPropValVerModel param);

    List<MaterialCtrlPropValModel> getCtrlPropValWithVersionId (int versionId);

    int updateCtrlPropValByParams (MaterialCtrlPropValModel param);
}
