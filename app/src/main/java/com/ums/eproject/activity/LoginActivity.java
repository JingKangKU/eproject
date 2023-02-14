package com.ums.eproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinaums.common.component.ScanActivity;
import com.chinaums.common.utils.CommonConst;
import com.chinaums.common.utils.permission.PermissionListener;
import com.chinaums.common.utils.permission.UMSPermissionUtil;
import com.luck.picture.lib.tools.SPUtils;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.activity.user.UserAgreementListActivity;
import com.ums.eproject.activity.user.UserResetPWActivity;
import com.ums.eproject.app.AppContext;
import com.ums.eproject.bean.NETData;
import com.ums.eproject.bean.UserBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.CountDownTimerUtil;
import com.ums.eproject.utils.MsgUtil;

import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout login_account, linear_login_px, login_register, linear_login_confirm_pw, linear_login_verification, linear_login_bottom, linear_login_sub_bottom;
    private TextView login_account_text, login_register_text, login_account_text_line, login_register_text_line;
    private TextView login_desc, login_submit,login_send_msg_code,login_send_register_msg_code;
    private EditText login_confirm_pw, login_pw, login_phone, login_verification;
    private boolean isRegister;
    private Context context;
    private int submitType;
    private static final int sub_type_pw_login = 1;
    private static final int sub_type_code_login = 2;
    private static final int sub_type_register = 3;
    private ImageView tv_login_register_eyes,tv_login_pw_eyes;

    private CountDownTimerUtil mCountDownTimerUtilsCode;
    private CountDownTimerUtil mCountDownTimerUtilsRegister;

    private boolean isHidePw;
    private boolean isHideCifPw;

    private ImageView login_agreement_img;
    private boolean isAgree;//是否同意协议

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> { });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();
        if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        login_account = findViewById(R.id.login_account);
        linear_login_px = findViewById(R.id.linear_login_px);
        login_register = findViewById(R.id.login_register);
        login_account_text = findViewById(R.id.login_account_text);
        login_register_text = findViewById(R.id.login_register_text);
        login_account_text_line = findViewById(R.id.login_account_text_line);
        login_register_text_line = findViewById(R.id.login_register_text_line);
        login_desc = findViewById(R.id.login_desc);
        login_submit = findViewById(R.id.login_submit);
        linear_login_sub_bottom = findViewById(R.id.linear_login_sub_bottom);

        tv_login_pw_eyes = findViewById(R.id.tv_login_pw_eyes);
        tv_login_register_eyes = findViewById(R.id.tv_login_register_eyes);
        tv_login_pw_eyes.setOnClickListener(this);
        tv_login_register_eyes.setOnClickListener(this);

        login_verification = findViewById(R.id.login_verification);
        login_phone = findViewById(R.id.login_phone);
        login_pw = findViewById(R.id.login_pw);
        login_confirm_pw = findViewById(R.id.login_confirm_pw);

        linear_login_verification = findViewById(R.id.linear_login_verification);
        linear_login_confirm_pw = findViewById(R.id.linear_login_confirm_pw);
        linear_login_bottom = findViewById(R.id.linear_login_bottom);

        login_agreement_img = findViewById(R.id.login_agreement_img);
        login_agreement_img.setOnClickListener(this);
        login_agreement_img.setImageResource(R.mipmap.select);
        isAgree = false;

        login_account.setOnClickListener(this);
        login_register.setOnClickListener(this);
        login_submit.setOnClickListener(this);
        login_desc.setOnClickListener(this);
        findViewById(R.id.tv_login_yzm).setOnClickListener(this);
        findViewById(R.id.tv_login_pw).setOnClickListener(this);
        findViewById(R.id.tv_login_wjpw).setOnClickListener(this);
        login_send_msg_code = findViewById(R.id.login_send_msg_code);
        login_send_register_msg_code = findViewById(R.id.login_send_register_msg_code);
        login_send_msg_code.setOnClickListener(this);
        login_send_register_msg_code.setOnClickListener(this);
        String loginName = (String) SPUtils.getInstance().getString("login_name");

        login_phone.setText(loginName);

        clickAcc();
        setManualText();

        initEyes();

        permissionGranted();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();

        ImmersiveLayout.darkStatusBar(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_account: //密码登录页面
                clickAcc();
                break;
            case R.id.login_register://注册页面
                clickRegister();
                break;
            case R.id.login_submit:
                doSubmit();
                break;
            case R.id.tv_login_yzm://验证码登录页面
                showYzm();
                break;
            case R.id.tv_login_pw://密码登录页面
                showPw();
                break;
            case R.id.tv_login_wjpw://忘记密码
                UIHelp.startActivity(context, UserResetPWActivity.class);
                break;
            case R.id.login_send_msg_code:
                mCountDownTimerUtilsCode = new CountDownTimerUtil(login_send_msg_code, 60000, 1000);
                mCountDownTimerUtilsCode.start();
                sendMsgCode();
                break;
            case R.id.login_send_register_msg_code:
                mCountDownTimerUtilsRegister = new CountDownTimerUtil(login_send_register_msg_code, 60000, 1000);
                mCountDownTimerUtilsRegister.start();
                sendMsgCode();
                break;
            case R.id.tv_login_pw_eyes:
                showAndHidePw(isHidePw,tv_login_pw_eyes,login_pw,true);
                break;
            case R.id.tv_login_register_eyes:
                showAndHidePw(isHideCifPw,tv_login_register_eyes,login_confirm_pw,false);
                break;
            case R.id.login_desc:
                UIHelp.startActivity(context, UserAgreementListActivity.class);
                break;
            case R.id.login_agreement_img:
                setAgree();
                break;
        }
    }

    private void permissionGranted() {
        UMSPermissionUtil.requestPermission(new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {

            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void setAgree() {
        isAgree = !isAgree;
        if (isAgree){
            login_agreement_img.setImageResource(R.mipmap.selected);
        }else{
            login_agreement_img.setImageResource(R.mipmap.select);
        }
    }

    private void doSubmit() {
        String mobile = login_phone.getText().toString().trim();
        String code = login_verification.getText().toString().trim();
        String pwd = login_pw.getText().toString().trim();
        String confirmPwd = login_confirm_pw.getText().toString().trim();

        if (submitType == sub_type_pw_login){
            if(mobile.equals("19941566866")){
                loginByPwd(mobile,pwd);
                return;
            }
            if (StrUtil.isEmpty(mobile)){
                MsgUtil.showCustom(context,"请输入手机号");return;
            }
            if (StrUtil.isEmpty(pwd)){
                MsgUtil.showCustom(context,"请输入密码");return;
            }
            loginByPwd(mobile,pwd);
        }else if(submitType == sub_type_code_login){
            if (StrUtil.isEmpty(mobile)){
                MsgUtil.showCustom(context,"请输入手机号");return;
            }
            if (StrUtil.isEmpty(code)){
                MsgUtil.showCustom(context,"请输入验证码");return;
            }
            loginByMsg(mobile,code);
        }else if (submitType == sub_type_register){
            if (StrUtil.isEmpty(mobile)){
                MsgUtil.showCustom(context,"请输入手机号");return;
            }
            if (StrUtil.isEmpty(code)){
                MsgUtil.showCustom(context,"请输入验证码");return;
            }
            if (StrUtil.isEmpty(pwd)){
                MsgUtil.showCustom(context,"请输入密码");return;
            }
            if (StrUtil.isEmpty(confirmPwd)){
                MsgUtil.showCustom(context,"请输入确认密码");return;
            }
            if (!pwd.equals(confirmPwd)){
                MsgUtil.showCustom(context,"两次密码输入不一致");return;
            }
            if (!StrUtil.isComplexPassword(pwd)){
                MsgUtil.showCustom(context,"密码规则不符，必须包含数字、大写字符、小写字符和特殊符且不能少于8位");return;
            }
            if (!isAgree){
                MsgUtil.showCustom(context,"请阅读并同意相关协议");return;
            }

            memRegister(mobile,code,pwd);
        }
    }
    private void showYzm() {
        submitType = sub_type_code_login;

        login_send_msg_code.setVisibility(View.VISIBLE);
        login_send_register_msg_code.setVisibility(View.GONE);

        linear_login_verification.setVisibility(View.VISIBLE);
        linear_login_px.setVisibility(View.GONE);

        initEyes();
    }

    private void showPw() {
        submitType = sub_type_pw_login;

        login_send_msg_code.setVisibility(View.GONE);
        login_send_register_msg_code.setVisibility(View.GONE);

        linear_login_verification.setVisibility(View.GONE);
        linear_login_px.setVisibility(View.VISIBLE);

        initEyes();
    }


    private void clickAcc() {
        submitType = sub_type_pw_login;

        login_send_msg_code.setVisibility(View.GONE);
        login_send_register_msg_code.setVisibility(View.GONE);

        LinearLayout.LayoutParams params_click = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        login_account_text.setTextColor(0xff0869b6);
        login_register_text.setTextColor(0xff828282);

        login_account_text_line.setBackgroundColor(Color.parseColor("#0869B6"));
        login_register_text_line.setBackgroundColor(Color.parseColor("#828282"));

        login_account_text_line.setLayoutParams(params_click);
        login_register_text_line.setLayoutParams(params);

        linear_login_px.setVisibility(View.VISIBLE);
        linear_login_verification.setVisibility(View.GONE);
        linear_login_confirm_pw.setVisibility(View.GONE);

        login_verification.setText("");
        login_pw.setText("");
        login_confirm_pw.setText("");

        linear_login_bottom.setVisibility(View.GONE);
        linear_login_sub_bottom.setVisibility(View.VISIBLE);
        login_submit.setText("登录");

        isRegister = false;

        initEyes();
    }

    private void clickRegister() {
        submitType = sub_type_register;

        login_send_msg_code.setVisibility(View.GONE);
        login_send_register_msg_code.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams params_click_register = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        LinearLayout.LayoutParams params_register = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        login_register_text.setTextColor(0xff0869b6);
        login_account_text.setTextColor(0xff828282);

        login_register_text_line.setBackgroundColor(Color.parseColor("#0869B6"));
        login_account_text_line.setBackgroundColor(Color.parseColor("#828282"));

        login_register_text_line.setLayoutParams(params_click_register);
        login_account_text_line.setLayoutParams(params_register);

        linear_login_px.setVisibility(View.VISIBLE);
        linear_login_verification.setVisibility(View.VISIBLE);
        linear_login_confirm_pw.setVisibility(View.VISIBLE);

        login_verification.setText("");
        login_pw.setText("");
        login_confirm_pw.setText("");

        linear_login_bottom.setVisibility(View.VISIBLE);
        linear_login_sub_bottom.setVisibility(View.GONE);

        login_submit.setText("注册");

        isRegister = true;

        initEyes();
    }
    //发送短信请求
    private void sendMsgCode() {
        String mobile = login_phone.getText().toString().trim();
        String purpose = Constant.purpose_new_register;
        if (submitType == sub_type_code_login){
            purpose = Constant.purpose_code_login;
        }else if (submitType == sub_type_register){
            purpose = Constant.purpose_new_register;
        }

        CommRequestApi.getInstance().sendMsgCode(mobile,purpose,new HttpSubscriber<>(new SubscriberOnListener<NETData>() {
            @Override
            public void onSucceed(NETData data) {
                if (data.getCode() == 200){

                }else{
                    MsgUtil.showCustom(context,data.getMessage());
                    if (submitType == sub_type_code_login){
                        mCountDownTimerUtilsCode.onFinish();
                    }else if (submitType == sub_type_register){
                        mCountDownTimerUtilsRegister.onFinish();
                    }
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();
                if (submitType == sub_type_code_login){
                    mCountDownTimerUtilsCode.onFinish();
                }else if (submitType == sub_type_register){
                    mCountDownTimerUtilsRegister.onFinish();
                }
            }
        }, context));
    }
    //注册
    private void memRegister(String mobile,String code,String passwd) {
        CommRequestApi.getInstance().memRegister(mobile,code,passwd,new HttpSubscriber<>(new SubscriberOnListener<NETData>() {
            @Override
            public void onSucceed(NETData data) {
                if (data.getCode() == 200){
                    SPUtils.getInstance().put("login_name",mobile);
                    MsgUtil.showCustom(context,"注册成功");
                    clickAcc();
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
    //密码登录
    private void loginByPwd(String mobile,String passwd) {
        CommRequestApi.getInstance().loginByPwd(mobile,passwd,new HttpSubscriber<>(new SubscriberOnListener<UserBean>() {
            @Override
            public void onSucceed(UserBean data) {
                if (data.getCode() == 200){
                    loginSuccess(data,mobile);
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
    //验证码登录
    private void loginByMsg(String mobile,String code) {
        CommRequestApi.getInstance().loginByMsg(mobile,code,new HttpSubscriber<>(new SubscriberOnListener<UserBean>() {
            @Override
            public void onSucceed(UserBean data) {
                if (data.getCode() == 200){
                    loginSuccess(data,mobile);

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

    //登录成功后的处理
    private void loginSuccess(UserBean userBean,String mobile){

        SPUtils.getInstance().put("login_name",mobile);

        SPUtils.getInstance().put("tokenReq",userBean.getData().getTokenHead()+userBean.getData().getToken());
        SPUtils.getInstance().put("token",userBean.getData().getToken());
        SPUtils.getInstance().put("tokenHead",userBean.getData().getTokenHead());

        if (userBean.getData().getUserInfo().getIsVerified() == 0){   //0未实名  1已实名 todo
            Bundle bundle = new Bundle();
            bundle.putSerializable("auditTypeVal",Constant.AUDIT_TYPE_LOGIN_AUDIT_VAL);
            UIHelp.startActivity(context,AuthenticationActivity.class,bundle);
        }else{
            UIHelp.startActivity(context,MainActivity.class);
        }
        finish();

    }

    private void initEyes(){
        isHidePw = true;
        isHideCifPw = true;
        showAndHidePw(false,tv_login_pw_eyes,login_pw,true);
        showAndHidePw(false,tv_login_register_eyes,login_confirm_pw,false);
    }

    private void showAndHidePw(boolean isShow,ImageView iv,EditText ed_pw,boolean isPw){
        if (isShow) {
            iv.setImageResource(R.mipmap.open_eyes);
            HideReturnsTransformationMethod method_pw = HideReturnsTransformationMethod.getInstance();
            ed_pw.setTransformationMethod(method_pw);

            if (isPw){
                isHidePw = false;
            }else{
                isHideCifPw = false;
            }
        } else {
            iv.setImageResource(R.mipmap.eyes);
            TransformationMethod method_pw_hide = PasswordTransformationMethod.getInstance();
            ed_pw.setTransformationMethod(method_pw_hide);

            if (isPw){
                isHidePw = true;
            }else{
                isHideCifPw = true;
            }
        }
        // 光标的位置
        int index_cif = ed_pw.getText().toString().length();
        ed_pw.setSelection(index_cif);
    }

    private void setManualText() {
        Spanned sp = Html.fromHtml("<font color='#ff717171' size='12'>我已阅读并同意</font>" +
                "<font color='#0160AB' size='12'>《用户服务协议》</font>" +
                "<font color='#ff717171' size='12'>、</font>" +
                "<font color='#0160AB' size='12'>《隐私政策》</font>" +
                "<font color='#ff717171' size='12'>和</font>" +
                "<font color='#0160AB' size='12'>《余额免密支付协议》</font>");
        login_desc.setText(sp);
    }


}