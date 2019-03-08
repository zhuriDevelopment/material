package org.material.managementfacade.model.responsemodel;

/**
 * @author cplayer on 2019-03-02 17:58
 * @version 1.0
 * 根据物料分类id和物料名称更新物料信息的回复类
 */

public class InfoModifyByCatCodeAndNameResp {
    private int errCode;
    private int errCodeInBaseProp;
    private int errCodeInCtrProp;

    public int getErrCode () {
        return errCode;
    }

    public void setErrCode (int errCode) {
        this.errCode = errCode;
    }

    public int getErrCodeInBaseProp () {
        return errCodeInBaseProp;
    }

    public void setErrCodeInBaseProp (int errCodeInBaseProp) {
        this.errCodeInBaseProp = errCodeInBaseProp;
    }

    public int getErrCodeInCtrProp () {
        return errCodeInCtrProp;
    }

    public void setErrCodeInCtrProp (int errCodeInCtrProp) {
        this.errCodeInCtrProp = errCodeInCtrProp;
    }
}
