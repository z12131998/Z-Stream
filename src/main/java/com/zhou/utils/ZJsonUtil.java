package com.zhou.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * a json transition
 * @author 周俊宇
 */
public class ZJsonUtil {

	private static char DOUBLE_QUOTE = '"';
	private static char COLON = ':';
	private static char COMMA = ',';

	/**
	 * this a basic type method for converting json to object
	 * it bast merit thing is it only modify original memory
	 * but it only support basic type;
	 * this uses a number of String method so Must be optimizing using char[]
	 * @version 1.0
	 * @param jsonString
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> T parseJsonOfBasicType(String jsonString, T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		int index = 0;
		Class<T> clazz = (Class<T>) t.getClass();
		while (index < jsonString.length()){
			//find fieldName
			int nameStartIndex = jsonString.indexOf(DOUBLE_QUOTE,index) + 1;
			int nameEndIndex = jsonString.indexOf(DOUBLE_QUOTE,nameStartIndex);
			String fieldName = jsonString.substring(nameStartIndex, nameEndIndex);

			//find fieldValue support String boolean long double
			int valueStartIndex = jsonString.indexOf(COLON,nameEndIndex) + 1;
			int valueEndIndex = valueStartIndex;
			String fieldValue = jsonString.substring(valueStartIndex, valueEndIndex);

			//String
			if (jsonString.charAt(valueEndIndex) == '"'){
				valueEndIndex = jsonString.indexOf(DOUBLE_QUOTE,valueStartIndex + 1);
				fieldValue = jsonString.substring(valueStartIndex,valueEndIndex).substring(1);
				Method method = clazz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), String.class);
				method.invoke(t,fieldValue);
			}
			// boolean
			else if (jsonString.charAt(valueEndIndex) == 't' ||jsonString.charAt(valueEndIndex) == 'f'){
				if (jsonString.charAt(valueEndIndex) == 't'){
					valueEndIndex +=4;
				}else{
					valueEndIndex +=5;
				}
				fieldValue = jsonString.substring(valueStartIndex, valueEndIndex);
				Method method = clazz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), boolean.class);
				method.invoke(t,Boolean.parseBoolean(fieldValue));
			}
			//TODO Number just support long, next version support double
			else {
				valueEndIndex = jsonString.indexOf(DOUBLE_QUOTE,valueStartIndex) == -1 ? jsonString.length() : (jsonString.indexOf(DOUBLE_QUOTE,valueStartIndex) - 1);
				fieldValue = jsonString.substring(valueStartIndex, valueEndIndex);
				Method method = clazz.getMethod("set"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), long.class);
				method.invoke(t,Long.parseLong(fieldValue));
			}

			index = valueEndIndex + 1;
		}
		return t;
	}
}
