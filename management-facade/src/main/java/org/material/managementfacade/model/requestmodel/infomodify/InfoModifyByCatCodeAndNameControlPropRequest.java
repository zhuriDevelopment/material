package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 20:26
 * @version 1.0
 * 根据物料分类编码、物料名称更新物料控制属性的请求封装类
 */

public class InfoModifyByCatCodeAndNameControlPropRequest {
    private String organizationCode;
    private int propertyType;
    private List<MaterialControlPropModifyRequestElement> ctrPropList;

    public InfoModifyByCatCodeAndNameControlPropRequest () {
        this.organizationCode = null;
        this.propertyType = -1;
        this.ctrPropList = null;
    }

    public String getOrganizationCode () {
        return organizationCode;
    }

    public void setOrganizationCode (String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public int getPropertyType () {
        return propertyType;
    }

    public void setPropertyType (int propertyType) {
        this.propertyType = propertyType;
    }

    public List<MaterialControlPropModifyRequestElement> getCtrPropList () {
        return ctrPropList;
    }

    public void setCtrPropList (List<MaterialControlPropModifyRequestElement> ctrPropList) {
        this.ctrPropList = ctrPropList;
    }
}
