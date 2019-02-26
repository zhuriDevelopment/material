package org.material.managementfacade.model.propertymodel.finance;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* @author cplayer on 2019-02-26 22:35.
* @version 1.0
* 物料的财务属性名列表
*/
@Component
@ConfigurationProperties (prefix = "zhuri-ctrproperties")
public class FinanceList {
    private String[] financeList;

    public String[] getFinanceList () {
        return financeList;
    }

    public void setFinanceList (String[] financeList) {
        this.financeList = financeList;
    }
}
