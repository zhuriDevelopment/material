package org.material.managementfacade.model.propertymodel.quality;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* @author cplayer on 2019-02-26 22:33.
* @version 1.0
* 物料的质量属性名列表
*/
@Component
@ConfigurationProperties (prefix = "zhuri-ctrproperties")
public class QualityList {
    private String[] qualityList;

    public String[] getQualityList () {
        return qualityList;
    }

    public void setQualityList (String[] qualityList) {
        this.qualityList = qualityList;
    }
}
