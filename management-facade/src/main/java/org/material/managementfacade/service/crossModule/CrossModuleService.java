package org.material.managementfacade.service.crossModule;

import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrossModuleService {

    // 根据materialCode获取物料信息
    List<MaterialModel> getMaterialByMaterialCode (String materialCode);

    // 根据materialName获取物料信息
    List<MaterialModel> getMaterialByMaterialName (String materialName);

    // 根据spuCode获取物料信息
    List<MaterialModel> getMaterialBySpuCode (String spuCode);

    // 根据spu编码获取物料基本信息
    List<MaterialBaseModel> getMaterialBaseBySpuCode (String spuCode);

    // 根据spu名称获取物料基本信息
    List<MaterialBaseModel> getMaterialBaseBySpuName (String spuName);

    // 根据materialCatId获取物料基本属性
    List<MaterialBasePropModel> getMaterialBasePropByMaterialCatId (int materialCatId);
}
