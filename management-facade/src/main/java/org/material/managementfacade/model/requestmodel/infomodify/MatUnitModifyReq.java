package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-03-01 20:38.
 * @version 1.0
 * updateMaterialInfo中计量单位修改请求的封装类
 */

public class MatUnitModifyReq {
    private int defaultUnitId;
    private List<MatUnitModifyReqEle> unitList;

    public int getDefaultUnitId () {
        return defaultUnitId;
    }

    public void setDefaultUnitId (int defaultUnitId) {
        this.defaultUnitId = defaultUnitId;
    }

    public List<MatUnitModifyReqEle> getUnitList () {
        return unitList;
    }

    public void setUnitList (List<MatUnitModifyReqEle> unitList) {
        this.unitList = unitList;
    }
}
