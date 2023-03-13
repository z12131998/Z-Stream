package com.zhou.zframe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 全局管理器，容器只初始化一个全局管理器
 *
 * @author zhoujunyu
 */
public class ZStreamManager {
    /**
     * 管理全局容器
     */
    private static Map<String, ZStreamApplication> map = new HashMap<>();


    /**
     * 添加一个处理实例
     *
     * @param application
     */
    public void addApplication(ZStreamApplication application) {
        map.put(UUID.randomUUID() + "", application);
    }

    /**
     * 指定姓名来添加一个处理实例
     *
     * @param name
     * @param application
     */
    public void addApplication(String name, ZStreamApplication application) {
        map.put(name, application);
    }

    public void exec(ZStreamApplication application){
        application.start();
    }

}
