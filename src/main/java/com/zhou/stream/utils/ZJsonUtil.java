package com.zhou.stream.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * a json transition
 *
 * @author 周俊宇
 */
public class ZJsonUtil<T> {

    private static char DOUBLE_QUOTE = '"';
    private static char COLON = ':';
    private static char COMMA = ',';
    private static char LEFT_CURLY_BRACE = '{';
    private static char RIGHT_CURLY_BRACE = '}';
    private static char BLANK = ' ';
    private static char UNDER_LINE = '_';

    /**
     * recode methods, save tme
     */
    private Map<String, Method> methodMap;

    private Class<T> clazz;


    public ZJsonUtil(Class<T> clazz) {
        methodMap = new HashMap<String, Method>();
        this.clazz = clazz;
    }

    /**
     * this a basic type method for converting json to object
     * it bast merit thing is it only modify original memory
     * but it only support basic type;
     * this uses a number of String method so Must be optimizing using char[]
     *
     * @param jsonString
     * @param t
     * @param codeRule
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T parseJsonOfBasicType(String jsonString, T t, CodeRule codeRule) throws Exception {

        //use char[] to saving time
        int index = 0;
        char[] chars = jsonString.toCharArray();
        while (index < chars.length) {

            //search key
            int filedNameStart = index;
            int filedNameEnd;
            while (chars[filedNameStart++] != DOUBLE_QUOTE) {
                if (filedNameStart == chars.length) {
                    return t;
                }
            }
            filedNameEnd = filedNameStart;
            while (chars[filedNameEnd++] != DOUBLE_QUOTE) ;

            //transKey
            String filedName = "";
            switch (codeRule) {
                case CAMELCASE:
                    filedName = camelcase(chars, filedNameStart, filedNameEnd - filedNameStart - 1);
                    break;
                case UNDER_LINE:
                    filedName = underLine(chars, filedNameStart, filedNameEnd - filedNameStart - 1);
                    break;
                default:
                    break;
            }

            //transValue , but just support basic type , long or bean or string ~
            int filedValueStart = filedNameEnd;
            int filedValueEnd = filedValueStart;
            while (chars[filedValueStart] == COLON || chars[filedValueStart] == BLANK) {
                filedValueStart++;
            }
            filedValueEnd = filedValueStart + 1;

            //catch the filedNameEnd
            switch (chars[filedValueStart]) {
                case '"':
                    while (chars[filedValueEnd++] != DOUBLE_QUOTE) ;
                    reflectInjectValue(t, filedName, String.class, new String(chars, filedValueStart + 1, filedValueEnd - filedValueStart - 2));
                    break;
                case 't':
                    reflectInjectValue(t, filedName, boolean.class, true);
                    filedValueEnd += 4;
                    break;
                case 'f':
                    reflectInjectValue(t, filedName, boolean.class, false);
                    filedValueEnd += 5;
                    break;
                default:
                    long number = 0;
                    while (chars[filedValueStart] >= '0' && chars[filedValueStart] <= '9') {
                        number = number * 10 + chars[filedValueStart];
                        filedValueStart++;
                    }
                    reflectInjectValue(t, filedName, long.class, number);
                    filedValueEnd = filedValueStart;
                    break;
            }

            index = filedValueEnd;
        }
        return t;
    }


    /**
     * key of in data
     * This identity is the naming policy for source data field names
     * class filed must case with hungarian notation
     */
    public enum CodeRule {
        /**
         * 驼峰
         */
        CAMELCASE,
        /**
         * 下划线
         */
        UNDER_LINE;
    }

    /**
     * let's change chars to String
     * and The naming policy is camelcase
     *
     * @param chars
     * @param chars
     * @param count
     * @return
     */
    private static String camelcase(char[] chars, int start, int count) {
        chars[start] -= 32;
        return new String(chars, start, count);
    }

    /**
     * support standard data, not support special char
     *
     * @param chars
     * @param start
     * @param count
     * @return
     */
    private static String underLine(char[] chars, int start, int count) {
        char[] newChar = new char[count];
        newChar[0] = (char) (chars[0] - 32);
        int j = 1;
        for (int i = 1; i < count; i++) {
            if (chars[start + i] == UNDER_LINE) {
                newChar[j] = (char) (chars[start + i + 1] - 32);
                i++;
            } else {
                newChar[j] = chars[start + i];
            }
            j++;
        }
        return new String(newChar, 0, j);
    }


    /**
     * hard hard inject
     *
     * @param paramClass
     * @param value
     */
    private void reflectInjectValue(Object target, String filedName, Class paramClass, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = methodMap.get(filedName);
        if (method == null) {
            method = clazz.getMethod("set" + filedName, paramClass);
            methodMap.put(filedName, method);
        }
        method.invoke(target, value);
    }
}
