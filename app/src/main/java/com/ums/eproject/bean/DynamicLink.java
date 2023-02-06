package com.ums.eproject.bean;

public class DynamicLink {

    /**
     * code : 200
     * message : 操作成功
     * data : {"linkUrl":"https://www.baidu.com?code=483472934259533487"}
     */

    private int code;
    private String message;
    /**
     * linkUrl : https://www.baidu.com?code=483472934259533487
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String linkUrl;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}