package com.ums.eproject.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ums.eproject.R;
import com.ums.eproject.adapter.MarketProductAdapter;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.MarketProductsBean;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MarketingActivity extends BaseActivity {
    private static final String TAG = MarketingActivity.class.getName();
    private RecyclerView productsRv;
    private MarketProductAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private int pageNum;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);
        productsRv = findViewById(R.id.rv_products);
        adapter = new MarketProductAdapter(MarketingActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        productsRv.setLayoutManager(gridLayoutManager);
        productsRv.setAdapter(adapter);
        refreshLayout = findViewById(R.id.srfl_marketing);
        pageNum = 1;
        pageSize = 10;
        initRefreshLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMarketProducts(pageNum, pageSize);
    }

    private void getMarketProducts(final int pageNum, int pageSize) {
        CommRequestApi.getInstance().queryMarketProducts(pageNum, pageSize, new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<MarketProductsBean>>() {
            @Override
            public void onSucceed(BaseRequest<MarketProductsBean> data) {
                if (data.getCode() != 200) {
                    MsgUtil.showCustom(context, data.getMessage());
                    refreshLayout.finishRefresh(false);
                    refreshLayout.finishLoadMore(false);
                    resetpageNum(pageNum);
                } else {
                    List<MarketProductsBean.MarketProductBean> datas = data.getData().getList();
                    if (null != datas & datas.size() > 0) {
                        if (pageNum == 1) {
                            refreshNotifyRecyclerView(datas);
                        } else {
                            addNotifyRecyclerView(datas);
                        }
                        refreshLayout.finishRefresh(true);
                        refreshLayout.finishLoadMore(true);
                    } else {
                        refreshLayout.finishRefresh(false);
                        refreshLayout.finishLoadMore(true);
                    }

                    Log.d(TAG, "onSucceed: " + data.toString());
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
                resetpageNum(pageNum);
            }
        }, context));
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() { //下拉刷新
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                pageNum = 1;
                getMarketProducts(pageNum, pageSize);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { //上拉加载更多
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pageNum++;
                getMarketProducts(pageNum, pageSize);
            }
        });
        refreshLayout.setEnableNestedScroll(true);
    }

    private void resetpageNum(int pageNum) {
        this.pageNum = pageNum--;
    }

    private void refreshNotifyRecyclerView(List<MarketProductsBean.MarketProductBean> list) {
        adapter.getDatas().clear();
        adapter.addDatas(list);
        adapter.notifyDataSetChanged();
    }

    private void addNotifyRecyclerView(List<MarketProductsBean.MarketProductBean> list) {
        adapter.addDatas(list);
        adapter.notifyItemRangeChanged(adapter.getDatas().size() - 10, adapter.getDatas().size());
    }
}
