package com.ums.eproject.bean;


import java.io.Serializable;

/**
 * Created by jk
 */

public class NETData implements Serializable {


    /**
     * code : 200
     * message : 操作成功
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
