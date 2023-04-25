package com.sunmnet.mediaroom.matrix.anotherUi.bean;

import com.sunmnet.mediaroom.device.bean.MatrixScene;

/**
 * Create by WangJincheng on 2021/4/29
 * 针对视频矩阵场景的GridView数据
 */

public class MatrixSceneForGridView {

    // 是否被选中
    private boolean isSelect = false;

    // 视频矩阵场景数据
    private MatrixScene matrixScene;

    public MatrixSceneForGridView(MatrixScene matrixScene) {
        this.matrixScene = matrixScene;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public MatrixScene getMatrixScene() {
        return matrixScene;
    }
}
