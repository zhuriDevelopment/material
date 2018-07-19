package org.material.management.model.propertymodel.quality;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zhuri-ctrproperties")
public class QualityList {
    private String[] qualityList;

    public String[] getQualityList() {
        return qualityList;
    }

    public void setQualityList(String[] qualityList) {
        this.qualityList = qualityList;
    }
}
