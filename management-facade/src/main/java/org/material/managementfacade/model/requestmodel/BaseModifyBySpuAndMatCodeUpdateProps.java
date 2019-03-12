package org.material.managementfacade.model.requestmodel;

import java.util.List;

/**
 * @author cplayer on 2019-03-03 04:58.
 * @version 1.0
 * 根据spu编码和物料编码更新物料基本属性请求中待更新属性对象的封装类
 */
public class BaseModifyBySpuAndMatCodeUpdateProps {
    private String materialCode;
    private List<BaseModifyBySpuAndMatCodeUpdatePropsDatas> valueList;

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }

    public List<BaseModifyBySpuAndMatCodeUpdatePropsDatas> getValueList () {
        return valueList;
    }

    public void setValueList (List<BaseModifyBySpuAndMatCodeUpdatePropsDatas> valueList) {
        this.valueList = valueList;
    }
}
