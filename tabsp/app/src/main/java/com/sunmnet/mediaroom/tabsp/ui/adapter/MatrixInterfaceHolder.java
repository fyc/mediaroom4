package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixInterfaceBinding;
import com.sunmnet.mediaroom.tabsp.ui.widget.RollingTextView;

public class MatrixInterfaceHolder extends AbstractHolder<MatrixInterfaceBinding, MatrixInterface> {
    TextView contentView;
    public static int SELECTED_TEXT_COLOR, UNSELECTED_TEXT_COLOR;

    public MatrixInterfaceHolder() {
    }

    public MatrixInterfaceHolder(int selected, int unselected) {
        SELECTED_TEXT_COLOR = selected;
        UNSELECTED_TEXT_COLOR = unselected;
    }

    @Override
    public void bindView(View view) {
        this.contentView = (TextView) view;
    }

    MatrixInterfaceBinding binding;

    @Override
    public void setProperty(MatrixInterfaceBinding matrixInterfaceBinding, MatrixInterface matrixInterface) {
        matrixInterfaceBinding.setInter(matrixInterface);
        this.binding = matrixInterfaceBinding;
        this.property = matrixInterface;
        setSelected(false);
    }

    @Override
    public MatrixInterfaceBinding getViewDataBinding() {
        return binding;
    }

    @Override
    public MatrixInterface getProperty() {
        return property;
    }

    @Override
    public void setSelected(boolean selected) {
        contentView.setBackgroundResource(selected ? R.drawable.circle_selected_drawable : R.drawable.circle_default_drawable);
        contentView.setTextColor(selected ? UNSELECTED_TEXT_COLOR : SELECTED_TEXT_COLOR);
        if (contentView instanceof RollingTextView) {
            contentView.setEllipsize(selected ? TextUtils.TruncateAt.valueOf("MARQUEE") : TextUtils.TruncateAt.valueOf("END"));
        }
    }
}
