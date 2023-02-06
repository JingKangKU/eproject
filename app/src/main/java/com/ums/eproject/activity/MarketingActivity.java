package com.ums.eproject.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ums.eproject.R;
import com.ums.eproject.adapter.MarketProductAdapter;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.MarketProductsBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MarketingActivity extends BaseActivity {
    private static final String TAG = MarketingActivity.class.getName();
    private RecyclerView productsRv;
    private MarketProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);
        productsRv = findViewById(R.id.rv_products);
        adapter = new MarketProductAdapter(MarketingActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        productsRv.setLayoutManager(gridLayoutManager);
        productsRv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMarketProducts();
    }

    private void getMarketProducts() {
        CommRequestApi.getInstance().queryMarketProducts(new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<MarketProductsBean>>() {
            @Override
            public void onSucceed(BaseRequest<MarketProductsBean> data) {
                try {
                    List<MarketProductsBean.MarketProductBean> datas = data.getData().getList();
                    adapter.setDatas(datas);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onSucceed: " + data.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }
}
