package org.material.managementservice.general;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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

    /**
     * 泛型工具类，返回泛型列表中第一个元素，若列表为空，则返回一个空元素
     *
     * @author cplayer
     * @date 2019-02-25 16:31
     * @param targetList 对应的列表
     *
     * @param cls List中对应类的class对象
     *
     * @return 对应的对象
     *
     */
    public static <T> T getInitElementOrFirstElement (List<T> targetList, Class<T> cls) {
        try {
            if (targetList.size() == 0) {
                return cls.newInstance();
            } else {
                return targetList.get(0);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
