package org.material.managementservice.general;

/**
 * @author cplayer on 2019-02-25 03:24.
 * @version 1.0
 * 物料管理模块中的错误码
 */

public class MaterialErrCode {
    // 传上来的类参数为空，一般用于参数为空的情况
    public static Integer errCodeClassIsEmpty = 0;

    // 物料信息更新成功
    public static Integer successUpdateMaterialBase = 1;
    // 物料信息更新失败
    public static Integer failedUpdateMaterialBase = 0;
}
