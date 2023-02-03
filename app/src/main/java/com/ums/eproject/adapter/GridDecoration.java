package com.ums.eproject.adapter;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chinaums.common.utils.UMSScreenUtil;

public class GridDecoration extends RecyclerView.ItemDecoration {

    private final int padding;
    private final int space;
    private final int spanCount;

    public GridDecoration(int paddingDp, int spaceDp, @IntRange(from = 2) int spanCount) {
        this.padding = UMSScreenUtil.dp2px(paddingDp);
        this.space = UMSScreenUtil.dp2px(spaceDp);
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);

        RecyclerView.Adapter<?> adapter = parent.getAdapter();
        int itemCount = adapter == null ? 0 : adapter.getItemCount();

        boolean isLeft = pos % spanCount == 0;
        boolean isRight = (pos + 1) % spanCount == 0;

        boolean isTop = pos < spanCount;
        boolean isBottom = pos > (itemCount - spanCount - 1);

        outRect.left = isLeft ? padding : space / 2;
        outRect.right = isRight ? padding : space / 2;
        outRect.top = isTop ? padding : space / 2;
        outRect.bottom = isBottom ? padding : space / 2;
    }

}


