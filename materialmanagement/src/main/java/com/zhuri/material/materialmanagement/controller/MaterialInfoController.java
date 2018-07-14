package com.zhuri.material.materialmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.zhuri.material.materialmanagement.bean.tablebean.MaterialBaseBean;
import com.zhuri.material.materialmanagement.service.MaterialInfoService;

@RestController
public class MaterialInfoController {
    @Autowired
    MaterialInfoService materialInfoService;

    @RequestMapping(value = "/MaterialManagement/getBaseInfo", method = RequestMethod.POST)
    public List<MaterialBaseBean> getBaseInfo (@RequestBody Map<String, Object> params) {
        // 参数必须非空！
        assert(params.size() > 0);
        return materialInfoService.getBaseInfoByParams(params);
    }

    @RequestMapping(value = "/MaterialManagement/getMaterialInfo", method = RequestMethod.POST)
    public List<Object> getMaterialInfo (@RequestBody Map<Object, Object> params) {
        try {
            String spuCode = (String) params.get("spuCode");
            String spuName = (String) params.get("spuName");
            List<Integer> typeArr = (List<Integer>) params.get("typeArr");
            return materialInfoService.getMaterialInfo(spuCode, spuName, typeArr);
        } catch (ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        }
    }
}