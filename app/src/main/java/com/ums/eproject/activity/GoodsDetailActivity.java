package com.ums.eproject.activity;

import static android.os.Build.VERSION_CODES.M;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chinaums.common.utils.UMSScreenUtil;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;
import com.ums.eproject.BuildConfig;
import com.ums.eproject.R;
import com.ums.eproject.adapter.TopupAdapter;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.utils.UriUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private XBanner goods_detail_banner;
    private WebView goods_detail_wb;
    private TextView goods_detail_buy;
    private TextView goods_detail_price,goods_detail_original_price,goods_detail_name,goods_detail_sub_title;
    private GoodsDetail.DataBean.InfoBean goodsInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        title_view = findViewById(R.id.title_view);
        title_text = findViewById(R.id.title_text);

        findViewById(R.id.title_back).setOnClickListener(this);
        title_text.setText("泰e通商城GO");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        goods_detail_price = findViewById(R.id.goods_detail_price);
        goods_detail_original_price = findViewById(R.id.goods_detail_original_price);
        goods_detail_name = findViewById(R.id.goods_detail_name);
        goods_detail_sub_title = findViewById(R.id.goods_detail_sub_title);


        goods_detail_banner = findViewById(R.id.goods_detail_banner);
        int wm = UIHelp.getScreenWidth(this) - UMSScreenUtil.dp2px(0);
        int hm = wm / 1 * 1;
        goods_detail_banner.setLayoutParams(new LinearLayout.LayoutParams(wm, hm));
        goods_detail_wb = findViewById(R.id.goods_detail_wb);
        goods_detail_buy = findViewById(R.id.goods_detail_buy);
        goods_detail_buy.setOnClickListener(this);

        initWebView();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        long goodsId = bundle.getLong("goodsId");
        getProductDetails(goodsId);

        goods_detail_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
    }
    private void setViewData(GoodsDetail.DataBean.InfoBean infoBean){
        goods_detail_price.setText(String.valueOf(infoBean.getPrice()));
        goods_detail_original_price.setText(String.valueOf(infoBean.getOriginalPrice()));
        goods_detail_name.setText(infoBean.getName());
        goods_detail_sub_title.setText(infoBean.getSubTitle());


        setBannerData(infoBean);
        setWebViewData(infoBean.getDetailDesc());
    }
    private void setBannerData(GoodsDetail.DataBean.InfoBean infoBean) {
        List<BaseBannerInfo> imageUrl = new ArrayList<>();

        if (infoBean.getGallery().size()>0){
            for(int i = 0 ; i < infoBean.getGallery().size() ; i ++){
                int finalI = i;
                imageUrl.add(new BaseBannerInfo() {
                    @Override
                    public Object getXBannerUrl() {
                        return infoBean.getGallery().get(finalI);
                    }

                    @Override
                    public String getXBannerTitle() {
                        return "";
                    }
                });
            }
        }



        goods_detail_banner.setBannerData(imageUrl);
        goods_detail_banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(context).load(imageUrl.get(position).getXBannerUrl()).into((ImageView) view);
            }
        });
        goods_detail_banner.setPageTransformer(Transformer.Default);//横向移动


        goods_detail_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                //点击事件

            }
        });
    }

    private void setWebViewData(String text) {
        if (StrUtil.isEmpty(text)){
            goods_detail_wb.setVisibility(View.GONE);
            return;
        }
        goods_detail_wb.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
    }

    private void getProductDetails(long id) {
        CommRequestApi.getInstance().getProductDetails(id,new HttpSubscriber<>(new SubscriberOnListener<GoodsDetail>() {
            @Override
            public void onSucceed(GoodsDetail data) {
                if (data.getCode() == 200) {
                    goodsInfo = data.getData().getInfo();
                    setViewData(data.getData().getInfo());
                } else {
                    MsgUtil.showCustom(context, data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }


    private void initWebView() {

        goods_detail_wb.setOnLongClickListener(v -> false);
        WebSettings webSettings = goods_detail_wb.getSettings();
        goods_detail_wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        goods_detail_wb.setScrollbarFadingEnabled(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            finish();
        }
        if (v.getId() == R.id.goods_detail_buy){
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodsInfo",goodsInfo);
            UIHelp.startActivity(context,GoodsOrderActivity.class,bundle);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}