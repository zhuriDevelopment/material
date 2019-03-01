package org.material.managementfacade.model.requestmodel;

/**
 * @author cplayer on 2019-03-02 05:52.
 * @version 1.0
 * 根据物料分类id获取所有物料分类信息请求的封装类
 */

public class MaterialCategoryObtainByIdRequest {
    private int id;

    public MaterialCategoryObtainByIdRequest () {
        this.id = -1;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }
}
