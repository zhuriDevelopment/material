package org.material.managementfacade.model.responsemodel;

import org.material.managementfacade.model.requestmodel.MaterialInfoObtainByCategoryInfoRequest;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 05:40.
 * @version 1.0
 * 根据物料分类信息获取所有物料基本信息回复的封装类
 */

public class MaterialInfoObtainByCategoryInfoResponse {
    private List<MaterialBaseModel> materialBaseResult;
    private List<MaterialCategoryModel> catResult;

    public MaterialInfoObtainByCategoryInfoResponse () {
        this.materialBaseResult = null;
        this.catResult = null;
    }

    public List<MaterialBaseModel> getMaterialBaseResult () {
        return materialBaseResult;
    }

    public void setMaterialBaseResult (List<MaterialBaseModel> materialBaseResult) {
        this.materialBaseResult = materialBaseResult;
    }

    public List<MaterialCategoryModel> getCatResult () {
        return catResult;
    }

    public void setCatResult (List<MaterialCategoryModel> catResult) {
        this.catResult = catResult;
    }
}
