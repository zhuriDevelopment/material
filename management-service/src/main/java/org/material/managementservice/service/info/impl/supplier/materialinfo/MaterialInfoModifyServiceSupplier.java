package org.material.managementservice.service.info.impl.supplier.materialinfo;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequestForMaterial;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cplayer on 2019-02-28 16:14
 * @version 1.0
 * 更新物料定义的补充类，存放具体的函数
 */

@Component
public class MaterialInfoModifyServiceSupplier {
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainMapper infoObtainMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 更新物料定义页面中物料定义部分的函数
     *
     * @author cplayer
     * @date 2019-02-27 21:08
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateMaterialInMaterial 代表成功
     *         MaterialErrCode.failedUpdateMaterialInMaterial 代表失败
     *
     */
    public int updateMaterialInfoForMaterialDataByMaterial (MaterialInfoModifyRequest params) {
        // 先根据spuCode获得物料基本信息ID
        List<MaterialBaseModel> baseList = infoObtainMapper.getBaseInfoWithSpuCode(params.getSpuCode());
        // 进而取得对应的物料基本信息对象，也就获得了id
        MaterialBaseModel baseInfo = MaterialGeneral.getInitElementOrFirstElement(baseList, MaterialBaseModel.class);
        if (baseInfo.getId() == -1) {
            // 若没有对应物料基本信息记录，则插入失败
            return MaterialErrCode.failedUpdateFormatInMaterial;
        }
        // 然后根据spuCode删除所有Material表记录
        int result = infoModifyMapper.deleteAllMaterialBySpuCode(params.getSpuCode());
        logger.info("删除了" + result + "条material表记录。");
        // 再完全重新插入
        result = 0;
        for (MaterialModifyRequestForMaterial element : params.getMaterialDatas().getMaterialList()) {
            MaterialModel param = new MaterialModel();
            param.setMaterialCode(element.getMaterialCode());
            param.setMaterialName(element.getMaterialName());
            param.setOldMaterialCode(element.getOldMaterialCode());
            param.setBarCode(element.getBarCode());
            param.setSpuCode(params.getSpuCode());
            param.setMaterialBaseId(baseInfo.getId());
            result = generalMapper.insertMaterialWithMaterialParams(param);
            logger.info("正在插入第" + result + "条记录！");
        }
        // 到此认为插入成功，返回正确结果
        return MaterialErrCode.successUpdateMaterialInMaterial;
    }
}
