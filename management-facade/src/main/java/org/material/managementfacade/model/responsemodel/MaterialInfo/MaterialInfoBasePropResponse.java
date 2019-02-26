package org.material.managementfacade.model.responsemodel.MaterialInfo;

import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;
import org.material.managementfacade.model.tablemodel.MaterialBasePropValModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cplayer on 2019-02-25 21:05
 * @version 1.0
 * getMaterialInfo的物料基本属性信息的回复包装类
 */

public class MaterialInfoBasePropResponse {
    private MaterialInfoBasePropResponseClass keyProp;

    private MaterialInfoBasePropResponseClass notKeyProp;

    private MaterialInfoBasePropResponseClass batchProp;

    private MaterialInfoBasePropResponseClass formatProp;

    private MaterialInfoBasePropResponseClass baseProps;

    public MaterialInfoBasePropResponse () {
        this.keyProp = null;
        this.notKeyProp = null;
        this.batchProp = null;
        this.formatProp = null;
        this.baseProps = null;
    }

    public MaterialInfoBasePropResponseClass getKeyProp () {
        return keyProp;
    }

    public void setKeyProp (MaterialInfoBasePropResponseClass keyProp) {
        this.keyProp = keyProp;
    }

    public MaterialInfoBasePropResponseClass getNotKeyProp () {
        return notKeyProp;
    }

    public void setNotKeyProp (MaterialInfoBasePropResponseClass notKeyProp) {
        this.notKeyProp = notKeyProp;
    }

    public MaterialInfoBasePropResponseClass getBatchProp () {
        return batchProp;
    }

    public void setBatchProp (MaterialInfoBasePropResponseClass batchProp) {
        this.batchProp = batchProp;
    }

    public MaterialInfoBasePropResponseClass getFormatProp () {
        return formatProp;
    }

    public void setFormatProp (MaterialInfoBasePropResponseClass formatProp) {
        this.formatProp = formatProp;
    }

    public MaterialInfoBasePropResponseClass getBaseProps () {
        return baseProps;
    }

    public void setBaseProps (MaterialInfoBasePropResponseClass baseProps) {
        this.baseProps = baseProps;
    }

    public void setProp (int propType, List<MaterialBasePropValModel> valList, List<MaterialBasePropModel> propList) {
        switch (propType) {
            case 1:
                this.setKeyProp(new MaterialInfoBasePropResponseClass());
                this.getKeyProp().setValList(valList);
                this.getKeyProp().setPropList(propList);
                break;
            case 2:
                this.setNotKeyProp(new MaterialInfoBasePropResponseClass());
                this.getNotKeyProp().setValList(valList);
                this.getNotKeyProp().setPropList(propList);
                break;
            case 3:
                this.setBatchProp(new MaterialInfoBasePropResponseClass());
                this.getBatchProp().setValList(valList);
                this.getBatchProp().setPropList(propList);
                break;
            case 4:
                this.setFormatProp(new MaterialInfoBasePropResponseClass());
                this.getFormatProp().setValList(valList);
                this.getFormatProp().setPropList(propList);
                break;
            default:
                break;
        }
    }

    public void sortAllLists () {
        // 将所有数据汇总到baseProp列表中
        this.setBaseProps(new MaterialInfoBasePropResponseClass());
        this.getBaseProps().getValList().addAll(this.getKeyProp().getValList());
        this.getBaseProps().getValList().addAll(this.getNotKeyProp().getValList());
        this.getBaseProps().getValList().addAll(this.getBatchProp().getValList());
        this.getBaseProps().getValList().addAll(this.getFormatProp().getValList());
        this.getBaseProps().getPropList().addAll(this.getKeyProp().getPropList());
        this.getBaseProps().getPropList().addAll(this.getNotKeyProp().getPropList());
        this.getBaseProps().getPropList().addAll(this.getBatchProp().getPropList());
        this.getBaseProps().getPropList().addAll(this.getFormatProp().getPropList());

        // 根据sort进行排序
        List<MaterialBasePropValModel> valList = this.getBaseProps().getValList();
        List<MaterialBasePropModel> propList = this.getBaseProps().getPropList();
        List<MaterialBasePropValModel> valSort = new ArrayList<>();
        List<MaterialBasePropModel> propSort = new ArrayList<>();
        int[] sortArray = new int[propList.size() + 1];
        for (int i = 0; i < propList.size(); ++i) {
            sortArray[propList.get(i).getSort()] = i;
        }
        for (int i = 1; i <= propList.size(); ++i) {
            valSort.add(valList.get(sortArray[i]));
            propSort.add(propList.get(sortArray[i]));
        }

        // 将排序结果放入baseProp列表中，并清空其他无用属性
        this.getBaseProps().setValList(valSort);
        this.getBaseProps().setPropList(propSort);
        this.setKeyProp(null);
        this.setNotKeyProp(null);
        this.setFormatProp(null);
        this.setBatchProp(null);
    }
}
