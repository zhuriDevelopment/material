package org.material.managementfacade.model.requestmodel.infomodify;

import java.util.List;

/**
 * @author cplayer on 2019-02-28 16:04
 * @version 1.0
 * 物料更新过程中sku定义更新的封装类
 */

public class MatSkuModifyReq {
    private List<MatSkuModifyReqEle> skuList;

    public List<MatSkuModifyReqEle> getSkuList () {
        return skuList;
    }

    public void setSkuList (List<MatSkuModifyReqEle> skuList) {
        this.skuList = skuList;
    }
}
