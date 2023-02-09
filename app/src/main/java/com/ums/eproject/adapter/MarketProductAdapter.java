package com.ums.eproject.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ums.eproject.R;
import com.ums.eproject.activity.AddressModifyActivity;
import com.ums.eproject.activity.MarketingDetailsActivity;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.MarketProductsBean.MarketProductBean;
import com.ums.eproject.bean.MarketingDetailsBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.ScaleImageView;

import java.util.ArrayList;
import java.util.List;

public class MarketProductAdapter extends RecyclerView.Adapter {
    private List<MarketProductBean> datas;
    Context context;
    EditCallBackListener listener;

    public MarketProductAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public List<MarketProductBean> getDatas() {
        return datas;
    }

    public void setDatas(List<MarketProductBean> datas) {
        this.datas = datas;
    }
    public void addDatas(List<MarketProductBean> datas) {
        this.datas.addAll(datas);
    }

    public EditCallBackListener getListener() {
        return listener;
    }

    public void setListener(EditCallBackListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MarketProductBean data = datas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.getSubjectTv().setText(data.getSubjectCatogryName());
        viewHolder.getUnitTv().setText(data.getName());
        Glide.with(context).load(data.getPicUrl()).into(viewHolder.productIV);
        viewHolder.getItemview().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommRequestApi.getInstance().getMarketProductDetails(data.getId(),new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<MarketingDetailsBean>>() {
                    @Override
                    public void onSucceed(BaseRequest<MarketingDetailsBean> data) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", data.getData());
                        UIHelp.startActivity(context, MarketingDetailsActivity.class, bundle);
                    }

                    @Override
                    public void onError(int code, String msg) {

                    }
                },context));
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectTv, unitTv;
        private ScaleImageView productIV;

        public TextView getSubjectTv() {
            return subjectTv;
        }

        public TextView getUnitTv() {
            return unitTv;
        }

        public ScaleImageView getProductIV() {
            return productIV;
        }

        public View getItemview() {
            return itemView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTv = itemView.findViewById(R.id.tv_name_suject);
            unitTv = itemView.findViewById(R.id.tv_name_unit);
            productIV = itemView.findViewById(R.id.iv_product);
        }
    }

    public interface EditCallBackListener {
        void doAfterEdit();
    }
}
