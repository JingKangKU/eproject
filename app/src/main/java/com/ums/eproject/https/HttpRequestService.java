package com.ums.eproject.https;




import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.AuthBean;
import com.ums.eproject.bean.BalanceBean;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.BookBalance;
import com.ums.eproject.bean.CBPayResultBean;
import com.ums.eproject.bean.CommonBean;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.DepositTrial;
import com.ums.eproject.bean.DynamicLink;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.HomeBean;
import com.ums.eproject.bean.MarketProductsBean;
import com.ums.eproject.bean.MarketingDetailsBean;
import com.ums.eproject.bean.MemberBean;
import com.ums.eproject.bean.NETData;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.bean.OrderDetailBean;
import com.ums.eproject.bean.OrderPerPdt;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.bean.PerPdtOrder;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.bean.ResetPwBean;
import com.ums.eproject.bean.StartAdvertise;
import com.ums.eproject.bean.UserBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jk
 * 网络请求接口配置
 */

public interface HttpRequestService {
    //登录注册修改密码
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:true"})
    @POST("/tetapp/sso/sendMsgCode")
    Observable<NETData> sendMsgCode(@Header("signKey") String signKey, @Body RequestBody body);

    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:true"})
    @POST("/tetapp/sso/memRegister")
    Observable<NETData> memRegister(@Header("signKey") String signKey, @Body RequestBody body);

    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:true"})
    @POST("/tetapp/sso/loginByPwd")
    Observable<UserBean> loginByPwd(@Header("signKey") String signKey, @Body RequestBody body);

    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:true"})
    @POST("/tetapp/sso/forgetPwd")
    Observable<ResetPwBean> forgetPwd(@Header("signKey") String signKey, @Body RequestBody body);

    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:true"})
    @POST("/tetapp/sso/loginByMsg")
    Observable<UserBean> loginByMsg(@Header("signKey") String signKey, @Body RequestBody body);

    //实名认证发起
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/auth/authenticateMem")
    Observable<AuthBean> authenticateMem( @Header("signKey") String signKey, @Body RequestBody body);

    //首页
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mkt/getHomePage")
    Observable<HomeBean> getHomePage(@Header("signKey") String signKey, @Body RequestBody body);


    //充值页面获取
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/getDepositRuleList")
    Observable<DepositRuleBean> getDepositRuleList(@Header("signKey") String signKey, @Body RequestBody body);

    //商品分类列表查询
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mkt/listPdtCategory")
    Observable<PdtCategory> getListPdtCategory(@Header("signKey") String signKey, @Body RequestBody body);

    //商品分类列表查询
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mkt/queryProducts")
    Observable<ProductsBean> queryProducts(@Header("signKey") String signKey, @Body RequestBody body);

    //商品订单试算
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/ord/trialPerPdtOrder")
    Observable<PerPdtOrder> trialPerPdtOrder(@Header("signKey") String signKey, @Body RequestBody body);

    //商品下单
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/ord/preOrderPerPdt")
    Observable<OrderPerPdt> preOrderPerPdt(@Header("signKey") String signKey, @Body RequestBody body);

    //商品查询
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mkt/getProductDetails")
    Observable<GoodsDetail> getProductDetails(@Header("signKey") String signKey, @Body RequestBody body);

    //在线充值试算
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/memDepositTrial")
    Observable<DepositTrial> memDepositTrial(@Header("signKey") String signKey, @Body RequestBody body);

    //获取动态H5地址
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/getDynamicLink")
    Observable<DynamicLink> getDynamicLink(@Header("signKey") String signKey, @Body RequestBody body);

    //新增收货地址
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/saveReceiveAddr")
    Observable<BaseRequest<String>> saveAddress(@Header("signKey") String signKey, @Body RequestBody body);
    //查询所有收货地址
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/queryMemReceiveAddr")
    Observable<BaseRequest<List<AddressBean>>> queryAllAddes(@Header("signKey") String signKey, @Body AddressBean body);
    //根据id查询单个收货地址
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/getReceiveAddr")
    Observable<BaseRequest<AddressBean>> queryAddByID(@Header("signKey") String signKey, @Body RequestBody body);

    //根据id删除单个收货地址
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/deleteReceiveAddr")
    Observable<BaseRequest<String>> delAddByID(@Header("signKey") String signKey, @Body RequestBody body);


    //根据id删除单个收货地址
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/busFeeC2BPay")
    Observable<BaseRequest<CBPayResultBean>> busFeeC2BPay(@Header("signKey") String signKey, @Body RequestBody body);

    //全民营销商品查询
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mkt/queryMarketProducts")
    Observable<BaseRequest<MarketProductsBean>> queryMarketProducts(@Header("signKey") String signKey, @Body RequestBody body);

    //全民营销商品明细查询
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mkt/getMarketProductDetails")
    Observable<BaseRequest<MarketingDetailsBean>> getMarketProductDetails(@Header("signKey") String signKey, @Body RequestBody body);

    //开屏广告
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:true"})
    @POST("/tetapp/mkt/listStartAdvertise")
    Observable<StartAdvertise> listStartAdvertise(@Header("signKey") String signKey, @Body RequestBody body);

     //我的订单
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/ord/queryOrders")
    Observable<OrderBean> queryOrders(@Header("signKey") String signKey, @Body RequestBody body);

    //我的订单详情
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/ord/getOrderDetail")
    Observable<OrderDetailBean> getOrderDetail(@Header("signKey") String signKey, @Body RequestBody body);

    //取消订单
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/ord/cancelOrder")
    Observable<CommonBean> cancelOrder(@Header("signKey") String signKey, @Body RequestBody body);

    //删除订单
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/ord/deleteOrder")
    Observable<CommonBean> deleteOrder(@Header("signKey") String signKey, @Body RequestBody body);

    //我的会员信息
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/getMemberDetails")
    Observable<MemberBean> getMemberDetails(@Header("signKey") String signKey, @Body RequestBody body);

    //会员余额查询
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/getAccountBalance")
    Observable<BalanceBean> getAccountBalance(@Header("signKey") String signKey, @Body RequestBody body);

    //会员余额台账
    @Headers({"Content-Type: application/json; charset=utf-8;","ignoreToken:false"})
    @POST("/tetapp/mem/queryBookBalance")
    Observable<BookBalance> queryBookBalance(@Header("signKey") String signKey, @Body RequestBody body);

}
