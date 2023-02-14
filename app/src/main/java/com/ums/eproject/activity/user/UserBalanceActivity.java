package com.ums.eproject.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.bean.BalanceBean;
import com.ums.eproject.bean.MemberBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.QRCodeUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.ResizableImageView;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class UserBalanceActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    private ResizableImageView user_one_code,user_two_code;
    private TextView user_balance_txt,user_info_balance_dis;

    private ImageView user_balance_eyes;

    private boolean isShowBalance;
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

        user_one_code = findViewById(R.id.user_one_code);
        user_two_code = findViewById(R.id.user_two_code);
        user_balance_txt = findViewById(R.id.user_balance_txt);
        user_info_balance_dis = findViewById(R.id.user_info_balance_dis);
        user_balance_eyes = findViewById(R.id.user_balance_eyes);
        user_balance_eyes.setOnClickListener(this);
        findViewById(R.id.user_balance_detail).setOnClickListener(this);
        findViewById(R.id.title_back).setOnClickListener(this);

        getAccountBalance();

        isShowBalance = false;
        setBalanceEyesAndData();
    }
    private void setBalanceEyesAndData(){
        if (isShowBalance){
            user_balance_txt.setVisibility(View.VISIBLE);
            user_info_balance_dis.setVisibility(View.GONE);
            user_balance_eyes.setImageResource(R.mipmap.black_eyes);
        }else{
            user_balance_txt.setVisibility(View.GONE);
            user_info_balance_dis.setVisibility(View.VISIBLE);
            user_balance_eyes.setImageResource(R.mipmap.eyes);
        }
    }

    private void createQRCode(BalanceBean data) {
        try {
            Bitmap bitmap1 = QRCodeUtil.CreateOneDCode(data.getData().getDynamicCode(),100,30);
            Bitmap bitmap2 = QRCodeUtil.CreateTwoDCode(data.getData().getDynamicCode(),300,300);
            user_one_code.setImageBitmap(bitmap1);
            user_two_code.setImageBitmap(bitmap2);
        } catch (WriterException e) {
            e.printStackTrace();
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
            case R.id.user_balance_detail:
                UIHelp.startActivity(context,UserBalanceChangeActivity.class);
                break;
            case R.id.user_balance_eyes:
                isShowBalance = !isShowBalance;
                setBalanceEyesAndData();
                break;
        }
    }
    private void setBalanceData(BalanceBean data) {

        user_balance_txt.setText(String.valueOf(data.getData().getBalance()));

    }
    private void getAccountBalance() {
        CommRequestApi.getInstance().getAccountBalance( new HttpSubscriber<>(new SubscriberOnListener<BalanceBean>() {
            @Override
            public void onSucceed(BalanceBean data) {
                if (data.getCode() == 200) {
                    setBalanceData(data);
                    createQRCode(data);
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