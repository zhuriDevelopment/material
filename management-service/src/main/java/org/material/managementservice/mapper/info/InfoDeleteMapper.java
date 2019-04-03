package org.material.managementservice.mapper.info;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author cplayer on 2019-04-03 16:12
 * @version 1.0
 * 物料信息删除的对应Mapper类
 */
@Mapper
@Component(value = "InfoDeleteMapper")
public interface InfoDeleteMapper {
    int deleteMaterialBaseBySpuCode (String spuCode);

    int deleteMaterialBySpuCode (String spuCode);

    int deleteMaterialSkuBySpuCode (String spuCode);

    int deleteMaterialUnitBySpuCode (String spuCode);

    int deleteMaterialBasePropValBySpuCode (String spuCode);

    int deleteMaterialCtrlPropValByVersionId (Integer versionId);

    int deleteMaterialCtrlPropValVerById (Integer id);
}
