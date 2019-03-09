package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-02-27 15:01
 * @version 1.0
 * updateMaterialInfo接口请求中物料定义更新的封装类
 */

public class MatModifyReq {
    private List<MatModifyReqForMat> materialList;

    public List<MatModifyReqForMat> getMaterialList () {
        return materialList;
    }

    public void setMaterialList (List<MatModifyReqForMat> materialList) {
        this.materialList = materialList;
    }
}
