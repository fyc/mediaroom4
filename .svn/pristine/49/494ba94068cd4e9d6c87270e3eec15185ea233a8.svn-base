package com.sunmnet.mediaroom.tabsp.controll.version2;

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
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.LectureItem;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.databinding.LectureBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.LectureFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 授课模式
 */
@Route(path = Version2Dispatcher.CONTROLLER_VERSION2_LECTURE)
public class LectureDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    LectureBinding binding;
    static int DFAULT_TEXT_COLOR;
    AbstractHolder<?, LectureItem> preHolder;

    @OnItemClick(R.id.lecture_grid)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<?, LectureItem> holder = (AbstractHolder<?, LectureItem>) view.getTag();
        if (holder != preHolder) {
            if (preHolder != null) preHolder.setSelected(false);
            holder.setSelected(true);
            preHolder = holder;
            MatrixScene scene = holder.getProperty().getEntity();
            DeviceScene<MatrixScene> deviceScene = new DeviceScene<>(scene, scene.sceneName);
            Controller.getInstance().setScene(deviceScene);
        }
    }

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        binding.tagName.setText(TabspApplication.getInstance().getString(R.string.name_lecture_mode));
        //加载本地配置
        List<LectureItem> items = getItems();

        if (items.size() > 0) {
            BindingAdapter modeAdapter = new BindingAdapter(R.layout.tabsp_lecture_item_layout, new LectureFactory());
            modeAdapter.setData(items);
            binding.lectureGrid.setNumColumns(items.size());
            binding.setAdapter(modeAdapter);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.lectureGrid.performItemClick(binding.lectureGrid.getChildAt(0), 0, binding.lectureGrid.getItemIdAtPosition(0));
                }
            }, 50);
        }
    }

    private List<LectureItem> getItems() {
        List<LectureItem> items = new ArrayList<>();
        List<SysSpTempConfigFileDto.LectureMode> modes = ((Version2Dispatcher) TabspApplication.getInstance().getDispatcher()).getLectureModes();
        if (modes != null) {
            LectureItem lectureItem = null;
            for (int i = 0, size = modes.size(); i < size; i++) {
                SysSpTempConfigFileDto.LectureMode mode = modes.get(i);
                MatrixScene matrixScene = new MatrixScene();
                matrixScene.sceneName = mode.getSceneName();
                matrixScene.sceneCode = mode.getId();
                lectureItem = new LectureItem(matrixScene, mode.getSelectedIcon(), mode.getUnselectedIcon(), R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
                items.add(lectureItem);
            }
        } else {
            Locale language = TabspApplication.getInstance().getConfig().getLang();
            LectureItem lectureItem = null;
            MatrixScene scene = new MatrixScene();
            scene.sceneMap = createAll("1");
            scene.sceneName = language == Locale.ENGLISH ? "Computer" : "讲台电脑";
            lectureItem = new LectureItem(scene, R.drawable.mediaroom4_pc_open, R.drawable.mediaroom4_pc_off, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
            items.add(lectureItem);

            scene = new MatrixScene();
            scene.sceneMap = createAll("2");
            scene.sceneName = language == Locale.ENGLISH ? "NoteBool" : "便携电脑有线接入";
            lectureItem = new LectureItem(scene, R.drawable.mediaroom4_notebook_selected, R.drawable.mediaroom4_notebook_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
            items.add(lectureItem);

            scene = new MatrixScene();
            scene.sceneMap = createAll("3");
            scene.sceneName = language == Locale.ENGLISH ? "Left Computer" : "大屏主机";
            lectureItem = new LectureItem(scene, R.drawable.mediaroom4_dpzj_selected, R.drawable.mediaroom4_dpzj_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
            items.add(lectureItem);

            scene = new MatrixScene();
            scene.sceneMap = createAll("5");
            scene.sceneName = language == Locale.ENGLISH ? "Portable Device" : "便携设备无线接入";
            lectureItem = new LectureItem(scene, R.drawable.mediaroom4_portable_selected, R.drawable.mediaroom4_portable_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
            items.add(lectureItem);
        }

        return items;
    }

    private Map<String, List<String>> createAll(String input) {
        Map<String, List<String>> maps = new LinkedHashMap<>();
        MatrixConfig config = Controller.getInstance().getMatrixConfig();
        List<String> arrayList = new ArrayList<>();
        for (int i = 0, size = config.getOutputInterface().size(); i < size; i++) {
            String output = config.getOutputInterface().get(i).input;
            arrayList.add(output);
        }
        maps.put(input, arrayList);
        return maps;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            DFAULT_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.device_black_text_color);
        } else return;
        this.dispatch(view);
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version2_lecture_layout;
    }
}
