package org.material.managementservice.mapper.info;

import org.springframework.stereotype.Repository;

/**
* @author cplayer on 2019-02-25 02:22.
* @version 1.0 
* 物料信息修改Mapper类
*/
@Repository
public interface InfoModifyMapper {
    int countBaseBySpuCode (String spuCode);

    int deleteAllMaterialBySpuCode (String spuCode);
}
