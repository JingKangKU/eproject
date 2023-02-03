package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ums.eproject.R;

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout title_view, title_right;
    private TextView title_text;
    private ImageView backIv;
    private RecyclerView addressRv;
    private LinearLayout addll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        title_view = findViewById(R.id.title_view);
        backIv = findViewById(R.id.title_back);
        backIv.setOnClickListener(this);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("收获地址");

        addressRv = findViewById(R.id.rv_address);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        addressRv.setLayoutManager(lm);



        addll = findViewById(R.id.ll_address_add);
        addll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
