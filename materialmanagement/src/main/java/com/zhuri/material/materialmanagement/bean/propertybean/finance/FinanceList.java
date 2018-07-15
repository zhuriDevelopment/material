package com.zhuri.material.materialmanagement.bean.propertybean.finance;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class FinanceList {
    private String[] financeList;
}
