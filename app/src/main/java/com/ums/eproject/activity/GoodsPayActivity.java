package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.PerPdtOrder;
import com.ums.eproject.bean.PlaceOrderBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.ScaleImageView;

import es.dmoral.toasty.Toasty;

public class GoodsPayActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private LinearLayout ll_goods_pay_sub,ll_order_pay_pop;
    private View popView;
    private AddressBean addressBean;

    private TextView pay_order_address_desc,pay_order_address_name,pay_order_address_mobile;
    private TextView pay_goods_title,pay_goods_price,pay_goods_quantity,pay_goods_remark,pay_order_actual_price,pay_goods_type;
    private ScaleImageView pay_goods_img;
    private ImageView popup_item1_selected,popup_item2_selected,popup_item3_selected,popup_item4_selected;
    private ImageView[] itemViews;
    private int defSelect;
    private int comPayType; //支付方式
    private PerPdtOrder.DataBean.ProductBean productBean;
    private double actualPrice; //支付金额
    private double orderTotalPrice; //订单总金额
    private String remark; //订单备注

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

        initPopView();

        pay_order_address_desc = findViewById(R.id.pay_order_address_desc);
        pay_order_address_name = findViewById(R.id.pay_order_address_name);
        pay_order_address_mobile = findViewById(R.id.pay_order_address_mobile);

        pay_goods_img = findViewById(R.id.pay_goods_img);
        pay_goods_title = findViewById(R.id.pay_goods_title);
        pay_goods_price = findViewById(R.id.pay_goods_price);
        pay_goods_quantity = findViewById(R.id.pay_goods_quantity);
        pay_goods_remark = findViewById(R.id.pay_goods_remark);
        pay_order_actual_price = findViewById(R.id.pay_order_actual_price);
        pay_goods_type = findViewById(R.id.pay_goods_type);

        ll_goods_pay_sub = findViewById(R.id.ll_goods_pay_sub);

        findViewById(R.id.ll_order_pay_pop).setOnClickListener(this);
        findViewById(R.id.goods_order_go_pay).setOnClickListener(this);


        Bundle bundle = getIntent().getBundleExtra("bundle");
        PerPdtOrder perPdtOrder = (PerPdtOrder) bundle.getSerializable("perPdtOrder");
        addressBean = (AddressBean) bundle.getSerializable("addressBean");

        setAddressData();
        productBean = perPdtOrder.getData().getProduct();
        actualPrice = perPdtOrder.getData().getActualPrice();
        orderTotalPrice = perPdtOrder.getData().getOrderTotalPrice();

        remark = bundle.getString("remark");
        setGoodsData(productBean ,actualPrice);
    }

    private void initPopView() {
        // TODO: 2023/2/8 默认余额支付
        defSelect = 0;
        comPayType = Constant.getComPayType(defSelect);
        popView = LayoutInflater.from(this).inflate(R.layout.layout_pay_pop, null, false);
        popView.findViewById(R.id.popup_item1).setOnClickListener(this);
        popView.findViewById(R.id.popup_item2).setOnClickListener(this);
        popView.findViewById(R.id.popup_item3).setOnClickListener(this);
        popView.findViewById(R.id.popup_item4).setOnClickListener(this);
        popup_item1_selected = popView.findViewById(R.id.popup_item1_selected);
        popup_item2_selected = popView.findViewById(R.id.popup_item2_selected);
        popup_item3_selected = popView.findViewById(R.id.popup_item3_selected);
        popup_item4_selected = popView.findViewById(R.id.popup_item4_selected);
        itemViews = new ImageView[]{popup_item1_selected, popup_item2_selected, popup_item3_selected, popup_item4_selected};
    }
    private void itemSelected(int index){
        UIHelp.closePopupWindow1(this);
        pay_goods_type.setText(Constant.transTypes[index]);
        for (int i = 0 ; i < itemViews.length ; i ++){
            if (i == index) {
                defSelect = index;
                comPayType = Constant.getComPayType(defSelect);
                itemViews[i].setImageResource(R.mipmap.selected);
            } else {
                itemViews[i].setImageResource(R.mipmap.select);
            }
        }
    }
    private void setGoodsData(PerPdtOrder.DataBean.ProductBean product,double actualPrice) {
        Glide.with(context).load(product.getPicUrl()).into(pay_goods_img);
        pay_goods_title.setText(product.getName());
        pay_goods_price.setText(String.valueOf(product.getPrice()));
        pay_goods_quantity.setText(String.valueOf(product.getQuantity()));
        pay_goods_remark.setText(remark);
        pay_order_actual_price.setText(String.valueOf(actualPrice));
    }


    private void setAddressData() {
        pay_order_address_desc.setText(addressBean.getAddressDesc());
        pay_order_address_name.setText(addressBean.getName());
        pay_order_address_mobile.setText(addressBean.getMobile());
    }

    private void preOrderPerPdt(PlaceOrderBean placeOrderBean){
        CommRequestApi.getInstance().preOrderPerPdt(context, placeOrderBean,new HttpSubscriber<>(new SubscriberOnListener<PerPdtOrder>() {
            @Override
            public void onSucceed(PerPdtOrder data) {
                if (data.getCode() == 200) {
                    UIHelp.startActivity(context,PayStateActivity.class);
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
    private void doPreOrderPerPdt() {
        PlaceOrderBean placeOrderBean = new PlaceOrderBean();
        placeOrderBean.setComPayType(comPayType);
        placeOrderBean.setProductId(productBean.getId());
        placeOrderBean.setShippingType(Constant.shippingType_kd);
        placeOrderBean.setReceiveAddrId(addressBean.getId());
        placeOrderBean.setQuantity(productBean.getQuantity());
        placeOrderBean.setTotalAmount(orderTotalPrice);
        placeOrderBean.setTotalScore(0);

        placeOrderBean.setRealPayScore(0);
        placeOrderBean.setRemark(remark);

        if (comPayType == Constant.ComPayType_ye){
            placeOrderBean.setRealPayAmount(0);
            placeOrderBean.setMemCardAmount(actualPrice);
        }else{
            placeOrderBean.setRealPayAmount(actualPrice);
            placeOrderBean.setMemCardAmount(0);
        }
        preOrderPerPdt(placeOrderBean);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.ll_order_pay_pop:
                UIHelp.showPopupWindow(this,ll_goods_pay_sub,popView,defSelect);
                break;
            case R.id.goods_order_go_pay:
                doPreOrderPerPdt();
                break;
            case R.id.popup_item1:
                itemSelected(0);
                break;
            case R.id.popup_item2:
                itemSelected(1);
                break;
            case R.id.popup_item3:
                itemSelected(2);
                break;
            case R.id.popup_item4:
                itemSelected(3);
                break;
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}