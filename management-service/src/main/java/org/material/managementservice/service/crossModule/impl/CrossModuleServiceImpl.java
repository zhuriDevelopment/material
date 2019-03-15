package org.material.managementservice.service.crossModule.impl;

import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementfacade.service.crossModule.CrossModuleService;
import org.material.managementservice.mapper.crossModule.CrossModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrossModuleServiceImpl implements CrossModuleService {

    @Autowired
    private CrossModuleMapper crossModuleMapper;

    @Override
    public List<MaterialModel> getMaterialByMaterialCode (String materialCode) {
        // 根据materialCode获取物料信息
        List<MaterialModel> materialResult = crossModuleMapper.getMaterialByMaterialCode(materialCode);
        return materialResult;
    }

    @Override
    public List<MaterialModel> getMaterialByMaterialName (String materialName) {
        // 根据materialName获取物料信息
        List<MaterialModel> materialResult = crossModuleMapper.getMaterialByMaterialName(materialName);
        return materialResult;
    }

    @Override
    public List<MaterialModel> getMaterialBySpuCode (String spuCode) {
        // 根据spuCode获取物料信息
        List<MaterialModel> materialResult = crossModuleMapper.getMaterialBySpuCode(spuCode);
        return materialResult;
    }

    @Override
    public List<MaterialBaseModel> getMaterialBaseBySpuCode (String spuCode) {
        // 根据spu编码获取物料基本信息
        List<MaterialBaseModel> materialBaseResult = crossModuleMapper.getMaterialBaseModelBySpuCode(spuCode);
        return materialBaseResult;
    }

    @Override
    public List<MaterialBaseModel> getMaterialBaseBySpuName (String spuName) {
        // 根据spu名称获取物料基本信息
        List<MaterialBaseModel> materialBaseResult = crossModuleMapper.getMaterialBaseModelBySpuName(spuName);
        return materialBaseResult;
    }

    @Override
    public List<MaterialBasePropModel> getMaterialBasePropByMaterialCatId (int materialCatId) {
        // 根据materialCatId获取物料基本属性
        List<MaterialBasePropModel> materialBasePropResult = crossModuleMapper.getMaterialBasePropByMaterialCatId(materialCatId);
        return materialBasePropResult;
    }
}
