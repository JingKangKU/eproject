package com.ums.eproject.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.bean.MemberBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;

import es.dmoral.toasty.Toasty;

public class UserBalanceActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_balance);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("余额查询");

        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();


        getAccountBalance();
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
    private void getAccountBalance() {
        CommRequestApi.getInstance().getAccountBalance( new HttpSubscriber<>(new SubscriberOnListener<MemberBean>() {
            @Override
            public void onSucceed(MemberBean data) {
                if (data.getCode() == 200) {

                } else {
                    MsgUtil.showCustom(context, data.getMessage());

                }

            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context,false));
    }

}