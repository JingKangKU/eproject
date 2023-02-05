package com.ums.eproject.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;
import com.ums.eproject.R;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class GoodsOrderActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private TextView goods_order_go_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_order);

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

        findViewById(R.id.goods_order_go_pay).setOnClickListener(this);

//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        long goodsId = bundle.getLong("goodsId");
//        getProductDetails(goodsId);
    }


    private void getProductDetails(long id) {
        CommRequestApi.getInstance().getProductDetails(id,new HttpSubscriber<>(new SubscriberOnListener<GoodsDetail>() {
            @Override
            public void onSucceed(GoodsDetail data) {
                if (data.getCode() == 200) {



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
//    private void getProductDetails(long id) {
//        CommRequestApi.getInstance().getProductDetails(id,new HttpSubscriber<>(new SubscriberOnListener<GoodsDetail>() {
//            @Override
//            public void onSucceed(GoodsDetail data) {
//                if (data.getCode() == 200) {
//
//
//
//                } else {
//                    MsgUtil.showCustom(context, data.getMessage());
//                }
//            }
//            @Override
//            public void onError(int code, String msg) {
//                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();
//
//            }
//        }, context));
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            finish();
        }
        if (v.getId() == R.id.goods_order_go_pay){
            UIHelp.startActivity(context,GoodsPayActivity.class);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}