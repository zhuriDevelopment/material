package org.material.managementservice.service.info.impl.supplier.baseprop;

import org.material.managementfacade.model.requestmodel.infomodify.InfoModifyByCatCodeAndNameBasePropEle;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 18:13
 * @version 1.0
 * 更新物料基本属性的补充类，存放了对应的工具函数
 */
@Component
public class BasePropModifyServiceSupplier {
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;

    /**
     * 根据物料分类编码删除所有已存在的物料基本属性的函数
     *
     * @author cplayer
     * @date 2019-03-09 04:54     
     * @param catId 物料分类编码
     *
     * @return MaterialInfoErrCode.successDeleteAllBasePropByCatId 成功
     *         MaterialInfoErrCode.failedDeleteAllBasePropByCatId 失败
     *
     */
    public int deleteAllMaterialBasePropByCatId (int catId) {
        MaterialBasePropModel param = new MaterialBasePropModel();
        param.setMaterialCatId(catId);
        List<MaterialBasePropModel> materialBasePropTmp = generalMapper.getMaterialBasePropWithMaterialBasePropParams(param);
        // 由于可能物料基础属性可能全部变更，故需要先删除所有已存在的记录，然后全部重新插入
        // 删除的时候要注意要同时从materialBaseProp表和materialBasePropVal表中一起删除，以防在materialBasePropVal表中出现无效数据
        if (materialBasePropTmp != null && !materialBasePropTmp.isEmpty()) {
            // 需要执行删除操作
            for (MaterialBasePropModel element : materialBasePropTmp) {
                int deletePropValRes = infoModifyMapper.deleteAllMaterialBasePropValWithMaterialBasePropId(element.getId());
                logger.info("从materialBasePropVal表中删除materialBasePropId = " + element.getId() + "的记录返回结果为：" + deletePropValRes);
            }
            int deletePropRes = infoModifyMapper.deleteAllMaterialBasePropWithCatId(catId);
            logger.info("从materialBaseProp表中删除所有materialCatId = " + catId + "的记录返回结果为：" + deletePropRes);
        } else {
            logger.info("原数据库没有materialCatId = " + catId + "的记录，无需删除！");
        }
        return MaterialInfoErrCode.successDeleteAllBasePropByCatId;
    }

    /**
     * 根据物料分类编码、物料名称以及待更新的数据更新物料基本属性的实现函数
     *
     * @param catId     物料分类id
     * @param baseProps 待更新的物料基本属性
     * @return MaterialInfoErrCode.successUpdateMaterialBasePropWithCatIdAndName 更新成功
     * MaterialInfoErrCode.failedUpdateMaterialBasePropWithCatIdAndName 更新失败
     * @author cplayer
     * @date 2019-03-02 18:18
     */
    public int updateMaterialBasePropByCatId (int catId, List<InfoModifyByCatCodeAndNameBasePropEle> baseProps) {
        int result = MaterialInfoErrCode.successUpdateMaterialBasePropWithCatIdAndName;
        deleteAllMaterialBasePropByCatId(catId);
        MaterialBasePropModel param;
        // 再添加所有新的数据
        for (InfoModifyByCatCodeAndNameBasePropEle element : baseProps) {
            // 每一个元素都是一条数据库记录
            param = new MaterialBasePropModel();
            param.setMaterialCatId(catId);
            param.setType(element.getType());
            param.setLabel(element.getLabel());
            param.setName(element.getName());
            param.setValueRange(element.getValueRange());
            param.setSort(element.getSort());
            int insertResult = infoModifyMapper.insertMaterialBasePropWithMaterialBasePropParams(param);
            if (insertResult <= 0) {
                result = MaterialInfoErrCode.failedUpdateMaterialBasePropWithCatIdAndName;
            }
            logger.info(String.format("在materialBaseProp中添加记录，添加的id为：%d，返回值为：%d。", param.getId(), insertResult));
        }
        return result;
    }
}
