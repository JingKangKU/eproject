package com.ums.eproject.bean;

import java.util.List;

public class PdtCategory {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":5011006,"name":"家居","orderNo":1},{"id":5011002,"name":"服饰","orderNo":2},{"id":5011003,"name":"鞋子","orderNo":3},{"id":5011007,"name":"护肤","orderNo":4},{"id":5011008,"name":"手机","orderNo":5},{"id":5011009,"name":"电器","orderNo":6}]
     */

    private int code;
    private String message;
    /**
     * id : 5011006
     * name : 家居
     * orderNo : 1
     */


    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private long id;
        private String name;
        private int orderNo;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }
    }
}
