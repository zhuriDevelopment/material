package org.material.managementfacade.model.requestmodel;

import org.material.managementfacade.model.requestmodel.infomodify.InfoModifyByCatCodeAndNameBasePropEle;
import org.material.managementfacade.model.requestmodel.infomodify.InfoModifyByCatCodeAndNameCtrPropReq;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 17:48
 * @version 1.0
 * 根据物料分类id和物料名称更新物料信息请求的封装类
 */

public class InfoModifyByCatCodeAndNameReq {
    private int id;
    private String catCode;
    private String catName;
    private int type;
    private List<InfoModifyByCatCodeAndNameBasePropEle> baseProps;
    private List<InfoModifyByCatCodeAndNameCtrPropReq> ctrProps;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getCatCode () {
        return catCode;
    }

    public void setCatCode (String catCode) {
        this.catCode = catCode;
    }

    public String getCatName () {
        return catName;
    }

    public void setCatName (String catName) {
        this.catName = catName;
    }

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
    }

    public List<InfoModifyByCatCodeAndNameBasePropEle> getBaseProps () {
        return baseProps;
    }

    public void setBaseProps (List<InfoModifyByCatCodeAndNameBasePropEle> baseProps) {
        this.baseProps = baseProps;
    }

    public List<InfoModifyByCatCodeAndNameCtrPropReq> getCtrProps () {
        return ctrProps;
    }

    public void setCtrProps (List<InfoModifyByCatCodeAndNameCtrPropReq> ctrProps) {
        this.ctrProps = ctrProps;
    }
}
