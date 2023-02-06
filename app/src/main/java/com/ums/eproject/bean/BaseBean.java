package com.ums.eproject.bean;

import java.io.Serializable;

public class BaseBean  implements Serializable {
    //签名值
    private String sign;
    //请求来源
    private String source;
    //随机值
    private String randomStr;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }


}
