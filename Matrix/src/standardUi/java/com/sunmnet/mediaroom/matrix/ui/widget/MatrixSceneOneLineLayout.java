package com.sunmnet.mediaroom.matrix.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.ui.MarqueTextView;

import java.util.List;

/**
 * Create by WangJincheng on 2021-10-20
 */
public class MatrixSceneOneLineLayout extends LinearLayout {

    public static final int UNSELECT_ALL = -1;

    // 默认一行4列
    private int maxItemNum = 4;

    // item的点击监听
    private ItemClickListener itemClickListener;

    public MatrixSceneOneLineLayout(Context context) {
        super(context);
        init();
    }

    public MatrixSceneOneLineLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixSceneOneLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MatrixSceneOneLineLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 设置一行多少列
     * @param number Int
     */
    public void setMaxItemNum (int number) {
        if (number <= 0) return;
        this.maxItemNum = number;
    }


    /**
     * 初始化
     */
    private void init() {
        setOrientation(HORIZONTAL);
    }

    /**
     * 添加子View
     * @param sceneList List<MatrixScene>
     */
    public void addMatrixSceneList(List<MatrixScene> sceneList) {
        if (sceneList.size() > this.maxItemNum) return;
        removeAllViews();
        for (int i = 0; i < sceneList.size(); i++) {
            MatrixScene scene = sceneList.get(i);
            View sceneView = LayoutInflater.from(getContext()).inflate(R.layout.matrix_scene_gridview_item_layout, null);
            sceneView.setBackgroundResource(R.drawable.matrix_scene_unselect_bg);
            MarqueTextView marqueTextView = sceneView.findViewById(R.id.value);
            marqueTextView.setText(scene.sceneName);
            marqueTextView.setTextColor(getResources().getColorStateList(R.color.matrix_scene_text_color));
            int finalI = i;
            marqueTextView.setOnClickListener(v -> {
                notifySelectChange(finalI);
                MatrixSceneOneLineLayout.this.itemClickListener.click(v, scene);
            });
            ViewGroup.LayoutParams layoutParams;
            if (i == 0) {
                layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                LayoutParams tmpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tmpLayoutParams.setMarginStart(getResources().getDimensionPixelOffset(R.dimen.px_30));
                layoutParams = tmpLayoutParams;
            }
            addView(sceneView, layoutParams);
        }
    }

    /**
     * 设置子选项点击
     * @param listener Function2<View, MatrixScene, Unit>
     */
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    /**
     * 视频矩阵场景选中更新
     * @param selectIndicator Int, 为-1时，全部设置为未选中状态
     */
    public void notifySelectChange(int selectIndicator) {
        if (getChildCount() == 0) return;
        for (int i = 0; i < getChildCount(); i++) {
            LinearLayout childView = (LinearLayout) getChildAt(i);
            MarqueTextView marqueTextView = childView.findViewById(R.id.value);
            if (selectIndicator == i) {
                // 设置为被选中
                childView.setBackgroundResource(R.drawable.matrix_scene_select_bg);
                marqueTextView.setTextColor(Color.WHITE);
                marqueTextView.setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));
            } else {
                // 设置为未被选中
                childView.setBackgroundResource(R.drawable.matrix_scene_unselect_bg);
                marqueTextView.setTextColor(getResources().getColorStateList(R.color.matrix_scene_text_color));
                marqueTextView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            }
        }
    }

    public interface ItemClickListener {
        Object click(View view, MatrixScene matrixScene);
    }
}
