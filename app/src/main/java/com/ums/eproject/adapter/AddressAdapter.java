package com.ums.eproject.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chinaums.common.utils.UMSStringUtil;
import com.ums.eproject.R;
import com.ums.eproject.activity.AddressModifyActivity;
import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.AesUtil;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddressAdapter extends RecyclerView.Adapter {
    private List<AddressBean> datas;
    Context context;
    EditCallBackListener listener;
    ItemClickListener itemClickListener;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public List<AddressBean> getDatas() {
        return datas;
    }

    public void setDatas(List<AddressBean> datas) {
        this.datas = datas;
    }

    public EditCallBackListener getListener() {
        return listener;
    }

    public void setListener(EditCallBackListener listener) {
        this.listener = listener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressBean data = datas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.nameTv.setText(data.name);
        viewHolder.mobileTv.setText(data.mobile);
        viewHolder.addressTv.setText((UMSStringUtil.isNotEmpty(data.provinceName) ? data.provinceName : "")
                + " "
                + (UMSStringUtil.isNotEmpty(data.getCityName()) ? data.getCityName() : "")
                + " "
                + (UMSStringUtil.isNotEmpty(data.getCountyName()) ? data.getCountyName() : "")
                + " "
                + data.getDetailAddress());
        if (data.isDefault.equals("1")) {
            viewHolder.idDefaultTv.setVisibility(View.VISIBLE);
            viewHolder.getDeafultIV().setImageResource(R.mipmap.selected);
        } else {
            viewHolder.idDefaultTv.setVisibility(View.INVISIBLE);
            viewHolder.getDeafultIV().setImageResource(R.mipmap.select);
        }

        viewHolder.getDeafultIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressBean bean = datas.get(position);
                bean.setIsDefault(bean.isDefault.equals("1") ? "0" : "1");
                bean.setMobile(AesUtil.encryptValue(bean.mobile));
                CommRequestApi.getInstance().saveAddress(bean, new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<String>>() {
                    @Override
                    public void onSucceed(BaseRequest<String> data) {
                        if (data.getCode() == 200) {
                            listener.doAfterEdit();
                        } else {
                            MsgUtil.showCustom(context, data.getMessage());
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

                    }
                }, context));
            }
        });


        viewHolder.editIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", data.getId().longValue());
                UIHelp.startActivity(context, AddressModifyActivity.class, bundle);
            }
        });
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommRequestApi.getInstance().delAddByID(data.getId(), new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<String>>() {
                    @Override
                    public void onSucceed(BaseRequest<String> bean) {
                        if (bean.getCode() == 200) {
//                            datas.remove(data);
//                            notifyDataSetChanged();
                            if (null != listener) {
                                listener.doAfterEdit();
                            }
                        } else {
                            MsgUtil.showCustom(context, bean.getMessage());
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

                    }
                }, context));
            }
        });

        // TODO: 2023/2/6 jk  条目点击返回
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(data);
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
        private ImageView deafultIV;
        private ImageButton editIb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            mobileTv = itemView.findViewById(R.id.tv_phone);
            idDefaultTv = itemView.findViewById(R.id.tv_default);
            addressTv = itemView.findViewById(R.id.tv_address);
            isDefaultCb = itemView.findViewById(R.id.tv_default_cb);
            deafultIV = itemView.findViewById(R.id.iv_deault);
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

        public ImageView getDeafultIV() {
            return deafultIV;
        }

        public TextView getDelBtn() {
            return delBtn;
        }

        public ImageButton getEditIb() {
            return editIb;
        }
    }

    public interface EditCallBackListener {
        void doAfterEdit();
    }

    public interface ItemClickListener {
        void itemClick(AddressBean addressBean);
    }
}
