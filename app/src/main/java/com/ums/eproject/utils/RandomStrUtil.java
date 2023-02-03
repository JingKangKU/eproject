package com.ums.eproject.utils;

import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author mrlixirong
 *
 * 2011-11-16
 */
public class RandomStrUtil {

    /*
     * 获取随机字符串
     */
    public static String getRandomString(){
        int len = 16;
        String returnStr="";
        char[] ch=new char[len];
        Random rd=new Random();
        for(int i=0;i<len;i++){
            ch[i]=(char)(rd.nextInt(9)+97);
        }
        returnStr=new String(ch);
        return returnStr;
    }
}


