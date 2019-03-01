package org.material.managementservice.service.info.impl.supplier.baseprop;

import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author cplayer on 2019-03-02 06:25.
 * @version 1.0
 * 获取基本信息的工具类，存放了对应的实现函数
 */
@Component
public class BasePropObtainServiceSupplier {
    @Autowired
    private GeneralMapper generalMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 根据物料分类id和物料类别获取对应的物料基本属性
     *
     * @author cplayer
     * @date 2019-03-02 06:28     
     * @param catId 物料分类id
     *
     * @param propertyType 物料类别
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialBasePropModel>
     *
     */
    private List<MaterialBasePropModel> getMaterialBasePropByCatIdAndType (int catId, int propertyType) {
        MaterialBasePropModel param = new MaterialBasePropModel();
        param.setMaterialCatId(catId);
        param.setType(propertyType);
        return generalMapper.getMaterialBasePropWithMaterialBasePropParams(param);
    }

    /**
     * 根据物料分类id获取对应的物料基本属性
     *
     * @author cplayer
     * @date 2019-03-02 06:32
     * @param catId 物料分类id
     *
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialBasePropModel>
     *
     */
    public List<MaterialBasePropModel> getAllMaterialBasePropByCatId (int catId) {
        List<MaterialBasePropModel> result = new ArrayList<>();
        logger.info("获取物料基本属性，物料分类id为：" + catId);
        for (int i = 1; i <= 4; ++i) {
            List<MaterialBasePropModel> tmpResult = getMaterialBasePropByCatIdAndType(catId, i);
            logger.info("获取物料基本属性中，类别为：" + i);
            if (tmpResult != null && tmpResult.size() > 0) {
                result.addAll(tmpResult);
            }
        }
        result.sort(Comparator.comparingInt(MaterialBasePropModel::getSort));
        return result;
    }
}
