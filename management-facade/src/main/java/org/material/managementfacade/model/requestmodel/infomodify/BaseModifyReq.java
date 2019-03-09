package org.material.managementfacade.model.requestmodel.infomodify;

/**
 * @author cplayer on 2019-02-27 04:20.
 * @version 1.0
 * <p>
 * updateMaterialInfo中物料基本信息更新的封装类
 * 包含以下几种属性：
 * spu编码
 * spu名称
 * 物料类型（ID）
 * 物料分类（ID）
 * 来源
 * 用途
 * 设计图号
 * 设计班次
 * 助记码
 * 备注
 * 其他属性一律不允许放在物料基本信息的更新接口中！
 */

public class BaseModifyReq {
    private String spuCode;
    private String spuName;
    private int type;
    private int materialCatId;
    private String source;
    private String usage;
    private String designCode;
    private String designVersion;
    private String mnemonic;
    private String description;
    private String note;

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public String getSpuName () {
        return spuName;
    }

    public void setSpuName (String spuName) {
        this.spuName = spuName;
    }

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
    }

    public int getMaterialCatId () {
        return materialCatId;
    }

    public void setMaterialCatId (int materialCatId) {
        this.materialCatId = materialCatId;
    }

    public String getSource () {
        return source;
    }

    public void setSource (String source) {
        this.source = source;
    }

    public String getUsage () {
        return usage;
    }

    public void setUsage (String usage) {
        this.usage = usage;
    }

    public String getDesignCode () {
        return designCode;
    }

    public void setDesignCode (String designCode) {
        this.designCode = designCode;
    }

    public String getDesignVersion () {
        return designVersion;
    }

    public void setDesignVersion (String designVersion) {
        this.designVersion = designVersion;
    }

    public String getMnemonic () {
        return mnemonic;
    }

    public void setMnemonic (String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getNote () {
        return note;
    }

    public void setNote (String note) {
        this.note = note;
    }
}
