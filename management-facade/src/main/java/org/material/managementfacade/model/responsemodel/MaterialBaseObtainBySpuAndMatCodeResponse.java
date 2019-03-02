package org.material.managementfacade.model.responsemodel;

import java.util.List;

/**
 * @author cplayer on 2019-03-03 01:41.
 * @version 1.0
 * 根据spu编码和物料编码获取物料基本属性回复封装类
 */

public class MaterialBaseObtainBySpuAndMatCodeResponse {
    private int errCode;
    private List<MaterialBaseObtainBySpuAndMatCodeElement> baseInfos;

    public int getErrCode () {
        return errCode;
    }

    public void setErrCode (int errCode) {
        this.errCode = errCode;
    }

    public List<MaterialBaseObtainBySpuAndMatCodeElement> getBaseInfos () {
        return baseInfos;
    }

    public void setBaseInfos (List<MaterialBaseObtainBySpuAndMatCodeElement> baseInfos) {
        this.baseInfos = baseInfos;
    }
}
