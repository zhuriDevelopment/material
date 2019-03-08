package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-02 05:15.
 * @version 1.0
 * 物料分类信息删除请求的封装类
 */

public class CatDeleteReq {
    private int id;
    private String code;
    private String name;
    private int parentId;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getParentId () {
        return parentId;
    }

    public void setParentId (int parentId) {
        this.parentId = parentId;
    }
}
