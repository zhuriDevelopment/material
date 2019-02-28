package org.material.managementservice.service.info.impl.supplier;

import com.sun.xml.internal.ws.api.model.MEP;
import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialBaseModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequestForFormatProp;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequestForMaterial;
import org.material.managementfacade.model.responsemodel.MaterialInfo.MaterialInfoBasePropResponse;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.baseinfo.BaseInfoModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.baseprop.FormatPropModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.materialinfo.MaterialInfoModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.skuinfo.SkuInfoModifyServiceSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 物料定义更新的中间函数
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
     * 物料规格属性更新的中间函数
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
     * 更新物料定义页面中更新信息的函数
     *
     * @author cplayer
     * @date 2019-02-27 21:14
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateMaterial 代表成功
     *         MaterialErrCode.failedUpdateMaterial 代表失败
     *
     */
    public int updateMaterialInfoForMaterialData (MaterialInfoModifyRequest params) {
        // 更新物料定义部分的结果
        int materialResult = updateMaterialInfoForMaterialDataByMaterial(params);
        // 更新规格信息的结果
        int formatResult = updateMaterialInfoForMaterialDataByFormat(params);
        return materialResult * formatResult;
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
}
