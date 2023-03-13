package com.zhou.zframe;

import com.zhou.utils.ZJsonUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A windows for input form all sources;
 */
public class InputHandle<T> {

    private Class<T> clazz;

    private Lock lock;

    private T t;

    public InputHandle(Class<T> clazz) {
        this.clazz = clazz;
        lock = new ReentrantLock();
    }

    /**
     * 现此方法只运行单线程操作
     *
     * @param t
     */
    public void send(T t) {
        lock.lock();
        //单线程的情况下链式调用处理
        lock.unlock();
    }

    /**
     * 默认使用驼峰的形式
     *
     * @param json
     */
    public void send(String json) {
        ZJsonUtil<T> tzJsonUtil = new ZJsonUtil<>(clazz);
        try {
            send(tzJsonUtil.parseJsonOfBasicType(json, t, ZJsonUtil.CodeRule.CAMELCASE));
        } catch (Exception e) {
            //异常处理，这个数据就算丢弃了，
        }
    }

    public T take() {
        return null;
    }
}
