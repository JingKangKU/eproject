package com.ums.eproject.utils;


/**
 * 常量
 */
public class Constant {

    /* 日志 TAG */
    public static final String TAG = "eProject";


     /* 网络请求 */
    public static String ERRMESSAGE;
    public static int RESPONSECODE;


    public static String baseUrl = "http://121.40.88.223:8601/";

    public static String signReqHeader = "signKey";

    public static String tokenReqHeader = "XAuthorization";
    public static String tokenReq="";
    public static String token="";
    public static String tokenHead="";

    // 公共密钥，2个用途，敏感信息加密和加签的时候使用
    public static String publicKey = "40qicgCuuOJL2uRZ";

    public static String source = "APP";

    //网络超时时间30秒
    public static final int timeout = 60;




    //用途编码 01:新注册,02=密码修改,03=密码登陆
    public static final String purpose_new_register = "01";
    public static final String purpose_pw_update = "02";
    public static final String purpose_code_login = "03";

    //实名认证类型
    public static final int AUDIT_TYPE_CR_VAL = 1;
    public static final int AUDIT_TYPE_XS_VAL = 2;
    public static final int AUDIT_TYPE_YJ_VAL = 3;
    public static final int AUDIT_TYPE_LN_VAL = 4;
    public static final int AUDIT_TYPE_YG_VAL = 5;
    public static final int AUDIT_TYPE_LOGIN_AUDIT_VAL = 6; //登录后的跳转认证



    public static String getAuditTypeLabel(int val){
        switch (val){
            case AUDIT_TYPE_CR_VAL: return "成人卡";
            case AUDIT_TYPE_XS_VAL: return "学生卡";
            case AUDIT_TYPE_YJ_VAL: return "拥军卡";
            case AUDIT_TYPE_LN_VAL: return "老年卡";
            case AUDIT_TYPE_YG_VAL: return "员工卡";
            case AUDIT_TYPE_LOGIN_AUDIT_VAL: return "";
        }
        return "未知";
    }

    //实名认证状态
    public static final int AUDIT_STATE_SUCCESS = 1;
    public static final int AUDIT_STATE_ERR = 2;
    public static final int AUDIT_STATE_ING = 3;
    public static String getAuditStateLabel(int val){
        switch (val){
            case AUDIT_STATE_SUCCESS: return "审核通过";
            case AUDIT_STATE_ERR: return "审核未通过";
            case AUDIT_STATE_ING: return "审核中";
        }
        return "状态未知";
    }


    public static class linkType{
        public static final int none = 0; //无
        public static final int static_h5 = 1; //静态H5
        public static final int img = 2; //图片
        public static final int app_page = 3; //app
        public static final int wx_app = 4; //微信小程序
        public static final int dynamic_app = 5; //动态H5
    }

    public static class linkApp{
        public static final String marketing = "app:marketing"; //全民营销
        public static final String shopping = "app:shopping"; //商城GO
        public static final String mobilebus = "app:mobilebus"; //掌上公交
    }

}
