package org.material.managementservice.mapper.info;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    int updateDefaultUnitIdBySpuCode (String spuCode, int defaultUnitId);

    int insertMaterialSkuByParams (MaterialSkuModel param);

    int deleteMaterialSkuByParams (MaterialSkuModel param);

    List<MaterialCtrlPropValVerModel> getCtrlPropValVerWithCtrlPropValVerParams (@Param("param") MaterialCtrlPropValVerModel param, @Param("timestamp") Timestamp timestamp);

    int insertCtrlPropValByParams (MaterialCtrlPropValModel param);

    int insertCtrlPropValVerByParams (MaterialCtrlPropValVerModel param);

    List<MaterialCtrlPropValModel> getCtrlPropValWithVersionId (int versionId);

    int updateCtrlPropValByParams (MaterialCtrlPropValModel param);

    int updateCtrlPropValVerCatIdById (int id, int materialCatId);

    int updateUnitByParams (MaterialUnitModel param);

    int insertUnitByParams (MaterialUnitModel param);

    int deleteAllMaterialBasePropValWithMaterialBasePropId (int id);

    int deleteAllMaterialBasePropWithCatId (int catId);

    int insertMaterialBasePropWithMaterialBasePropParams (MaterialBasePropModel param);

    int countCtrlPropWithCtrlPropParams (int materialCtrlPropId, int versionId);

    int insertCtrlPropWithCtrlPropParams (int versionId, int materialCtrlPropId, String value);

    int updateCtrlPropWithCtrlPropParams (int versionId, int materialCtrlPropId, String value);

    int deleteAllMaterialCtrlPropValByVersionId (int versionId);

    int countControlPropertyByCatIdAndTypeAndDatas (int catId, String spuCode, String organizationCode);
}
