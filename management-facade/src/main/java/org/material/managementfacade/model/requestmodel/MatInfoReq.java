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

    public MatInfoReq (String spuCode, List<Integer> typeArr, Integer organizationId) {
        this.spuCode = spuCode;
        this.typeArr = typeArr;
        this.organizationId = organizationId;
    }

    public MatInfoReq () {
        this.spuCode = null;
        this.typeArr = null;
        this.organizationId = -1;
    }

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
