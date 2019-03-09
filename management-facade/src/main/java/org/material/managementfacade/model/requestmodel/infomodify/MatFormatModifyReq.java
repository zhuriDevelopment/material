package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-03-01 22:10.
 * @version 1.0
 * updateMaterialInfo接口请求中物料规格更新的封装类
 */

public class MatFormatModifyReq {
    private List<MatModifyReqForFormatProp> formatList;

    public List<MatModifyReqForFormatProp> getFormatList () {
        return formatList;
    }

    public void setFormatList (List<MatModifyReqForFormatProp> formatList) {
        this.formatList = formatList;
    }
}
