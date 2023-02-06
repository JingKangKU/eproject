package com.ums.eproject.fragment;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;
import com.ums.eproject.R;
import com.ums.eproject.activity.AuditWebViewActivity;
import com.ums.eproject.activity.CommonWebViewActivity;
import com.ums.eproject.activity.EasyparkActivity;
import com.ums.eproject.activity.MallActivity;
import com.ums.eproject.activity.MarketingActivity;
import com.ums.eproject.adapter.HomeAbilityAdapter;
import com.ums.eproject.adapter.HomeBottomAdapter;
import com.ums.eproject.adapter.TopupAdapter;
import com.ums.eproject.bean.DynamicLink;
import com.ums.eproject.bean.GoodsDetail;
import com.ums.eproject.bean.HomeBean;
import com.ums.eproject.bean.SingleOption;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.GlideUtil;
import com.ums.eproject.utils.MLog;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.HomeFuncView;
import com.ums.eproject.view.ResizableImageView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  implements View.OnClickListener {


    private Context context;
    private RecyclerView home_recycler_func_view,home_recycler_ability_view;

    private XBanner home_top_banner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();
        home_recycler_func_view = view.findViewById(R.id.home_recycler_func_view);
        home_recycler_ability_view = view.findViewById(R.id.home_recycler_ability_view);
        home_top_banner = view.findViewById(R.id.home_top_banner);

        getHomePage();

        return view;

    }

    private void setBannerData(List<HomeBean.DataBean.ListBean> list) {
        List<BaseBannerInfo> imageUrl = new ArrayList<>();

        if (list.size()>0){
            for(int i = 0 ; i < list.size() ; i ++){
                int finalI = i;
                imageUrl.add(new BaseBannerInfo() {
                    @Override
                    public Object getXBannerUrl() {
                        return list.get(finalI).getImageUrl();
                    }

                    @Override
                    public String getXBannerTitle() {
                        return "";
                    }
                });
            }
        }

        home_top_banner.setBannerData(R.layout.layout_banner_home_top,imageUrl);
        home_top_banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                ((ImageView)view).setScaleType(ImageView.ScaleType.FIT_XY);
//                ((ImageView)view).setAdjustViewBounds(true);
                Glide.with(context).load(imageUrl.get(position).getXBannerUrl()).into((ImageView) view);
            }
        });
        home_top_banner.setPageTransformer(Transformer.Default);//横向移动


        home_top_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                //点击事件

            }
        });
    }
    private void initAbilityRecyclerView(List<HomeBean.DataBean.ListBean> funcList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL,false);
        home_recycler_ability_view.setLayoutManager(gridLayoutManager);
        HomeAbilityAdapter adapter = new HomeAbilityAdapter(context,funcList);
        adapter.setClickListener(new HomeAbilityAdapter.ClickListenerInterface() {
            @Override
            public void doClick(HomeBean.DataBean.ListBean listBean) {
                doItemClick(listBean,null);
            }
        });
        home_recycler_ability_view.setAdapter(adapter);
    }
    private void initFuncRecyclerView(List<HomeBean.DataBean.ListBean> funcList) {
        home_recycler_func_view.setLayoutManager(new LinearLayoutManager(context));
        HomeBottomAdapter adapter = new HomeBottomAdapter(context,funcList);
        adapter.setClickListener(new HomeBottomAdapter.ClickListenerInterface() {
            @Override
            public void doClick(HomeBean.DataBean.ListBean.DetailsBean detailsBean) {
                doItemClick(null,detailsBean);
            }
        });
        home_recycler_func_view.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {

    }
    private void getHomePage() {
        CommRequestApi.getInstance().getHomePage(new HttpSubscriber<>(new SubscriberOnListener<HomeBean>() {
            @Override
            public void onSucceed(HomeBean data) {
                if (data.getCode() == 200){
//                    loopFuncLinear(data);
                    setBannerData(data.getData().get(0).getList());
                    initAbilityRecyclerView(data.getData().get(1).getList());
                    initFuncRecyclerView(data.getData().get(2).getList());
                }else{
                    MsgUtil.showCustom(getActivity(),data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }
    private void getDynamicLink(String linkUrl,String navName) {
        CommRequestApi.getInstance().getDynamicLink(linkUrl,new HttpSubscriber<>(new SubscriberOnListener<DynamicLink>() {
            @Override
            public void onSucceed(DynamicLink data) {
                if (data.getCode() == 200){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url",data.getData().getLinkUrl());
                    bundle.putSerializable("titleText",navName);
                    UIHelp.startActivity(context, CommonWebViewActivity.class,bundle);
                }else{
                    MsgUtil.showCustom(getActivity(),data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }
    private void doItemClick(HomeBean.DataBean.ListBean listBean ,HomeBean.DataBean.ListBean.DetailsBean detailsBean){
        int linkType = 0;
        String linkUrl = "";
        String navName = "";
        if (listBean != null){
            linkType = listBean.getLinkType();
            linkUrl = listBean.getLinkUrl();
            navName = listBean.getNavName();
        }else{
            linkType = detailsBean.getLinkType();
            linkUrl = detailsBean.getLinkUrl();
            navName = detailsBean.getFuncName();
        }

        switch (linkType){
            case Constant.linkType.static_h5:
                Bundle bundle = new Bundle();
                bundle.putSerializable("url",linkUrl);
                bundle.putSerializable("titleText",navName);
                UIHelp.startActivity(context, CommonWebViewActivity.class,bundle);
                break;
            case Constant.linkType.img:

                break;
            case Constant.linkType.app_page:
                if (linkUrl.equals(Constant.linkApp.shopping)){
                    UIHelp.startActivity(context, MallActivity.class);
                }else if(linkUrl.equals(Constant.linkApp.marketing)) { //全民营销
                    UIHelp.startActivity(context, MarketingActivity.class);
                }
                break;
            case Constant.linkType.wx_app:
                break;
            case Constant.linkType.dynamic_app:
                getDynamicLink(linkUrl,navName);
                break;
        }
    }

//    //调整item的上下左右的间距大小
//    public void setItemSpace(){
//        RecyclerView.ItemDecoration decoration=new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
//                                       @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//
//                outRect.bottom = 28;
//
//            }
//        };
//        home_recycler_ability_view.addItemDecoration(decoration);
//    }
    @Override
    public void onStart() {
        super.onStart();
        if (home_top_banner!=null){
            home_top_banner.startAutoPlay();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (home_top_banner!=null){
            home_top_banner.stopAutoPlay();
        }

    }

    //待完善
    private void loopFuncLinear(HomeBean data){
        List<HomeBean.DataBean.ListBean> funcList =  data.getData().get(2).getList();

        if (!data.getData().get(2).getVisible().equals("1")) return; //"visible": "1" 显示

        //0 轮播 1功能 2底部动态布局
        for (int i = 0; i < funcList.size(); i++){
            HomeFuncView homeFuncView = new HomeFuncView(context);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置边距
            layoutParams.setMargins(0, 20, 20, 0);
            //将以上的属性赋给LinearLayout
            homeFuncView.setLayoutParams(layoutParams);

            homeFuncView.setFuncDataAndType(funcList.get(i), funcList.get(i).getGroupLayOutType());

            MLog.d("jingk------funcList.get(i).getGroupLayOutType()---"+funcList.get(i).getGroupLayOutType());

//            home_linear_loop_func.addView(homeFuncView);
//            TextView tv = new TextView(context);
//            tv.setText("阿松大稍等");
//            home_linear_loop_func.addView(tv);
        }
    }
}