package org.material.managementfacade.model.responsemodel;

import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;

import java.util.List;

/**
 * @author cplayer on 2019-03-03 02:18.
 * @version 1.0
 * 根据物料编码和spu编码获取物料基本属性回复的返回值数组元素
 */

public class MaterialBaseObtainBySpuAndMatCodeElement {
    private List<MaterialBasePropModel> baseProp;
    private List<String> values;
    private String materialCode;

    public List<MaterialBasePropModel> getBaseProp () {
        return baseProp;
    }

    public void setBaseProp (List<MaterialBasePropModel> baseProp) {
        this.baseProp = baseProp;
    }

    public List<String> getValues () {
        return values;
    }

    public void setValues (List<String> values) {
        this.values = values;
    }

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }
}
