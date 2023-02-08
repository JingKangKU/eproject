package com.ums.eproject.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;
import com.ums.eproject.R;
import com.ums.eproject.adapter.MarketProductAdapter;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.MarketProductsBean;
import com.ums.eproject.bean.MarketingDetailsBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MarketingDetailsActivity extends BaseActivity {
    private static final String TAG = MarketingDetailsActivity.class.getName();
    private XBanner picXB;
    private WebView detailsWV;
    MarketingDetailsBean showData;
    private TextView subjectName, unitName;
    private LinearLayout callLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_marketing);
        picXB = findViewById(R.id.banner_detail_marketing);
        detailsWV = findViewById(R.id.wb_desc_marketing);
        subjectName = findViewById(R.id.tv_subject);
        unitName = findViewById(R.id.tv_unitName);
        callLL = findViewById(R.id.ll_call);
        Bundle bundle = this.getIntent().getBundleExtra("bundle");
        showData = (MarketingDetailsBean) bundle.getSerializable("data");
        if (null != showData) {
            setBannerData();
            setWebViewData(showData.getDescription());
            subjectName.setText(showData.getSubjectCategoryName());
            unitName.setText(showData.getUnitName());
            callLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + showData.getContactPerson()));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setBannerData() {
        List<BaseBannerInfo> imageUrl = new ArrayList<>();

        if (showData.getGallery().size() > 0) {
            for (int i = 0; i < showData.getGallery().size(); i++) {
                int finalI = i;
                imageUrl.add(new BaseBannerInfo() {
                    @Override
                    public Object getXBannerUrl() {
                        return showData.getGallery().get(finalI);
                    }

                    @Override
                    public String getXBannerTitle() {
                        return "";
                    }
                });
            }
        }


        picXB.setBannerData(imageUrl);
        picXB.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(context).load(imageUrl.get(position).getXBannerUrl()).into((ImageView) view);
            }
        });
        picXB.setPageTransformer(Transformer.Default);//横向移动


        picXB.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                //点击事件

            }
        });
    }

    private void setWebViewData(String text) {
        initWebView();
        if (StrUtil.isEmpty(text)) {
            detailsWV.setVisibility(View.GONE);
            return;
        }
        detailsWV.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
    }

    private void initWebView() {
        WebSettings settings = detailsWV.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

}
