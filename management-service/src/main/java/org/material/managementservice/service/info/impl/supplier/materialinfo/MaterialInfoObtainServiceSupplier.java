package org.material.managementservice.service.info.impl.supplier.materialinfo;

import org.material.managementfacade.model.requestmodel.MaterialInfoRequest;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoUnitResponse;
import org.material.managementfacade.model.tablemodel.*;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.InfoObtainServiceSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cplayer on 2019-02-25 21:22
 * @version 1.0
 * getMaterialInfo接口的补充类，包含其各个小功能模块函数
 */
@Component
public class MaterialInfoObtainServiceSupplier {
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private InfoObtainServiceSupplier infoObtainServiceSupplier;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 根据参数中的spuCode获取物料基本信息的函数
     *
     * @author cplayer
     * @date 2019-02-25 21:26
     * @param params 从getMaterialInfo接口传入的参数
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialBaseModel>
     *
     */
    public List<MaterialBaseModel> getMaterialInfoForBaseInfo (MaterialInfoRequest params) {
        return infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode());
    }
    
    /**
     * 根据参数中的spuCode获取物料信息的函数
     *
     * @author cplayer
     * @date 2019-02-25 21:27
     * @param params 从getMaterialInfo接口传入的参数
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialModel>
     *
     */
    public List<MaterialModel> getMaterialInfoForMaterialInfo (MaterialInfoRequest params) {
        return infoObtainMapper.getMaterialWithSpuCode(params.getSpuCode());
    }

    /**
     * 根据参数中的spuCode获取sku信息的函数
     *
     * @author cplayer
     * @date 2019-02-25 21:30
     * @param params 从getMaterialInfo接口传入的参数
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialSkuModel>
     *
     */
    public List<MaterialSkuModel> getMaterialInfoForMaterialSkuInfo (MaterialInfoRequest params) {
        return infoObtainMapper.getMaterialSkuWithSpuCode(params.getSpuCode());
    }

    /**
     * 根据参数中的spuCode获取附件信息的函数
     *
     * @author cplayer
     * @date 2019-02-25 21:32
     * @param params 从getMaterialInfo接口传入的参数
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialFilesModel>
     *
     */
    public List<MaterialFilesModel> getMaterialInfoForFileInfo (MaterialInfoRequest params) {
        // 附件功能待之后完善
        return null;
    }

    /**
     * 根据参数中的spuCode获取所有计量单位信息的函数
     *
     * @author cplayer
     * @date 2019-02-25 21:38
     * @param params 从getMaterialInfo接口传入的参数
     *
     * @return org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoUnitResponse
     *
     */
    public MaterialInfoUnitResponse getMaterialInfoForUnitInfo (MaterialInfoRequest params) {
        MaterialInfoUnitResponse result = null;
        // 先获取所有的物料基本信息记录，通过spuCode
        List<MaterialBaseModel> baseResult = infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode());
        // 正确情况下必须只有一条记录，因为spuCode唯一；但如果多于一条，暂定处理方法为修改第一条记录
        if (baseResult.size() > 1) {
            logger.error("根据参数获取计量单位信息时，获取到的MaterialBase记录不止一条！");
        }
        // 获取用于获取计量单位的MaterialBase记录
        MaterialBaseModel baseModel = infoObtainServiceSupplier.getInitElementOrFirstElement(baseResult, MaterialBaseModel.class);
        if (baseModel.getSpuCode().equals(params.getSpuCode())) {
            // 此时可以确定获取到的结果是正确的结果，因为spuCode相等
            result = new MaterialInfoUnitResponse();
            // 记录默认计量单位id
            int defaultUnitId = baseModel.getDefaultUnitId();
            // 获取所有物料计量单位
            List<MaterialUnitModel> materialUnitList = infoObtainMapper.getMaterialUnitWithSpuCode(params.getSpuCode());
            List<UnitModel> allUnit = new ArrayList<>();
            // 对于每个物料单位记录逐个查询，以覆盖转换系数和排序号
            for (MaterialUnitModel materialUnitModel : materialUnitList) {
                UnitModel param = new UnitModel();
                param.setId(materialUnitModel.getUnitId());
                UnitModel unit = infoObtainServiceSupplier.getInitElementOrFirstElement(
                        generalMapper.getUnitWithUnitParams(param), UnitModel.class);
                // 覆盖掉排序号和转换系数
                if (materialUnitModel.getConversionFactor() >= 0) {
                    unit.setConversionFactor(materialUnitModel.getConversionFactor());
                }
                if (materialUnitModel.getSort() >= 0) {
                    unit.setSort(materialUnitModel.getSort());
                }
                allUnit.add(unit);
                if (unit.getId() == defaultUnitId) {
                    result.setDefaultUnit(unit);
                }
            }
            result.setUnitList(allUnit);
        }
        // 若不正确，则返回null
        return result;
    }
}
