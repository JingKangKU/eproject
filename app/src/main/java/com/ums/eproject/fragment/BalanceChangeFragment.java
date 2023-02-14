package com.ums.eproject.fragment;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ums.eproject.R;
import com.ums.eproject.adapter.BalanceChangeAdapter;
import com.ums.eproject.adapter.UserOrderAdapter;
import com.ums.eproject.bean.BookBalance;
import com.ums.eproject.bean.CommonBean;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.view.ReItemDecoration;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 单一类型余额记录
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BalanceChangeFragment extends BaseLazyFragment {

    private String fragmentTitle;
    private Integer incomeType;
    private Context context;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView balance_recycler_view;
    private BalanceChangeAdapter adapter;
    private int pageNum;
    private int pageSize;

    public BalanceChangeFragment(String fragmentTitle, Integer incomeType){
        this.fragmentTitle = fragmentTitle;
        this.incomeType = incomeType;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_balance_change;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        context = getActivity();
        pageNum = 1;
        pageSize = 10;
        refreshLayout = view.findViewById(R.id.goods_refreshLayout);
        balance_recycler_view = view.findViewById(R.id.balance_recycler_view);
        initRefreshLayout();
        initRecyclerView();
    }



    @Override
    protected void initData() {
        super.initData();
        queryBookBalance(pageNum,pageSize);
    }

    //对外部Activity 提供刷新刷新
    public void refreshData(){
        queryBookBalance(1,pageSize);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
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

        adapter = new BalanceChangeAdapter(context,new ArrayList<>());

        //添加分隔线
//        balance_recycler_view.addItemDecoration(new ReItemDecoration(context, ReItemDecoration.VERTICAL_LIST));
        balance_recycler_view.setAdapter(adapter);

    }
    private void refreshNotifyNoneRecyclerView(){ //无数据情况
        adapter.getListData().clear();
        adapter.notifyDataSetChanged();
    }

    private void refreshNotifyRecyclerView(List<BookBalance.DataBean.ListBean> list){
        adapter.getListData().clear();
        adapter.addListData(list);
        adapter.notifyDataSetChanged();
    }

    private void addNotifyRecyclerView(List<BookBalance.DataBean.ListBean> list) {
        adapter.addListData(list);
        adapter.notifyItemRangeChanged(adapter.getListData().size()-10,adapter.getListData().size());
    }

    private void queryBookBalance(int pageNum,int pageSize) {

        boolean isHidden = this.isHidden();
        if (isHidden){
            return;
        }

        CommRequestApi.getInstance().queryBookBalance(incomeType, pageNum, pageSize, new HttpSubscriber<>(new SubscriberOnListener<BookBalance>() {
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
                            refreshNotifyRecyclerView( data.getData().getList() );
                        }else{
                            addNotifyRecyclerView( data.getData().getList() );
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

    public CharSequence getTitle(){ return fragmentTitle; }


    private  ClickOrderItemListenerInterface clickOrderItemListenerInterface;
    public interface ClickOrderItemListenerInterface {
        void doClick(long id);
    }
    public void setClickOrderItemListenerInterface(ClickOrderItemListenerInterface clickOrderItemListenerInterface) {
        this.clickOrderItemListenerInterface = clickOrderItemListenerInterface;
    }
}