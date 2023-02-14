package com.ums.eproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ums.eproject.R;
import com.ums.eproject.bean.BookBalance;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.view.ConfirmDialog;
import com.ums.eproject.view.ScaleImageView;

import java.util.List;

public class BalanceChangeAdapter extends RecyclerView.Adapter<BalanceChangeAdapter.ViewHolder> {
    private Context context;
    public List<BookBalance.DataBean.ListBean> listData;
    public BalanceChangeAdapter(Context context, List<BookBalance.DataBean.ListBean> listData) {
        this.listData = listData;
        this.context = context;
    }
    public void addListData(List<BookBalance.DataBean.ListBean> list){
        listData.addAll(list);
    }

    public List<BookBalance.DataBean.ListBean> getListData(){
        return listData;
    }

    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_change, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookBalance.DataBean.ListBean listBean = listData.get(position);

        holder.item_balance_tradeNote.setText(listBean.getTradeNote());
        holder.item_balance_tradeBalance.setText(String.valueOf(listBean.getTradeBalance()));
        holder.item_balance_tradeDatetime.setText(listBean.getTradeDatetime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_balance_tradeNote,item_balance_tradeBalance,item_balance_tradeDatetime;

        public ViewHolder(View view) {
            super(view);
            item_balance_tradeNote = view.findViewById(R.id.item_balance_tradeNote);
            item_balance_tradeBalance = view.findViewById(R.id.item_balance_tradeBalance);
            item_balance_tradeDatetime = view.findViewById(R.id.item_balance_tradeDatetime);
        }

    }
    //订单点击回调
    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void doClick(long id);
    }
    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    //取消订单回调
    private CancelClickListenerInterface cancelClickListenerInterface;
    public interface CancelClickListenerInterface {
        void doClick(long id);
    }
    public void setCancelClickListener(CancelClickListenerInterface cancelClickListenerInterface) {
        this.cancelClickListenerInterface = cancelClickListenerInterface;
    }
}
