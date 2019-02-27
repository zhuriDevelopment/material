package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-02-27 15:01
 * @version 1.0
 * updateMaterialInfo接口请求中物料定义更新的封装类
 */

public class MaterialModifyRequest {
    private List<MaterialModifyRequestForMaterial> materialList;
    private List<MaterialModifyRequestForFormatProp> formatList;

    public List<MaterialModifyRequestForMaterial> getMaterialList () {
        return materialList;
    }

    public void setMaterialList (List<MaterialModifyRequestForMaterial> materialList) {
        this.materialList = materialList;
    }

    public List<MaterialModifyRequestForFormatProp> getFormatList () {
        return formatList;
    }

    public void setFormatList (List<MaterialModifyRequestForFormatProp> formatList) {
        this.formatList = formatList;
    }
}
