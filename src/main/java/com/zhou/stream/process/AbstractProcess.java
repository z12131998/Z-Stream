package com.zhou.stream.process;

import com.zhou.stream.global.GlobalStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 拓扑结构处理节点
 * @param <T> 输入类
 * @param <K> 存储作为key
 * @param <V> 存储作为value
 * @author zhoujunyu
 */
public abstract class AbstractProcess<T, K, V> {

    private String name;

    private Map<K,V> map;

    /**
     * 初始化
     * 把自身存储放入到全局里面，使其整个application通用
     */
    public void init() {
        if (name == null){
            name = this.getClass().getName();
        }
        map = new HashMap<>();
        GlobalStore.put(name, map);
    }

    public void beforeHandle(T t) {

    }

    public void afterHandle(T t) {

    }

    /**
     * 具体执行
     * @param t
     */
    abstract public void exec(T t);
}
