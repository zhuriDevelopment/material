package org.material.managementservice.service.info.impl.supplier.baseprop;

import org.material.managementfacade.model.requestmodel.MaterialInfoModifyRequest;
import org.material.managementfacade.model.requestmodel.infomodify.MaterialModifyRequestForFormatProp;
import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;
import org.material.managementservice.general.MaterialErrCode;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoModifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cplayer on 2019-02-28 16:17
 * @version 1.0
 * 物料规格信息更新的补充类，存放具体的实现函数
 */
@Component
public class FormatPropModifyServiceSupplier {
    @Autowired
    private InfoModifyMapper infoModifyMapper;
    @Autowired
    private GeneralMapper generalMapper;
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");

    /**
     * 更新单个物料规格属性值的函数
     *
     * @author cplayer
     * @date 2019-02-28 15:53
     * @param params 更新物料信息请求的参数
     *
     * @param data 单个待更新的规格属性值对象
     *
     * @param propList 物料基本属性列表
     *
     * @return MaterialErrCode.failedUpdateSingleFormatInMaterial 单个规格信息更新失败
     *         MaterialErrCode.successUpdateSingleFormatInMaterial 单个规格信息更新成功
     *         MaterialErrCode.notAllowedFormatObject 规格信息名字未找到
     *
     */
    public int updateMaterialInfoForMaterialDataBySingleFormat (MaterialInfoModifyRequest params,
                                                                MaterialModifyRequestForFormatProp data,
                                                                List<MaterialBasePropModel> propList) {
        // 若物料编码、值为空，则直接跳过
        if (data.getMaterialCode() == null || data.getValue() == null || data.getName() == null) {
            logger.error(String.format("物料规格信息中，存在不符合要求的对象！"));
            return MaterialErrCode.notAllowedFormatObject;
        }
        // 先获取待更新的属性名
        String name = data.getName();
        // 在物料基本属性列表中查找对应的名字
        int index = propList.indexOf(name);
        if (index != -1) {
            // 若存在，获取物料基本属性id
            int id = propList.get(index).getId();
            int count = infoModifyMapper.countMaterialBasePropValByParams(id,
                    data.getMaterialCode(),
                    params.getSpuCode());
            int modifyResult;
            MaterialBasePropValModel paramPropVal = new MaterialBasePropValModel();
            paramPropVal.setMaterialBasePropId(id);
            paramPropVal.setMaterialCode(data.getMaterialCode());
            paramPropVal.setSpuCode(params.getSpuCode());
            paramPropVal.setValue(data.getValue());
            if (count > 0) {
                // 若存在对应的物料基本属性值记录，则更新
                modifyResult = infoModifyMapper.updateMaterialBasePropValByParams(paramPropVal);
            } else {
                // 否则则插入
                modifyResult = infoModifyMapper.insertMaterialBasePropValByParams(paramPropVal);
                logger.info(String.format("更新的物料基本属性值记录id = %d。", paramPropVal.getId()));
            }
            if (modifyResult <= 0) {
                return MaterialErrCode.failedUpdateSingleFormatInMaterial;
            } else {
                return MaterialErrCode.successUpdateSingleFormatInMaterial;
            }
        } else {
            // 若不存在，返回错误
            logger.error(String.format("属性名为%s，物料编码为%s的规格属性不存在！"));
            return MaterialErrCode.notFoundFormatNameInDB;
        }
    }

    /**
     * 更新物料定义页面中规格部分的函数
     *
     * @author cplayer
     * @date 2019-02-27 21:15
     * @param params 更新物料信息请求的参数
     *
     * @return MaterialErrCode.successUpdateFormatInMaterial 代表成功
     *         MaterialErrCode.failedUpdateFormatInMaterial 代表失败
     *
     */
    public int updateMaterialInfoForMaterialDataByFormat (MaterialInfoModifyRequest params) {
        // 先删除所有规格属性值，规格属性类别对应的是4
        int propertyType = 4;
        // 根据spu编码获取物料分类id
        MaterialBaseModel baseParam = new MaterialBaseModel();
        baseParam.setSpuCode(params.getSpuCode());
        List<MaterialBaseModel> baseList = generalMapper.getMaterialBaseWithMaterialBaseParams(baseParam);
        // 取第一个数据对象
        baseParam = MaterialGeneral.getInitElementOrFirstElement(baseList, MaterialBaseModel.class);
        if (baseParam.getId() == -1) {
            // 若不存在返回错误
            logger.error(String.format("检查spu编码为%s的时候并没有发现对应的数据！", params.getSpuCode()));
            return MaterialErrCode.failedUpdateFormatInMaterial;
        }
        // 然后根据物料分类id、属性类型（type = 4）筛选出所有当前spu编码可能出现的物料基本属性列表，并暂存
        // 必须保证同物料分类下属性名不重复！！
        MaterialBasePropModel paramProp = new MaterialBasePropModel();
        paramProp.setMaterialCatId(baseParam.getMaterialCatId());
        paramProp.setType(propertyType);
        List<MaterialBasePropModel> propList = generalMapper.getMaterialBasePropWithMaterialBasePropParams(paramProp);
        // 获取所有待更新的规格信息对象列表
        List<MaterialModifyRequestForFormatProp> formatList = params.getFormatDatas().getFormatList();
        // 总的更新结果状态
        int sumModifyResult = MaterialErrCode.successUpdateFormatInMaterial;
        // 遍历待更新的规格对象信息
        for (MaterialModifyRequestForFormatProp formatData : formatList) {
            // 逐个调用更新函数更新
            int modifyResult = updateMaterialInfoForMaterialDataBySingleFormat(params, formatData, propList);
            if (modifyResult == MaterialErrCode.failedUpdateSingleFormatInMaterial ||
                    modifyResult == MaterialErrCode.notAllowedFormatObject ||
                    modifyResult == MaterialErrCode.notFoundFormatNameInDB) {
                sumModifyResult = MaterialErrCode.failedUpdateFormatInMaterial;
                logger.error(String.format("更新规格属性中，更新单个规格属性操作对应的错误码为：%d。", modifyResult));
            } else {
                logger.info(String.format("更新规格属性成功！"));
            }
        }
        return sumModifyResult;
    }
}
