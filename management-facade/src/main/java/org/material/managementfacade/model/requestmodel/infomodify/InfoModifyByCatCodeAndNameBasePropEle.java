package org.material.managementfacade.model.requestmodel.infomodify;

/**
 * @author cplayer on 2019-03-02 17:55
 * @version 1.0
 * 根据物料分类id和物料名称更新物料信息请求的基本属性部分的封装类
 */

public class InfoModifyByCatCodeAndNameBasePropEle {
    private int type;
    private String label;
    private String valueRange;
    private String name;
    private int sort;

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public String getValueRange () {
        return valueRange;
    }

    public void setValueRange (String valueRange) {
        this.valueRange = valueRange;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getSort () {
        return sort;
    }

    public void setSort (int sort) {
        this.sort = sort;
    }
}
