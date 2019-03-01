package org.material.managementservice.service.info.impl.supplier;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementservice.service.info.impl.supplier.baseinfo.BaseInfoModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.baseprop.FormatPropModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.controlprop.ControlPropModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.materialinfo.MaterialInfoModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.skuinfo.SkuInfoModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.unit.UnitModifyServiceSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cplayer on 2019-02-27 03:52.
 * @version 1.0
 * 物料信息更新的工具类，存放更新的通用工具函数
 */

@Component
public class InfoModifyServiceSupplier {
    @Autowired
    private BaseInfoModifyServiceSupplier baseInfoModifyServiceSupplier;
    @Autowired
    private MaterialInfoModifyServiceSupplier materialInfoModifyServiceSupplier;
    @Autowired
    private FormatPropModifyServiceSupplier formatPropModifyServiceSupplier;
    @Autowired
    private SkuInfoModifyServiceSupplier skuInfoModifyServiceSupplier;
    @Autowired
    private ControlPropModifyServiceSupplier controlPropModifyServiceSupplier;
    @Autowired
    private UnitModifyServiceSupplier unitModifyServiceSupplier;

    /**
     * 物料信息更新的中间函数，其中进行调用实际功能代码的操作
     *
     * @author cplayer
     * @date 2019-02-28 16:12
     * @param params 更新物料信息请求的参数
     *
     * @return BaseInfoModifyServiceSupplier中updateMaterialInfoForBaseData方法的返回值
     *
     */
    public int updateMaterialInfoForBaseData (MaterialInfoModifyRequest params) {
        return baseInfoModifyServiceSupplier.updateMaterialInfoForBaseData(params);
    }


    /**
     * 物料定义更新的对应函数
     *
     * @author cplayer
     * @date 2019-02-28 16:16
     * @param params 更新物料信息请求的参数
     *
     * @return materialInfoModifyServiceSupplier中updateMaterialInfoForMaterialDataByMaterial方法的返回值
     *
     */
    public int updateMaterialInfoForMaterialDataByMaterial (MaterialInfoModifyRequest params) {
        return materialInfoModifyServiceSupplier.updateMaterialInfoForMaterialDataByMaterial(params);
    }

    /**
     * 物料规格属性更新的对应函数
     *
     * @author cplayer
     * @date 2019-02-28 16:19
     * @param params 更新物料信息请求的参数
     *
     * @return formatPropModifyServiceSupplier中updateMaterialInfoForMaterialDataByFormat方法的返回值
     *
     */
    public int updateMaterialInfoForMaterialDataByFormat (MaterialInfoModifyRequest params) {
        return formatPropModifyServiceSupplier.updateMaterialInfoForMaterialDataByFormat(params);
    }

    /**
     * 更新物料sku信息的对应函数
     *
     * @author cplayer
     * @date 2019-02-28 16:22
     * @param params 更新物料信息请求的参数
     *
     * @return skuInfoModifyServiceSupplier中updateMaterialInfoForSkuData方法的返回值
     *
     */
    public int updateMaterialInfoForSkuData (MaterialInfoModifyRequest params) {
        return skuInfoModifyServiceSupplier.updateMaterialInfoForSkuData(params);
    }

    /**
     * 更新物料控制属性信息的对应函数
     *
     * @author cplayer
     * @date 2019-02-28 17:28
     * @param params 更新物料信息请求的参数
     *
     * @return controlPropModifyServiceSupplier中updateMaterialInfoForCtrData方法的返回值
     *
     */
    public int updateMaterialInfoForCtrData (MaterialInfoModifyRequest params) {
        return controlPropModifyServiceSupplier.updateMaterialInfoForCtrData(params);
    }

    /**
     * 更新物料计量单位信息的对应函数
     *
     * @author cplayer
     * @date 2019-03-01 22:20
     * @param params 更新物料信息请求的参数
     *
     * @return unitModifyServiceSupplier中updateMaterialInfoForUnitData方法的返回值
     *
     */
    public int updateMaterialInfoForUnitData (MaterialInfoModifyRequest params) {
        return unitModifyServiceSupplier.updateMaterialInfoForUnitData(params);
    }
}
