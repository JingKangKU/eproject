package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import es.dmoral.toasty.Toasty;

public class GoodsPayActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private LinearLayout ll_goods_pay_sub,ll_order_pay_pop;
    private View popView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_pay);

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

        ll_goods_pay_sub = findViewById(R.id.ll_goods_pay_sub);
        findViewById(R.id.ll_order_pay_pop).setOnClickListener(this);
        findViewById(R.id.goods_order_go_pay).setOnClickListener(this);
        popView = LayoutInflater.from(this).inflate(R.layout.layout_pay_pop, null, false);

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
        if (v.getId() == R.id.ll_order_pay_pop){
            UIHelp.showPopupWindow(this,ll_goods_pay_sub,popView,0);
        }
        if (v.getId() == R.id.goods_order_go_pay){
            UIHelp.startActivity(context,PayStateActivity.class);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}