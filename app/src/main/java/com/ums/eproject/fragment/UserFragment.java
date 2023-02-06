package com.ums.eproject.fragment;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.ums.eproject.R;
import com.ums.eproject.activity.AddressActivity;
import com.ums.eproject.activity.user.UserBalanceActivity;
import com.ums.eproject.activity.TopupActivity;
import com.ums.eproject.utils.UIHelp;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment  implements View.OnClickListener {

    private LinearLayout ll_user_balance,user_topup,user_item_8,user_item_7,user_item_6,user_item_5,user_item_4,user_item_3,user_item_2;
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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_topup:
                UIHelp.startActivity(requireActivity(), TopupActivity.class);
                break;
            case R.id.user_item_2:
            case R.id.user_item_3:
            case R.id.user_item_4:
                break;
            case R.id.user_item_address:
                UIHelp.startActivity(requireActivity(), AddressActivity.class);
                break;
            case R.id.user_item_6:
            case R.id.user_item_7:
            case R.id.user_item_8:
            case R.id.ll_user_balance:
                UIHelp.startActivity(requireActivity(), UserBalanceActivity.class);
                break;
        }
    }
}