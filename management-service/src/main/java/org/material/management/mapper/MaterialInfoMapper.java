package org.material.management.mapper;

import org.apache.ibatis.annotations.*;
import org.material.management.mapper.provider.MaterialInfoProvider;
import org.material.management.model.tablemodel.*;

import java.util.Map;
import java.util.List;

@Mapper
public interface MaterialInfoMapper {
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
                    method = "getMaterialCategoryWithMaterialCategoryParams")
    List<MaterialCategoryModel> getMaterialCategoryWithMaterialCategoryParams (Map<String, Object> params);

    @SelectProvider(type = MaterialInfoProvider.class,
                    method = "getMaterialSkuWithMaterialSkuParams")
    List<MaterialSkuModel> getMaterialSkuWithMaterialSkuParams (Map<String, Object> params);

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

    @Insert("INSERT INTO materialCategory(code,name,parentId) VALUES(#{code},#{name},#{parentId})")
    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "id")
    int addMaterialCategory(@Param("code") String code, @Param("name") String name, @Param("parentId") int parentId);

    @Update("UPDATE materialCategory SET name=#{name} WHERE code=#{code} AND parentId=#{parentId}")
    int updateMaterialCategory(@Param("code")String code,@Param("name")String name,@Param("parentId") int parentId);

    @Delete("DELETE FROM materialCategory WHERE code=#{code}")
    int deleteMaterialCategoryByCode(String code);

}
