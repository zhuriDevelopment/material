package org.material.managementfacade.model.requestmodel;

import org.material.managementfacade.model.requestmodel.infomodify.InfoModifyByCatCodeAndNameBasePropElement;
import org.material.managementfacade.model.requestmodel.infomodify.InfoModifyByCatCodeAndNameControlPropRequest;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 17:48
 * @version 1.0
 * 根据物料分类id和物料名称更新物料信息请求的封装类
 */

public class MaterialInfoModifyByCatCodeAndNameRequest {
    private String catCode;
    private String catName;
    private List<InfoModifyByCatCodeAndNameBasePropElement> baseProps;
    private List<InfoModifyByCatCodeAndNameControlPropRequest> ctrProps;

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

    public List<InfoModifyByCatCodeAndNameBasePropElement> getBaseProps () {
        return baseProps;
    }

    public void setBaseProps (List<InfoModifyByCatCodeAndNameBasePropElement> baseProps) {
        this.baseProps = baseProps;
    }

    public List<InfoModifyByCatCodeAndNameControlPropRequest> getCtrProps () {
        return ctrProps;
    }

    public void setCtrProps (List<InfoModifyByCatCodeAndNameControlPropRequest> ctrProps) {
        this.ctrProps = ctrProps;
    }
}
