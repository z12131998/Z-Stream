package com.zhou.zframe;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 具体运行的程序，包含一系列拓扑结构
 * @author zhoujunyu
 */
public class ZStreamApplication<T> {
    private Map<String, AbstractProcess> processList;

    private InputHandle<T> inputHandle;

    public ZStreamApplication(T t) {
        processList = new LinkedHashMap<>(8);
        inputHandle = new InputHandle(t.getClass());
    }

    public void start() {

    }

    public InputHandle getInputHandle(){
        return inputHandle;
    }

    class ProcessRun implements Runnable{

        @Override
        public void run() {
            while(true){
                inputHandle.take();
            }
        }
    }
}
