package org.material.managementservice.service.info.impl.supplier.skuinfo;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialSkuModifyRequestElement;
import org.material.managementfacade.model.tablemodel.MaterialSkuModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cplayer on 2019-02-28 16:09
 * @version 1.0
 * 物料sku信息更新的补充类，存放对应的功能函数
 */

@Component
public class SkuInfoModifyServiceSupplier {
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private InfoModifyMapper infoModifyMapper;

    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 检查对应的sku数据是否合法的函数
     *
     * @param skuData 需要检查的sku数据
     * @return true为合法，false为不合法
     * @author cplayer
     * @date 2019-02-28 16:27
     */
    public boolean isSkuDataValid (MaterialSkuModifyRequestElement skuData) {
        // 有空属性，返回否
        if (MaterialGeneral.isContainsEmpty(skuData)) {
            return false;
        }
        // 逐个检查
        if (skuData.getSkuCode().equals("") ||
                skuData.getSkuName().equals("") ||
                skuData.getMaterialCode().equals("") ||
                skuData.getUnitId() == -1 ||
                skuData.getBarCode().equals("") ||
                skuData.getPurchasePrice() == -1 ||
                skuData.getSellingPrice() == -1 ||
                skuData.getDescription().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * 将MaterialSkuModifyRequestElement转换成MaterialSkuModel对象的函数
     *
     * @author cplayer
     * @date 2019-02-28 17:19
     * @param skuElement 待转换的MaterialSkuModifyRequestElement对象
     *
     * @return org.material.managementfacade.model.tablemodel.MaterialSkuModel
     *
     */
    public MaterialSkuModel convertToSkuModel (MaterialSkuModifyRequestElement skuElement) {
        MaterialSkuModel result = new MaterialSkuModel();
        result.setSkuCode(skuElement.getSkuCode());
        result.setSkuName(skuElement.getSkuName());
        result.setMaterialCode(skuElement.getMaterialCode());
        result.setUnitId(skuElement.getUnitId());
        result.setBarCode(skuElement.getBarCode());
        result.setPurchasePrice(skuElement.getPurchasePrice());
        result.setSellingPrice(skuElement.getSellingPrice());
        result.setDescription(skuElement.getDescription());
        return result;
    }

    /**
     * 删除集合中的所有对应的materialSku信息的函数
     *
     * @author cplayer
     * @date 2019-02-28 17:20
     * @param deleteSet 待删除的materialSku表记录集合
     *
     * @param spuCode 对应的spu编码
     *
     * @return 操作成功的行数
     *
     */
    public int deleteAllSkuInfosInSet (Set<MaterialSkuModifyRequestElement> deleteSet, String spuCode) {
        int countResult = 0;
        for (MaterialSkuModifyRequestElement skuData : deleteSet) {
            if (isSkuDataValid(skuData)) {
                logger.info("skuData合法，删除中。。");
                MaterialSkuModel param = convertToSkuModel(skuData);
                param.setSpuCode(spuCode);
                int result = infoModifyMapper.deleteMaterialSkuByParams(param);
                if (result > 0) {
                    countResult += result;
                    logger.info(String.format("删除sku成功，skuCode = %s，spuCode = %s, skuName = %s。",
                            param.getSkuCode(),
                            param.getSpuCode(),
                            param.getSkuName()));
                } else {
                    logger.error("删除sku数据失败！");
                }
            } else {
                logger.error("上传的skuData中存在不合法对象！");
            }
        }
        return countResult;
    }

    /**
     * 添加集合中的所有对应的materialSku信息的函数
     *
     * @author cplayer
     * @date 2019-02-28 17:20
     * @param insertSet 待添加的materialSku表记录集合
     *
     * @param spuCode 对应的spu编码
     *
     * @return 操作成功的行数
     *
     */
    public int insertAllSkuInfosInSet (Set<MaterialSkuModifyRequestElement> insertSet, String spuCode) {
        int countResult = 0;
        for (MaterialSkuModifyRequestElement skuData : insertSet) {
            // 检查上传的skudata是否合法
            if (isSkuDataValid(skuData)) {
                logger.info("skuData合法，更新中。。");
                // 合法则更新
                MaterialSkuModel param;
                // 逐个的更新
                param = convertToSkuModel(skuData);
                param.setSpuCode(spuCode);
                int result = infoModifyMapper.insertMaterialSkuByParams(param);
                if (result > 0) {
                    countResult += result;
                    logger.info(String.format("更新sku成功，id = %d。", param.getId()));
                } else {
                    logger.error("插入sku数据失败！");
                }
            } else {
                logger.error("上传的skuData中存在不合法对象！");
            }
        }
        return countResult;
    }

    /**
     * 更新sku信息的函数
     *
     * @author cplayer
     * @date 2019-02-28 17:21
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateSku 成功更新
     *         MaterialErrCode.failedUpdateSku 更新失败
     *
     */
    public int updateMaterialInfoForSkuData (MaterialInfoModifyRequest params) {
        // 先获取当前数据库中已经有的sku信息，并存到集合中
        Set<MaterialSkuModifyRequestElement> skuExistsSet = infoObtainMapper.getMaterialSkuWithSpuCode(params.getSpuCode())
                .stream()
                .map(ele -> {
                    MaterialSkuModifyRequestElement result = new MaterialSkuModifyRequestElement();
                    result.setSkuCode(ele.getSkuCode());
                    result.setBarCode(ele.getBarCode());
                    result.setDescription(ele.getDescription());
                    result.setMaterialCode(ele.getMaterialCode());
                    result.setPurchasePrice(ele.getPurchasePrice());
                    result.setSellingPrice(ele.getSellingPrice());
                    result.setUnitId(ele.getUnitId());
                    result.setSkuName(ele.getSkuName());
                    return result;
                })
                .collect(Collectors.toSet());
        // 获取目前需要更新的sku信息，若已经在数据库中存在了，则去除
        Set<MaterialSkuModifyRequestElement> skuSet = new HashSet<>(params.getSkuDatas().getSkuList());
        // 获取需要删除的sku信息
        Set<MaterialSkuModifyRequestElement> skuDeleteSet = new HashSet<>(skuExistsSet);
        skuDeleteSet.removeAll(skuSet);
        skuSet.remove(skuExistsSet);
        // 删除不需要了的信息
        int deleteResult = deleteAllSkuInfosInSet(skuDeleteSet, params.getSpuCode());
        // 更新需要插入的信息
        int insertResult = insertAllSkuInfosInSet(skuSet, params.getSpuCode());
        logger.info(String.format("更新sku信息时，deleteResult = %d, insertResult = %d。", deleteResult, insertResult));
        if (deleteResult * insertResult > 0) {
            return MaterialErrCode.successUpdateSku;
        } else {
            return MaterialErrCode.failedUpdateSku;
        }
    }
}
