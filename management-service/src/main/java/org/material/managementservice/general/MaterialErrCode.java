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

    // 更新物料控制属性成功
    public static Integer successUpdateControlProp = 1;
    // 更新物料控制属性失败
    public static Integer failedUpdateControlProp = 0;
    // 更新物料控制属性部分失败
    public static Integer failedUpdateSomeControlProp = 1;
    // 更新物料控制属性全部失败
    public static Integer failedUpdateAllControlProp = 2;
    // 更新物料控制属性成功
    public static Integer successUpdateAllControlProp = 0;

    // 更新物料计量单位成功
    public static Integer successUpdateUnit = 1;
    // 更新物料计量单位失败
    public static Integer failedUpdateUnit = 0;
    // 更新物料计量单位中新增部分成功
    public static Integer successUpdateInsertUnit = 1;
    // 更新物料计量单位中新增部分失败
    public static Integer failedUpdateInsertUnit = 0;
    // 更新物料计量单位中修改部分成功
    public static Integer successUpdateModifyUnit = 1;
    // 更新物料计量单位中修改部分失败
    public static Integer failedUpdateModifyUnit = 0;
    // 更新物料计量单位中删除部分成功
    public static Integer successUpdateDeleteUnit = 1;
    // 更新物料计量单位中删除部分失败
    public static Integer failedUpdateDeleteUnit = 0;
}
