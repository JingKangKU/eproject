package com.ums.eproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ums.eproject.R;
import com.ums.eproject.bean.HomeBean;

public class HomeFuncView extends LinearLayout {


    private HomeBean.DataBean.ListBean listBean;
    private int groupLayOutType;

    //组内布局类型 1: 普通; 2: 左一右二; 3: 左二右一
    public void setFuncDataAndType(HomeBean.DataBean.ListBean listBean, int groupLayOutType) {
        this.listBean = listBean;
        this.groupLayOutType = groupLayOutType;
    }

    public HomeFuncView(Context context) {
        super(context);

        initView(context);
    }

    public HomeFuncView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeFuncView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context){
        if (groupLayOutType == 1) {
            View view = View.inflate(context, R.layout.layout_home_func_1, this);
            ResizableImageView func_1_left_image = view.findViewById(R.id.func_1_left_image);
            ResizableImageView func_1_right_image = view.findViewById(R.id.func_1_right_image);
            TextView func_1_left_chn_txt = view.findViewById(R.id.func_1_left_chn_txt);
            TextView func_1_right_chn_txt = view.findViewById(R.id.func_1_right_chn_txt);

            Glide.with(context)
                    .load(listBean.getDetails().get(0).getImageUrl())
                    .into(func_1_left_image);
            Glide.with(context)
                    .load(listBean.getDetails().get(1).getImageUrl())
                    .into(func_1_right_image);
            func_1_left_chn_txt.setText(listBean.getDetails().get(0).getFuncName());
            func_1_right_chn_txt.setText(listBean.getDetails().get(0).getFuncName());

        }

        if (groupLayOutType == 2) {
            View view = View.inflate(context, R.layout.layout_home_func_2, this);
            ResizableImageView func_2_left_image = view.findViewById(R.id.func_2_left_image);
            ResizableImageView func_2_right_1_image = view.findViewById(R.id.func_2_right_1_image);
            ResizableImageView func_2_right_2_image = view.findViewById(R.id.func_2_right_2_image);
            TextView func_2_left_chn_tex = view.findViewById(R.id.func_2_left_chn_tex);
            TextView func_2_right_1_chn_txt = view.findViewById(R.id.func_2_right_1_chn_txt);
            TextView func_2_right_2_chn_txt = view.findViewById(R.id.func_2_right_2_chn_txt);

            Glide.with(context).load(listBean.getDetails().get(0).getImageUrl()).into(func_2_left_image);
            Glide.with(context).load(listBean.getDetails().get(1).getImageUrl()).into(func_2_right_1_image);
            Glide.with(context).load(listBean.getDetails().get(2).getImageUrl()).into(func_2_right_2_image);

            func_2_left_chn_tex.setText(listBean.getDetails().get(0).getFuncName());
            func_2_right_1_chn_txt.setText(listBean.getDetails().get(1).getFuncName());
            func_2_right_2_chn_txt.setText(listBean.getDetails().get(2).getFuncName());
        }

        if (groupLayOutType == 3) {
            View view = View.inflate(context, R.layout.layout_home_func_3, this);
            ResizableImageView func_3_left_1_image = view.findViewById(R.id.func_3_left_1_image);
            ResizableImageView func_3_left_2_image = view.findViewById(R.id.func_3_left_2_image);
            ResizableImageView func_3_right_image = view.findViewById(R.id.func_3_right_image);
            TextView func_3_left_1_chn_txt = view.findViewById(R.id.func_3_left_1_chn_txt);
            TextView func_3_left_2_chn_txt = view.findViewById(R.id.func_3_left_2_chn_txt);
            TextView func_3_right_chn_txt = view.findViewById(R.id.func_3_right_chn_txt);

            Glide.with(context)
                    .load(listBean.getDetails().get(0).getImageUrl())
                    .into(func_3_left_1_image);
            Glide.with(context)
                    .load(listBean.getDetails().get(1).getImageUrl())
                    .into(func_3_left_2_image);
            Glide.with(context)
                    .load(listBean.getDetails().get(2).getImageUrl())
                    .into(func_3_right_image);
            func_3_left_1_chn_txt.setText(listBean.getDetails().get(0).getFuncName());
            func_3_left_2_chn_txt.setText(listBean.getDetails().get(1).getFuncName());
            func_3_right_chn_txt.setText(listBean.getDetails().get(2).getFuncName());

        }
    }

}
