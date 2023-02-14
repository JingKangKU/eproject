package com.chinaums.common.utils;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class UMSStringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String string) {
        return !TextUtils.isEmpty(string);
    }

    /**
     * 获取字符串长度
     *
     * @return 如果字符串为空, 返回 0, 否则 返回 {@link CharSequence#length()}.
     */
    public static int length(CharSequence c) {
        return c == null ? 0 : c.length();
    }

    /**
     * 是否全是数字
     */
    public static boolean isAllNum(String string) {
        char[] chars = string.toCharArray();
        boolean result = true;
        for (char aChar : chars) {
            if (!Character.isDigit(aChar)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 是否是手机号
     */
    public static boolean isMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        if (mobile.length() == 11) {
            Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$");
            Matcher m = p.matcher(mobile);
            return m.matches();
        }
        return false;
    }

    /**
     * 手机号中间4位隐藏
     */
    public static String maskMobile(String mobile) {
        if (isMobile(mobile)) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7);
        }
        return mobile;
    }

    /**
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static final String[] cityCode = {"11", "12", "13", "14", "15", "21",
            "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",
            "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
            "63", "64", "65", "71", "81", "82", "91"};

    private static final String[] checkCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * 每位加权因子
     */
    private static final int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 判断身份证格式
     */
    public static boolean isIdCardNum(String idNum) {
        //定义判别用户身份证号的正则表达式（要么是15位或18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idNum);
        if (!idNumMatcher.matches()) {//长度及格式校验
            return false;
        }
        String cityStr = idNum.substring(0, 2);
        if (!Arrays.asList(cityCode).contains(cityStr)) {//城市代码校验
            return false;
        }
        try {//校验码校验
            int powerTotal = 0;
            for (int i = 0; i < idNum.length() - 1; i++) {
                powerTotal += Integer.parseInt(idNum.substring(i, i + 1)) * power[i];
            }
            if (!checkCode[powerTotal % 11].equals(idNum.substring(idNum.length() - 1, idNum.length()))) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断是否是银行卡号
     */
    public static boolean isBankCardNo(String cardNo) {
        char bit = getBankCardCheckCode(cardNo.substring(0, cardNo.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardNo.charAt(cardNo.length() - 1) == bit;
    }

    /**
     * 获取银行卡校验码
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
