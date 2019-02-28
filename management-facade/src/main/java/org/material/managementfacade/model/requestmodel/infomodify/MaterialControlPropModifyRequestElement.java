package org.material.managementfacade.model.requestmodel.infomodify;

/**
 * @author cplayer on 2019-02-28 17:25
 * @version 1.0
 * 物料控制属性请求更新的单个元素类
 */

public class MaterialControlPropModifyRequestElement {
    private String name;
    private String value;

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
}
