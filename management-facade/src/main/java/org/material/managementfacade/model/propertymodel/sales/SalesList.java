package org.material.managementfacade.model.propertymodel.sales;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cplayer on 2019-02-26 22:34.
 * @version 1.0
 * 物料的销售类属性名列表
 */
@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class SalesList {
    private String[] salesList;

    public String[] getSalesList () {
        return salesList;
    }

    public void setSalesList (String[] salesList) {
        this.salesList = salesList;
    }
}
