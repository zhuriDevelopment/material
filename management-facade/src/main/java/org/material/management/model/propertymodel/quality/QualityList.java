package org.material.management.model.propertymodel.quality;

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
public class QualityList {
    private String[] qualityList;
    private List<Map<String, Object>> qualityListMap;

    public String[] getQualityList() {
        return qualityList;
    }

    public void setQualityList(String[] qualityList) {
        this.qualityList = qualityList;
    }

    public List<Map<String, Object>> getQualityListMap() {
        return qualityListMap;
    }

    public void setQualityListMap(List<Map<String, Object>> qualityListMap) {
        this.qualityListMap = qualityListMap;
    }
}
