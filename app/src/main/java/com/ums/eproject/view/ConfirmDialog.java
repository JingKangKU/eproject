package com.ums.eproject.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ums.eproject.R;

public class ConfirmDialog extends Dialog implements View.OnClickListener {

    ImageView iv_close;//关闭图标
    TextView tv_title;//标题
    TextView tv_content;//内容
    TextView tv_cancle;//取消按钮
    TextView tv_confirm;//确定按钮
    private Context context;
    private OnAction onAction;//动作

    private boolean touchOutsideCancle = true;//点击空白对话框是否消失
    private String titleStr = "温馨提示";//对话框标题
    private String contentStr = "是否确认?";//对话框内容
    private String cancleStr = "取消";//取消按钮文字
    private String confirmStr = "确认";//确定按钮文字

    public ConfirmDialog(
            @NonNull Context context,
            boolean touchOutsideCancle,
            String titleStr,
            String contentStr,
            String cancleStr,
            String confirmStr,
            OnAction onAction) {
        super(context, R.style.CumstomDialog);
        this.context = context;
        this.touchOutsideCancle = touchOutsideCancle;
        this.titleStr = titleStr;
        this.contentStr = contentStr;
        this.cancleStr = cancleStr;
        this.confirmStr = confirmStr;
        this.onAction = onAction;
        init();
    }

    public ConfirmDialog(
            @NonNull Context context,
            String contentStr,
            OnAction onAction) {
        super(context, R.style.CumstomDialog);
        this.context = context;
        this.contentStr = contentStr;
        this.onAction = onAction;
        init();
    }
    public ConfirmDialog(
            @NonNull Context context,
            String contentStr) {
        super(context, R.style.CumstomDialog);
        this.context = context;
        this.contentStr = contentStr;
        init();
    }

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    private void init() {
        initView();
        initParam();
        settingWindow();
    }

    private void initParam() {
        tv_title.setText(titleStr);
        tv_content.setText(contentStr);
        tv_cancle.setText(cancleStr);
        tv_confirm.setText(confirmStr);
        setCanceledOnTouchOutside(touchOutsideCancle);
        iv_close.setVisibility(touchOutsideCancle ? View.VISIBLE : View.INVISIBLE);//如果不同意
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog_confirm, null);
        setContentView(view);

        iv_close = findViewById(R.id.iv_close);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_cancle = findViewById(R.id.tv_cancle);
        tv_confirm = findViewById(R.id.tv_confirm);

        iv_close.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    private void settingWindow() {
        Window dialogWindow = getWindow();
        assert dialogWindow != null;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高度
        lp.width = (int) (d.widthPixels * 0.9); // 宽度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_cancle:
                onAction.onCancleClicked(this);
                break;
            case R.id.tv_confirm:
                onAction.onConfirmClicked(this);
                break;
            default:
                break;
        }
    }

    public interface OnAction {
        void onCancleClicked(ConfirmDialog yesOrNoDialog);

        void onConfirmClicked(ConfirmDialog yesOrNoDialog);
    }
}
