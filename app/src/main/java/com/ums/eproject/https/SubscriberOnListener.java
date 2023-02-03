package com.ums.eproject.https;

/**
 * Created by jk on 2017/4/27.
 */

public interface SubscriberOnListener<T> {
    void onSucceed(T data);
    void onError(int code, String msg);
}
