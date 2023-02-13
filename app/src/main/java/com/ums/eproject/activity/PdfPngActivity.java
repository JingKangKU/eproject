package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.view.ResizableImageView;

public class PdfPngActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private LinearLayout ll_pdf_png;

    private ResizableImageView pdf_png_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfpng);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("预览协议");
        findViewById(R.id.title_back).setOnClickListener(this);
        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();

        ll_pdf_png = findViewById(R.id.ll_pdf_png);
        pdf_png_img = findViewById(R.id.pdf_png_img);
        Glide.with(context).load("http://121.40.88.223:9080/configweb/openapi/file/202302/08/1d4e3fbfd627465f921dbb0c1ae0435a.jpg").into((ImageView) pdf_png_img);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }


}