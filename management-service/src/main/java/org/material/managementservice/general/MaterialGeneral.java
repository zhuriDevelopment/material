package org.material.managementservice.general;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author cplayer on 2019-02-25 02:56.
 * @version 1.0
 * 存放一些物料管理模块中的通用函数
 */

public class MaterialGeneral {
    /**
     * 通过反射来判断是否类中的所有属性均为空
     * 不支持泛型
     *
     * @author cplayer
     * @date 2019-02-25 03:12     
     * @param object 对应的类
     *
     * @return boolean
     *
     */
    public static boolean isEmpty (Object object) {
        boolean result = true;
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                if (field.get(object) != null) {
                    result = false;
                }
            }
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            result = true;
            return result;
        }
    }
}
