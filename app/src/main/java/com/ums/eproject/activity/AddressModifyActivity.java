package com.ums.eproject.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.ums.eproject.R;
import com.ums.eproject.bean.AddressBean;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.AesUtil;
import com.ums.eproject.utils.MsgUtil;

import es.dmoral.toasty.Toasty;

public class AddressModifyActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AddressModifyActivity.class.getName();
    private LinearLayout title_view, title_right;
    private TextView title_text, areaTv,right_tv;
    private ImageView backIv;
    private EditText nameEt, mobileEt, detailEt;
    RadioGroup labelRG;
    RadioButton homeRB, schoolRB, companyRB;
    CheckBox defaultCB;
    CityPickerView mPicker = new CityPickerView();
    String selectProvince;
    String selectCity;
    String selectDistrict;
    long paramId;
    AddressBean modifyBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_modify);
        Bundle bundle = this.getIntent().getBundleExtra("bundle");
        paramId = bundle.getLong("id");
        initTitle();
        if (0 != paramId) {
            getAddressById();
            title_text.setText("编辑收货地址");
        } else
            title_text.setText("新增收货地址");
        initView();
        initListener();
        mPicker.init(this);
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);
    }

    private void initTitle() {
        title_view = findViewById(R.id.title_view);
        backIv = findViewById(R.id.title_back);
        backIv.setOnClickListener(this);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.VISIBLE);
        title_right.setOnClickListener(this);
        title_text = findViewById(R.id.title_text);
        right_tv= findViewById(R.id.tv_right);
        right_tv.setText("保存");
    }

    private void initListener() {
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                selectProvince = province.getName();
                selectCity = city.getName();
                selectDistrict = district.getName();
                areaTv.setText(selectProvince + " " + selectCity + " " + selectDistrict);
            }
        });
        areaTv.setInputType(InputType.TYPE_NULL);
        areaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicker.showCityPicker();
            }
        });
        defaultCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    private void initView() {
        nameEt = findViewById(R.id.et_name);
        mobileEt = findViewById(R.id.et_mobile);
        areaTv = findViewById(R.id.et_area);
        detailEt = findViewById(R.id.et_details);
        labelRG = findViewById(R.id.rg_label);
        homeRB = findViewById(R.id.rb_home);
        schoolRB = findViewById(R.id.rb_school);
        companyRB = findViewById(R.id.rb_company);

        defaultCB = findViewById(R.id.cb_default);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                AddressBean dataBean = new AddressBean();
                if (paramId == 0)
                    dataBean.setId(null);
                else
                    dataBean.setId(paramId);
                dataBean.setName(nameEt.getText().toString().trim());
                dataBean.setMobile(AesUtil.encryptValue(mobileEt.getText().toString().trim()));
                dataBean.setProvinceName(selectProvince);
                dataBean.setCityName(selectCity);
                dataBean.setCountyName(selectDistrict);
                dataBean.setDetailAddress(detailEt.getText().toString().trim());
                dataBean.setIsDefault(defaultCB.isChecked() ? "1" : "0");
                CommRequestApi.getInstance().saveAddress(dataBean, new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<String>>() {
                    @Override
                    public void onSucceed(BaseRequest<String> data) {
                        if (data.getCode() == 200) {
                            Log.d(TAG, "onSucceed: " + data.getMessage());
                            AddressModifyActivity.this.finish();
                        } else {
                            MsgUtil.showCustom(AddressModifyActivity.this, data.getMessage());
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

                    }
                }, context));
                break;
        }
    }

    private void getAddressById() {
        CommRequestApi.getInstance().queryAddByID(paramId, new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<AddressBean>>() {
            @Override
            public void onSucceed(BaseRequest<AddressBean> data) {
                if (data.getCode() == 200) {
                    Log.d(TAG, "onSucceed: " + data.getMessage());
                    AddressBean bean = data.getData();
                    if (null != data) {
                        modifyBean = bean;
                        if (null != modifyBean) {
                            selectProvince = bean.getProvinceName();
                            selectCity = bean.getCityName();
                            selectDistrict = bean.getCountyName();
                            showData(modifyBean);
                        }
                    }
                } else {
                    MsgUtil.showCustom(AddressModifyActivity.this, data.getMessage());
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }

    private void showData(AddressBean modifyBean) {
        nameEt.setText(modifyBean.getName());
        mobileEt.setText(modifyBean.getMobile());
        areaTv.setText(selectProvince + " " + selectCity + " " + selectDistrict);
        detailEt.setText(modifyBean.getDetailAddress());
        defaultCB.setChecked("1".equals(modifyBean.isDefault) ? true : false);
    }
}
