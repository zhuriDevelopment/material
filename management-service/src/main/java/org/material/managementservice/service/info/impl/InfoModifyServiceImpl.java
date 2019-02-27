package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.service.info.InfoModifyService;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.service.info.impl.supplier.InfoModifyServiceSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */

@Component
public class InfoModifyServiceImpl implements InfoModifyService {
    @Autowired
    private InfoModifyServiceSupplier infoModifyServiceSupplier;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /*
        物料基本信息：1
        物料定义：2
        SKU定义：3
        附件管理：4 （依赖于物料基本信息Id）
        采购和库存属性：5
        计划类属性：6
        销售类属性：7
        质量类属性：8
        财务类属性：9
        计量单位：10
        规格信息：11
    */
    /**
     * 更新物料信息的函数
     *
     * 包含更新：
     * 物料基本信息（materialBase表）
     * 物料定义（material表）
     * SKU定义
     * 附件管理（暂无）
     * 控制属性：包括采购和库存属性、计划类属性、销售类属性、质量类属性和财务类属性
     * 计量单位
     * 规格信息
     *
     * @author cplayer
     * @date 2019-02-27 04:37
     * @param params 更新物料信息请求的参数
     *
     * @return 成功了的操作个数
     *
     */
    @Override
    public int updateMaterialInfo (MaterialInfoModifyRequest params) {
        int result = 0;
        String spuCode = params.getSpuCode();
        // 更新物料基本信息
        if (params.getBaseDatas() != null) {
            int updateBaseResult = infoModifyServiceSupplier.updateMaterialInfoForBaseData(params);
            // 成功的操作进入统计
            if (updateBaseResult == MaterialErrCode.successUpdateMaterialBase) {
                result++;
            }
        }
        // 更新物料定义
        if (params.getMaterialDatas() != null) {
            int updateMaterialResult = infoModifyServiceSupplier.updateMaterialInfoForMaterialData(params);
            if (updateMaterialResult == MaterialErrCode.successUpdateMaterial) {
                result++;
            }
        }
        return result;
    };
}
