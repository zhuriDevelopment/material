package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-02 04:30.
 * @version 1.0
 * 增加物料分类信息请求的封装类
 */

public class CatAddReq {
    private String code;
    private String name;
    private int parentId;
    private int type;

    CatAddReq () {
        this.code = null;
        this.name = null;
        this.parentId = -1;
        this.type = -1;
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

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
    }
}
