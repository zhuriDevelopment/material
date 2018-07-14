package com.zhuri.material.materialmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import com.zhuri.material.materialmanagement.bean.MaterialBaseBean;
import com.zhuri.material.materialmanagement.service.MaterialInfoService;

@RestController
public class MaterialInfoController {
    @Autowired
    MaterialInfoService materialInfoService;

    @RequestMapping(value = "/MaterialManagement/getBaseInfo", method = RequestMethod.POST)
    public List<MaterialBaseBean> getBaseInfo (@RequestBody Map<String, Object> params) {
        return materialInfoService.getBaseInfoByParams(params);
    }

    @RequestMapping(value = "/MaterialManagement/getMaterialInfo", method = RequestMethod.POST)
    public List<Object> getMaterialInfo (@RequestBody Map<Object, Object> params) {
        return null;
    }
}