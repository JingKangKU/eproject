package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.labels.LabelsView;
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
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class TopupActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private RecyclerView topup_recycler_view;
    private double depositAmount;
    private LinearLayout ll_topup_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_recycler);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);

        findViewById(R.id.title_back).setOnClickListener(this);
        title_text.setText("账户充值");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        topup_recycler_view = findViewById(R.id.topup_recycler_view);
        ll_topup_input = findViewById(R.id.ll_topup_input);
        findViewById(R.id.topup_recharge).setOnClickListener(this);;

        getDepositRuleList();
    }

    private void initRecyclerView(List<Integer> depositAmountList){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL,false);
        topup_recycler_view.setLayoutManager(gridLayoutManager);


        ArrayList<SingleOption> list = new ArrayList<>();
        for (int i = 0 ; i < depositAmountList.size() ; i++){
            SingleOption singleOption = new SingleOption(depositAmountList.get(i)+"","1");
            singleOption.setDepositAmount(depositAmountList.get(i));
            list.add(singleOption);
        }
        TopupAdapter adapter = new TopupAdapter(list);
        adapter.setClickListener(new TopupAdapter.ClickListenerInterface() {
            @Override
            public void doClick(SingleOption singleOption,int position) {
                depositAmount = singleOption.getDepositAmount();
            }
        });
        topup_recycler_view.setAdapter(adapter);
    }
    private void memDepositTrial(double depositAmount) {
        CommRequestApi.getInstance().memDepositTrial(depositAmount,new HttpSubscriber<>(new SubscriberOnListener<DepositTrial>() {
            @Override
            public void onSucceed(DepositTrial data) {
                if (data.getCode() == 200) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("depositTrial",data);
                    UIHelp.startActivity(context,TopupPayActivity.class,bundle);
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

    private void getDepositRuleList() {
        CommRequestApi.getInstance().getDepositRuleList(new HttpSubscriber<>(new SubscriberOnListener<DepositRuleBean>() {
            @Override
            public void onSucceed(DepositRuleBean data) {
                if (data.getCode() == 200){
                    initRecyclerView(data.getData().getDepositAmountList());
                }else{
                    MsgUtil.showCustom(context,data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            finish();
        }
        if (v.getId() == R.id.topup_recharge){
            memDepositTrial(depositAmount);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}