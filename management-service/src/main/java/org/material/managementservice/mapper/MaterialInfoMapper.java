package org.material.managementservice.mapper;


import org.apache.ibatis.annotations.*;
import org.material.managementservice.mapper.provider.MaterialInfoProvider;
import org.material.managementfacade.model.tablemodel.*;

import java.util.Map;
import java.util.List;

@Mapper
public interface MaterialInfoMapper {

    // ---------------------------------------- 获取物料信息部分 ----------------------------------------
    @Select("SELECT * FROM materialBase")
    List<MaterialBaseModel> getAllBaseInfo ();

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getBaseInfoWithBaseInfoParams")
    List<MaterialBaseModel> getBaseInfoWithBaseInfoParams (Map<String, Object> params);

    @Select("SELECT * FROM materialBase WHERE spuCode=#{spuCode};")
    List<MaterialBaseModel> getBaseInfoWithSpuCode (String spuCode);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialWithMaterialParams")
    List<MaterialModel> getMaterialWithMaterialParams (Map<String, Object> params);

    @Select("SELECT * FROM material WHERE spuCode=#{spuCode};")
    List<MaterialModel> getMaterialWithSpuCode (String spuCode);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialSkuWithMaterialSkuParams")
    List<MaterialSkuModel> getMaterialSkuWithMaterialSkuParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getUnitWithUnitParams")
    List<UnitModel> getUnitWithUnitParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialUnitWithMaterialUnitParams")
    List<MaterialUnitModel> getMaterialUnitWithMaterialUnitParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialCategoryWithMaterialCategoryParams")
    List<MaterialCategoryModel> getMaterialCategoryWithMaterialCategoryParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialBasePropWithMaterialBasePropParams")
    List<MaterialBasePropModel> getMaterialBasePropWithMaterialBasePropParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialBasePropValWithMaterialBasePropValParams")
    List<MaterialBasePropValModel> getMaterialBasePropValWithMaterialBasePropValParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getFilesWithFilesParams")
    List<MaterialFilesModel> getFilesWithFilesParams (Map<String, Object> params);

    @Select("SELECT * FROM materialCtrlProp WHERE id=#{id};")
    List<MaterialCtrlPropModel> getCtrlPropWithCtrlPropId (int id);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getCtrlPropWithCtrlPropParams")
    List<MaterialCtrlPropModel> getCtrlPropWithCtrlPropParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getCtrlPropValWithCtrlPropValParams")
    List<MaterialCtrlPropValModel> getCtrlPropValWithCtrlPropValParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getCtrlPropValVerWithCtrlPropValVerParams")
    List<MaterialCtrlPropValVerModel> getCtrlPropValVerWithCtrlPropValVerParams (Map<String, Object> params);

    // ---------------------------------------- 更新物料基本信息部分 ----------------------------------------
    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateBaseInfoWithBaseInfoParams")
    int updateBaseInfoWithBaseInfoParams (String spuCode, String name, String value);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateupdateBaseInfoWithBaseInfoParamsArray")
    int updateupdateBaseInfoWithBaseInfoParamsArray (String spuCode, String[] names, String[] values);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateMaterialUnitWithMaterialUnitParams")
    int updateMaterialUnitWithMaterialUnitParams (String spuCode, String name, String value);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateMaterialUnitWithMaterialUnitParamsArray")
    int updateMaterialUnitWithMaterialUnitParamsArray (String spuCode, String[] names, String[] values);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateMaterialFilesWithMaterialFilesParams")
    int updateMaterialFilesWithMaterialFilesParams (int materialBaseId, String name, String value);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateMaterialFilesWithMaterialFilesParamsArray")
    int updateMaterialFilesWithMaterialFilesParamsArray (int materialBaseId, String[] names, String[] values);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateMaterialBaseProp")
    int updateMaterialBasePropWithMaterialBaseProp (int id, String name, String value);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateMaterialBasePropVal")
    int updateMaterialBasePropValWithMaterialBasePropValParams (String spuCode, String materialCode, int materialBasePropId, String name, String value);

    @UpdateProvider(type = MaterialInfoProvider.class,
                    method = "updateCtrlPropWithCtrlPropParams")
    int updateCtrlPropWithCtrlPropParams (int versionId, int ctrlPropId, String value);

    // ---------------------------------------- 删除物料基本信息部分 ----------------------------------------
    @DeleteProvider(type = MaterialInfoProvider.class,
                    method = "deleteAllMaterialWithSpuCode")
    int deleteAllMaterialWithSpuCode (String spuCode);

    @DeleteProvider(type = MaterialInfoProvider.class,
                    method = "deleteAllMaterialSkuWithSpuCode")
    int deleteAllMaterialSkuWithSpuCode (String spuCode);

    // ---------------------------------------- 增加物料基本信息部分 ---------------------------------------
    @InsertProvider(type = MaterialInfoProvider.class,
                    method = "insertMaterialWithSpuCodeAndParams")
    int insertMaterialWithSpuCodeAndParams (String spuCode, int materialBaseId, Map<String, Object> params);

    @InsertProvider(type = MaterialInfoProvider.class,
                    method = "insertMaterialSkuWithSpuCodeAndParams")
    int insertMaterialSkuWithSpuCodeAndParams (String spuCode, Map<String, Object> params);

    @Insert("INSERT INTO materialUnit(spuCode, unitId, relatedId, conversionFactor, sort) VALUES(#{spuCode},#{unitId},#{relatedId},#{conversionFactor},#{sort});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addMaterialUnit (@Param("spuCode") String spuCode, @Param("unitId") int unitId, @Param("relatedId") int relatedId, @Param("conversionFactor") double conversionFactor, @Param("sort") int sort);

    @Insert("INSERT INTO unit(label, name, englishName, relatedId, conversionFactor, sort) VALUES(#{label},#{name},#{englishName},#{relatedId},#{conversionFactor},#{sort});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUnit (@Param("label") String label, @Param("name") String name, @Param("englishName") String englishName,
                 @Param("relatedId") int id, @Param("conversionFactor") double conversionFactor, @Param("sort") int sort);

    @InsertProvider(type = MaterialInfoProvider.class,
                    method = "insertMaterialBasePropWithMaterialBasePropParams")
    int insertMaterialBasePropWithMaterialBasePropParams (Map<String, Object> params);

    @InsertProvider(type = MaterialInfoProvider.class,
                    method = "insertMaterialBasePropValWithMaterialBasePropValParams")
    int insertMaterialBasePropValWithMaterialBasePropValParams (Map<String, Object> params);

    // ---------------------------------------- 获取物料分类信息部分 ----------------------------------------
    @Select("SELECT * FROM materialCategory;")
    List<MaterialCategoryModel> getMaterialCategory ();

    // ---------------------------------------- 修改物料分类信息部分 ----------------------------------------
    @Insert("INSERT INTO materialCategory(code,name,parentId) VALUES(#{code},#{name},#{parentId});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addMaterialCategory (@Param("code") String code, @Param("name") String name, @Param("parentId") int parentId);

    @Update("UPDATE materialCategory SET name=#{newName} WHERE name=#{oldName} AND parentId=#{parentId};")
    int updateMaterialCategory (@Param("newName") String newName, @Param("oldName") String oldName, @Param("parentId") int parentId);

    // ---------------------------------------- 删除物料分类信息部分 ----------------------------------------
    @Delete("DELETE FROM materialCategory WHERE name=#{name} AND parentId=#{parentId};")
    int deleteMaterialCategoryByNameAndParentId (@Param("name") String name, @Param("parentId") int parentId);

}
