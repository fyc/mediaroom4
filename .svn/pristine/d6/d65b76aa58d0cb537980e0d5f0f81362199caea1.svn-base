package com.sunmnet.mediaroom.matrix.anotherUi.interfaces;

import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixSceneDto;

import java.util.List;
import java.util.Map;

/**
 * Created by dengzl on 2018/11/19.
 */

public interface IMatrixLayout
{
    public interface MatrixChangedListener{
        /**
         * 矩阵列表中输入口选中事件
         * @param isSelected true:全选按钮可用，false：全选按钮不可用
         * */
        public void onMatrixInputSelected(boolean isSelected);
        /**
         * 矩阵列表中输出口更新，只在选中时回调
         * */
        public void onMatrixOutputSelected(String input, String output);
        public void onMatrixOutputSelected(String input, List<String> outputs);
    }
    public void setOnVisible();
    /**
     * 切换矩阵场景
     * */
    public void setMatrixScene(TabspMatrixSceneDto scene);
    /**
     * 设置输入输出口自定义值
     * */
    public void setMatrix(Map<String, String> inputTags, Map<String, String> outputTags);
    /**
     * 全选按钮
     * */
    public void setSelectAll();
    /**
     * 清空
     * */
    public void setClearAll();
    /**
     * 获取当前正在处理的场景
     * @return
     * */
    public TabspMatrixSceneDto getCurrentScene();
    /**
     * 设置监听
     * */
    public void setMatrixChangedListener(MatrixChangedListener listener);
}
