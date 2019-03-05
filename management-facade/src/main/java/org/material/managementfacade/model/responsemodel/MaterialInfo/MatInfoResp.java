package org.material.managementfacade.model.responsemodel.MaterialInfo;

/**
 * @author cplayer on 2019-03-05 21:39
 * @version 1.0
 * getMaterialInfo方法的回复类
 */

public class MatInfoResp {
    private MatInfoRespParams result = null;
    private int errCode = -1;

    public MatInfoRespParams getResult () {
        return result;
    }

    public void setResult (MatInfoRespParams result) {
        this.result = result;
    }

    public int getErrCode () {
        return errCode;
    }

    public void setErrCode (int errCode) {
        this.errCode = errCode;
    }
}
