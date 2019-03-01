package org.material.managementservice.service.info.impl.supplier.unit;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialUnitModifyRequestElement;
import org.material.managementfacade.model.tablemodel.MaterialUnitModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cplayer on 2019-03-01 22:21.
 * @version 1.0
 * 更新物料计量单位信息的补充类，存放对应的实现函数
 */
@Component
public class UnitModifyServiceSupplier {
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    private MaterialUnitModel setParam (String spuCode, int defaultUnitId, double conversionFactor, int sort, int unitId) {
        MaterialUnitModel result = new MaterialUnitModel();
        result.setSpuCode(spuCode);
        result.setRelatedId(defaultUnitId);
        result.setConversionFactor(conversionFactor);
        result.setSort(sort);
        result.setUnitId(unitId);
        return result;
    }

    /**
     * 更新物料计量单位信息中更新部分的函数
     *
     * @author cplayer
     * @date 2019-03-01 23:08
     * @param updateList 待更新的物料计量单位信息列表
     *
     * @param spuCode spu编码
     *
     * @param defaultUnitId 默认计量单位id
     *
     * @return MaterialErrCode.successUpdateModifyUnit 成功更新
     *         MaterialErrCode.failedUpdateModifyUnit 更新失败
     *
     */
    public int updateUnitWithUnitList (List<MaterialUnitModifyRequestElement> updateList,
                                       String spuCode,
                                       int defaultUnitId) {
        int updateCount = 0;
        for (MaterialUnitModifyRequestElement element : updateList) {
            MaterialUnitModel param = setParam(spuCode, defaultUnitId, element.getConversionFactor(), element.getSort(), element.getUnitId());
            if (infoModifyMapper.updateUnitByParams(param) > 0) {
                updateCount++;
            }
        }
        if (updateCount == updateList.size()) {
            return MaterialErrCode.successUpdateModifyUnit;
        } else {
            return MaterialErrCode.failedUpdateModifyUnit;
        }
    }

    /**
     * 更新物料计量单位信息中新增部分的函数
     *
     * @author cplayer
     * @date 2019-03-01 23:09
     * @param insertList 待新增的物料计量单位信息列表
     *
     * @param spuCode spu编码
     *
     * @param defaultUnitId 默认计量单位id
     *
     * @return MaterialErrCode.successUpdateInsertUnit 新增成功
     *         MaterialErrCode.failedUpdateInsertUnit 新增失败
     *
     */
    public int insertUnitWithUnitList (List<MaterialUnitModifyRequestElement> insertList,
                                       String spuCode,
                                       int defaultUnitId) {
        int insertCount = 0;
        for (MaterialUnitModifyRequestElement element : insertList) {
            MaterialUnitModel param = setParam(spuCode, defaultUnitId, element.getConversionFactor(), element.getSort(), element.getUnitId());
            if (infoModifyMapper.insertUnitByParams(param) > 0) {
                insertCount++;
            }
        }
        if (insertCount == insertList.size()) {
            return MaterialErrCode.successUpdateInsertUnit;
        } else {
            return MaterialErrCode.failedUpdateInsertUnit;
        }
    }

    /**
     * 更新物料计量单位信息中删除部分的函数
     *
     * @author cplayer
     * @date 2019-03-01 23:10
     * @param deleteList 待删除的物料计量单位信息列表
     *
     * @return MaterialErrCode.successUpdateDeleteUnit 删除成功
     *         MaterialErrCode.failedUpdateDeleteUnit 删除成功
     *
     */
    public int deleteUnitWithUnitList (List<MaterialUnitModel> deleteList) {
        int deleteCount = 0;
        for (MaterialUnitModel deleteEle : deleteList) {
            if (generalMapper.deleteUnitByParams(deleteEle) > 0) {
                deleteCount++;
            }
        }
        if (deleteCount == deleteList.size()) {
            return MaterialErrCode.successUpdateDeleteUnit;
        } else {
            return MaterialErrCode.failedUpdateDeleteUnit;
        }
    }

    /**
     * 更新物料计量单位信息的函数
     *
     * @author cplayer
     * @date 2019-03-01 22:25
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateUnit 更新成功
     *         MaterialErrCode.failedUpdateUnit 更新失败
     *
     */
    public int updateMaterialInfoForUnitData (MaterialInfoModifyRequest params) {
        // 关联计量单位id统一设置成默认计量单位id
        // 先根据spu编码筛选出所有已有的计量单位记录
        MaterialUnitModel materialUnitParam = new MaterialUnitModel();
        materialUnitParam.setSpuCode(params.getSpuCode());
        LinkedList<MaterialUnitModel> existedList =
                new LinkedList<>(generalMapper.getMaterialUnitWithMaterialUnitParams(materialUnitParam));
        // 逐个遍历集合中的元素并查找是否有计量单位id相同的元素
        // 若计量单位id相同，则更新转换系数和排序号，更新后移除
        List<MaterialUnitModifyRequestElement> insertList = new ArrayList<>();
        List<MaterialUnitModifyRequestElement> updateList = new ArrayList<>();
        // 遍历提交上来的计量单位信息
        for (MaterialUnitModifyRequestElement materialUnit : params.getUnitDatas().getUnitList()) {
            boolean find = false;
            // 遍历目前数据库中已经有的记录去查找对应的记录
            for (MaterialUnitModel existedRecord : existedList) {
                if (materialUnit.getUnitId() == existedRecord.getUnitId()) {
                    // 存在不同，需要更新
                    if (materialUnit.getConversionFactor() != existedRecord.getConversionFactor() ||
                        materialUnit.getSort() !=  existedRecord.getSort()) {
                        updateList.add(materialUnit);
                    }
                    // 存储完对应的信息之后，移除对应的记录
                    existedList.remove(existedRecord);
                    find = true;
                    break;
                }
            }
            if (!find) {
                insertList.add(materialUnit);
            }
        }
        // 插入与更新对应的物料计量单位信息
        int insertResult = insertUnitWithUnitList(insertList, params.getSpuCode(), params.getUnitDatas().getDefaultUnitId());
        int updateResult = updateUnitWithUnitList(updateList, params.getSpuCode(), params.getUnitDatas().getDefaultUnitId());
        int deleteResult = deleteUnitWithUnitList(existedList);
        return insertResult * updateResult * deleteResult;
    }
}
