package com.ums.eproject.adapter;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ums.eproject.R;
import com.ums.eproject.app.AppContext;
import com.ums.eproject.bean.AddressBean;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter {
    private List<AddressBean> datas;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressBean.DataBean data = datas.get(position).data;
        ViewHolder viewHolder = (AddressAdapter.ViewHolder) holder;
        viewHolder.nameTv.setText(data.name);
        viewHolder.mobileTv.setText(data.mobile);
        viewHolder.addressTv.setText(data.provinceName + " " + data.getCityName() + " " + data.getCountyName() + " " + data.getDetailAddress());
        if (data.isDefault.equals("1")) {
            viewHolder.idDefaultTv.setVisibility(View.VISIBLE);
            viewHolder.isDefaultCb.setCompoundDrawables(AppContext.getContext().getResources().getDrawable(R.mipmap.selected),null,null,null);
        } else {
            viewHolder.idDefaultTv.setVisibility(View.INVISIBLE);
            viewHolder.isDefaultCb.setCompoundDrawables(AppContext.getContext().getResources().getDrawable(R.mipmap.select),null,null,null);

        }


        viewHolder.editIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv, mobileTv, idDefaultTv, addressTv;
        private TextView isDefaultCb, delBtn;
        private ImageButton editIb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            mobileTv = itemView.findViewById(R.id.tv_phone);
            idDefaultTv = itemView.findViewById(R.id.tv_default);
            addressTv = itemView.findViewById(R.id.rv_address);
            isDefaultCb = itemView.findViewById(R.id.tv_default_cb);
            delBtn = itemView.findViewById(R.id.tv_del);
            editIb = itemView.findViewById(R.id.ib_edit);
        }

        public TextView getNameTv() {
            return nameTv;
        }

        public TextView getMobileTv() {
            return mobileTv;
        }

        public TextView getAddressTv() {
            return addressTv;
        }

        public TextView getIsDefaultCb() {
            return isDefaultCb;
        }

        public TextView getDelBtn() {
            return delBtn;
        }

        public ImageButton getEditIb() {
            return editIb;
        }
    }
}
