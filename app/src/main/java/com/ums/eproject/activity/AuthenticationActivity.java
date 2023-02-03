package com.ums.eproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.bean.AuthBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MLog;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;

import es.dmoral.toasty.Toasty;

public class AuthenticationActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right,ll_auth_yj,ll_auth_xs;
    private TextView title_text,tv_authentication_title;

    private int auditTypeVal;
    private Context context;

    private EditText authentication_name,authentication_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        context = this;
        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        tv_authentication_title = findViewById(R.id.tv_authentication_title);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        ll_auth_yj = findViewById(R.id.ll_auth_yj);
        ll_auth_xs = findViewById(R.id.ll_auth_xs);
        authentication_name = findViewById(R.id.authentication_name);
        authentication_id = findViewById(R.id.authentication_id);
        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.authentication_submit).setOnClickListener(this);
        findViewById(R.id.authentication_skip).setOnClickListener(this);

        //获取认证类型
        Bundle bundle = getIntent().getBundleExtra("bundle");
        auditTypeVal = bundle.getInt("auditTypeVal");
        if (auditTypeVal == Constant.AUDIT_TYPE_LOGIN_AUDIT_VAL){
            title_text.setText("实名认证");
            tv_authentication_title.setText("实名认证:");
        }else{
            String auditTitle = Constant.getAuditTypeLabel(auditTypeVal) +"实名认证：";
            title_text.setText(auditTitle);
            tv_authentication_title.setText(auditTitle);
        }


        if (auditTypeVal == Constant.AUDIT_TYPE_YJ_VAL){
            ll_auth_yj.setVisibility(View.VISIBLE);
            ll_auth_xs.setVisibility(View.GONE);
        }else if (auditTypeVal == Constant.AUDIT_TYPE_XS_VAL){
            ll_auth_yj.setVisibility(View.GONE);
            ll_auth_xs.setVisibility(View.VISIBLE);
        }else {
            ll_auth_yj.setVisibility(View.GONE);
            ll_auth_xs.setVisibility(View.GONE);
        }
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
            case R.id.authentication_submit:
                switch (auditTypeVal){
                    case Constant.AUDIT_TYPE_LOGIN_AUDIT_VAL:
                        authenticateMem();
                        break;
                }
                break;
            case R.id.authentication_skip:
                UIHelp.startActivity(context,MainActivity.class);
                finish();
                break;
        }
    }



    //登录后的实名认证
    private void authenticateMem() {
        String realName = authentication_name.getText().toString().trim();
        String idCardNo = authentication_id.getText().toString().trim();
        if (StrUtil.isEmpty(realName) && StrUtil.isEmpty(idCardNo)){
            MsgUtil.showCustom(context,"请完整输入姓名与身份证号");
            return;
        }
        MLog.d("1111111111111111" + realName + "          "+idCardNo);

        CommRequestApi.getInstance().authenticateMem(realName,idCardNo,new HttpSubscriber<>(new SubscriberOnListener<AuthBean>() {
            @Override
            public void onSucceed(AuthBean data) {
                if (data.getCode() == 200){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url",data.getData().getUrl());
                    UIHelp.startActivity(context, AuditWebViewActivity.class,bundle);

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
}