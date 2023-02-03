package com.ums.eproject.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.ums.eproject.R;

import es.dmoral.toasty.Toasty;

public class MsgUtil {

    public static void success(Context context,String msg){
        Toasty.success(context,msg, Toast.LENGTH_LONG).show();
    }
    public static void success(Context context,String msg,int len){
        Toasty.success(context,msg, len).show();
    }

    public static void info(Context context,String msg){
        Toasty.info(context,msg, Toast.LENGTH_LONG).show();
    }
    public static void info(Context context,String msg,int len){
        Toasty.info(context,msg, len).show();
    }

    public static void warning(Context context,String msg){
        Toasty.warning(context,msg, Toast.LENGTH_LONG).show();
    }
    public static void warning(Context context,String msg,int len){
        Toasty.warning(context,msg, len).show();
    }


    public static void error(Context context,String msg){
        Toasty.error(context,msg, Toast.LENGTH_LONG).show();
    }
    public static void error(Context context,String msg,int len){
        Toasty.error(context,msg, len).show();
    }


    public static void showIcon(Context context, String msg, Drawable drawable){
        Toasty.normal(context,msg,drawable).show();
    }
    public static void showIcon(Context context, String msg, int len, Drawable drawable){
        Toasty.normal(context,msg,len,drawable).show();
    }


    public static void showCustom(Context context,String msg) {
        Toasty.custom(context,msg,R.drawable.msg_info, ContextCompat.getColor(context,R.color.toasty_info) ,Toast.LENGTH_SHORT, true,true).show();
    }
}
