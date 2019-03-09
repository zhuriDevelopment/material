package org.material.managementfacade.model.responsemodel.MaterialInfo;

import org.material.managementfacade.model.tablemodel.UnitModel;

import java.util.List;

/**
 * @author cplayer on 2019-02-25 21:35
 * @version 1.0
 * getMaterialInfo接口中关于单位回复的封装类
 */

public class MatInfoUnitResp {
    private UnitModel defaultUnit;
    private List<UnitModel> unitList;

    public UnitModel getDefaultUnit () {
        return defaultUnit;
    }

    public void setDefaultUnit (UnitModel defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public List<UnitModel> getUnitList () {
        return unitList;
    }

    public void setUnitList (List<UnitModel> unitList) {
        this.unitList = unitList;
    }
}
