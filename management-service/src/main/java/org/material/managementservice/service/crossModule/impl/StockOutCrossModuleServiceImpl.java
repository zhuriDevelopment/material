package org.material.managementservice.service.crossModule.impl;

import org.material.managementfacade.model.tablemodel.MaterialBaseModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialModel;
import org.material.managementfacade.model.tablemodel.UnitModel;
import org.material.managementfacade.service.crossModule.StockOutCrossModuleService;
import org.material.managementservice.general.MaterialGeneral;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.mapper.info.InfoObtainMapper;
import org.material.managementservice.service.info.impl.supplier.baseprop.BasePropObtainServiceSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cplayer on 2019-04-04 00:04.
 * @version 1.0
 * 出库跨模块调用服务实现
 */
@Component
public class StockOutCrossModuleServiceImpl implements StockOutCrossModuleService {
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private InfoObtainMapper infoObtainMapper;
    @Autowired
    private BasePropObtainServiceSupplier basePropObtainServiceSupplier;

    /**
     * 根据物料编码获取规格信息
     *
     * @author cplayer
     * @date 2019-04-04 00:05
     * @param materialCodes 物料编码列表
     *
     * @return 规格信息列表
     *
     */
    @Override
    public List<String> getFormatByMaterialCode (List<String> materialCodes) {
        List<String> res = new ArrayList<>();
        for (String materialCode : materialCodes) {
            StringBuilder sb = new StringBuilder();
            MaterialModel searchParam = new MaterialModel();
            searchParam.setMaterialCode(materialCode);
            MaterialModel matRes = MaterialGeneral.getInitElementOrFirstElement(
                    generalMapper.getMaterialWithMaterialParams(searchParam),
                    MaterialModel.class
            );
            if (matRes.getId() != -1) {
                // 有效
                String spuCode = matRes.getSpuCode();
                MaterialBaseModel baseParam = new MaterialBaseModel();
                baseParam.setSpuCode(spuCode);
                MaterialBaseModel baseRes = MaterialGeneral.getInitElementOrFirstElement(
                        infoObtainMapper.getBaseInfoWithSpuCode(spuCode),
                        MaterialBaseModel.class
                );
                if (baseRes.getId() != -1) {
                    // 有效的物料基本信息
                    int materialCatId = baseRes.getMaterialCatId();
                    List<MaterialBasePropModel> formatRes =
                            basePropObtainServiceSupplier.getMaterialBasePropByCatIdAndType(materialCatId, 4);
                    for (MaterialBasePropModel element : formatRes) {
                        sb.append(element.getName());
                        sb.append(' ');
                    }
                }
            }
            res.add(sb.toString());
        }
        return res;
    }

    /**
     * 根据计量单位id获取计量单位名称
     *
     * @author cplayer
     * @date 2019-04-04 00:42
     * @param unitId 计量单位id
     *
     * @return 对应的计量单位名称
     *
     */
    @Override
    public String getUnitNameByUnitId (int unitId) {
        UnitModel unitParam = new UnitModel();
        unitParam.setId(unitId);
        List<UnitModel> unitRes = generalMapper.getUnitWithUnitParams(unitParam);
        UnitModel res = MaterialGeneral.getInitElementOrFirstElement(unitRes, UnitModel.class);
        if (res.getId() != -1) {
            return res.getName();
        } else {
            return "Not Found";
        }
    }

    /**
     * 根据跨模块参数获取物料编码
     *
     * @author cplayer
     * @date 2019-04-04 02:56
     * @param materialCode 物料编码
     * @param mnemonic 助记码
     * @param spuCode spu编码
     * @param spuName spu名称
     * @param materialCategory 物料分类
     * @param description 物料描述
     *
     * @return 对应的物料编码列表
     *
     */
    @Override
    public List<String> getMaterialCodeByCrossModuleParams
            (String materialCode, String mnemonic,
             String spuCode, String spuName,
             String materialCategory, String description) {
        int matId;
        if (materialCategory == null) {
            matId = -1;
        } else {
            matId = Integer.valueOf(materialCategory);
        }
        HashSet<String> materialCodes = new HashSet<>();
        if (materialCode != null) {
            materialCodes.add(materialCode);
        }
        if (spuCode != null || spuName != null || matId > -1 || description != null || mnemonic != null) {
            MaterialBaseModel baseParam = new MaterialBaseModel();
            baseParam.setSpuCode(spuCode);
            baseParam.setSpuName(spuName);
            baseParam.setMaterialCatId(matId);
            baseParam.setDescription(description);
            baseParam.setMnemonic(mnemonic);
            List<MaterialBaseModel> ret = generalMapper.getMaterialBaseWithMaterialBaseParams(baseParam);
            for (MaterialBaseModel ele : ret) {
                String resSpu = ele.getSpuCode();
                List<MaterialModel> matRes = infoObtainMapper.getMaterialWithSpuCode(resSpu);
                for (MaterialModel matEle : matRes) {
                    if (matEle.getSpuCode() != null) {
                        materialCodes.add(matEle.getMaterialCode());
                    }
                }
            }
        }
        return new ArrayList<>(materialCodes);
    }

    /**
     * 根据物料编码获取物料名称
     *
     * @author cplayer
     * @date 2019-04-04 02:57
     * @param materialCodes 物料编码列表
     *
     * @return 物料名称列表
     *
     */
    @Override
    public List<String> getMaterialNamesByMaterialCodes (List<String> materialCodes) {
        List<String> resList = new ArrayList<>();
        for (String materialCode : materialCodes) {
            MaterialModel searchParam = new MaterialModel();
            searchParam.setMaterialCode(materialCode);
            List<MaterialModel> searchRes = generalMapper.getMaterialWithMaterialParams(searchParam);
            MaterialModel res = MaterialGeneral.getInitElementOrFirstElement(searchRes, MaterialModel.class);
            if (res.getId() != -1) {
                resList.add(res.getMaterialName());
            } else {
                resList.add("");
            }
        }
        return resList;
    }

}
