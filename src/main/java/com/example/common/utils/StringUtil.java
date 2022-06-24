package com.example.common.utils;

/**
 * 判断字符串是否为空
 */
public class StringUtil {

    public static boolean isNotBlank(String s) {
        return s != null && !"null".equals(s) && !"undefined".equals(s) && !"".equals(s);
    }
}
