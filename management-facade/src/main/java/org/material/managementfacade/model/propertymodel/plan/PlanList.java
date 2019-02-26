package org.material.managementfacade.model.propertymodel.plan;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* @author cplayer on 2019-02-26 22:34.
* @version 1.0
* 物料的计划类属性列表
*/
@Component
@ConfigurationProperties (prefix = "zhuri-ctrproperties")
public class PlanList {
    private String[] planList;

    public String[] getPlanList () {
        return planList;
    }

    public void setPlanList (String[] planList) {
        this.planList = planList;
    }
}
