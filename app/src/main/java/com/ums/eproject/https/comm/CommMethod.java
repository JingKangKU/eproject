package com.ums.eproject.https.comm;



import com.ums.eproject.app.AppContext;
import com.ums.eproject.https.interceptor.LoggingInterceptor;
import com.ums.eproject.https.interceptor.SSLSocketClient;
import com.ums.eproject.https.util.NetUtil;
import com.ums.eproject.utils.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.CustomConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jk on 2019/3/12.
 */

public class CommMethod {
    /**
     * 测试版地址
     */
    public static final String BASE_URL = Constant.baseUrl;
    private static Retrofit retrofit;

    private CommMethod() {
        retrofit = new Retrofit.Builder()
                .client(genericClient())
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
    private CommMethod(String base_url) {
        retrofit = new Retrofit.Builder()
                .client(genericClient())
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
    }

    public static <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }


    private static  CommMethod INSTANCE = null;


    //获取单例
    public static CommMethod getInstance() {
        if (INSTANCE == null){
            INSTANCE = new CommMethod();
        }
        return INSTANCE;
    }
    public static CommMethod getInstance(String base_url) {
        return new CommMethod(base_url);
    }
    private static OkHttpClient genericClient() {
        LoggingInterceptor logging = new LoggingInterceptor();
        // set your desired log level
        logging.setLevel(LoggingInterceptor.Level.BODY);
        //设置缓存路径
       // File httpCacheDirectory = new File(AppContext.getInstance().getExternalCacheDir().getAbsolutePath(), "responses");
        //设置缓存 10M
       // Cache cache = new Cache(httpCacheDirectory, 50 * 1024 * 1024);
//        String credentials="51kalaxin:62kaxin";
//        final String basic =
//                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())// TODO:  忽略HTTPS证书
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())// TODO:  忽略HTTPS证书
                .connectTimeout(Constant.timeout, TimeUnit.SECONDS)
                .readTimeout(Constant.timeout, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = null;
//                        if (Constant.TOKEN == null || Constant.TOKEN.equals("")) {
                            request = chain.request().newBuilder()
//                                    .addHeader("Authorization",basic)
                                    .build();
//                        } else {
//                            request = chain.request()
//                                    .newBuilder()
//                                    .addHeader("Authorization", Constant.TOKEN)//添加token
//                                    .build();
//                        }
                        /*有没有网络的情况*/
                        if (!NetUtil.getNetworkIsConnected(AppContext.getInstance())) {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                        }
                        Response response = chain.proceed(request);
                        System.out.println("响应码---------" + response.code());
                        Constant.RESPONSECODE = response.code();
                        if (NetUtil.getNetworkIsConnected(AppContext.getInstance())) {
                            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                            response.newBuilder()
                                    .addHeader("Cache-Control", "public, max-age=" + maxAge)
                                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                    .build();
                        } else {
                            int maxStale = 60 * 60 * 24 * 7; // 无网络时，设置超时为1周
                            response.newBuilder()
                                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .removeHeader("Pragma")
                                    .build();
                        }
                        return response;
                    }

                }).addInterceptor(logging).build();

        return httpClient;
    }
}
