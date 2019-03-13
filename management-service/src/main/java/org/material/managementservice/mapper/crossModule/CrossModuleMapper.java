package org.material.managementservice.mapper.crossModule;

import org.apache.ibatis.annotations.*;
import org.material.managementfacade.model.tablemodel.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrossModuleMapper {

    // 根据materialCode获取物料信息
    @Select("SELECT * FROM material WHERE materialCode=#{materialCode}")
    List <MaterialModel> getMaterialByMaterialCode (String materialCode);

    // 根据materialName获取物料信息
    @Select("SELECT * FROM material WHERE materialName=#{materialName}")
    List <MaterialModel> getMaterialByMaterialName (String materialName);

    // 根据spuCode获取物料信息
    @Select("SELECT * FROM material WHERE spuCode=#{spuCode}")
    List <MaterialModel> getMaterialBySpuCode (String spuCode);

    // 根据spu编码获取物料基本信息
    @Select("SELECT * FROM materialBase WHERE spuCode=#{spuCode}")
    List <MaterialBaseModel> getMaterialBaseModelBySpuCode (String spuCode);

    // 根据spu名称获取物料基本信息
    @Select("SELECT * FROM materialBase WHERE spuName=#{spuName}")
    List <MaterialBaseModel> getMaterialBaseModelBySpuName (String spuName);

    // 根据materialCatId获取物料基本属性
    @Select("SELECT * FROM materialBaseProp WHERE materialCatId=#{materialCatId}")
    List <MaterialBasePropModel> getMaterialBasePropByMaterialCatId (int materialCatId);
}
