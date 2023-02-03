package com.ums.eproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ums.eproject.R;
import com.ums.eproject.activity.EasyparkActivity;
import com.ums.eproject.activity.MallActivity;
import com.ums.eproject.bean.HomeBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.HomeFuncView;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  implements View.OnClickListener {

    private LinearLayout home_go, parkLL,home_linear_loop_func;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        home_go = view.findViewById(R.id.home_go);
//        home_linear_loop_func = view.findViewById(R.id.home_linear_loop_func);
        parkLL= view.findViewById(R.id.ll_park);
        home_go.setOnClickListener(this);
        parkLL.setOnClickListener(this);
        context = getContext();

        getHomePage();

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_go:
                UIHelp.startActivity(requireActivity(), MallActivity.class);
                break;
            case R.id.ll_park:
                UIHelp.startActivity(requireActivity(), EasyparkActivity.class);
                break;
        }
    }

    private void getHomePage() {
        CommRequestApi.getInstance().getHomePage(new HttpSubscriber<>(new SubscriberOnListener<HomeBean>() {
            @Override
            public void onSucceed(HomeBean data) {
                if (data.getCode() == 200){
//                    loopFuncLinear(data);

                }else{
                    MsgUtil.showCustom(getActivity(),data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }


    //待完善
    private void loopFuncLinear(HomeBean data){
        List<HomeBean.DataBean.ListBean> funcList =  data.getData().get(2).getList();

        if (!data.getData().get(2).getVisible().equals("1")) return; //"visible": "1" 显示

        //0 轮播 1功能 2底部动态布局
        for (int i = 0; i < funcList.size(); i++){
            HomeFuncView homeFuncView = new HomeFuncView(context);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置边距
//            layoutParams.setMargins(54, 0, 84, 0);
            //将以上的属性赋给LinearLayout
            homeFuncView.setLayoutParams(layoutParams);

            homeFuncView.setFuncDataAndType(funcList.get(i), funcList.get(i).getGroupLayOutType());
            home_linear_loop_func.addView(homeFuncView);
            TextView tv = new TextView(context);
            tv.setText("阿松大稍等·");
            home_linear_loop_func.addView(tv);
        }
    }
}