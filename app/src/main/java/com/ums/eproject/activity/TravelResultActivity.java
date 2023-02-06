package com.ums.eproject.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.bean.CBPayResultBean;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TravelResultActivity extends BaseActivity {
    private TextView cardTypeTv, travelTv, billAmtTv, payAmtTv, travelTimeTv, isTransferTv, billIdTv;
    ImageView backIv;
    private LinearLayout titleLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cb_result);
        titleLL = findViewById(R.id.code_title);

        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                titleLL.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        cardTypeTv = findViewById(R.id.tv_cardtype);
        travelTv = findViewById(R.id.tv_travel_detail);
        billAmtTv = findViewById(R.id.tv_billamt);
        payAmtTv = findViewById(R.id.tv_payamt);
        travelTimeTv = findViewById(R.id.tv_travel_time);
        isTransferTv = findViewById(R.id.tv_istransfer);
        billIdTv = findViewById(R.id.tv_billid);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        CBPayResultBean bean = (CBPayResultBean) bundle.getSerializable("data");
        if (null != bean) {
            cardTypeTv.setText("12313");
            travelTv.setText(bean.getBusNo());
            billAmtTv.setText(bean.getBusPrice() + "元");
            payAmtTv.setText(bean.getRealAmount() + "元");
            travelTimeTv.setText(bean.getRideDate());
            isTransferTv.setText(bean.getIsTransfer() == 1 ? "是" : "否");
            billIdTv.setText(String.valueOf(bean.getPayCardFlowId()));
        }
        backIv = findViewById(R.id.main_title_back);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelResultActivity.this.finish();
            }
        });
    }

}