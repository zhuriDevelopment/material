package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-02-28 17:24
 * @version 1.0
 * 更新物料信息中物料控制属性请求的封装类
 */

public class MaterialControlPropModifyRequest {
    List<MatCtrPropModifyReqEle> ctrPropList;

    public List<MatCtrPropModifyReqEle> getCtrPropList () {
        return ctrPropList;
    }

    public void setCtrPropList (List<MatCtrPropModifyReqEle> ctrPropList) {
        this.ctrPropList = ctrPropList;
    }
}
