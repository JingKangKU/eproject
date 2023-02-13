package com.ums.eproject.fragment;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ums.eproject.R;
import com.ums.eproject.activity.GoodsDetailActivity;
import com.ums.eproject.activity.user.UserOrderDetailActivity;
import com.ums.eproject.adapter.GoodsAdapter;
import com.ums.eproject.adapter.GridDecoration;

import com.ums.eproject.adapter.UserOrderAdapter;
import com.ums.eproject.bean.CommonBean;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.bean.OrderDetailBean;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

/**
 * 商品分类列表
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseLazyFragment {

    private String fragmentTitle;
    private String orderStatus;
    private Context context;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView order_recycler_view;
    private UserOrderAdapter adapter;
    private int pageNum;
    private int pageSize;

    public OrderFragment(String fragmentTitle,String orderStatus){
        this.fragmentTitle = fragmentTitle;
        this.orderStatus = orderStatus;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_user_order;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        context = getActivity();
        pageNum = 1;
        pageSize = 10;
        refreshLayout = view.findViewById(R.id.goods_refreshLayout);
        order_recycler_view = view.findViewById(R.id.order_recycler_view);
        initRefreshLayout();
        initRecyclerView();
    }



    @Override
    protected void initData() {
        super.initData();
        queryOrders(pageNum,pageSize);
    }

    public void refreshData(){
        queryOrders(1,pageSize);
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
                queryOrders(pageNum,pageSize);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { //上拉加载更多
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pageNum ++;
                queryOrders(pageNum,pageSize);
            }
        });
        refreshLayout.setEnableNestedScroll(true);
    }

    private void initRecyclerView(){
        order_recycler_view.setLayoutManager(new LinearLayoutManager(context));

        adapter = new UserOrderAdapter(context,new ArrayList<>());
        adapter.setClickListener(new UserOrderAdapter.ClickListenerInterface() {
            @Override
            public void doClick(long id) {
                clickOrderItemListenerInterface.doClick(id);
            }
        });
        adapter.setCancelClickListener(new UserOrderAdapter.CancelClickListenerInterface() {
            @Override
            public void doClick(long id) {
                cancelOrder(id);
            }
        });
        order_recycler_view.setAdapter(adapter);

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

    private void queryOrders(int pageNum,int pageSize) {

        boolean isHidden = this.isHidden();
        if (isHidden){
            return;
        }

        CommRequestApi.getInstance().queryOrders(orderStatus, Constant.resp_mktType_ptg, pageNum, pageSize, new HttpSubscriber<>(new SubscriberOnListener<OrderBean>() {
            @Override
            public void onSucceed(OrderBean data) {
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

    private void cancelOrder(long orderId) {
        CommRequestApi.getInstance().cancelOrder(orderId, new HttpSubscriber<>(new SubscriberOnListener<CommonBean>() {
            @Override
            public void onSucceed(CommonBean data) {
                if (data.getCode() == 200) {
                    MsgUtil.showCustom(context,"已取消当前订单");
                    pageNum = 1;
                    queryOrders(pageNum,pageSize);
                } else {
                    MsgUtil.showCustom(context, data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

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