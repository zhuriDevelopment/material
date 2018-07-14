package com.zhuri.material.materialmanagement.mapper;

import com.zhuri.material.materialmanagement.bean.tablebean.MaterialBaseBean;
import com.zhuri.material.materialmanagement.bean.tablebean.MaterialBean;
import com.zhuri.material.materialmanagement.bean.tablebean.MaterialCategoryBean;
import com.zhuri.material.materialmanagement.bean.tablebean.MaterialSkuBean;
import com.zhuri.material.materialmanagement.bean.tablebean.MaterialFilesBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MaterialInfoMapper {
    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getBaseInfoWithBaseInfoParams")
    List<MaterialBaseBean> getBaseInfoWithBaseInfoParams (Map<String, Object> params);

    @Select("SELECT * FROM materialBase WHERE spuCode=#{spuCode};")
    List<MaterialBaseBean> getBaseInfoWithSpuCode (String spuCode);
    
    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getMaterialCategoryWithMaterialCategoryParams")
    List<MaterialCategoryBean> getMaterialCategoryWithMaterialCategoryParams (Map<String, Object> params);

    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getMaterialWithMaterialParams")
    List<MaterialBean> getMaterialWithMaterialParams (Map<String, Object> params);

    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getMaterialSkuWithMaterialSkuParams")
    List<MaterialSkuBean> getMaterialSkuWithMaterialSkuParams (Map<String, Object> params);

    @SelectProvider(type = com.zhuri.material.materialmanagement.mapper.provider.MaterialInfoProvider.class,
                    method = "getFilesWithFilesParams")
    List<MaterialFilesBean> getFilesWithFilesParams (Map<String, Object> params);

}
