package com.sunmnet.mediaroom.matrix.impl;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IMatrixInit;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutBinding;
import com.sunmnet.mediaroom.matrix.ui.widget.SecondMatrixLayout;

/**
 * Create by WangJincheng on 2021/5/10
 * 初始化视频矩阵
 */

public class MatrixInit implements IMatrixInit {

    // matrix_layout的dataBinding
    private MatrixLayoutBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    public void init(View view, AppCompatActivity activity) {
        this.binding = DataBindingUtil.bind(view);
        MatrixConfig config = Controller.getInstance().getMatrixConfig();
        if (config.getScenes() != null) {
            this.binding.matrixMain.smlMatrix.setPageListener(new SecondMatrixLayout.OnPageListener() {
                @Override
                public void onPageChange(int position, int total) {
                    binding.matrixMain.tvPageIndex.setText((position + 1) + "/" + total);
                }

                @Override
                public void onFirstPage() {
                    ToastUtil.show(view.getContext(), "已经是第一页", false);
                }

                @Override
                public void onLastPage() {
                    ToastUtil.show(view.getContext(), "已经是最后一页", false);
                }
            });

            this.binding.matrixMain.ivLeftNavigation.setOnClickListener(v ->
                this.binding.matrixMain.smlMatrix.previousPage());
            this.binding.matrixMain.ivRightNavigation.setOnClickListener(v ->
                this.binding.matrixMain.smlMatrix.nextPage());

            this.binding.matrixMain.smlMatrix.setMatrixList(config.getScenes());
        }
    }

    @Override
    public void unInit() {

    }

}
