package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-02 04:56.
 * @version 1.0
 * 更新物料分类名称请求的封装类
 */

public class CategoryModifyNameRequest {
    private String newName;
    private String oldName;
    private int parentId;

    public String getNewName () {
        return newName;
    }

    public void setNewName (String newName) {
        this.newName = newName;
    }

    public String getOldName () {
        return oldName;
    }

    public void setOldName (String oldName) {
        this.oldName = oldName;
    }

    public int getParentId () {
        return parentId;
    }

    public void setParentId (int parentId) {
        this.parentId = parentId;
    }
}
