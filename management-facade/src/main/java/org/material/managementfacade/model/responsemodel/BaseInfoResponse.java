package org.material.managementfacade.model.responsemodel;

import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.model.tablemodel.MaterialKeyPropModel;
import org.material.managementfacade.model.tablemodel.UnitModel;

import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 *
 * 用于回复基础信息请求的回复类
 *
 * 包含三个列表：
 *  第一个列表代表对应的基础信息，即materialBase表；
 *  第二个列表代表对应的分类信息，即materialCategory表；
 *  第三个列表代表对应的单位信息，即unit表；
 */

public class BaseInfoResponse {
    private List<MaterialBaseModel> baseResult;
    private List<MaterialCategoryModel> catResult;
    private List<UnitModel> unitResult;

    public List<MaterialBaseModel> getBaseResult () {
        return baseResult;
    }

    public void setBaseResult (List<MaterialBaseModel> baseResult) {
        this.baseResult = baseResult;
    }

    public List<MaterialCategoryModel> getCatResult () {
        return catResult;
    }

    public void setCatResult (List<MaterialCategoryModel> catResult) {
        this.catResult = catResult;
    }

    public List<UnitModel> getUnitResult () {
        return unitResult;
    }

    public void setUnitResult (List<UnitModel> unitResult) {
        this.unitResult = unitResult;
    }
}
