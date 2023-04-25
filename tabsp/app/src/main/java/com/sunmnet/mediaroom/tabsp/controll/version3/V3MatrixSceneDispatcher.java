package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixScene3Binding;
import com.sunmnet.mediaroom.tabsp.databinding.SceneItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_DEVICE_MATRIX_SCENE)
public class V3MatrixSceneDispatcher extends AbstractFragmentDispatcher implements AdapterView.OnItemClickListener {
    BaseActivity activity;
    MatrixScene3Binding binding;
    public int SELECTED_TEXT_COLOR, UNSELECTED_TEXT_COLOR;

    @Override
    public void dispatch(View view) {
        binding = DataBindingUtil.bind(view);
        BindingAdapter adapter = new BindingAdapter(R.layout.tabsp_version3_scene_item, new IHolder.HolderFactory() {
            @Override
            public IHolder newHolder() {
                return new Version3MatrixSceneHolder(SELECTED_TEXT_COLOR, UNSELECTED_TEXT_COLOR);
            }
        });
        MatrixConfig config = Controller.getInstance().getMatrixConfig();
        List<DeviceScene> scenes = new ArrayList<>();
        for (int i = 0, size = config.getScenes().size(); i < size; i++) {
            MatrixScene matrixScene = config.getScenes().get(i);
            DeviceScene scene = new DeviceScene(matrixScene, matrixScene.sceneName);
            scenes.add(scene);
        }
        adapter.setData(scenes);
        binding.setAdapter(adapter);
        binding.matrixSceneGrid.setOnItemClickListener(this);
    }

    AbstractHolder<SceneItem, DeviceScene> prevInputSelected = null;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<SceneItem, DeviceScene> holder = (AbstractHolder) view.getTag();
        if (holder != prevInputSelected) {
            if (prevInputSelected != null) prevInputSelected.setSelected(false);
            holder.setSelected(true);
            prevInputSelected = holder;

        }
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            SELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.common_text_color);
            UNSELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.device_black_text_color);
            this.dispatch(view);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_matrix_scene_layout;
    }
}
