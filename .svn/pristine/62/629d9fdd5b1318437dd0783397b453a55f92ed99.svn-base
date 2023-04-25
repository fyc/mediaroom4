package com.sunmnet.mediaroom.matrix.ui.adapter;


import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.adapter.CommonAdapter;
import com.sunmnet.mediaroom.matrix.anotherUi.bean.MatrixSceneForGridView;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IHolder;
import com.sunmnet.mediaroom.matrix.anotherUi.ui.MarqueTextView;

/**
 * Create by WangJincheng on 2021/4/28
 * 视频矩阵场景适配器
 */

public class MatrixSceneAdapter extends CommonAdapter<MatrixSceneForGridView> {

    public MatrixSceneAdapter(Class clazz) {
        super(clazz, R.layout.matrix_scene_gridview_item_layout);
    }

    public MatrixSceneAdapter(Class clazz, View.OnClickListener listener) {
        super(clazz, R.layout.matrix_scene_gridview_item_layout, listener);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IHolder holder = null;
        MatrixSceneForGridView matrixScened = this.datas.get(i);
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(this.layoutId, viewGroup, false);
            holder = createHolder();
            holder.setView(view);
            view.setTag(holder);
        } else holder = (IHolder) view.getTag();
        holder.setProperty(matrixScened);
        MarqueTextView textView = view.findViewById(R.id.value);
        if (matrixScened.isSelect()) {
            // 被选中
            view.setBackgroundResource(R.drawable.matrix_scene_select_bg);
            textView.setTextColor(Color.WHITE);
            textView.setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));
        } else {
            // 未被选中
            view.setBackgroundResource(R.drawable.matrix_scene_unselect_bg);
            textView.setTextColor(view.getResources().getColorStateList(R.color.matrix_scene_text_color));
            textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        }
        textView.setText(matrixScened.getMatrixScene().sceneName);
        return view;
    }
}
