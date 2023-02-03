package com.ums.eproject.https.interceptor;


import static okhttp3.internal.platform.Platform.INFO;


import com.ums.eproject.utils.AesUtil;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MLog;
import com.ums.eproject.utils.RandomStrUtil;
import com.ums.eproject.utils.SignHelper;
import com.ums.eproject.utils.StrUtil;

import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by jk on 2017/2/23.
 * 拦截器拦截请求数据
 */

public class LoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /** No logs. */
        NONE,
        /**
         * Logs request and response lines.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /** A {@link Logger} defaults output appropriate for the current platform. */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public LoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public LoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /** Change the level at which this interceptor logs. */
    public LoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request requestOld = chain.request();
        Request request = checkIgnoreToken(requestOld);

        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        logger.log(requestStartMessage);

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    logger.log("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    logger.log("Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logger.log(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
                logger.log("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                logger.log("");
                if (isPlaintext(buffer)) {
                    logger.log(buffer.readString(charset));
                    logger.log("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    logger.log("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        logger.log("<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ')');
        Constant.RESPONSECODE=response.code();

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logger.log(headers.name(i) + ": " + headers.value(i));
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logger.log("<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                logger.log("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        logger.log("");
                        logger.log("Couldn't decode the response body; charset is likely malformed.");
                        logger.log("<-- END HTTP");

                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    logger.log("");
                    logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    logger.log("");
                    logger.log(buffer.clone().readString(charset));
                    Constant.ERRMESSAGE=buffer.clone().readString(charset);
                }

                logger.log("<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }

        //判断token是否失效，若失效则自动登录
        Response newResponse = checkTokenExpired(chain, response);
        if (null != newResponse) {
            response = newResponse;
        }

        return response;
    }

    /**
     * @Description:检查是否忽略token
     * @Param: [request]
     * @Return: boolean
     **/
    private Request checkIgnoreToken(Request request) {
        try {
            boolean ignoreToken;
            String ignoreTokenStr = request.header("ignoreToken");
            if (StrUtil.isTrimEmpty(ignoreTokenStr)) {
                ignoreToken = true;
            } else {
                if (ignoreTokenStr.equals("true") || ignoreTokenStr.equals("false")) {
                    ignoreToken = Boolean.parseBoolean(ignoreTokenStr);
                } else {
                    ignoreToken = true;
                }
            }
            if (!ignoreToken) {
                request = request.newBuilder()
                        .addHeader(Constant.tokenReqHeader,Constant.tokenReq)
                        .build();
                return request;
            }
            return request;
        } catch (Exception e) {
            MLog.d("11111111111111111111");
            MLog.d(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @Description:判断token是否失效，若失效则自动登录  401Token过期
     * @Param: [response]
     * @Return: okhttp3.Response
     **/
    private Response checkTokenExpired(Chain chain, Response response) {
        try {
            JSONObject tokenJson = new JSONObject(getResponseInfo(response));
            if (tokenJson.has("code")) {
                if (tokenJson.optInt("code") == 401) { //超时
                    MLog.d("token失效，开始重新登录...");

                    RequestBody body = getRefreshTokenReqBody();
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Constant.baseUrl+"/tetapp/mem/refreshToken")
                            .addHeader("Accept", "application/json; q=0.5")
                            .post(body)
                            .build();

                    Call call = mOkHttpClient.newCall(request);
                    Response execute = call.execute();
                    String bodyString = execute.body().string();
                    JSONObject jsonObject = new JSONObject(bodyString);

                    if (jsonObject.optInt("code") == 200) {
                        MLog.d("token失效，结束获取...");
                        JSONObject dataJson =  jsonObject.optJSONObject("data");
                        if (dataJson!=null){
                            Constant.tokenHead = dataJson.optString("tokenHead");
                            Constant.token = dataJson.optString("token");
                            Constant.tokenReq = Constant.tokenHead+Constant.token;
                        }
                        //使用新的Token，创建新的请求
                        Request newRequest = chain.request().newBuilder()
                                .addHeader(Constant.tokenReqHeader, Constant.tokenReq)
                                .build();
                        return chain.proceed(newRequest);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private RequestBody getRefreshTokenReqBody(){
        JSONObject json = new JSONObject();
        String signKey = "";
        try {
            signKey = RandomStrUtil.getRandomString();
            //业务参数start
            json.put("token", Constant.token);
            json.put("tokenHead",Constant.tokenHead);
            //业务参数end
            json.put("randomStr",signKey);
            json.put("source", Constant.source);

            String sign = SignHelper.getSignValue(json.toString(), signKey + Constant.publicKey);
            json.put("sign",sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RequestBody.create(MediaType.parse("application/json; charset=utf-8;"),json.toString());
    }
    /**
     * 获取返回字符串
     * @param response 返回的对象
     */
    private String getResponseInfo(Response response) {
        String str = "";
        if (null == response || !response.isSuccessful()) {
            return str;
        }
        ResponseBody responseBody = response.body();
        if (null == responseBody) {
            return str;
        }
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.getBuffer();
        Charset charset = StandardCharsets.UTF_8;
        if (contentLength != 0) {
            str = buffer.clone().readString(charset);
        }
        return str;
    }
    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}