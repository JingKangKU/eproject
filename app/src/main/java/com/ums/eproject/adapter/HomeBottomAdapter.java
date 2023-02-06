package com.ums.eproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ums.eproject.R;
import com.ums.eproject.bean.HomeBean;
import com.ums.eproject.bean.SingleOption;
import com.ums.eproject.view.ResizableImageView;

import java.util.List;

public class HomeBottomAdapter extends RecyclerView.Adapter<HomeBottomAdapter.ViewHolder> {
    private List<HomeBean.DataBean.ListBean> funcList;
    private Context context;
    //组内布局类型 1: 普通; 2: 左一右二; 3: 左二右一
    public HomeBottomAdapter(Context context,List<HomeBean.DataBean.ListBean> funcList) {
        this.context = context;
        this.funcList = funcList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_func, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeBean.DataBean.ListBean listBean = funcList.get(position);

        if (listBean.getGroupLayOutType() == 1) {
            holder.home_func_item_1.setVisibility(View.VISIBLE);
            holder.home_func_item_2.setVisibility(View.GONE);
            holder.home_func_item_3.setVisibility(View.GONE);

            Glide.with(context)
                    .load(listBean.getDetails().get(0).getImageUrl())
                    .into(holder.func_1_left_image);
            Glide.with(context)
                    .load(listBean.getDetails().get(1).getImageUrl())
                    .into(holder.func_1_right_image);
            holder.func_1_left_chn_txt.setText(listBean.getDetails().get(0).getFuncName());
            holder.func_1_right_chn_txt.setText(listBean.getDetails().get(1).getFuncName());

            holder.func_1_left_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(0)));
            holder.func_1_right_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(1)));

        }

        if (listBean.getGroupLayOutType() == 2) {
            holder.home_func_item_1.setVisibility(View.GONE);
            holder.home_func_item_2.setVisibility(View.VISIBLE);
            holder.home_func_item_3.setVisibility(View.GONE);

            Glide.with(context).load(listBean.getDetails().get(0).getImageUrl()).into(holder.func_2_left_image);
            Glide.with(context).load(listBean.getDetails().get(1).getImageUrl()).into(holder.func_2_right_1_image);
            Glide.with(context).load(listBean.getDetails().get(2).getImageUrl()).into(holder.func_2_right_2_image);

            holder.func_2_left_chn_txt.setText(listBean.getDetails().get(0).getFuncName());
            holder.func_2_right_1_chn_txt.setText(listBean.getDetails().get(1).getFuncName());
            holder.func_2_right_2_chn_txt.setText(listBean.getDetails().get(2).getFuncName());

            holder.func_2_left_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(0)));
            holder.func_2_right_1_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(1)));
            holder.func_2_right_2_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(2)));
        }

        if (listBean.getGroupLayOutType() == 3) {
            holder.home_func_item_1.setVisibility(View.GONE);
            holder.home_func_item_2.setVisibility(View.GONE);
            holder.home_func_item_3.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(listBean.getDetails().get(0).getImageUrl())
                    .into(holder.func_3_left_1_image);
            Glide.with(context)
                    .load(listBean.getDetails().get(1).getImageUrl())
                    .into(holder.func_3_left_2_image);
            Glide.with(context)
                    .load(listBean.getDetails().get(2).getImageUrl())
                    .into(holder.func_3_right_image);
            holder.func_3_left_1_chn_txt.setText(listBean.getDetails().get(0).getFuncName());
            holder.func_3_left_2_chn_txt.setText(listBean.getDetails().get(1).getFuncName());
            holder.func_3_right_chn_txt.setText(listBean.getDetails().get(2).getFuncName());

            holder.func_3_left_1_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(0)));
            holder.func_3_left_2_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(1)));
            holder.func_3_right_image.setOnClickListener(v -> clickListenerInterface.doClick(listBean.getDetails().get(2)));
        }

    }



    @Override
    public int getItemCount() {
        return funcList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout home_func_item_1,home_func_item_2,home_func_item_3;

        ResizableImageView func_1_left_image,func_1_right_image;
        TextView func_1_left_chn_txt,func_1_right_chn_txt;

        ResizableImageView func_2_left_image,func_2_right_1_image,func_2_right_2_image;
        TextView func_2_left_chn_txt,func_2_right_1_chn_txt,func_2_right_2_chn_txt;

        ResizableImageView func_3_left_1_image,func_3_left_2_image,func_3_right_image;
        TextView func_3_left_1_chn_txt,func_3_left_2_chn_txt,func_3_right_chn_txt;

        public ViewHolder(View view) {
            super(view);
            home_func_item_1 = view.findViewById(R.id.home_func_item_1);
            home_func_item_2 = view.findViewById(R.id.home_func_item_2);
            home_func_item_3 = view.findViewById(R.id.home_func_item_3);

            func_1_left_image = view.findViewById(R.id.func_1_left_image);
            func_1_right_image = view.findViewById(R.id.func_1_right_image);
            func_1_left_chn_txt = view.findViewById(R.id.func_1_left_chn_txt);
            func_1_right_chn_txt = view.findViewById(R.id.func_1_right_chn_txt);

            func_2_left_image = view.findViewById(R.id.func_2_left_image);
            func_2_right_1_image = view.findViewById(R.id.func_2_right_1_image);
            func_2_right_2_image = view.findViewById(R.id.func_2_right_2_image);
            func_2_left_chn_txt = view.findViewById(R.id.func_2_left_chn_txt);
            func_2_right_1_chn_txt = view.findViewById(R.id.func_2_right_1_chn_txt);
            func_2_right_2_chn_txt = view.findViewById(R.id.func_2_right_2_chn_txt);

            func_3_left_1_image = view.findViewById(R.id.func_3_left_1_image);
            func_3_left_2_image = view.findViewById(R.id.func_3_left_2_image);
            func_3_right_image = view.findViewById(R.id.func_3_right_image);
            func_3_left_1_chn_txt = view.findViewById(R.id.func_3_left_1_chn_txt);
            func_3_left_2_chn_txt = view.findViewById(R.id.func_3_left_2_chn_txt);
            func_3_right_chn_txt = view.findViewById(R.id.func_3_right_chn_txt);

        }


    }



    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void doClick(HomeBean.DataBean.ListBean.DetailsBean detailsBean);
    }
    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }
}
