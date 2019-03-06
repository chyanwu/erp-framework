package com.chenyanwu.erp.erpframework.common.util;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/22 12:53
 * @Description:
 * @Version 1.0
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
