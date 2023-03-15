package com.zhou.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个全局管理仓库，我想把保存的东西对外进行开放
 * 这个类的构想是key->value key(ZStreamApplication)  Value(Store)
 * @author zhoujunyu
 */
public class GlobalStore {
    /**
     * 状态仓库全局引用存放
     */
    private static Map<String,Object> globalStoreMap = new HashMap<>();

    public Object getValue(String key){
        return globalStoreMap.get(key);
    }

    public Object put(String key, Object value){
        return globalStoreMap.put(key, value);
    }

    public static Map<String, Object> getGlobalStoreMap() {
        return globalStoreMap;
    }
}
