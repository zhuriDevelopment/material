package org.material.managementfacade.model.requestmodel;

import org.material.managementfacade.model.requestmodel.infomodify.MaterialBaseModifyRequest;

/**
 * @author cplayer on 2019-02-27 04:07.
 * @version 1.0
 * updateMaterialInfo接口请求的封装总类
 */

public class MaterialInfoModifyRequest {
    // 直接一一对应，放弃propertyType
    private String spuCode;
    private MaterialBaseModifyRequest baseDatas;

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public MaterialBaseModifyRequest getBaseDatas () {
        return baseDatas;
    }

    public void setBaseDatas (MaterialBaseModifyRequest baseDatas) {
        this.baseDatas = baseDatas;
    }
}
