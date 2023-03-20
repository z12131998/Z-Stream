package com.zhou.stream.schedule;

import com.zhou.stream.zframe.ZStreamApplication;

/**
 * 定时执行任务
 * 他很重要，是流与时间窗口的对比
 */
public abstract class AbstractSchedule implements Runnable{

    /**
     * 每一个都必须获取全局对象，需要调用或者获取全局对象里的某些
     */
    protected ZStreamApplication zStreamApplication;

    protected int interval;

    /**
     * 执行，一个定时的任务不需要输入参数或者返回函数
     * 他应该在内部做一些清理或者发送或者任何等业务
     */
    abstract public void exec();

    /**
     * 提交他自己
     */
    public void submit(){
    }
}
