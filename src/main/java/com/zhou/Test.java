package com.zhou;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zhou.test.bean.IpCountBean;
import com.zhou.utils.ZJsonUtil;
import jdk.nashorn.internal.parser.JSONParser;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 周俊宇
 */
public class Test {
	public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
		IpCountBean ipCountBean = new IpCountBean();
		ipCountBean.setCount(100);
		ipCountBean.setName("test");
		ipCountBean.setConnection(true);
		String s = JSONUtil.toJsonStr(ipCountBean);
		IpCountBean zJsonToBean = new IpCountBean();
		ZJsonUtil.parseJsonOfBasicType(s,zJsonToBean);
		System.out.println(JSONUtil.toJsonStr(zJsonToBean));
	}
}
