package org.material.managementfacade.model.requestmodel;

import org.material.managementfacade.model.requestmodel.infomodify.MaterialBaseModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialControlPropModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialSkuModifyRequest;

/**
 * @author cplayer on 2019-02-27 04:07.
 * @version 1.0
 * updateMaterialInfo接口请求的封装总类
 */

public class MaterialInfoModifyRequest {
    // 直接一一对应，放弃propertyType
    private String spuCode;
    private MaterialBaseModifyRequest baseDatas;
    private MaterialModifyRequest materialDatas;
    private MaterialSkuModifyRequest skuDatas;
    private MaterialControlPropModifyRequest ctrPropDatas;

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

    public MaterialModifyRequest getMaterialDatas () {
        return materialDatas;
    }

    public void setMaterialDatas (MaterialModifyRequest materialDatas) {
        this.materialDatas = materialDatas;
    }

    public MaterialSkuModifyRequest getSkuDatas () {
        return skuDatas;
    }

    public void setSkuDatas (MaterialSkuModifyRequest skuDatas) {
        this.skuDatas = skuDatas;
    }

    public MaterialControlPropModifyRequest getCtrPropDatas () {
        return ctrPropDatas;
    }

    public void setCtrPropDatas (MaterialControlPropModifyRequest ctrPropDatas) {
        this.ctrPropDatas = ctrPropDatas;
    }
}
