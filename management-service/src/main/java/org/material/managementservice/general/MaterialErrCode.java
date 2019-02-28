package org.material.managementservice.general;

/**
 * @author cplayer on 2019-02-25 03:24.
 * @version 1.0
 * 物料管理模块中的错误码
 */

public class MaterialErrCode {
    // 传上来的类参数为空，一般用于参数为空的情况
    public static Integer errCodeClassIsEmpty = 0;

    // 物料基本信息更新成功
    public static Integer successUpdateMaterialBase = 1;
    // 物料基本信息更新失败
    public static Integer failedUpdateMaterialBase = 0;

    // 物料定义更新成功
    public static Integer successUpdateMaterial = 1;
    // 物料定义更新失败
    public static Integer failedUpdateMaterial = 0;
    // 物料定义中物料定义信息更新成功
    public static Integer successUpdateMaterialInMaterial = 1;
    // 物料定义中物料定义信息更新失败
    public static Integer failedUpdateMaterialInMaterial = 0;
    // 物料定义中规格信息更新成功
    public static Integer successUpdateFormatInMaterial = 1;
    // 物料定义中规格信息更新失败
    public static Integer failedUpdateFormatInMaterial = 0;
    // 物料定义中规格信息参数不符合要求
    public static Integer notAllowedFormatObject = -1;
    // 物料定义中单个规格信息更新成功
    public static Integer successUpdateSingleFormatInMaterial = 1;
    // 物料定义中单个规格信息更新失败
    public static Integer failedUpdateSingleFormatInMaterial = 0;
    // 物料定义中规格信息名字未找到
    public static Integer notFoundFormatNameInDB = -2;

    // 更新物料sku信息成功
    public static Integer successUpdateSku = 1;
    // 更新物料sku信息失败
    public static Integer failedUpdateSku = 0;
}
