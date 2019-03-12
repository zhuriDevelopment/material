package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-03 04:58.
 * @version 1.0
 * 根据spu编码和物料编码更新物料基本属性请求中属性名和属性值的封装类
 */
public class BaseModifyBySpuAndMatCodeUpdatePropsDatas {
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
