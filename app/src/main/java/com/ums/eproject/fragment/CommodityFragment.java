package com.ums.eproject.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chinaums.common.utils.UMSScreenUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ums.eproject.R;
import com.ums.eproject.activity.AuthenticationActivity;
import com.ums.eproject.activity.GoodsDetailActivity;
import com.ums.eproject.adapter.GoodsAdapter;
import com.ums.eproject.adapter.GridDecoration;
import com.ums.eproject.adapter.RecyclerViewSpacesItemDecoration;
import com.ums.eproject.adapter.TopupAdapter;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 商品分类列表
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CommodityFragment extends Fragment {

    private PdtCategory.DataBean dataBean;
    private Context context;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView goods_recycler_view;
    private GoodsAdapter adapter;
    private int pageNum;
    private int pageSize;
    public CommodityFragment(PdtCategory.DataBean dataBean){
        this.dataBean = dataBean;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_commodity, container, false);
        refreshLayout = view.findViewById(R.id.goods_refreshLayout);
        goods_recycler_view = view.findViewById(R.id.goods_recycler_view);

        initRefreshLayout();


        initRecyclerView();
        pageNum = 1;
        pageSize = 10;
        queryProducts(pageNum,pageSize);
        return view;
    }

    private void initRefreshLayout(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() { //下拉刷新
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                pageNum = 1;
                queryProducts(pageNum,pageSize);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { //上拉加载更多
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pageNum ++;
                queryProducts(pageNum,pageSize);
            }
        });
        refreshLayout.setEnableNestedScroll(true);
    }

    private void initRecyclerView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL,false);
        goods_recycler_view.setLayoutManager(gridLayoutManager);
        goods_recycler_view.addItemDecoration(new GridDecoration(0, 15, 2));

        adapter = new GoodsAdapter(context,new ArrayList<>());
        adapter.setClickListener(new GoodsAdapter.ClickListenerInterface() {
            @Override
            public void doClick(long id) {
//                MsgUtil.showCustom(context,id+"");

                Bundle bundle = new Bundle();
                bundle.putLong("goodsId",id);
                UIHelp.startActivity(context,GoodsDetailActivity.class,bundle);

            }
        });
        goods_recycler_view.setAdapter(adapter);

    }


    private void refreshNotifyRecyclerView(List<ProductsBean.DataBean.ListBean> list){
        adapter.getListData().clear();
        adapter.addListData(list);
        adapter.notifyDataSetChanged();
    }

    private void addNotifyRecyclerView(List<ProductsBean.DataBean.ListBean> list) {
        adapter.addListData(list);
        adapter.notifyItemRangeChanged(adapter.getListData().size()-10,adapter.getListData().size());
    }

    private void queryProducts(int pageNum,int pageSize) {

        boolean isHidden = this.isHidden();
        if (isHidden){
            return;
        }

        CommRequestApi.getInstance().queryProducts(dataBean.getId(), pageNum, pageSize, new HttpSubscriber<>(new SubscriberOnListener<ProductsBean>() {
            @Override
            public void onSucceed(ProductsBean data) {
                if (data.getCode() == 200) {
                    setPageNum(data.getData().getPageNum());
                    if (data.getData().getList().size() <= 0){ //无数据状态

                        if (pageNum == 1){
//                            MsgUtil.showCustom(context,"暂无数据");
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
        }, context,false));
    }

    private void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public CharSequence getTitle(){
        return dataBean.getName();
    }


}