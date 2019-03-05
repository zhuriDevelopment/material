package org.material.managementservice.general;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author cplayer on 2019-02-25 02:56.
 * @version 1.0
 * 存放一些物料管理模块中的通用函数以及通用配置
 */

public class MaterialGeneral {

    // 通用组织编码
    public static String generalOrganizationCode = "-1";
    // 通用spu编码
    public static String generalSpuCode = "-1";
    // 通用物料编码
    public static String generalMaterialCode = "-1";

    /**
     * 泛型工具函数，判断一个数组内是否有需要的对象
     *
     * @author cplayer
     * @date 2019-03-02 21:09
     * @param eleList 待检查的元素数组
     *
     * @param key 需要判断是否存在的对象
     *
     * @return boolean
     *
     */
    public static <T> boolean checkList (T[] eleList, T key) {
        for (T ele : eleList) {
            if (ele.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过反射来判断是否对象中含有空属性
     *
     * @author cplayer
     * @date 2019-02-28 16:30
     * @param object 对应的对象
     *
     * @return boolean
     *
     */
    public static boolean isContainsEmpty (@NotNull Object object) {
        boolean result = false;
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                if (field.get(object) == null) {
                    result = true;
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
     * 通过反射来判断是否类中的所有属性均为空
     * 不支持泛型
     *
     * @author cplayer
     * @date 2019-02-25 03:12     
     * @param object 对应的对象
     *
     * @return boolean
     *
     */
    public static boolean isAllEmpty (@NotNull Object object) {
        boolean result = true;
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
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

    /**
     * 判断传入的所有对象是否含有无效对象的函数
     * Object以null作为无效，int以-1作为无效
     *
     * @author cplayer
     * @date 2019-03-05 19:43
     * @param params 参数数组
     *
     * @return true代表含有，false代表不含
     *
     */
    public static boolean isAllEmptyParams (Object... params) {
        boolean result = true;
        for (Object object : params) {
            if (object instanceof Integer) {
                Integer temp = (Integer) object;
                if (temp != -1) {
                    result = false;
                }
            } else {
                if (object != null) {
                    result = false;
                }
            }
        }
        return result;
    }
}
