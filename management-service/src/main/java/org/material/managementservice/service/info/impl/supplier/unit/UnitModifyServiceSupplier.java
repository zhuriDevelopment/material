package org.material.managementservice.service.info.impl.supplier.unit;

import org.material.managementfacade.model.requestmodel.InfoModifyReq;
import org.material.managementfacade.model.requestmodel.infomodify.MatUnitModifyReqEle;
import org.material.managementfacade.model.tablemodel.MaterialUnitModel;
import org.material.managementservice.general.MaterialInfoErrCode;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cplayer on 2019-03-01 22:21.
 * @version 1.0
 * 更新物料计量单位信息的补充类，存放对应的实现函数
 */
@Component
public class UnitModifyServiceSupplier {
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;

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
     * @param updateList    待更新的物料计量单位信息列表
     * @param spuCode       spu编码
     * @param defaultUnitId 默认计量单位id
     * @return MaterialInfoErrCode.successUpdateModifyUnit 成功更新
     * MaterialInfoErrCode.failedUpdateModifyUnit 更新失败
     * @author cplayer
     * @date 2019-03-01 23:08
     */
    public int updateUnitWithUnitList (List<MatUnitModifyReqEle> updateList,
                                       String spuCode,
                                       int defaultUnitId) {
        int updateCount = 0;
        for (MatUnitModifyReqEle element : updateList) {
            MaterialUnitModel param = setParam(spuCode, defaultUnitId, element.getConversionFactor(), element.getSort(), element.getUnitId());
            if (infoModifyMapper.updateUnitByParams(param) > 0) {
                updateCount++;
            }
        }
        if (updateCount == updateList.size()) {
            return MaterialInfoErrCode.successUpdateModifyUnit;
        } else {
            return MaterialInfoErrCode.failedUpdateModifyUnit;
        }
    }

    /**
     * 更新物料计量单位信息中新增部分的函数
     *
     * @param insertList    待新增的物料计量单位信息列表
     * @param spuCode       spu编码
     * @param defaultUnitId 默认计量单位id
     * @return MaterialInfoErrCode.successUpdateInsertUnit 新增成功
     * MaterialInfoErrCode.failedUpdateInsertUnit 新增失败
     * @author cplayer
     * @date 2019-03-01 23:09
     */
    public int insertUnitWithUnitList (List<MatUnitModifyReqEle> insertList,
                                       String spuCode,
                                       int defaultUnitId) {
        int insertCount = 0;
        for (MatUnitModifyReqEle element : insertList) {
            MaterialUnitModel param = setParam(spuCode, defaultUnitId, element.getConversionFactor(), element.getSort(), element.getUnitId());
            if (infoModifyMapper.insertUnitByParams(param) > 0) {
                insertCount++;
            }
        }
        if (insertCount == insertList.size()) {
            return MaterialInfoErrCode.successUpdateInsertUnit;
        } else {
            return MaterialInfoErrCode.failedUpdateInsertUnit;
        }
    }

    /**
     * 更新物料计量单位信息中删除部分的函数
     *
     * @param deleteList 待删除的物料计量单位信息列表
     * @return MaterialInfoErrCode.successUpdateDeleteUnit 删除成功
     * MaterialInfoErrCode.failedUpdateDeleteUnit 删除成功
     * @author cplayer
     * @date 2019-03-01 23:10
     */
    public int deleteUnitWithUnitList (List<MaterialUnitModel> deleteList) {
        int deleteCount = 0;
        for (MaterialUnitModel deleteEle : deleteList) {
            if (generalMapper.deleteUnitByParams(deleteEle) > 0) {
                deleteCount++;
            }
        }
        if (deleteCount == deleteList.size()) {
            return MaterialInfoErrCode.successUpdateDeleteUnit;
        } else {
            return MaterialInfoErrCode.failedUpdateDeleteUnit;
        }
    }

    /**
     * 更新物料计量单位信息的函数
     *
     * @param params 更新物料信息请求的参数
     * @return MaterialInfoErrCode.successUpdateUnit 更新成功
     * MaterialInfoErrCode.failedUpdateUnit 更新失败
     * @author cplayer
     * @date 2019-03-01 22:25
     */
    public int updateMaterialInfoForUnitData (InfoModifyReq params) {
        // 关联计量单位id统一设置成默认计量单位id
        // 先根据spu编码筛选出所有已有的计量单位记录
        MaterialUnitModel materialUnitParam = new MaterialUnitModel();
        materialUnitParam.setSpuCode(params.getSpuCode());
        LinkedList<MaterialUnitModel> existedList =
                new LinkedList<>(generalMapper.getMaterialUnitWithMaterialUnitParams(materialUnitParam));
        // 逐个遍历集合中的元素并查找是否有计量单位id相同的元素
        // 若计量单位id相同，则更新转换系数和排序号，更新后移除
        List<MatUnitModifyReqEle> insertList = new ArrayList<>();
        List<MatUnitModifyReqEle> updateList = new ArrayList<>();
        // 遍历提交上来的计量单位信息
        for (MatUnitModifyReqEle materialUnit : params.getUnitDatas().getUnitList()) {
            boolean find = false;
            // 遍历目前数据库中已经有的记录去查找对应的记录
            for (MaterialUnitModel existedRecord : existedList) {
                if (materialUnit.getUnitId() == existedRecord.getUnitId()) {
                    // 存在不同，需要更新
                    if (materialUnit.getConversionFactor() != existedRecord.getConversionFactor() ||
                            materialUnit.getSort() != existedRecord.getSort()) {
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
