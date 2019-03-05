package org.material.managementfacade.model.responsemodel;

/**
 * @author cplayer on 2019-03-05 22:13
 * @version 1.0
 * 根据物料分类信息获取所有物料基本信息回复的封装类
 */

public class MatInfoObtainByCatInfoResp {
    private MatInfoObtainByCatInfoRespParams result;
    private int errCode;

    public MatInfoObtainByCatInfoRespParams getResult () {
        return result;
    }

    public void setResult (MatInfoObtainByCatInfoRespParams result) {
        this.result = result;
    }

    public int getErrCode () {
        return errCode;
    }

    public void setErrCode (int errCode) {
        this.errCode = errCode;
    }
}
