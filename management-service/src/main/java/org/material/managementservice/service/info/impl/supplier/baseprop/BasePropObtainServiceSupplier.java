package org.material.managementservice.service.info.impl.supplier.baseprop;

import org.material.managementfacade.model.requestmodel.MaterialBaseObtainBySpuAndMatCodeRequest;
import org.material.managementfacade.model.responsemodel.MaterialBaseObtainBySpuAndMatCodeElement;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
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
    @Autowired
    private InfoObtainMapper infoObtainMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 根据物料分类id和物料类别获取对应的物料基本属性
     *
     * @param catId        物料分类id
     * @param propertyType 物料类别
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialBasePropModel>
     * @author cplayer
     * @date 2019-03-02 06:28
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
     * @param catId 物料分类id
     * @return java.util.List<org.material.managementfacade.model.tablemodel.MaterialBasePropModel>
     * @author cplayer
     * @date 2019-03-02 06:32
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

    /**
     * 根据给定物料编码以及spu编码查询所有存在的物料基本属性
     *
     * @param params          请求对应的参数
     * @param materialCode    物料编码
     * @param commonBaseProps 所有通用的物料基本属性映射
     * @return org.material.managementfacade.model.responsemodel.MaterialBaseObtainBySpuAndMatCodeElement
     * @author cplayer
     * @date 2019-03-03 02:40
     */
    public MaterialBaseObtainBySpuAndMatCodeElement getMateialBaseBySpuCodeAndSpecificMatCode (
            MaterialBaseObtainBySpuAndMatCodeRequest params,
            String materialCode,
            Map<Integer, MaterialBasePropModel> commonBaseProps
    ) {
        MaterialBaseObtainBySpuAndMatCodeElement singleClass = new MaterialBaseObtainBySpuAndMatCodeElement();
        // 存放物料基本属性以及其值
        List<MaterialBasePropModel> singleResultBaseProp = new ArrayList<>();
        List<String> singleResultValue = new ArrayList<>();
        // 先查对应的物料基本属性值
        List<MaterialBasePropValModel> materialBasePropValResult = infoObtainMapper.getMaterialBasePropValWithSpuCodeAndMatCode(params.getSpuCode(), materialCode);
        Set<Integer> commonBasePropIdSet = new HashSet<>(commonBaseProps.keySet());
        for (MaterialBasePropValModel element : materialBasePropValResult) {
            // 此spu编码和物料编码对应的物料基本属性id
            int materialBasePropId = element.getMaterialBasePropId();
            // 先去除处理到的记录id
            if (commonBasePropIdSet.contains(materialBasePropId)) {
                commonBasePropIdSet.remove(materialBasePropId);
            }
            // 先查找是否在数据库中存在对应的数据记录
            List<MaterialBasePropModel> tmpResult = infoObtainMapper.getMaterialBasePropWithId(materialBasePropId);
            MaterialBasePropModel baseProp = MaterialGeneral.getInitElementOrFirstElement(tmpResult, MaterialBasePropModel.class);
            if (baseProp.getId() != -1) {
                // 存在对应的记录
                if (tmpResult.size() > 1) {
                    logger.info("在查找materialBasePropId = " + materialBasePropId + "的记录时出现了多个记录，请检查数据库！");
                    // 有重复记录，取数据出错，用空数据填充
                    singleResultBaseProp.add(new MaterialBasePropModel());
                    singleResultValue.add("");
                } else {
                    singleResultBaseProp.add(tmpResult.get(0));
                    singleResultValue.add(element.getValue());
                }
            } else {
                // 出现空记录，先查找是否存在默认值，反之以空数据填充
                if (commonBaseProps.containsKey(materialBasePropId)) {
                    // 存在则用默认值
                    singleResultBaseProp.add(commonBaseProps.get(materialBasePropId));
                    singleResultValue.add(element.getValue());
                } else {
                    logger.info("在查找materialBasePropId = " + materialBasePropId + "的记录时出现了空记录，请检查数据库！");
                    singleResultBaseProp.add(new MaterialBasePropModel());
                    singleResultValue.add("");
                }
            }
        }
        // 有一些通用属性没有记录，以空值填充
        if (commonBasePropIdSet.size() > 0) {
            for (int materialBasePropId : commonBasePropIdSet) {
                singleResultBaseProp.add(commonBaseProps.get(materialBasePropId));
                singleResultValue.add("");
            }
        }
        singleClass.setBaseProp(singleResultBaseProp);
        singleClass.setMaterialCode(materialCode);
        singleClass.setValues(singleResultValue);
        return singleClass;
    }
}
