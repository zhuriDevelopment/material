package org.material.managementservice.mapper.info;

import org.apache.ibatis.annotations.Mapper;
import org.material.managementfacade.model.tablemodel.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author cplayer on 2019-02-25 02:24.
 * @version 1.0
 * <p>
 * 物料信息获取Mapper类，本类定义了物料信息获取的相关方法
 */
@Mapper
@Component(value = "infoObtainMapper")
public interface InfoObtainMapper {
    List<MaterialBaseModel> getAllBaseInfo ();

    List<MaterialBaseModel> getBaseInfoWithSpuCode (String spuCode);

    List<MaterialBaseModel> getBaseInfoWithDateRange (Date startDate, Date endDate);

    List<MaterialModel> getMaterialWithSpuCode (String spuCode);

    List<MaterialSkuModel> getMaterialSkuWithSpuCode (String spuCode);

    List<MaterialUnitModel> getMaterialUnitWithSpuCode (String spuCode);

    List<MaterialBasePropModel> getMaterialBasePropWithId (int id);

    List<MaterialBasePropValModel> getMaterialBasePropValWithSpuCodeAndMatCode (String spuCode, String materialCode);

    List<MaterialBasePropModel> getMaterialBasePropWithNameAndMatCatId (String name, int materialCatId);

    List<MaterialBasePropValModel> getMaterialBasePropValWithSpuAndCatCodeAndPropId (String spuCode, String materialCode, int materialBasePropId);

    List<MaterialCtrlPropValVerModel> getMaterialCtrlPropValVerByCatId (int materialCatId);
}
