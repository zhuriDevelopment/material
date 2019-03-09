package org.material.managementfacade.model.responsemodel;

import org.material.managementfacade.model.tablemodel.UnitModel;

/**
 * @author cplayer on 2019-03-10 01:28.
 * @version 1.0
 * 获取所有计量单位信息的元素封装类
 */

public class AllUnitInfosObtainResp {
    private int value;
    private String label;

    public AllUnitInfosObtainResp () {
        this.value = -1;
        this.label = null;
    }

    public AllUnitInfosObtainResp (UnitModel unit) {
        this.value = unit.getId();
        this.label = unit.getName();
    }

    public int getValue () {
        return value;
    }

    public void setValue (int value) {
        this.value = value;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }
}
