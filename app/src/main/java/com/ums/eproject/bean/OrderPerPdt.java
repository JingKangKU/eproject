package com.ums.eproject.bean;

public class OrderPerPdt {


    /**
     * code : 200
     * message : 操作成功
     * data : {"miniPayToken":"","appPayRequest":"","payStatus":3,"merOrderId":"20230214195842344868525834602800"}
     */

    private int code;
    private String message;
    /**
     * miniPayToken :
     * appPayRequest :
     * payStatus : 3
     * merOrderId : 20230214195842344868525834602800
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
        private String miniPayToken;
        private String appPayRequest;
        private int payStatus;
        private String merOrderId;

        public String getMiniPayToken() {
            return miniPayToken;
        }

        public void setMiniPayToken(String miniPayToken) {
            this.miniPayToken = miniPayToken;
        }

        public String getAppPayRequest() {
            return appPayRequest;
        }

        public void setAppPayRequest(String appPayRequest) {
            this.appPayRequest = appPayRequest;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getMerOrderId() {
            return merOrderId;
        }

        public void setMerOrderId(String merOrderId) {
            this.merOrderId = merOrderId;
        }
    }
}
