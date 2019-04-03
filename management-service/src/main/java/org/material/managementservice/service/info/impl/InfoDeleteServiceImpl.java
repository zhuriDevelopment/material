package org.material.managementservice.service.info.impl;

import org.material.managementfacade.model.tablemodel.MaterialCtrlPropValVerModel;
import org.material.managementfacade.service.info.InfoDeleteService;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoDeleteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cplayer on 2019-04-03 16:02
 * @version 1.0
 * 物料信息删除的服务实现类
 */
@Component
public class InfoDeleteServiceImpl implements InfoDeleteService {
    @Autowired
    private InfoDeleteMapper infoDeleteMapper;
    @Autowired
    private GeneralMapper generalMapper;

    /**
     * 根据spu编码删除物料信息的函数
     *
     * @author cplayer
     * @date 2019-04-03 16:06
     * @param spuCode spu编码
     *
     * @return 对应的状态代码
     *
     */
    @Override
    public int deleteInfoBySpuCode (String spuCode) {
        try {
            // 删除物料基本信息
            infoDeleteMapper.deleteMaterialBaseBySpuCode(spuCode);
            // 删除物料信息
            infoDeleteMapper.deleteMaterialBySpuCode(spuCode);
            // 删除物料sku信息
            infoDeleteMapper.deleteMaterialSkuBySpuCode(spuCode);
            // 删除物料单位信息
            infoDeleteMapper.deleteMaterialUnitBySpuCode(spuCode);
            // 删除物料基本属性值
            infoDeleteMapper.deleteMaterialBasePropValBySpuCode(spuCode);
            // 删除所有物料控制属性值和物料控制属性值版本
            // 先获取所有可能的版本id
            MaterialCtrlPropValVerModel param = new MaterialCtrlPropValVerModel();
            param.setSpuCode(spuCode);
            List<MaterialCtrlPropValVerModel> searchRes = generalMapper.getCtrlPropValVerWithCtrlPropValVerParams(param);
            List<Integer> possibleId = searchRes.stream().map(ele -> ele.getId()).collect(Collectors.toList());
            for (Integer id : possibleId) {
                // 删除此id下所有的物料控制属性值
                infoDeleteMapper.deleteMaterialCtrlPropValByVersionId(id);
                // 删除对应id的物料控制属性值版本
                infoDeleteMapper.deleteMaterialCtrlPropValVerById(id);
            }
            return MaterialInfoErrCode.successOperation;
        } catch (Exception e) {
            e.printStackTrace();
            return MaterialInfoErrCode.failedDeleteMaterialInfoBySpuCode;
        }
    }

    /**
     * 根据spu编码数组删除物料信息的函数
     *
     * @author cplayer
     * @date 2019-04-03 16:08
     * @param spuCodes spu编码数组
     *
     * @return 对应的状态代码
     *
     */
    @Override
    public int deleteInfoBySpuCodes (List<String> spuCodes) {
        boolean flag = false;
        for (String spuCode : spuCodes) {
            int tmp = deleteInfoBySpuCode(spuCode);
            if (tmp == MaterialInfoErrCode.failedDeleteMaterialInfoBySpuCode) flag = true;
        }
        if (flag) return MaterialInfoErrCode.failedDeleteMaterialInfoBySpuCode;
        else return MaterialInfoErrCode.successOperation;
    }
}
