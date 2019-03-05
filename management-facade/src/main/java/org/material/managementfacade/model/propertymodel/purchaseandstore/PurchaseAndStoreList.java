package org.material.managementfacade.model.propertymodel.purchaseandstore;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author cplayer on 2019-02-26 22:31.
 * @version 1.0
 * 物料的采购和库存属性名列表
 */
@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class PurchaseAndStoreList {
    private String[] purchaseAndStoreList;

    public String[] getPurchaseAndStoreList () {
        return purchaseAndStoreList;
    }

    public void setPurchaseAndStoreList (String[] purchaseAndStoreList) {
        this.purchaseAndStoreList = purchaseAndStoreList;
    }
}
