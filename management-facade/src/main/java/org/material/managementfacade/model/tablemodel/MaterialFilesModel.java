package org.material.managementfacade.model.tablemodel;

import io.swagger.annotations.ApiModel;

@ApiModel("物料描述文件表")
public class MaterialFilesModel {
    private int id;
    private int materialBaseId;
    private int fileId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialBaseId() {
        return materialBaseId;
    }

    public void setMaterialBaseId(int materialBaseId) {
        this.materialBaseId = materialBaseId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
