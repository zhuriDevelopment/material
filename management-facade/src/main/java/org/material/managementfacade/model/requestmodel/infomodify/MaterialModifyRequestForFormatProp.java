package org.material.managementfacade.model.requestmodel.infomodify;

/**
 * @author cplayer on 2019-02-27 21:04
 * @version 1.0
 * updateMaterialInfo接口请求中物料定义更新中规格属性的封装类
 */

public class MaterialModifyRequestForFormatProp {
    private String name;
    private String value;
    private String materialCode;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getValue () {
        return value;
    }

    public void setValue (String value) {
        this.value = value;
    }

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }
}
