package com.chinaums.common.utils.manager;

import android.webkit.WebSettings;

import com.chinaums.common.utils.UMSSharedPrefUtil;
import com.chinaums.common.utils.UMSStringUtil;

public class WebCacheManager {

    public static String SP_WEB_CACHE = "webCache";

    private static WebCacheManager webCacheManager = new WebCacheManager();
    private static UMSSharedPrefUtil sharedPrefUtil;

    private WebCacheManager() {
    }

    /***
     * 获得WebCacheManager的实例
     *
     * @return WebCacheManager实例
     */
    public static WebCacheManager getInstance() {
        if (webCacheManager == null) {
            webCacheManager = new WebCacheManager();
            sharedPrefUtil = new UMSSharedPrefUtil(SP_WEB_CACHE);
        }
        return webCacheManager;
    }

    private UMSSharedPrefUtil getSharedPrefUtil() {
        if (sharedPrefUtil == null) {
            sharedPrefUtil = new UMSSharedPrefUtil(SP_WEB_CACHE);
        }
        return sharedPrefUtil;
    }

    public void setCacheMode(String url, WebSettings settings) {
        if (hasCache(url)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            saveCache(url);
        }
    }

    public void setCacheMode(String url, int hour, WebSettings settings) {
        if (hasCache(url) && getSharedPrefUtil().getLong(url) > System.currentTimeMillis()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            saveCache(url, System.currentTimeMillis() + hour * 60 * 60 * 1000);
        }
    }

    public boolean hasCache(String url) {
        if (UMSStringUtil.isEmpty(url))
            return false;

        return getSharedPrefUtil().getLong(url, -1) > 0;
    }

    public void saveCache(String url) {
        getSharedPrefUtil().putLong(url, 0);
    }

    public void saveCache(String url, long expiryTime) {
        getSharedPrefUtil().putLong(url, expiryTime);
    }

    public void removeCache(String url) {
        getSharedPrefUtil().remove(url);
    }

    public void clear() {
        getSharedPrefUtil().clear();
    }
}
