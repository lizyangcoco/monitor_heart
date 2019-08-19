package com.heart.beat.util;

/**
 * 类描述：字符串处理及转换工具类
 * Created by 李泽阳 on 2019/8/8
 */
public class StringUtil {
    /**
     * 字符串string转换时空过滤
     *
     * @param obj
     * @return
     */
    public static String formatEmpty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }


}
