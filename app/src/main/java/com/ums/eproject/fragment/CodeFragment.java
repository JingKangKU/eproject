package com.ums.eproject.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.fragment.app.Fragment;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CodeFragment extends Fragment {
    private int paddingTop;
    public CodeFragment(int paddingTop){
        this.paddingTop = paddingTop;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code, container, false);

        LinearLayout code_title = view.findViewById(R.id.code_title);

        code_title.setPadding(0,paddingTop,0,0);

        return view;
    }


}