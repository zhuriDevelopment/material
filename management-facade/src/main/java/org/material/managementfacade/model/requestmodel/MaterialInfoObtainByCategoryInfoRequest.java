package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-02 05:39.
 * @version 1.0
 * 根据物料分类信息获取所有物料基本信息请求的封装类
 */

public class MaterialInfoObtainByCategoryInfoRequest {
    private int id;

    MaterialInfoObtainByCategoryInfoRequest () {
        this.id = -1;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }
}
