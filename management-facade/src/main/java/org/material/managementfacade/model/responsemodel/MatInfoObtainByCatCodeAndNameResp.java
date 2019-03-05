package org.material.managementfacade.model.responsemodel;

import org.material.managementfacade.model.propertymodel.ControlPropertyBean;
import org.material.managementfacade.model.tablemodel.MaterialBasePropModel;

import java.util.List;

/**
 * @author cplayer on 2019-03-02 06:09.
 * @version 1.0
 * 根据物料编码和物料名称获取所有物料信息的回复类
 */

public class MatInfoObtainByCatCodeAndNameResp {
    private int resultCode;
    private List<MaterialBasePropModel> basePropList;
    private List<ControlPropertyBean> purchaseAndStoreInfos;
    private List<ControlPropertyBean> planInfos;
    private List<ControlPropertyBean> salesInfos;
    private List<ControlPropertyBean> qualifyInfos;
    private List<ControlPropertyBean> financeInfos;

    public int getResultCode () {
        return resultCode;
    }

    public void setResultCode (int resultCode) {
        this.resultCode = resultCode;
    }

    public List<MaterialBasePropModel> getBasePropList () {
        return basePropList;
    }

    public void setBasePropList (List<MaterialBasePropModel> basePropList) {
        this.basePropList = basePropList;
    }

    public List<ControlPropertyBean> getPurchaseAndStoreInfos () {
        return purchaseAndStoreInfos;
    }

    public void setPurchaseAndStoreInfos (List<ControlPropertyBean> purchaseAndStoreInfos) {
        this.purchaseAndStoreInfos = purchaseAndStoreInfos;
    }

    public List<ControlPropertyBean> getPlanInfos () {
        return planInfos;
    }

    public void setPlanInfos (List<ControlPropertyBean> planInfos) {
        this.planInfos = planInfos;
    }

    public List<ControlPropertyBean> getSalesInfos () {
        return salesInfos;
    }

    public void setSalesInfos (List<ControlPropertyBean> salesInfos) {
        this.salesInfos = salesInfos;
    }

    public List<ControlPropertyBean> getQualifyInfos () {
        return qualifyInfos;
    }

    public void setQualifyInfos (List<ControlPropertyBean> qualifyInfos) {
        this.qualifyInfos = qualifyInfos;
    }

    public List<ControlPropertyBean> getFinanceInfos () {
        return financeInfos;
    }

    public void setFinanceInfos (List<ControlPropertyBean> financeInfos) {
        this.financeInfos = financeInfos;
    }
}
