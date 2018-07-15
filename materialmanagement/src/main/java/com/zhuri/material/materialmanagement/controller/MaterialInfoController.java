package com.zhuri.material.materialmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.zhuri.material.materialmanagement.bean.tablebean.MaterialBaseBean;
import com.zhuri.material.materialmanagement.service.MaterialInfoService;

import com.zhuri.material.materialmanagement.bean.propertybean.purchaseandstore.PurchaseAndStoreList;

@RestController
public class MaterialInfoController {
    @Autowired
    MaterialInfoService materialInfoService;

    @Autowired
    PurchaseAndStoreList purchaseAndStoreList;

    @RequestMapping(value = "/MaterialManagement/getBaseInfo", method = RequestMethod.POST)
    public List<MaterialBaseBean> getBaseInfo (@RequestBody Map<String, Object> params) {
        // 参数必须非空！
        assert(params.size() > 0);
        return materialInfoService.getBaseInfoByParams(params);
    }

    @RequestMapping(value = "/MaterialManagement/getMaterialInfo", method = RequestMethod.POST)
    // 如果list和hashmap的转换会出问题，则会抛出异常
    @SuppressWarnings("unchecked")
    public List<Object> getMaterialInfo (@RequestBody Map<Object, Object> params) {
        try {
            String spuCode = (String) params.get("spuCode");
            String spuName = (String) params.get("spuName");
            List<Integer> typeArr = (List<Integer>) params.get("typeArr");
            // return materialInfoService.getMaterialInfo(spuCode, spuName, typeArr);
            List<Map<String, Object>> kv = purchaseAndStoreList.getPurchasePropertiesListMap();
            System.out.println(kv.get(0).get("key"));
            System.out.println(kv.get(0).get("type"));
            System.out.println(kv.get(0).get("key").getClass());
            System.out.println(kv.get(0).get("type").getClass());
            System.out.println(kv.get(0).get("allowedList").getClass());
            // LinkedHashMap<String, String> hshMap = (LinkedHashMap<String, String>) kv.get(0).get("allowedList");
            // for (Map.Entry<String, String> entry : hshMap.entrySet()) {
            //     System.out.println(entry.getKey());
            //     System.out.println(entry.getValue());
            // }
            return null;
        } catch (ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        }
    }
}