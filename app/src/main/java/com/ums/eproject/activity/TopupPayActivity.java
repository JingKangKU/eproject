package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.adapter.TopupAdapter;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.DepositTrial;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.SingleOption;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class TopupPayActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    private TextView topup_pay_rechargeAmount,topup_pay_rechargeGiftAmount;
    private TextView topup_pay_rechargeDiscountAmount,topup_pay_payAmount;
    private LinearLayout topup_pay_dopay;

    private ImageView popup_item1_selected,popup_item2_selected,popup_item3_selected,popup_item4_selected;
    private ImageView[] itemViews;
    private int defSelect = -1; //无默认选择
    private int comPayType; //支付方式
    private View popView;
    private TextView topup_pay_pay_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_pay);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);

        findViewById(R.id.title_back).setOnClickListener(this);
        title_text.setText("充值确认");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        findViewById(R.id.topup_pay_pay_sub).setOnClickListener(this);
        topup_pay_dopay = findViewById(R.id.topup_pay_dopay);
        topup_pay_dopay.setOnClickListener(this);
        topup_pay_rechargeAmount = findViewById(R.id.topup_pay_rechargeAmount);
        topup_pay_rechargeGiftAmount = findViewById(R.id.topup_pay_rechargeGiftAmount);
        topup_pay_rechargeDiscountAmount = findViewById(R.id.topup_pay_rechargeDiscountAmount);
        topup_pay_payAmount = findViewById(R.id.topup_pay_payAmount);
        topup_pay_pay_type = findViewById(R.id.topup_pay_pay_type);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        DepositTrial depositTrial = (DepositTrial) bundle.getSerializable("depositTrial");
        initViewData(depositTrial);
        initPopView();
    }

    private void initViewData(DepositTrial depositTrial) {
        topup_pay_rechargeAmount.setText(String.valueOf(depositTrial.getData().getRechargeAmount()));
        topup_pay_rechargeGiftAmount.setText(String.valueOf(depositTrial.getData().getRechargeGiftAmount()));
        topup_pay_rechargeDiscountAmount.setText(String.valueOf(depositTrial.getData().getRechargeDiscountAmount()));
        topup_pay_payAmount.setText(String.valueOf(depositTrial.getData().getPayAmount()));

    }

    private void initPopView() {
        popView = LayoutInflater.from(this).inflate(R.layout.layout_pay_pop, null, false);
        popView.findViewById(R.id.popup_item1).setVisibility(View.GONE);//隐藏余额支付
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
        topup_pay_pay_type.setText(Constant.transTypes[index]);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.topup_pay_dopay:
                UIHelp.showPopupWindow(this,topup_pay_dopay,popView,defSelect);
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
            case R.id.topup_pay_pay_sub:
                if (defSelect == -1){
                    MsgUtil.showCustom(context,"请选择支付方式");
                }else{
                    UIHelp.startActivity(context,PayStateActivity.class);
                }

                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}