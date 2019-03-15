package org.material.managementservice.service.info.impl.supplier;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cplayer on 2019-02-25 15:51
 * @version 1.0
 * 物料信息获取的工具类，存放获取的通用工具函数
 */
@Component
public class InfoObtainServiceSupplier {

    /**
     * 将传进来的各类list中的spuCode提取出来并取交集的工具函数
     *
     * @param lists
     *         待处理的List（或者多个List）
     *
     * @return java.util.HashSet<java.lang.String>
     *
     * @author cplayer
     * @date 2019-02-25 20:18
     */
    public <T extends List> HashSet<String> addAllListSpuCode (T... lists) {
        HashSet<String> spuCodes = new HashSet<>();
        for (T list : lists) {
            if (list != null) {
                Object result = list.stream()
                        .map(ele -> {
                            try {
                                Method spuMethod = ele.getClass().getMethod("getSpuCode");
                                return spuMethod.invoke(ele).toString();
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                return null;
                            }
                        })
                        .distinct()
                        .collect(Collectors.toList());
                if (result instanceof List) {
                    if (!((List) result).isEmpty() && ((List) result).get(0) instanceof String) {
                        List<String> spuList = (List<String>) result;
                        if (spuCodes.isEmpty()) {
                            spuCodes.addAll(spuList);
                        } else {
                            spuCodes.retainAll(spuList);
                        }
                    }
                }
            }
        }
        return spuCodes;
    }


}
