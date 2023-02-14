package com.ums.eproject.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ums.eproject.R;
import com.ums.eproject.bean.SingleOption;
import com.ums.eproject.utils.MsgUtil;

import java.util.List;

public class TopupAdapter extends RecyclerView.Adapter<TopupAdapter.ViewHolder> {
    public List<SingleOption> topupList;
    public TopupAdapter(List<SingleOption> fruitList) {
        topupList = fruitList;
    }

    public List<SingleOption> getTopupList() {
        return topupList;
    }

    public void setTopupList(List<SingleOption> topupList) {
        this.topupList.addAll(topupList);
    }

    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topup, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SingleOption singleOption = topupList.get(position);
        holder.item_topup_amt.setText(singleOption.getSingleStr());
        String ss = singleOption.getSingleStr();

        if(singleOption.getSelectFlag().equals("2")){
            holder.item_topup_linear.setBackgroundResource(R.drawable.yellow_round_shape_10);
        }else {
            holder.item_topup_linear.setBackgroundResource(R.drawable.white_round_shape_10);
        }

        int iNew = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleSelect(iNew);
                clickListenerInterface.doClick(singleOption,iNew);
            }
        });
    }

    private void singleSelect(int position){
        for (int i = 0 ; i < topupList.size() ; i ++){
            topupList.get(i).setSelectFlag("1");
        }
        topupList.get(position).setSelectFlag("2");
        notifyDataSetChanged();
    }

    public void clearSelect(){
        for (int i = 0 ; i < topupList.size() ; i ++){
            topupList.get(i).setSelectFlag("1");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return topupList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item_topup_linear;
        TextView item_topup_amt;

        public ViewHolder(View view) {
            super(view);
            item_topup_amt = view.findViewById(R.id.item_topup_amt);
            item_topup_linear = view.findViewById(R.id.item_topup_linear);

        }

        public LinearLayout getItem_topup_linear() {
            return item_topup_linear;
        }
    }

    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void doClick(SingleOption singleOption,int position);
    }
    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }
}
