package com.zhuri.material.materialmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Select;
import com.zhuri.material.materialmanagement.bean.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MaterialInfoMapper {
    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getBaseInfoWithBaseInfoParams")
    public List<MaterialBaseBean> getBaseInfoWithBaseInfoParams (Map<String, Object> params);

    @Select("SELECT * FROM materialBase WHERE spuCode=#{spuCode};")
    public List<MaterialBaseBean> getBaseInfoWithSpuCode (String spuCode);
    
    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getMaterialCategoryWithMaterialCategoryParams")
    public List<MaterialCategoryBean> getMaterialCategoryWithMaterialCategoryParams (Map<String, Object> params);

    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getMaterialWithMaterialParams")
    public List<MaterialBean> getMaterialWithMaterialParams (Map<String, Object> params);

}
