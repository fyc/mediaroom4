package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.databinding.DataBindingUtil;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.matrix.MatrixView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixBinding;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixInterfaceBinding;
import com.sunmnet.mediaroom.tabsp.databinding.SceneItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.MatrixInterfaceHolder;

import java.util.List;

/**
 * 矩阵控制界面
 * */
@Deprecated
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_MATRIX)
public class MatrixDispatcher extends AbstractFragmentDispatcher implements View.OnClickListener, AdapterView.OnItemClickListener, MatrixView.OnMatrixSelectListener {
    MatrixBinding binding;
    BaseActivity activity;

    @Override
    public int getLayout() {
        return R.layout.tabsp_matrix_layout;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) this.activity = (BaseActivity) e;
        MatrixInterfaceHolder.SELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.device_black_text_color);
        MatrixInterfaceHolder.UNSELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.common_text_color);
        this.dispatch(view);
    }

    @Override
    public void dispatch(View view) {
        binding = DataBindingUtil.bind(view);
        MatrixConfig config = Controller.getInstance().getMatrixConfig();
        binding.matrixview.setOnMatrixSelectListener(this);
        TextView textView = new TextView(view.getContext());
        int wh = view.getContext().getResources().getDimensionPixelSize(R.dimen.px_100);
        textView.setWidth(wh);
        textView.setHeight(wh);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.circle_default_drawable);
        binding.matrixview.addCenterView(textView);
        binding.matrixview.apply(config);
        List<MatrixScene> scenes = config.getScenes();
        if (scenes != null && scenes.size() > 0) {
            binding.matrixSceneGrid.setVisibility(View.VISIBLE);
            int width = this.activity.getResources().getDimensionPixelSize(R.dimen.px_134);
            int height = this.activity.getResources().getDimensionPixelSize(R.dimen.px_50);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            LayoutInflater inflater = LayoutInflater.from(this.activity);
            params.rightMargin = 20;
            for (int i = 0, size = scenes.size(); i < size; i++) {
                MatrixScene scene = scenes.get(i);
                DeviceScene tabspScene = new DeviceScene(scene, scene.sceneName);
                View sceneView = inflater.inflate(R.layout.tabsp_version1_scene_item_layout, null, false);
                SceneItem item = DataBindingUtil.bind(sceneView);
                item.setScene(tabspScene);
                sceneView.setTag(tabspScene);
                sceneView.setOnClickListener(this);
                binding.sceneLayout.addView(sceneView, params);
            }
        }

        HolderAdapter adapter = new HolderAdapter(R.layout.tabsp_matrix_interface_item_layout, new IHolder.HolderFactory() {
            @Override
            public IHolder newHolder() {
                return new MatrixInterfaceHolder();
            }
        });
        adapter.setData(config.getInputInterface());
        binding.setInputAdapter(adapter);
        binding.matrixInputGrid.setOnItemClickListener(this);
        if (adapter.getCount() > 0) {
            binding.matrixInputGrid.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.matrixInputGrid.performItemClick(binding.matrixInputGrid.getChildAt(0), 0, binding.matrixInputGrid.getItemIdAtPosition(0));
                }
            }, 100);
        }
    }

    View prevSceneView;

    private void setSelect(View view, boolean select) {
        if (view != null) {
            if (select) view.setBackgroundResource(R.drawable.device_on_background);
            else view.setBackgroundResource(R.drawable.scene_item_background);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextColor(select ? MatrixInterfaceHolder.UNSELECTED_TEXT_COLOR : MatrixInterfaceHolder.SELECTED_TEXT_COLOR);
                textView.setEllipsize(select ? TextUtils.TruncateAt.valueOf("MARQUEE") : TextUtils.TruncateAt.valueOf("END"));
            }
        }
    }

    @Override
    public void onClick(View view) {
        setSelect(prevSceneView, false);
        setSelect(view, true);
        DeviceScene<MatrixScene> tabspScene = (DeviceScene) view.getTag();
        Controller.getInstance().controll(tabspScene.getSceneName());
        prevSceneView = view;
        Controller.getInstance().controll(tabspScene.getScene());
        MatrixScene scene = tabspScene.getScene();
        MatrixInterface first = binding.matrixview.setScene(scene);
        try {
            int value = Integer.parseInt(first.input) - 1;
            binding.matrixInputGrid.performItemClick(binding.matrixInputGrid.getChildAt(value), value, binding.matrixInputGrid.getItemIdAtPosition(value));
        } catch (Exception e) {
            RunningLog.error(e);
        }
    }

    AbstractHolder prevInputSelected;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<MatrixInterfaceBinding, MatrixInterface> holder = (AbstractHolder) view.getTag();
        if (holder != prevInputSelected) {
            if (prevInputSelected != null) prevInputSelected.setSelected(false);
            holder.setSelected(true);
            prevInputSelected = holder;
            binding.matrixview.setInput(holder.getProperty());
        }
    }

    @Override
    public void onMatrixSelected(String input, String output) {
        Controller.getInstance().controll(input, output);
    }

    @Override
    public void onMatrixSelected(String input, List<MatrixInterface> interfaces) {
        String[] outputs = new String[interfaces.size()];
        for (int i = 0, size = interfaces.size(); i < size; i++) {
            outputs[i] = interfaces.get(i).input;
        }
        Controller.getInstance().controll(input, outputs);
    }
}
