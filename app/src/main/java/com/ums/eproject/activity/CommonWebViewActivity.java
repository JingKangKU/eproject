package com.ums.eproject.activity;

import static android.os.Build.VERSION_CODES.M;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.BuildConfig;
import com.ums.eproject.R;
import com.ums.eproject.utils.MLog;
import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonWebViewActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    private WebView web_view;
    private boolean supportZoom;//是否支持放大缩小
    private boolean isHtmlStr;//是否为父文本模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web_view);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        web_view = findViewById(R.id.web_view);

        findViewById(R.id.title_back).setOnClickListener(this);

        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();


        Bundle bundle = getIntent().getBundleExtra("bundle");
        isHtmlStr =  bundle.getBoolean("isHtmlStr",false);

        if (isHtmlStr){
            String titleText = bundle.getString("titleText");
            title_text.setText(titleText);
            String detailDesc = bundle.getString("detailDesc","");
            initHtmlStrWebView();
            setWebViewHtmlStr(detailDesc);
        }else{
            String url = bundle.getString("url","");
            String titleText = bundle.getString("titleText");
            title_text.setText(titleText);
            supportZoom = bundle.getBoolean("supportZoom",false);
            initWebView(url);
        }

    }

    private void closeWebView() {
        web_view.loadUrl("about:blank");
        web_view = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        closeWebView();
        super.finish();
    }
    private void initHtmlStrWebView() {

        web_view.setOnLongClickListener(v -> false);
        WebSettings webSettings = web_view.getSettings();
        web_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web_view.setScrollbarFadingEnabled(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webSettings.setBlockNetworkImage(false);//解决图片不显示
        // 解决混合资源的加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }



    }

    private void setWebViewHtmlStr(String text) {
        if (StrUtil.isEmpty(text)){
            web_view.setVisibility(View.GONE);
            return;
        }
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');" +
                "for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";//将img标签属性定死的js代码
        text = text.replaceAll("width=\"\\d+\"", "width=\"100%\"").replaceAll("height=\"\\d+\"", "height=\"auto\"");
        web_view.loadDataWithBaseURL(null, varjs+text, "text/html", "utf-8", null);
    }



    private void initWebView(String url) {

        web_view.setOnLongClickListener(v -> false);
        WebSettings webSettings = web_view.getSettings();
        web_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web_view.setScrollbarFadingEnabled(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(supportZoom);//是否支持缩放

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webSettings.setBlockNetworkImage(false);//解决图片不显示
        // 解决混合资源的加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }


        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                interceptUrl(view, url);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                interceptUrl(view, url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }


        });
        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            //for  Android 4.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) { }

            @SuppressLint("NewApi")
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return true;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return true;
            }
        });

        web_view.loadUrl(url);
    }

    private void interceptUrl(WebView webView, String url) {
        MLog.d("commonWebUrl：" + url);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}