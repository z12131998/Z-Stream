package com.zhou.stream.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个全局管理仓库，我想把保存的东西对外进行开放
 * 这个类的构想是key->value key(ZStreamApplication)  Value(Store)
 *
 * @author zhoujunyu
 */
public class GlobalStore {
    /**
     * 状态仓库全局引用存放
     */
    private static Map<String, Map> globalStoreMap = new HashMap<>();

    public static Object getValue(String key) {
        return globalStoreMap.get(key);
    }

    public static Object put(String key, Map value) {
        return globalStoreMap.put(key, value);
    }

    public static Map<String, Map> getGlobalStoreMap() {
        return globalStoreMap;
    }
}
