package org.material.managementfacade.service.info;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cplayer on 2019-04-03 16:02
 * @version 1.0
 * 物料信息删除的服务类
 */

@Service
public interface InfoDeleteService {
    int deleteInfoBySpuCode (String spuCode);

    int deleteInfoBySpuCodes (List<String> spuCodes);
}
