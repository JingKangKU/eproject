package com.ums.eproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ums.eproject.R;
import com.ums.eproject.bean.MarketProductsBean.MarketProductBean;

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
        viewHolder.getUnitTv().setText(data.getUnitName());

    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectTv, unitTv;
        private ImageView productIV;

        public TextView getSubjectTv() {
            return subjectTv;
        }

        public TextView getUnitTv() {
            return unitTv;
        }

        public ImageView getProductIV() {
            return productIV;
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
