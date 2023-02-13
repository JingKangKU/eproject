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
import com.ums.eproject.activity.AddressActivity;
import com.ums.eproject.activity.MainActivity;
import com.ums.eproject.activity.user.UserBalanceActivity;
import com.ums.eproject.activity.TopupActivity;
import com.ums.eproject.activity.user.UserOrderActivity;
import com.ums.eproject.activity.user.UserSettingActivity;
import com.ums.eproject.bean.MemberBean;
import com.ums.eproject.bean.OrderDetailBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment  implements View.OnClickListener {

    private LinearLayout ll_user_balance,user_topup,user_item_8,user_item_7,user_item_6,user_item_5,user_item_4,user_item_3,user_item_2;
    private Context context;
    private TextView user_info_balance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        user_topup = view.findViewById(R.id.user_topup);
        user_topup.setOnClickListener(this);
        view.findViewById(R.id.user_item_2).setOnClickListener(this);
        view.findViewById(R.id.user_item_3).setOnClickListener(this);
        view.findViewById(R.id.user_item_4).setOnClickListener(this);
        view.findViewById(R.id.user_item_address).setOnClickListener(this);
        view.findViewById(R.id.user_item_6).setOnClickListener(this);
        view.findViewById(R.id.user_item_7).setOnClickListener(this);
        view.findViewById(R.id.user_item_8).setOnClickListener(this);
        view.findViewById(R.id.ll_user_balance).setOnClickListener(this);
        user_info_balance = view.findViewById(R.id.user_info_balance);
        context = getActivity();
        getMemberDetails(true);

        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //首次加载不执行 此处调用网络加载,与onCreateView调用的网络加载不冲突
        if(!hidden){
            getMemberDetails(false);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_topup:
                UIHelp.startActivity(requireActivity(), TopupActivity.class);
                break;
            case R.id.user_item_2:
                break;
            case R.id.user_item_3:
                break;
            case R.id.user_item_4:
                UIHelp.startActivity(requireActivity(), UserOrderActivity.class);
                break;
            case R.id.user_item_address:
                UIHelp.startActivity(requireActivity(), AddressActivity.class);
                break;
            case R.id.user_item_6:
                break;
            case R.id.user_item_7:
                break;
            case R.id.user_item_8: //设置
                UIHelp.startActivity(requireActivity(), UserSettingActivity.class);
                break;
            case R.id.ll_user_balance:
                UIHelp.startActivity(requireActivity(), UserBalanceActivity.class);
                break;

        }
    }

    private void getMemberDetails(boolean isOpenDialog) {
        CommRequestApi.getInstance().getMemberDetails( new HttpSubscriber<>(new SubscriberOnListener<MemberBean>() {
            @Override
            public void onSucceed(MemberBean data) {
                if (data.getCode() == 200) {
                    ((MainActivity) getActivity()).setUserFragmentTitleInfo(data);
                    setUserInfoData(data);
                } else {
                    MsgUtil.showCustom(context, data.getMessage());

                }

            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context,isOpenDialog));
    }

    private void setUserInfoData(MemberBean data) {
        user_info_balance.setText(String.valueOf(data.getData().getBalance()));
    }
}