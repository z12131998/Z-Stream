package com.zhou.zframe;

/**
 *拓扑结构处理器
 * @param <T>
 * @author zhoujunyu
 */
public abstract class AbstractProcess<T> {
    public void init() {

    }

    public void beforeHandle(T t) {

    }

    public void afterHandle(T t) {

    }

    abstract public void exec(T t);
}
