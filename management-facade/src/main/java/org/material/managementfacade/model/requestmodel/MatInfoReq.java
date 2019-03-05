package org.material.managementfacade.model.requestmodel;

import java.util.List;

/**
 * @author cplayer on 2019-02-25 20:39
 * @version 1.0
 * getMaterialInfo的请求类
 */

public class MatInfoReq {
    private String spuCode;
    private List<Integer> typeArr;
    private Integer organizationId;

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public List<Integer> getTypeArr () {
        return typeArr;
    }

    public void setTypeArr (List<Integer> typeArr) {
        this.typeArr = typeArr;
    }

    public Integer getOrganizationId () {
        return organizationId;
    }

    public void setOrganizationId (Integer organizationId) {
        this.organizationId = organizationId;
    }
}
