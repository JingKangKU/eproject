package com.ums.eproject.bean;

public class AuthBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"url":"https://api.megvii.com/faceid/lite/do?token=8646e9c9fa3c394c4ee9cf14a3f21f90","voiceSession":"8646e9c9fa3c394c4ee9cf14a3f21f90"}
     */

    private int code;
    private String message;
    /**
     * url : https://api.megvii.com/faceid/lite/do?token=8646e9c9fa3c394c4ee9cf14a3f21f90
     * voiceSession : 8646e9c9fa3c394c4ee9cf14a3f21f90
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
        private String url;
        private String voiceSession;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVoiceSession() {
            return voiceSession;
        }

        public void setVoiceSession(String voiceSession) {
            this.voiceSession = voiceSession;
        }
    }
}
