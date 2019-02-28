package org.material.managementservice.service.info.impl.supplier.controlprop;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.springframework.stereotype.Component;

/**
 * @author cplayer on 2019-02-28 17:27
 * @version 1.0
 * 物料控制属性更新的工具类，存放具体的实现
 */
@Component
public class ControlPropModifyServiceSupplier {

    /**
     * 更新物料控制属性对应的功能函数
     *
     * @author cplayer
     * @date 2019-02-28 17:30
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateControlPropInMaterial 更新成功
     *         MaterialErrCode.failedUpdateControlPropInMaterial 更新失败
     *
     */
    public int updateMaterialInfoForCtrData (MaterialInfoModifyRequest params) {
        // 在后续设计出来之前，组织编码统一设置成-1，版本号统一设置成"ver-001"
        int organizationCode = -1;
        String version = "ver-001";
        String spuCode = params.getSpuCode();
        return 0;
    }
}
