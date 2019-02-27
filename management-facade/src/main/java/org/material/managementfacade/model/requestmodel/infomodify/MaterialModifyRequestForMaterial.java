package org.material.managementfacade.model.requestmodel.infomodify;

/**
 * @author cplayer on 2019-02-27 21:02
 * @version 1.0
 * updateMaterialInfo接口请求中物料定义更新中物料定义部分的封装类
 */

public class MaterialModifyRequestForMaterial {
    private String materialCode;
    private String materialName;
    private String oldMaterialCode;
    private String barCode;

    public String getMaterialCode () {
        return materialCode;
    }

    public void setMaterialCode (String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName () {
        return materialName;
    }

    public void setMaterialName (String materialName) {
        this.materialName = materialName;
    }

    public String getOldMaterialCode () {
        return oldMaterialCode;
    }

    public void setOldMaterialCode (String oldMaterialCode) {
        this.oldMaterialCode = oldMaterialCode;
    }

    public String getBarCode () {
        return barCode;
    }

    public void setBarCode (String barCode) {
        this.barCode = barCode;
    }
}
