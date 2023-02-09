package com.ums.eproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ums.eproject.R;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.bean.ProductsBean;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.view.ScaleImageView;
import com.ums.eproject.view.SquareImageView;

import org.w3c.dom.Text;

import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {
    private Context context;
    public List<OrderBean.DataBean.ListBean> listData;
    public UserOrderAdapter(Context context, List<OrderBean.DataBean.ListBean> listData) {
        this.listData = listData;
        this.context = context;
    }
    public void addListData(List<OrderBean.DataBean.ListBean> list){
        listData.addAll(list);
    }

    public List<OrderBean.DataBean.ListBean> getListData(){
        return listData;
    }

    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderBean.DataBean.ListBean listBean = listData.get(position);
        OrderBean.DataBean.ListBean.PdtLisBean pdtLisBean = listBean.getPdtLis().get(0);
        holder.user_order_no.setText(String.valueOf(listBean.getOrderId()));
        holder.user_order_type.setText(listBean.getOrderStatusName());
        Glide.with(context).load(pdtLisBean.getPicUrl()).into(holder.user_order_goods_img);
        holder.user_order_goods_name.setText(pdtLisBean.getProductName());
        holder.user_order_goods_price.setText(String.valueOf(pdtLisBean.getPrice()));
        holder.user_order_goods_quantity.setText(String.valueOf(pdtLisBean.getQuantity()));
        holder.user_order_goods_itemAmount.setText(String.valueOf(pdtLisBean.getItemAmount()));

        holder.user_order_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2023/2/9
                MsgUtil.showCustom(context,"调用订单删除或关闭");
            }
        });
        //待支付可关闭
        if (Constant.resp_orderStatus_dfk == listBean.getOrderStatus()){
            holder.user_order_del.setVisibility(View.VISIBLE);
        }else{
            holder.user_order_del.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerInterface.doClick(listBean.getOrderId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_order_no,user_order_type;
        TextView user_order_goods_name,user_order_goods_price,user_order_goods_quantity,user_order_goods_itemAmount;
        SquareImageView user_order_goods_img;
        TextView user_order_del;

        public ViewHolder(View view) {
            super(view);
            user_order_no = view.findViewById(R.id.user_order_no);
            user_order_type = view.findViewById(R.id.user_order_type);
            user_order_goods_name = view.findViewById(R.id.user_order_goods_name);
            user_order_goods_price = view.findViewById(R.id.user_order_goods_price);
            user_order_goods_quantity = view.findViewById(R.id.user_order_goods_quantity);
            user_order_goods_itemAmount = view.findViewById(R.id.user_order_goods_itemAmount);
            user_order_goods_img = view.findViewById(R.id.user_order_goods_img);
            user_order_del = view.findViewById(R.id.user_order_del);

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
