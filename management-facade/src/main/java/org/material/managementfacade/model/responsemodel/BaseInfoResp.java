package org.material.managementfacade.model.responsemodel;

/**
 * @author cplayer on 2019-03-05 18:01
 * @version 1.0
 * 回复基础信息请求的封装类，其中包含了错误码
 */

public class BaseInfoResp {
    private BaseInfoRespParams result;
    private int errCode;

    public BaseInfoRespParams getResult () {
        return result;
    }

    public void setResult (BaseInfoRespParams result) {
        this.result = result;
    }

    public int getErrCode () {
        return errCode;
    }

    public void setErrCode (int errCode) {
        this.errCode = errCode;
    }
}
