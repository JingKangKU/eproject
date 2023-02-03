package com.ums.eproject.bean;

import java.io.Serializable;

public class BaseBean  implements Serializable {
    //签名值
    private String sign;
    //请求来源
    private String source;
    //随机值
    private String randomStr;

    private String getSign() {
        return sign;
    }

    private void setSign(String sign) {
        this.sign = sign;
    }

    private String getSource() {
        return source;
    }

    private void setSource(String source) {
        this.source = source;
    }

    private String getRandomStr() {
        return randomStr;
    }

    private void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }


}
