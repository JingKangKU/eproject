package com.ums.eproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.adapter.AddressAdapter;
import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AddressActivity.class.getName();
    private LinearLayout title_view, title_right;
    private TextView title_text;
    private ImageView backIv;
    private RecyclerView addressRv;
    AddressAdapter adapter;
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
        title_text.setText("收货地址");

        addressRv = findViewById(R.id.rv_address);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        addressRv.setLayoutManager(lm);
        adapter = new AddressAdapter(AddressActivity.this);
        adapter.setListener(new AddressAdapter.EditCallBackListener() {
            @Override
            public void doAfterEdit() {
                getAllAddreses();
            }
        });

        addressRv.setAdapter(adapter);


        addll = findViewById(R.id.ll_address_add);
        addll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                UIHelp.startActivity(context, AddressModifyActivity.class, bundle);
            }
        });

        // TODO: 2023/2/6 jk 地址条目点击
        Bundle bundle = getIntent().getBundleExtra("bundle");
        adapter.setItemClickListener(new AddressAdapter.ItemClickListener() {
            @Override
            public void itemClick(AddressBean addressBean) {
                if (bundle !=null && bundle.getInt("startForOrder") == Constant.startForOrder){
                    Intent intent = getIntent();
                    intent.putExtra("addressBean", addressBean);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllAddreses();
        ImmersiveLayout.darkStatusBar(this); // TODO: 2023/2/6 jk 高亮顶部状态栏
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    public void getAllAddreses() {
        CommRequestApi.getInstance().queryAllAddes(new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<List<AddressBean>>>() {
            @Override
            public void onSucceed(BaseRequest<List<AddressBean>> data) {
                if (data.getCode() == 200) {
                    List<AddressBean> addressBeanList = data.getData();
                    if (addressBeanList != null && addressBeanList.size() > 0) {
                        adapter.setDatas(addressBeanList);
                        adapter.notifyDataSetChanged();
                    }
                    Log.d(TAG, "onSucceed: " + data.getMessage());
                } else {
                    MsgUtil.showCustom(AddressActivity.this, data.getMessage());
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }
}
