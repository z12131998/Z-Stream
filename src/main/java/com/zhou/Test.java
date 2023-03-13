package com.zhou;

import com.zhou.test.bean.IpCountBean;
import com.zhou.utils.ZJsonUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 周俊宇
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String test = "{\"name\":\"test\",\"connection\":true,\"count\":1}";
        ZJsonUtil<IpCountBean> ipCountBeanZJsonUtil = new ZJsonUtil<>(IpCountBean.class);
        IpCountBean ipCountBean = new IpCountBean();
        ipCountBeanZJsonUtil.parseJsonOfBasicType(test, ipCountBean, ZJsonUtil.CodeRule.CAMELCASE);
        System.out.println(ipCountBean.getName() + "\t" + ipCountBean.getCount() + "\t" + ipCountBean.isConnection());
    }
}
