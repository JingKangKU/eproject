package com.ums.eproject.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.adapter.UserBalanceAdapter;
import com.ums.eproject.adapter.UserOrderAdapter;
import com.ums.eproject.bean.BalanceBean;
import com.ums.eproject.bean.BookBalance;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class UserBalanceChangeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    private SmartRefreshLayout refreshLayout;
    private RecyclerView balance_recycler_view;
    private UserBalanceAdapter adapter;

    private int pageNum;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_balance_change);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("余额记录查询");
        findViewById(R.id.title_back).setOnClickListener(this);

        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();

        refreshLayout = findViewById(R.id.refreshLayout);
        balance_recycler_view = findViewById(R.id.balance_recycler_view);

        pageNum = 1;
        pageSize = 10;

        initRefreshLayout();
        initRecyclerView();
    }

    private void initRefreshLayout(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() { //下拉刷新
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                pageNum = 1;
                queryBookBalance(pageNum,pageSize);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { //上拉加载更多
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pageNum ++;
                queryBookBalance(pageNum,pageSize);
            }
        });
        refreshLayout.setEnableNestedScroll(true);
    }

    private void initRecyclerView(){
        balance_recycler_view.setLayoutManager(new LinearLayoutManager(context));

        adapter = new UserBalanceAdapter(context,new ArrayList<>());
        adapter.setClickListener(new UserBalanceAdapter.ClickListenerInterface() {
            @Override
            public void doClick(long id) {

            }
        });
        adapter.setCancelClickListener(new UserBalanceAdapter.CancelClickListenerInterface() {
            @Override
            public void doClick(long id) {

            }
        });
        balance_recycler_view.setAdapter(adapter);

    }
    private void refreshNotifyNoneRecyclerView(){ //无数据情况
        adapter.getListData().clear();
        adapter.notifyDataSetChanged();
    }

    private void refreshNotifyRecyclerView(List<OrderBean.DataBean.ListBean> list){
        adapter.getListData().clear();
        adapter.addListData(list);
        adapter.notifyDataSetChanged();
    }

    private void addNotifyRecyclerView(List<OrderBean.DataBean.ListBean> list) {
        adapter.addListData(list);
        adapter.notifyItemRangeChanged(adapter.getListData().size()-10,adapter.getListData().size());
    }

    private void queryBookBalance(int pageNum,int pageSize) {

        CommRequestApi.getInstance().queryBookBalance(1,pageNum, pageSize, new HttpSubscriber<>(new SubscriberOnListener<BookBalance>() {
            @Override
            public void onSucceed(BookBalance data) {
                if (data.getCode() == 200) {
                    setPageNum(data.getData().getPageNum());
                    if (data.getData().getList().size() <= 0){ //无数据状态

                        if (pageNum == 1){
                            MsgUtil.showCustom(context,"暂无数据");
                            refreshNotifyNoneRecyclerView();
                        }
                        refreshLayout.finishRefresh(false);
                        refreshLayout.finishLoadMore(true);


                    }else{ //有数据状态

                        if (pageNum == 1){
//                            refreshNotifyRecyclerView( data.getData().getList() );
//                        }else{
//                            addNotifyRecyclerView( data.getData().getList() );
                        }

                        refreshLayout.finishRefresh(true);
                        refreshLayout.finishLoadMore(true);
                    }

                } else {
                    MsgUtil.showCustom(context, data.getMessage());
                    refreshLayout.finishRefresh(false);
                    refreshLayout.finishLoadMore(false);
                    setPageNum(pageNum);
                }

            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
                setPageNum(pageNum);
            }
        }, context));
    }

    private void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
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