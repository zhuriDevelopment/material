package org.material.managementservice.general;

/**
 * @author cplayer on 2019-03-02 04:33.
 * @version 1.0
 * 物料分类管理模块中的错误码
 */

public class MaterialCategoryErrCode {
    // 增加物料分类信息请求不合法
    public static Integer invalidAddCategoryRequest = -1;
    // 增加物料分类信息成功
    public static Integer successAddCategory = 1;
    // 增加物料分类信息失败
    public static Integer failedAddCategory = 0;
    // 试图增加当前已有的物料分类（编码相同）
    public static Integer existedCategoryCode = -2;
    // 父物料分类信息不存在
    public static Integer notExistFatherCategory = -3;
    // 试图增加当前已有的物料分类（名称相同）
    public static Integer existedCategoryName = -4;

    // 修改物料分类信息请求不合法
    public static Integer invalidModifyCategoryRequest = -1;
    // 修改物料分类信息成功
    public static Integer successModifyCategory = 1;
    // 修改物料分类信息失败
    public static Integer failedModifyCategory = 0;
    // 待修改的物料分类信息在数据库中不存在
    public static Integer notExistModifyCategory = -2;
    // 待修改的物料分类信息名称在数据库中已存在
    public static Integer existNewCategoryName = -3;

    // 删除物料分类信息请求不合法
    public static Integer invalidDeleteCategoryRequest = -1;
    // 删除物料分类信息成功
    public static Integer successDeleteCategory = 1;
    // 删除物料分类信息失败
    public static Integer failedDeleteCategory = 0;
    // 待删除的物料分类信息在数据库中有重复
    public static Integer duplicatedDeleteCategory = -2;
    // 待删除的物料分类信息在数据库中不存在
    public static Integer notExistDeleteCategory = -3;
}
