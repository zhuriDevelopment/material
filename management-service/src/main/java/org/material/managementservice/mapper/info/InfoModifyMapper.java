package org.material.managementservice.mapper.info;

import org.apache.ibatis.annotations.Mapper;
import org.material.managementfacade.model.tablemodel.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cplayer on 2019-02-25 02:22.
 * @version 1.0
 * 物料信息修改Mapper类
 */
@Mapper
@Component(value = "infoModifyMapper")
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

    int updateUnitByParams (MaterialUnitModel param);

    int insertUnitByParams (MaterialUnitModel param);

    int deleteAllMaterialBasePropValWithMaterialBasePropId (int id);

    int deleteAllMaterialBasePropWithCatId (int catId);

    int insertMaterialBasePropWithMaterialBasePropParams (MaterialBasePropModel param);

    int updateCtrlPropWithCtrlPropParams (int versionId, int materialCtrlPropId, String value);

    int deleteAllMaterialCtrlPropValByVersionId (int versionId);
}
