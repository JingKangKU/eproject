package com.ums.eproject.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;
import com.ums.eproject.R;
import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.ScaleImageView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class GoodsOrderActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private TextView goods_order_go_pay;
    private GoodsDetail.DataBean.InfoBean goodsInfo;
    private LinearLayout ll_goods_order_address_null, ll_goods_order_address;
    private TextView order_goods_address, order_goods_name, order_goods_mobile;

    private final static int openAddressReq = 100;

    private ScaleImageView order_goods_img;
    private TextView order_goods_price, order_goods_title;
    private TextView goods_order_thing_num, goods_order_thing_pic;
    private int thingNum = 1;
    private double thingPic;

    private ImageView goods_order_goods_num_reduce,goods_order_goods_num_add;
    private TextView goods_order_goods_num;

    private AddressBean addressBean;
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
        ll_goods_order_address_null = findViewById(R.id.ll_goods_order_address_null);
        ll_goods_order_address = findViewById(R.id.ll_goods_order_address);

        order_goods_address = findViewById(R.id.order_goods_address);
        order_goods_name = findViewById(R.id.order_goods_name);
        order_goods_mobile = findViewById(R.id.order_goods_mobile);

        order_goods_title = findViewById(R.id.order_goods_title);
        order_goods_price = findViewById(R.id.order_goods_price);
        order_goods_img = findViewById(R.id.order_goods_img);

        goods_order_thing_num = findViewById(R.id.goods_order_thing_num);
        goods_order_thing_pic = findViewById(R.id.goods_order_thing_pic);

        goods_order_goods_num_reduce = findViewById(R.id.goods_order_goods_num_reduce);
        goods_order_goods_num_add = findViewById(R.id.goods_order_goods_num_add);
        goods_order_goods_num = findViewById(R.id.goods_order_goods_num);

        ll_goods_order_address_null.setOnClickListener(this);
        ll_goods_order_address.setOnClickListener(this);
        goods_order_goods_num_reduce.setOnClickListener(this);
        goods_order_goods_num_add.setOnClickListener(this);
        ll_goods_order_address_null.setVisibility(View.VISIBLE);
        ll_goods_order_address.setVisibility(View.GONE);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        goodsInfo = (GoodsDetail.DataBean.InfoBean) bundle.getSerializable("goodsInfo");

        setGoodsData(goodsInfo);

        thingPic = goodsInfo.getPrice();
        setThingData();
    }

    private void setThingData() {
        goods_order_thing_num.setText(String.valueOf(thingNum));
        goods_order_thing_pic.setText(String.valueOf(thingPic * thingNum));

        goods_order_goods_num.setText(String.valueOf(thingNum));
    }

    private void setGoodsData(GoodsDetail.DataBean.InfoBean goodsInfo) {
        order_goods_title.setText(goodsInfo.getName());
        order_goods_price.setText(String.valueOf(goodsInfo.getPrice()));
        Glide.with(context).load(goodsInfo.getPicUrl()).into(order_goods_img);
    }


    private void getProductDetails(long id) {
        CommRequestApi.getInstance().getProductDetails(id, new HttpSubscriber<>(new SubscriberOnListener<GoodsDetail>() {
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
        if (v.getId() == R.id.goods_order_go_pay) {
            if (addressBean!=null && !StrUtil.isEmpty(addressBean.getMobile())){
                UIHelp.startActivity(context, GoodsPayActivity.class);
            }else{
                MsgUtil.showCustom(context,"请选择收获地址");
            }

        }
        if (v.getId() == R.id.ll_goods_order_address_null || v.getId() == R.id.ll_goods_order_address) {
            Bundle bundle = new Bundle();
            bundle.putInt("startForOrder", Constant.startForOrder);
            UIHelp.startActivity(this, AddressActivity.class, bundle, openAddressReq);
        }

        if (v.getId() == R.id.goods_order_goods_num_reduce){
            if (thingNum == 1) return;
            thingNum--;
            setThingData();
        }
        if (v.getId() == R.id.goods_order_goods_num_add){
            if (thingNum == Constant.buyMaxThingNum){
                MsgUtil.showCustom(context,"当前订单最多购买"+Constant.buyMaxThingNum+"件");
                return;
            }
            thingNum++;
            setThingData();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == openAddressReq && resultCode == RESULT_OK) {
            if (data != null) {
                addressBean = (AddressBean) data.getSerializableExtra("addressBean");
                setAddressData(addressBean);

            }
        }
    }

    private void setAddressData(AddressBean addressBean) {
        addressBean.toString();
        ll_goods_order_address_null.setVisibility(View.GONE);
        ll_goods_order_address.setVisibility(View.VISIBLE);
        order_goods_address.setText(addressBean.getAddressDesc());
        order_goods_name.setText(addressBean.getName());
        order_goods_mobile.setText(addressBean.getMobile());
    }


    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}