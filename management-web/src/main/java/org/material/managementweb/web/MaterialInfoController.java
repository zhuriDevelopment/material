package org.material.managementweb.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.material.managementservice.service.impl.MaterialInfoServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/materialmanagement")
@Api(value = "物料信息接口", description = "物料信息接口")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*",
            methods = {RequestMethod.GET, RequestMethod.POST},
             origins = "*")
public class MaterialInfoController {
    @Autowired
    private MaterialInfoServiceImpl materialInfoService;

    @PostMapping(value = "/getBaseInfo")
    @ApiOperation(value = "根据给定参数查询基础信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Object> getBaseInfo (@RequestBody Map<String, Object> params) {
        // 参数必须非空！
        assert (params.size() > 0);
        return materialInfoService.getBaseInfoByParams(params);
    }

    @PostMapping(value = "/getMaterialInfo")
    @ApiOperation(value = "根据给定参数查询物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SuppressWarnings("unchecked")
    // 如果list和hashmap的转换会出问题，则会抛出异常
    public List<Object> getMaterialInfo (@RequestBody Map<Object, Object> params) {
        try {
            String spuCode = (String) params.get("spuCode");
            String spuName = (String) params.get("spuName");
            List<Integer> typeArr = (List<Integer>) params.get("typeArr");
            // 先不考虑组织编码
            int organizationId = 1;
            // 需要查询物料ID和物料分类ID
            return materialInfoService.getMaterialInfo(spuCode, spuName, typeArr, organizationId);
        } catch (ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        }
    }







    @PostMapping(value = "/updateMaterialCategory")
    @ApiOperation(value = "根据物料oldName及parentId更新newName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //返回1为成功更新数据，返回0为失败
    public int updateMaterialCategory (@RequestBody Map<String, Object> params) {
        //确保三个属性值全部获取
        if (params.containsKey("newName") && params.containsKey("oldName") && params.containsKey("parentId")) {
            String newName = (String) params.get("newName");
            String oldName = (String) params.get("oldName");
            int parentId = (int) params.get("parentId");
            return materialInfoService.updateMaterialCategory(newName, oldName, parentId);
        } else {
            return 0;
        }
    }

    @PostMapping(value = "/deleteMaterialCategory")
    @ApiOperation(value = "删除物料分类编码信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //返回0为失败，正整数为成功，其值表示删除记录数
    public int deleteMaterialCategory (@RequestBody Map<String, Object> params) {
        //必须获取全部属性值
        if (params.containsKey("id") && params.containsKey("code") && params.containsKey("name") && params.containsKey("parentId")) {
            int id = (int) params.get("id");
            String code = (String) params.get("code");
            String name = (String) params.get("name");
            int parentId = (int) params.get("parentId");
            return materialInfoService.deleteMaterialCategory(id, code, name, parentId);
        } else {
            return 0;
        }
    }

    @PostMapping(value = "/getAllMaterialBaseByCategoryInfos")
    @ApiOperation(value = "根据物料分类信息获取所有物料基本信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Object> getAllMaterialBaseByCategoryInfos (@RequestBody Map<String, Object> params) {
        try {
            // 根据id唯一确定物料分类
            int id = (int) params.get("id");
            return materialInfoService.getAllMaterialBaseByCategoryInfos(id);
        } catch (NullPointerException | ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        }
    }

    @PostMapping(value = "/getMaterialCategoryInfosWithId")
    @ApiOperation(value = "根据物料分类id获取所有物料分类信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Object> getMaterialCategoryInfosWithId (@RequestBody Map<String, Object> params) {
        try {
            int id = (int) params.get("id");
            return materialInfoService.getMaterialCategoryInfosWithId(id);
        } catch (NullPointerException | ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        }
    }

    @PostMapping(value = "/getMaterialInfoWithCatCodeAndCatName")
    @ApiOperation(value = "根据物料分类id和物料名称获取所有物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Object> getMaterialInfoWithCatCodeAndCatName (@RequestBody Map<String, Object> params) {
        try {
            int catCode = Integer.parseInt(params.get("code").toString());
            String catName = params.get("name").toString();
            List<Integer> typeArr = (List<Integer>) params.get("typeArr");
            // 先不考虑组织编码
            int organizationId = 1;
            return materialInfoService.getMaterialInfoWithCatCodeAndCatName(catCode, catName, typeArr, organizationId);
        } catch (NullPointerException | ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确！");
            return result;
        }
    }

    @PostMapping(value = "/updateMaterialInfoWithCatCodeAndCatName")
    @ApiOperation(value = "根据物料分类id和物料名称更新物料信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialInfoWithCatCodeAndCatName (@RequestBody Map<String, Object> params) {
        try {
            String catCode = params.get("code").toString();
            String catName = params.get("name").toString();
            List<Object> data = (List<Object>) params.get("data");
            return materialInfoService.updateMaterialInfoWithCatCodeAndCatName(catCode, catName, data);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return -1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -2;
        }
    }

    @PostMapping(value = "/getMaterialBasePropsBySpuCodeAndMaterialCodes")
    @ApiOperation(value = "根据spu编码和物料编码获取物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Object> getMaterialBasePropsBySpuCodeAndMaterialCodes (@RequestBody Map<String, Object> params) {
        try {
            String spuCode = params.get("spuCode").toString();
            List<String> materialCodes = (List<String>) params.get("materialCodes");
            int propertyType = (int) params.get("propertyType");
            return materialInfoService.getMaterialBasePropsBySpuCodeAndMaterialCodesAndType(spuCode, materialCodes, propertyType);
        } catch (ClassCastException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确，例如物料编码不为数组！");
            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            List<Object> result = new ArrayList<>();
            result.add("请检查输入格式是否正确，例如不存在spuCode或者不存在物料编码数组！");
            return result;
        }
    }

    @PostMapping(value = "/updateMaterialBasePropsBySpuCodeAndMaterialCodes")
    @ApiOperation(value = "根据spu编码和物料编码更新物料基本属性", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMaterialBasePropsBySpuCodeAndMaterialCodes (@RequestBody Map<String, Object> params) {
        try {
            String spuCode = params.get("spuCode").toString();
            int propertyType = (int) params.get("propertyType");
            List<Object> updateValue = (List<Object>) params.get("updateValue");
            return materialInfoService.updateMaterialBasePropsBySpuCodeAndMaterialCodes(spuCode, propertyType, updateValue);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return -1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -2;
        }
    }

    @PostMapping(value = "/getMaterialBaseByCatIdAndType")
    @ApiOperation(value = "根据CatId和Type获取规格", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Object> getMaterialBaseByCatIdAndType(@RequestBody Map<String, Object> params){
        int catId = (int)params.get("catId");
        int propertyType = (int)params.get("propertyType");
        return materialInfoService.getMaterialBaseByCatIdAndType(catId, propertyType);
    }
}