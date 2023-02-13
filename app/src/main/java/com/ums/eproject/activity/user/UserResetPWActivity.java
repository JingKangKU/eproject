package com.ums.eproject.activity.user;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.tools.StringUtils;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.activity.CommonWebViewActivity;
import com.ums.eproject.activity.LoginActivity;
import com.ums.eproject.bean.NETData;
import com.ums.eproject.bean.ResetPwBean;
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

public class UserResetPWActivity extends BaseActivity implements View.OnClickListener {

    private EditText login_reset_phone,login_reset_verification,login_new_pw,login_confirm_pw;
    private TextView login_send_reset_px_code,login_reset_submit;
    private ImageView tv_login_new_pw_eyes,tv_login_register_eyes;

    private CountDownTimerUtil mCountDownTimerUtilsCode;

    private boolean isHidePw;
    private boolean isHideCifPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_pw);


        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);

        immersiveLayout.addAdapter(layout -> { });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();
        if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        login_reset_phone = findViewById(R.id.login_reset_phone);
        login_reset_verification = findViewById(R.id.login_reset_verification);
        login_new_pw = findViewById(R.id.login_new_pw);
        login_confirm_pw = findViewById(R.id.login_confirm_pw);

        login_reset_submit = findViewById(R.id.login_reset_submit);

        login_send_reset_px_code = findViewById(R.id.login_send_reset_px_code);
        tv_login_new_pw_eyes = findViewById(R.id.tv_login_new_pw_eyes);
        tv_login_register_eyes = findViewById(R.id.tv_login_register_eyes);


        login_send_reset_px_code.setOnClickListener(this);
        tv_login_new_pw_eyes.setOnClickListener(this);
        tv_login_register_eyes.setOnClickListener(this);
        login_reset_submit.setOnClickListener(this);

        initEyes();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_send_reset_px_code:
                sendMsgCode();
                break;
            case R.id.tv_login_new_pw_eyes:
                showAndHidePw(isHidePw,tv_login_new_pw_eyes,login_new_pw,true);
                break;
            case R.id.tv_login_register_eyes:
                showAndHidePw(isHideCifPw,tv_login_register_eyes,login_confirm_pw,false);
                break;
            case R.id.login_reset_submit:
                resetSubmit();
                break;
        }
    }

    private void resetSubmit() {
        String mobile = login_reset_phone.getText().toString().trim();
        String code = login_reset_verification.getText().toString().trim();
        String pwd = login_new_pw.getText().toString().trim();
        String confirmPwd = login_confirm_pw.getText().toString().trim();

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
       forgetPwd(mobile,pwd,code);
    }

    private void initEyes(){
        isHidePw = true;
        isHideCifPw = true;
        showAndHidePw(false,tv_login_new_pw_eyes,login_new_pw,true);
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
    //发送短信请求
    private void sendMsgCode() {
        String mobile = login_reset_phone.getText().toString().trim();
        if (StrUtil.isEmpty(mobile)){
            MsgUtil.showCustom(context,"请输入手机号");
            return;
        }
        mCountDownTimerUtilsCode = new CountDownTimerUtil(login_send_reset_px_code, 60000, 1000);
        mCountDownTimerUtilsCode.start();
        CommRequestApi.getInstance().sendMsgCode(mobile,Constant.purpose_pw_update,new HttpSubscriber<>(new SubscriberOnListener<NETData>() {
            @Override
            public void onSucceed(NETData data) {
                if (data.getCode() == 200){

                }else{
                    MsgUtil.showCustom(context,data.getMessage());
                    mCountDownTimerUtilsCode.onFinish();
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();
                mCountDownTimerUtilsCode.onFinish();
            }
        }, context));
    }
    //重置密码
    private void forgetPwd(String mobile,String passwd,String code) {
        CommRequestApi.getInstance().forgetPwd(mobile,passwd,code,new HttpSubscriber<>(new SubscriberOnListener<ResetPwBean>() {
            @Override
            public void onSucceed(ResetPwBean data) {
                if (data.getCode() == 200){
                    MsgUtil.showCustom(context,"密码修改成功");
                    UIHelp.startActivity(context, LoginActivity.class);
                    finish();
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
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 2023/2/10 待测试
        if (mCountDownTimerUtilsCode!=null){
            mCountDownTimerUtilsCode.onFinish();
        }
    }
}