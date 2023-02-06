package com.ums.eproject.https.comm;


import android.content.Context;
import android.util.Log;

import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.AuthBean;
import com.ums.eproject.bean.BaseBean;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.CBPayResultBean;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.DepositTrial;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.HomeBean;
import com.ums.eproject.bean.MarketProductsBean;
import com.ums.eproject.bean.NETData;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.bean.UserBean;
import com.ums.eproject.https.BaseApi;
import com.ums.eproject.https.HttpRequestService;
import com.ums.eproject.utils.AesUtil;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.RandomStrUtil;
import com.ums.eproject.utils.SignHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by jk on 2017/4/27.
 */

public class CommRequestApi extends BaseApi {
    public static CommRequestApi httpRequestApi;
    public HttpRequestService httpRequestService;

    public CommRequestApi() {
        httpRequestService = CommMethod.getInstance().createApi(HttpRequestService.class);
    }

    public CommRequestApi(String base_url) {
        httpRequestService = CommMethod.getInstance(base_url).createApi(HttpRequestService.class);
    }

    public static CommRequestApi getInstance() {
        if (httpRequestApi == null) {
            httpRequestApi = new CommRequestApi();
        }
        return httpRequestApi;
    }

    public static CommRequestApi getInstance(String url) {
        if (httpRequestApi == null) {
            httpRequestApi = new CommRequestApi(url);
        }
        return httpRequestApi;
    }


    public void sendMsgCode(String mobile, String purpose, Subscriber<NETData> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            json.put("mobile", AesUtil.encryptValue(mobile));
//            json.put("purpose", Constant.new_register);
            json.put("purpose", purpose);

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Observable observable = httpRequestService.sendMsgCode(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void memRegister(String mobile, String code, String passwd, Subscriber<NETData> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("mobile", AesUtil.encryptValue(mobile));
            json.put("code", code);
            json.put("passwd", AesUtil.encryptValue(passwd));
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Observable observable = httpRequestService.memRegister(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void loginByPwd(String mobile, String passwd, Subscriber<UserBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("mobile", AesUtil.encryptValue(mobile));
            json.put("passwd", AesUtil.encryptValue(passwd));
//            json.put("passwd", AesUtil.encryptValue("pw123456")); // TODO: 2023/1/16 test
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Observable observable = httpRequestService.loginByPwd(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void loginByMsg(String mobile, String code, Subscriber<UserBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("mobile", AesUtil.encryptValue(mobile));
            json.put("code", code);
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Observable observable = httpRequestService.loginByMsg(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void authenticateMem(String realName, String idCardNo, Subscriber<AuthBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("realName", AesUtil.encryptValue(realName));
            json.put("idCardNo", AesUtil.encryptValue(idCardNo));
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.authenticateMem(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }


    public void getHomePage(Subscriber<HomeBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.getHomePage(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void getDepositRuleList(Subscriber<DepositRuleBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.getDepositRuleList(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void getListPdtCategory(Subscriber<PdtCategory> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.getListPdtCategory(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }


    public void queryProducts(long categoryId, int pageNum, int pageSize, Subscriber<ProductsBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("categoryId", categoryId);
            json.put("pdtName", "");
            json.put("pageNum", pageNum);
            json.put("pageSize", pageSize);
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.queryProducts(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void getProductDetails(long id,Subscriber<GoodsDetail> subscriber){
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("id",id);
            //业务参数end

            json.put("randomStr",signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign",sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"),json.toString());
        Observable observable = httpRequestService.getProductDetails(signKey,body).map(new HttpResultFunc<>());

        toSubscribe(observable,subscriber);
    }


    public void memDepositTrial(double depositAmount,Subscriber<DepositTrial> subscriber){
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("depositAmount",depositAmount);
            //业务参数end

            json.put("randomStr",signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign",sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"),json.toString());
        Observable observable = httpRequestService.memDepositTrial(signKey,body).map(new HttpResultFunc<>());

        toSubscribe(observable,subscriber);
    }

    public void saveAddress(AddressBean dataBean, Subscriber<AddressBean> subscriber) {
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("name", dataBean.getName());
            json.put("mobile", dataBean.getMobile());
            json.put("provinceName", dataBean.getProvinceName());
            json.put("cityName", dataBean.getCityName());
            json.put("countyName", dataBean.getCountyName());
            json.put("detailAddress", dataBean.getDetailAddress());
            json.put("isDefault", dataBean.getIsDefault());
            if (null != dataBean.getId() && dataBean.getId() != 0)
                json.put("id", dataBean.getId());
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String signKey = "";
//        try {
//            signKey = RandomStrUtil.getRandomString();
//            dataBean.setRandomStr(signKey);
//            dataBean.setSource(Constant.source);
//            String sign = SignHelper.getSignValue4Bean(dataBean, signKey + Constant.publicKey);
//            dataBean.setSign(sign);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.saveAddress(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void queryAllAddes(Subscriber<BaseRequest<List<AddressBean>>> subscriber) {
        String signKey = "";
        AddressBean bean = new AddressBean();
        try {
            signKey = RandomStrUtil.getRandomString();
            bean.setRandomStr(signKey);
            bean.setSource(Constant.source);
            String sign = SignHelper.getSignValue4Bean(bean, signKey + Constant.publicKey);
            Log.d("chendong", "origin sign : " + sign);
            bean.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Observable observable = httpRequestService.queryAllAddes(signKey, bean).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void queryAddByID(Long id, Subscriber<BaseRequest<AddressBean>> subscriber) {
        String signKey = "";
        JSONObject json = new JSONObject();
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("id", id);
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.queryAddByID(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void delAddByID(Long id, Subscriber<BaseRequest<String>> subscriber) {
        String signKey = "";
        JSONObject json = new JSONObject();
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("id", id);
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.delAddByID(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public void busFeeC2BPay(String code, Subscriber<BaseRequest<CBPayResultBean>> subscriber) {
        String signKey = "";
        JSONObject json = new JSONObject();
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            json.put("code", code);
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.busFeeC2BPay(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }


    public void queryMarketProducts(Subscriber<BaseRequest<MarketProductsBean>> subscriber) {
        String signKey = "";
        JSONObject json = new JSONObject();
        try {
            signKey = RandomStrUtil.getRandomString();

            //业务参数start
            //业务参数end

            json.put("randomStr", signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8;"), json.toString());
        Observable observable = httpRequestService.queryMarketProducts(signKey, body).map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

}
