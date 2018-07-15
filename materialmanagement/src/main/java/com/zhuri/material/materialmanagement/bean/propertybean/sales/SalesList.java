package com.zhuri.material.materialmanagement.bean.propertybean.sales;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class SalesList {
    private String[] salesList;

    public String[] getSalesList() {
        return salesList;
    }

    public void setSalesList(String[] salesList) {
        this.salesList = salesList;
    }
}
