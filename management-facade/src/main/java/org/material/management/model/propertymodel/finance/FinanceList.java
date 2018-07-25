package org.material.management.model.propertymodel.finance;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
    * 对于AllowedList这个LinkedHashMap，key代表序号（从0开始），value代表迭代值
    * 需要在yml数据里确保数据格式正确！！
    * AllowedList中所有数据格式均为String！！！若需要其他类型必须转换！
*/

@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class FinanceList {
    private String[] financeList;
    private List<Map<String, Object>> financeListMap;

    public String[] getFinanceList() {
        return financeList;
    }

    public void setFinanceList(String[] financeList) {
        this.financeList = financeList;
    }

    public List<Map<String, Object>> getFinanceListMap() {
        return financeListMap;
    }

    public void setFinanceListMap(List<Map<String, Object>> financeListMap) {
        this.financeListMap = financeListMap;
    }
}
