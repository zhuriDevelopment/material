package org.material.managementfacade.model.requestmodel;

import org.material.managementfacade.model.requestmodel.infomodify.*;

/**
 * @author cplayer on 2019-02-27 04:07.
 * @version 1.0
 * updateMaterialInfo接口请求的封装总类
 */

public class InfoModifyReq {
    // 直接一一对应，放弃propertyType
    private String spuCode;
    private BaseModifyReq baseDatas;
    private MatModifyReq materialDatas;
    private MatFormatModifyReq formatDatas;
    private MatSkuModifyReq skuDatas;
    private MatCtrlPropModifyReq ctrPropDatas;
    private MatUnitModifyReq unitDatas;

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public BaseModifyReq getBaseDatas () {
        return baseDatas;
    }

    public void setBaseDatas (BaseModifyReq baseDatas) {
        this.baseDatas = baseDatas;
    }

    public MatModifyReq getMaterialDatas () {
        return materialDatas;
    }

    public void setMaterialDatas (MatModifyReq materialDatas) {
        this.materialDatas = materialDatas;
    }

    public MatSkuModifyReq getSkuDatas () {
        return skuDatas;
    }

    public void setSkuDatas (MatSkuModifyReq skuDatas) {
        this.skuDatas = skuDatas;
    }

    public MatCtrlPropModifyReq getCtrPropDatas () {
        return ctrPropDatas;
    }

    public void setCtrPropDatas (MatCtrlPropModifyReq ctrPropDatas) {
        this.ctrPropDatas = ctrPropDatas;
    }

    public MatFormatModifyReq getFormatDatas () {
        return formatDatas;
    }

    public void setFormatDatas (MatFormatModifyReq formatDatas) {
        this.formatDatas = formatDatas;
    }

    public MatUnitModifyReq getUnitDatas () {
        return unitDatas;
    }

    public void setUnitDatas (MatUnitModifyReq unitDatas) {
        this.unitDatas = unitDatas;
    }
}
