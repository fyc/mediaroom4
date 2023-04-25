package com.sunmnet.mediaroom.matrix.impl;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.sunmnet.mediaroom.common.tools.GsonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.adapter.MatrixSceneHolder;
import com.sunmnet.mediaroom.matrix.anotherUi.bean.MatrixSceneForGridView;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IMatrixInit;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IMatrixLayout;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutBinding;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutMainBinding;
import com.sunmnet.mediaroom.matrix.ui.adapter.MatrixSceneAdapter;
import com.sunmnet.mediaroom.matrix.ui.widget.MatrixSceneOneLineLayout;
import com.sunmnet.mediaroom.matrix.utils.MatrixUtils;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixSceneDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by WangJincheng on 2021/5/10
 * 初始化视频矩阵
 */

public class MatrixInit implements IMatrixInit {

    private MatrixLayoutBinding matrixLayoutBinding;
    private MatrixSceneAdapter matrixSceneAdapter;

    // 列数
    private static final int ROW_NUMBER = 4;

    // matrix_layout_main的dataBinding
    private MatrixLayoutMainBinding matrixMainBinding;
    // 场景列表的指示器
    private int matrixGridPosition = -1;

    @Override
    public void init(View view, AppCompatActivity activity) {
        this.matrixLayoutBinding = DataBindingUtil.bind(view);
        this.matrixMainBinding = this.matrixLayoutBinding.matrixMain;

        // 场景相关
        this.matrixSceneAdapter = new MatrixSceneAdapter(MatrixSceneHolder.class);
        this.matrixMainBinding.matrixMainGridview.setNumColumns(ROW_NUMBER);
        this.matrixMainBinding.matrixMainGridview.setAdapter(matrixSceneAdapter);
        this.matrixMainBinding.matrixMainGridview.setOnItemClickListener((adapterView, view1, i, l) -> {
            this.matrixGridPosition = i;
            for (int j = 0; j < MatrixInit.this.matrixSceneAdapter.getData().size(); j++) {
                if (j != i) {
                    MatrixInit.this.matrixSceneAdapter.getData().get(j).setSelect(false);
                } else {
                    MatrixInit.this.matrixSceneAdapter.getData().get(j).setSelect(true);
                }
            }
            MatrixInit.this.matrixSceneAdapter.notifyDataSetChanged();

            MatrixSceneForGridView matrixScene = MatrixInit.this.matrixSceneAdapter.getData().get(i);
            RunningLog.run("选中的场景数据:" + GsonUtil.objToJsonStr(matrixScene));
            TabspMatrixSceneDto matrixSceneDto = new TabspMatrixSceneDto();
            Map<String, List<String>> map = new LinkedHashMap<>();
            for (Map.Entry<String, List<String>> entry : matrixScene.getMatrixScene().sceneMap.entrySet()) {
                // 使用新的列表，解决Map拷贝带来的修改引用的问题
                map.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
            matrixSceneDto.setInterfaces(map);
            matrixSceneDto.setSceneName(matrixScene.getMatrixScene().sceneName);
            MatrixInit.this.matrixMainBinding.matrixLayout.setMatrixScene(matrixSceneDto);

            Controller.getInstance().controll(matrixScene.getMatrixScene().sceneName);
            Controller.getInstance().controll(matrixScene.getMatrixScene());
        });

        MatrixConfig config = Controller.getInstance().getMatrixConfig();
        List<MatrixSceneForGridView> sceneForGridViewList = new ArrayList<>();
        for (MatrixScene matrixScene : config.getScenes()) {
            sceneForGridViewList.add(new MatrixSceneForGridView(matrixScene));
        }
        this.matrixSceneAdapter.setData(sceneForGridViewList);
        this.matrixSceneAdapter.notifyDataSetChanged();

        // 输入输出口相关
        this.matrixMainBinding.matrixLayout.setMatrixChangedListener(new IMatrixLayout.MatrixChangedListener() {
            @Override
            public void onMatrixInputSelected(boolean isSelected) {
                RunningLog.run("视频矩阵改变");
            }

            @Override
            public void onMatrixOutputSelected(String input, String output) {
                RunningLog.run("视频矩阵改变, input:" + input + ", output:" + output);
                for (MatrixSceneForGridView matrixSceneForGridView : matrixSceneAdapter.getData()) {
                    matrixSceneForGridView.setSelect(false);
                    matrixMainBinding.msolMatrixSceneList.notifySelectChange(MatrixSceneOneLineLayout.UNSELECT_ALL);
                }
                matrixSceneAdapter.notifyDataSetChanged();
                MatrixInit.this.matrixGridPosition = -1;
                Controller.getInstance().controll(input, output);
            }

            @Override
            public void onMatrixOutputSelected(String input, List<String> outputs) {
                RunningLog.run("视频矩阵改变, input:" + input + ", output:" + GsonUtil.objToJsonStr(outputs));
                Controller.getInstance().controll(input, outputs.toArray(new String[outputs.size()]));
            }

        });

        this.matrixMainBinding.matrixLayout.post(() -> {
            HashMap<String, String> matrixInputMap = new HashMap<>();
            List<MatrixInterface> inputInterface = Controller.getInstance().getMatrixConfig().getInputInterface();
            for (MatrixInterface matrixInterface : inputInterface) {
                matrixInputMap.put(matrixInterface.input, matrixInterface.inputName);
            }
            HashMap<String, String> matrixOutputMap = new HashMap<>();
            List<MatrixInterface> outputInterface = Controller.getInstance().getMatrixConfig().getOutputInterface();
            for (MatrixInterface matrixInterface : outputInterface) {
                matrixOutputMap.put(matrixInterface.input, matrixInterface.inputName);
            }
            this.matrixMainBinding.matrixLayout.setMatrix(matrixInputMap, matrixOutputMap);
        });

        // 设置箭头背景
        int partOneVisibility = this.matrixMainBinding.llMatrixPartOne.getVisibility();
        if (partOneVisibility == View.VISIBLE) {
            this.matrixMainBinding.ivArrow.setImageResource(R.drawable.arrow_up_bg);
        } else {
            this.matrixMainBinding.ivArrow.setImageResource(R.drawable.arrow_down_bg);
        }

        this.matrixMainBinding.ivArrow.setOnClickListener(v -> {
            int visibility = this.matrixMainBinding.llMatrixPartOne.getVisibility();
            if (visibility == View.VISIBLE) {
                this.matrixMainBinding.llMatrixPartOne.setVisibility(View.GONE);
                ((ImageView) v).setImageResource(R.drawable.arrow_down_bg);
            } else {
                this.matrixMainBinding.llMatrixPartOne.setVisibility(View.VISIBLE);
                ((ImageView) v).setImageResource(R.drawable.arrow_up_bg);
            }
        });

        // 全选、默认、全清
        this.matrixMainBinding.btnSelectall.setOnClickListener(v -> {
            MatrixInit.this.matrixMainBinding.matrixLayout.setSelectAll();

            for (MatrixSceneForGridView matrixSceneForGridView : matrixSceneAdapter.getData()) {
                matrixSceneForGridView.setSelect(false);
            }
            matrixSceneAdapter.notifyDataSetChanged();
            MatrixInit.this.matrixGridPosition = -1;
        });

        this.matrixMainBinding.btnDefault.setOnClickListener(v-> {
            int count = Controller.getInstance().getMatrixConfig().getInputInterface().size();
            TabspMatrixSceneDto sceneDto = new TabspMatrixSceneDto();
            MatrixUtils.makeDefaultScene(count, sceneDto);
            sceneDto.setSceneName("默认");
            MatrixInit.this.matrixMainBinding.matrixLayout.setMatrixScene(sceneDto);

            for (MatrixSceneForGridView matrixSceneForGridView : matrixSceneAdapter.getData()) {
                matrixSceneForGridView.setSelect(false);
            }
            matrixSceneAdapter.notifyDataSetChanged();
            MatrixInit.this.matrixGridPosition = -1;

            Controller.getInstance().control(sceneDto.getInterfaces());
        });

        this.matrixMainBinding.btnClearall.setOnClickListener(v-> {
            MatrixInit.this.matrixMainBinding.matrixLayout.setClearAll();
            for (MatrixSceneForGridView matrixSceneForGridView : matrixSceneAdapter.getData()) {
                matrixSceneForGridView.setSelect(false);
            }
            matrixSceneAdapter.notifyDataSetChanged();
            MatrixInit.this.matrixGridPosition = -1;
        });

        // 选择选用哪种矩阵场景布局
        if (config.getScenes().size() > ROW_NUMBER) {
            // 设置场景gridview显示
            this.matrixMainBinding.rlMatrixPartTwo.setVisibility(View.VISIBLE);
        } else {
            // 设置单行场景布局显示
            this.matrixMainBinding.msolMatrixSceneList.setMaxItemNum(ROW_NUMBER);
            this.matrixMainBinding.msolMatrixSceneList.addMatrixSceneList(config.getScenes());
            this.matrixMainBinding.msolMatrixSceneList.setItemClickListener((v, matrixScene) -> {
                TabspMatrixSceneDto matrixSceneDto = new TabspMatrixSceneDto();
                Map<String, List<String>> map = new LinkedHashMap<>();
                for (Map.Entry<String, List<String>> entry : matrixScene.sceneMap.entrySet()) {
                    // 使用新的列表，解决Map拷贝带来的修改引用的问题
                    map.put(entry.getKey(), new ArrayList<>(entry.getValue()));
                }
                matrixSceneDto.setInterfaces(map);
                matrixSceneDto.setSceneName(matrixScene.sceneName);
                MatrixInit.this.matrixMainBinding.matrixLayout.setMatrixScene(matrixSceneDto);

                Controller.getInstance().controll(matrixScene.sceneName);
                Controller.getInstance().controll(matrixScene);

                return null;
            });
            this.matrixMainBinding.rlMatrixPartThree.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void unInit() {

    }

}
