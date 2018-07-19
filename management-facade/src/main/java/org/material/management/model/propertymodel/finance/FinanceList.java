package org.material.management.model.propertymodel.finance;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class FinanceList {
    private String[] financeList;
}
