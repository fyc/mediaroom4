package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IMatrixInit;
import com.sunmnet.mediaroom.matrix.impl.MatrixInit;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;

/**
 * Create by WangJincheng on 2021/4/27
 * 跟MatrixDispatcher功能一致，仅是界面不同，后续可能沿用这套3.0的界面
 */

@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_MATRIX2)
public class MatrixDispatcher2 extends AbstractFragmentDispatcher {

    BaseActivity activity;
    private IMatrixInit matrixInit;

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) this.activity = (BaseActivity) e;
        this.dispatch(view);
    }

    @Override
    public void dispatch(View view) {
        matrixInit = new MatrixInit();
        matrixInit.init(view, this.activity);
    }

    @Override
    public void release() {
        super.release();
        matrixInit.unInit();
    }

    @Override
    public int getLayout() {
        return R.layout.matrix_layout;
    }
}
