package com.ums.eproject.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.adapter.ViewPagerOrderAdapter;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.bean.OrderDetailBean;
import com.ums.eproject.fragment.OrderFragment;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.view.SquareImageView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class UserOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    private ImageView or_dtl_img;
    private TextView or_dtl_title,or_dtl_desc;

    private SquareImageView user_order_goods_img;
    private TextView user_order_goods_name,user_order_goods_price,user_order_goods_quantity,user_order_goods_itemAmount;

    private TextView or_dtl_id,or_dtl_xd_time,or_dtl_pay_time,or_dtl_pay_type,or_dtl_remark,or_dtl_pay_totalAmount,or_dtl_pay_payAmount;

    private TextView or_dtl_kd_company,or_dtl_kd_id,or_dtl_kd_name,or_dtl_kd_phone,or_dtl_kd_address,or_dtl_kd_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_detail);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("订单详情");

        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();


        initView();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        long orderId = bundle.getLong("orderId");
        getOrderDetail(orderId);
    }
    private void initView() {
        or_dtl_img = findViewById(R.id.or_dtl_img);
        or_dtl_title = findViewById(R.id.or_dtl_title);
        or_dtl_desc = findViewById(R.id.or_dtl_desc);

        user_order_goods_img = findViewById(R.id.user_order_goods_img);
        user_order_goods_name = findViewById(R.id.user_order_goods_name);
        user_order_goods_price = findViewById(R.id.user_order_goods_price);
        user_order_goods_quantity = findViewById(R.id.user_order_goods_quantity);
        user_order_goods_itemAmount = findViewById(R.id.user_order_goods_itemAmount);

        or_dtl_id = findViewById(R.id.or_dtl_id);
        or_dtl_xd_time = findViewById(R.id.or_dtl_xd_time);
        or_dtl_pay_time = findViewById(R.id.or_dtl_pay_time);
        or_dtl_pay_type = findViewById(R.id.or_dtl_pay_type);
        or_dtl_remark = findViewById(R.id.or_dtl_remark);
        or_dtl_pay_totalAmount = findViewById(R.id.or_dtl_pay_totalAmount);
        or_dtl_pay_payAmount = findViewById(R.id.or_dtl_pay_payAmount);

        or_dtl_kd_company = findViewById(R.id.or_dtl_kd_company);
        or_dtl_kd_id = findViewById(R.id.or_dtl_kd_id);
        or_dtl_kd_name = findViewById(R.id.or_dtl_kd_name);
        or_dtl_kd_phone = findViewById(R.id.or_dtl_kd_phone);
        or_dtl_kd_address = findViewById(R.id.or_dtl_kd_address);
        or_dtl_kd_time = findViewById(R.id.or_dtl_kd_time);

    }

    private void setOrderData(OrderDetailBean.DataBean data) {
        OrderDetailBean.DataBean.PdtLisBean pdt = data.getPdtLis().get(0);// TODO: 2023/2/9 单一下单商品待改造
//        or_dtl_img.setImageResource(Constant.getOrderStatusIconId(data.getOrderStatus()));
//        or_dtl_title.setText(Constant.getOrderTitleAndDesc(data.getOrderStatus())[0]);
//        or_dtl_desc.setText(Constant.getOrderTitleAndDesc(data.getOrderStatus())[1]);
        or_dtl_img.setImageResource(Constant.getOrderStatusIconId(1));
        or_dtl_title.setText(Constant.getOrderTitleAndDesc(1)[0]);
        or_dtl_desc.setText(Constant.getOrderTitleAndDesc(1)[1]);

        Glide.with(context).load(pdt.getPicUrl()).into(user_order_goods_img);
        user_order_goods_name.setText(pdt.getProductName());
        user_order_goods_price.setText(String.valueOf(pdt.getPrice()));
        user_order_goods_quantity.setText(String.valueOf(pdt.getQuantity()));
        user_order_goods_itemAmount.setText(String.valueOf(pdt.getItemAmount()));

        or_dtl_id.setText(String.valueOf(data.getOrderId()));
        or_dtl_xd_time.setText(data.getCreateDate());
        or_dtl_pay_time.setText(data.getPaymentDate());
        or_dtl_pay_type.setText(data.getOrderPayType());
        or_dtl_remark.setText(data.getRemark());
        or_dtl_pay_totalAmount.setText(String.valueOf(data.getTotalAmount()));
        or_dtl_pay_payAmount.setText(String.valueOf(data.getPayAmount()));

        or_dtl_kd_company.setText(data.getDeliveryCompany());
        or_dtl_kd_id.setText(data.getDeliverySn());
        or_dtl_kd_name.setText(data.getReceiverName());
        or_dtl_kd_phone.setText(data.getReceiverPhone());
        or_dtl_kd_address.setText(data.getReceiverProvince()+data.getReceiverCity()+data.getReceiverCounty()+data.getReceiverDetailAddress());
        or_dtl_kd_time.setText(data.getDeliveryDate());
    }


    private void getOrderDetail(long orderId) {

        CommRequestApi.getInstance().getOrderDetail(orderId, new HttpSubscriber<>(new SubscriberOnListener<OrderDetailBean>() {
            @Override
            public void onSucceed(OrderDetailBean data) {
                if (data.getCode() == 200) {

                    setOrderData(data.getData());

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


}