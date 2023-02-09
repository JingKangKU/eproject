package com.ums.eproject.utils;


import com.ums.eproject.R;

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

    public static final int startForOrder = 101; //order 启动地址列表

    public static final int buyMaxThingNum = 30; //一个订单最多30件

    public static final int shippingType_zt = 0; //运输方式 0: 自提
    public static final int shippingType_kd = 1; //运输方式 1: 快递

    public static final String[] transTypes = new String[]{"余额支付","云闪付","微信支付","支付宝支付"};

    public static final int ComPayType_ye = 7; //余额支付
    public static int getComPayType(int index){ // 2: 微信; 4:支付宝; 5：云闪付; 7，余额
        switch (index){
            case 0:return 7;
            case 1:return 5;
            case 2:return 2;
            case 3:return 4;

        }
        return 0;
    }

    //订单查询状态
    public static final String orderStatus_all = "";//全部
    public static final String orderStatus_unpaid = "0";//待支付
    public static final String orderStatus_harvested = "1,2";//待收货
    public static final String orderStatus_success = "3";//已完成
    public static final String orderStatus_cancel_and_refund = "4,5,9";//已取消

    //服务器订单状态  0:待付款; 1:待发货; 2:待收货 3:已完成; 4:已关闭; 5:无效订单; 9:已退货
    public static final int resp_orderStatus_dfk = 0;
    public static final int resp_orderStatus_dfh = 1;
    public static final int resp_orderStatus_dsh = 2;
    public static final int resp_orderStatus_ywc = 3;
    public static final int resp_orderStatus_ygb = 4;
    public static final int resp_orderStatus_wx = 5;
    public static final int resp_orderStatus_yth = 9;

    //服务器订单类型 0:普通购买; 1:秒杀; 2:团购; 3:砍价
    public static final int resp_mktType_ptg = 0;
    public static final int resp_mktType_ms = 1;
    public static final int resp_mktType_tg = 2;
    public static final int resp_mktType_kj = 3;

    //订单详情的title 订单详情的desc
    public static String[] getOrderTitleAndDesc(int orderStatus){
        switch (orderStatus){
            case resp_orderStatus_dfk:
                return new String[]{"订单待支付","该笔订单未支付！"};
            case resp_orderStatus_dfh:
                return new String[]{"订单待发货","该笔订单未发货！"};
            case resp_orderStatus_dsh:
                return new String[]{"订单待收货","该笔订单已发货！"};
            case resp_orderStatus_ywc:
                return new String[]{"订单已完成","该笔订单已完成！"};
            case resp_orderStatus_ygb:
                return new String[]{"订单已关闭","该笔订单已关闭！"};
            case resp_orderStatus_wx:
                return new String[]{"无效订单","该笔订单已无效！"};
            case resp_orderStatus_yth:
                return new String[]{"订单已退货","该笔订单已退货！"};
        }
        return new String[]{"状态未知","未知"};
    }

    //订单详情的状态图片资源id
    public static int getOrderStatusIconId(int orderStatus){
//        switch (orderStatus){
//            case resp_orderStatus_dfk:
//                return R.mipmap.order_detail_qx;
//            case resp_orderStatus_dfh:
//                return R.mipmap.order_detail_qx;
//            case resp_orderStatus_dsh:
//                return R.mipmap.order_detail_qx;
//            case resp_orderStatus_ywc:
//                return R.mipmap.order_detail_qx;
//            case resp_orderStatus_ygb:
//                return R.mipmap.order_detail_qx;
//            case resp_orderStatus_wx:
//                return R.mipmap.order_detail_qx;
//            case resp_orderStatus_yth:
//                return R.mipmap.order_detail_qx;
//        }
        return R.mipmap.order_detail_qx;
    }


    //用户相关三个协议
    //用户服务协议
    public static final String agreement_yh = "http://36.137.57.29:7480/tetprotocols/userservice.html";
    //隐私协议
    public static final String agreement_ys = "http://36.137.57.29:7480/tetprotocols/privateinfo.html";
    //免密协议
    public static final String agreement_mm = "http://36.137.57.29:7480/tetprotocols/grantservice.html";
}
