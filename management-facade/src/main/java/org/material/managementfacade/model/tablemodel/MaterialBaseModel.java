package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

import java.sql.Timestamp;

@ApiModel("物料基本信息表")
public class MaterialBaseModel {
    private int id;
    private String spuCode;
    private String mnemonic;
    private String spuName;
    private String description;
    private int type;
    private String designCode;
    private String designVersion;
    private int defaultUnitId;
    private String source;
    private String usage;
    private String note;
    private int materialCatId;
    private int keyPropId;
    private Timestamp createDate;

    public MaterialBaseModel () {
        this.id = -1;
        this.spuCode = null;
        this.mnemonic = null;
        this.spuName = null;
        this.description = null;
        this.type = -1;
        this.designCode = null;
        this.designVersion = null;
        this.defaultUnitId = -1;
        this.source = null;
        this.usage = null;
        this.note = null;
        this.materialCatId = -1;
        this.keyPropId = -1;
        this.createDate = null;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getSpuCode () {
        return spuCode;
    }

    public void setSpuCode (String spuCode) {
        this.spuCode = spuCode;
    }

    public String getMnemonic () {
        return mnemonic;
    }

    public void setMnemonic (String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getSpuName () {
        return spuName;
    }

    public void setSpuName (String spuName) {
        this.spuName = spuName;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
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

    public int getDefaultUnitId () {
        return defaultUnitId;
    }

    public void setDefaultUnitId (int defaultUnitId) {
        this.defaultUnitId = defaultUnitId;
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

    public String getNote () {
        return note;
    }

    public void setNote (String note) {
        this.note = note;
    }

    public int getMaterialCatId () {
        return materialCatId;
    }

    public void setMaterialCatId (int materialCatId) {
        this.materialCatId = materialCatId;
    }

    public int getKeyPropId () {
        return keyPropId;
    }

    public void setKeyPropId (int keyPropId) {
        this.keyPropId = keyPropId;
    }

    public Timestamp getCreateDate () {
        return createDate;
    }

    public void setCreateDate (Timestamp createDate) {
        this.createDate = createDate;
    }
}
