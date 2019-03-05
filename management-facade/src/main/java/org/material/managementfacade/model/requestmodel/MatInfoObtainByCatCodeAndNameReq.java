package org.material.managementfacade.model.requestmodel;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 06:08.
 * @version 1.0
 * 根据物料编码和物料名称获取所有物料信息请求的封装类
 */

public class MatInfoObtainByCatCodeAndNameReq {
    private String code;
    private String name;
    private List<Integer> typeArr;

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

    public List<Integer> getTypeArr () {
        return typeArr;
    }

    public void setTypeArr (List<Integer> typeArr) {
        this.typeArr = typeArr;
    }
}
