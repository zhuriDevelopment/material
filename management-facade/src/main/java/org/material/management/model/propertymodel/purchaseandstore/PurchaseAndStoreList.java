package org.material.management.model.propertymodel.purchaseandstore;

import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
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
public class PurchaseAndStoreList {
    private String[] purchasePropertiesList;
    private List<Map<String, Object>> purchasePropertiesListMap;

    public String[] getPurchasePropertiesList () {
        return purchasePropertiesList;
    }

    public void setPurchasePropertiesList (String[] purchasePropertiesList) {
        this.purchasePropertiesList = purchasePropertiesList;
    }

    public List<Map<String, Object>> getPurchasePropertiesListMap () {
        return purchasePropertiesListMap;
    }

    public void setPurchasePropertiesListMap (List<Map<String, Object>> purchasePropertiesListMap) {
        this.purchasePropertiesListMap = purchasePropertiesListMap;
    }
}
