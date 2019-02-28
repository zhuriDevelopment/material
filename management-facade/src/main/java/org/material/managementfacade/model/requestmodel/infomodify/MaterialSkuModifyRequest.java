package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-02-28 16:04
 * @version 1.0
 * 物料更新过程中sku定义更新的封装类
 */

public class MaterialSkuModifyRequest {
    private List<MaterialSkuModifyRequestElement> skuList;

    public List<MaterialSkuModifyRequestElement> getSkuList () {
        return skuList;
    }

    public void setSkuList (List<MaterialSkuModifyRequestElement> skuList) {
        this.skuList = skuList;
    }
}
