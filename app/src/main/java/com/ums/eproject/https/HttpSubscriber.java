package com.ums.eproject.https;

import android.app.Activity;
import android.content.Context;


import com.ums.eproject.https.dialog.loadingdialog.view.LoadingDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by jk
 */

public class HttpSubscriber<T> extends Subscriber<T>{
    private SubscriberOnListener subscriberOnListener;
    private Context context;
    private LoadingDialog loadingDialog;
    public static  boolean isOpen = true;
    private boolean isOpenDialog = true;

    public HttpSubscriber(SubscriberOnListener subscriberOnListener, Context context) {
        loadingDialog=new LoadingDialog(context);
        this.subscriberOnListener = subscriberOnListener;
        this.context = context;
    }
    public HttpSubscriber(SubscriberOnListener subscriberOnListener, Context context,boolean isOpenDialog) {
        this.isOpenDialog = isOpenDialog;
        loadingDialog=new LoadingDialog(context);
        this.subscriberOnListener = subscriberOnListener;
        this.context = context;
    }

    public void onUnsubscribe() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    /**
     * 访问网络开始前（可以处理缓存）
     */
    @Override
    public void onStart() {
        if (context instanceof Activity){
            /**********show出网络加载dialog*******************/
            if (isOpen && isOpenDialog) {
                loadingDialog.show();
            }
        }
        super.onStart();
    }

    @Override
    public void onCompleted() {
        if (subscriberOnListener != null && context != null) {
            //subscriberOnListener.onError("完成", 1);
            /**********dismiss网络加载dialog*******************/
            if (isOpen && isOpenDialog) {
                loadingDialog.close();
            }
            isOpen = true;
        } else {
            onUnsubscribe();
        }
    }

    @Override
    public void onError(Throwable e) {
        /**********dismiss网络加载dialog*******************/
        loadingDialog.close();
        if (subscriberOnListener != null && context != null) {
            if (e instanceof SocketTimeoutException) {
                subscriberOnListener.onError(-1001, "网络超时，请检查您的网络状态");
            } else if (e instanceof ConnectException) {
                subscriberOnListener.onError(-1002, "网络链接中断，请检查您的网络状态");
            }
//            else if (e instanceof ExceptionBean) {
//                subscriberOnListener.onError(((ExceptionBean) e).getCode(), ((ExceptionBean) e).getMsg());
//            }
            else {
                System.out.println("jk-----------------");
                e.printStackTrace();
                subscriberOnListener.onError(-1003, e.getMessage());
            }
        } else {
            onUnsubscribe();
        }
    }

    @Override
    public void onNext(T t) {
        if (subscriberOnListener != null && context != null) {
            subscriberOnListener.onSucceed(t);
        } else {
            onUnsubscribe();
        }
    }

    public void setOpen(boolean isOpen){
        this.isOpen = isOpen;
    }
}
