package org.material.managementservice.service.info.impl.supplier;

import org.material.managementfacade.model.requestmodel.infomodify.MaterialBaseModifyRequest;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author cplayer on 2019-02-27 03:52.
 * @version 1.0
 * 物料信息更新的工具类，存放更新的通用工具函数
 */

@Component
public class InfoModifyServiceSupplier {
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    @Autowired
    private GeneralMapper generalMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 更新物料基本信息的函数
     *
     * 若成功更新返回MaterialErrCode.successUpdateMaterialBase
     * 不成功返回MaterialErrCode.failedUpdateMaterialBase
     *
     * @author cplayer
     * @date 2019-02-27 04:35
     * @param updateBaseDatas 物料基本信息更新请求封装类
     *
     * @return int
     *
     */
    public int updateMaterialInfoForBaseData (MaterialBaseModifyRequest updateBaseDatas) {
        // 先检查spuCode是否为空
        if (updateBaseDatas.getSpuCode() == null) {
            logger.error("更新物料基本信息过程中，spuCode为空！禁止的操作！");
            return MaterialErrCode.failedUpdateMaterialBase;
        }
        // 再检查是否只有一条spuCode记录
        int recordNum = infoModifyMapper.countBaseBySpuCode(updateBaseDatas.getSpuCode());
        if (recordNum > 1) {
            logger.error("更新物料基本信息过程中，spuCode为指定代码的记录有多条！不正确的库内数据！");
            return MaterialErrCode.failedUpdateMaterialBase;
        }
        // 转换成MaterialBaseModel
        MaterialBaseModel param = new MaterialBaseModel();
        param.setSpuCode(updateBaseDatas.getSpuCode());
        param.setSpuName(updateBaseDatas.getSpuName());
        param.setType(updateBaseDatas.getType());
        param.setMaterialCatId(updateBaseDatas.getMaterialCatId());
        param.setSource(updateBaseDatas.getSource());
        param.setUsage(updateBaseDatas.getUsage());
        param.setDesignCode(updateBaseDatas.getDesignCode());
        param.setDesignVersion(updateBaseDatas.getDesignVersion());
        param.setMnemonic(updateBaseDatas.getMnemonic());
        param.setNote(updateBaseDatas.getNote());
        if (recordNum == 0) {
            // 若没有spuCode记录，则新增
            // 此时设置创建时间为当前服务器时间
            param.setCreateDate(new Date());
            generalMapper.insertMaterialBaseWithMaterialBaseParams(param);
            // 此时返回的id应该为新增记录的id
            int id = param.getId();
            logger.info("新增物料基本信息成功，新增记录id = " + id);
            return MaterialErrCode.successUpdateMaterialBase;
        } else {
            // 反之则更新
            int affectedRows = generalMapper.updateMaterialBaseWithMaterialBaseParams(param);
            // 检查受影响记录条数
            if (affectedRows <= 0) {
                return MaterialErrCode.failedUpdateMaterialBase;
            } else {
                return MaterialErrCode.successUpdateMaterialBase;
            }
        }
    }
}
