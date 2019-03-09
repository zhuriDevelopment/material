package org.material.managementfacade.model.responsemodel.MaterialInfo;

import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;

import java.util.List;

/**
 * @author cplayer on 2019-02-26 17:22.
 * @version 1.0
 * getMaterialInfo中关键属性回复的封装类
 */

public class MatInfoBasePropRespClass {
    private List<MaterialBasePropValModel> valList;
    private List<MaterialBasePropModel> propList;

    public List<MaterialBasePropValModel> getValList () {
        return valList;
    }

    public void setValList (List<MaterialBasePropValModel> valList) {
        this.valList = valList;
    }

    public List<MaterialBasePropModel> getPropList () {
        return propList;
    }

    public void setPropList (List<MaterialBasePropModel> propList) {
        this.propList = propList;
    }
}
