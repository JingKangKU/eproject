package com.ums.eproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ums.eproject.R;
import com.ums.eproject.bean.HomeBean;
import com.ums.eproject.bean.SingleOption;
import com.ums.eproject.view.ResizableImageView;

import java.util.List;

public class HomeAbilityAdapter extends RecyclerView.Adapter<HomeAbilityAdapter.ViewHolder> {
    private List<HomeBean.DataBean.ListBean> funcList;
    private Context context;

    public HomeAbilityAdapter(Context context, List<HomeBean.DataBean.ListBean> funcList) {
        this.context = context;
        this.funcList = funcList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_ability, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeBean.DataBean.ListBean listBean = funcList.get(position);
        Glide.with(context)
                .load(listBean.getImageUrl())
                .into(holder.item_home_ability_img);
        holder.item_home_ability_txt.setText(listBean.getNavName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListenerInterface.doClick(listBean);
            }
        });
    }



    @Override
    public int getItemCount() {
        return funcList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_home_ability_img;

        TextView item_home_ability_txt;

        public ViewHolder(View view) {
            super(view);
            item_home_ability_img = view.findViewById(R.id.item_home_ability_img);
            item_home_ability_txt = view.findViewById(R.id.item_home_ability_txt);
        }


    }



    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void doClick(HomeBean.DataBean.ListBean listBean);
    }
    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }
}
