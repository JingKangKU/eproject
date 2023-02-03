package com.ums.eproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.ums.eproject.R;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.view.ScaleImageView;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private Context context;
    public List<ProductsBean.DataBean.ListBean> listData;
    public GoodsAdapter(Context context, List<ProductsBean.DataBean.ListBean> listData) {
        this.listData = listData;
        this.context = context;
    }
    public void addListData(List<ProductsBean.DataBean.ListBean> list){
        listData.addAll(list);
    }
    public List<ProductsBean.DataBean.ListBean> getListData(){
        return listData;
    }

    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductsBean.DataBean.ListBean listBean = listData.get(position);
        holder.item_goods_pic.setText(String.valueOf(listBean.getPrice()));
        holder.item_goods_title.setText(listBean.getName());
        Glide.with(context).load(listBean.getPicUrl()).into(holder.item_goods_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerInterface.doClick(listBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ScaleImageView item_goods_image;
        TextView item_goods_title,item_goods_pic;


        public ViewHolder(View view) {
            super(view);
            item_goods_pic = view.findViewById(R.id.item_goods_pic);
            item_goods_title = view.findViewById(R.id.item_goods_title);
            item_goods_image = view.findViewById(R.id.item_goods_image);

        }

    }
    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void doClick(long id);
    }
    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }
}
