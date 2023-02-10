package com.ums.eproject.utils;

/**
 * Created by jk
 * Data 2018/10/15
 */

public class StrUtil {

    public static boolean isEmpty(String str){
        return str == null || str.length() <= 0;
    }
    public static boolean isTrimEmpty(String str){
        return str == null || str.trim().length() <= 0;
    }


    public static boolean isNotEmpty(String str){
        if (str != null && str.length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isComplexPassword(String pass) {
        if (isEmpty(pass)) {
            return false;
        }
        String regx = "(?=.*[\\d]+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[^a-zA-Z0-9]+).{8,}";
        return pass.matches(regx);
    }
}
