package com.ums.eproject.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ums.eproject.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TravelDetailFragment extends Fragment {
    private int paddingTop;
    private TextView cardTypeTv, travelTv, billAmtTv, payAmtTv;

    public TravelDetailFragment(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_details, container, false);

        LinearLayout code_title = view.findViewById(R.id.code_title);
        initView(view);
        code_title.setPadding(0, paddingTop, 0, 0);
        return view;
    }

    private void initView(View view) {
        cardTypeTv = view.findViewById(R.id.tv_cardtype);
        travelTv = view.findViewById(R.id.tv_travel_detail);
        billAmtTv = view.findViewById(R.id.tv_billamt);
        payAmtTv = view.findViewById(R.id.tv_payamt);
    }


}