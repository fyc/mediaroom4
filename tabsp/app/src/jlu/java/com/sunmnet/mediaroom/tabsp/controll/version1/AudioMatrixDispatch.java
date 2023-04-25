package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.bean.protocol.SerialControl;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.AudioMatrixChannelDto;
import com.sunmnet.mediaroom.tabsp.bean.AudioMatrixChannelVo;
import com.sunmnet.mediaroom.tabsp.bean.AudioMatrixResetEvent;
import com.sunmnet.mediaroom.tabsp.customization.CustomPath;
import com.sunmnet.mediaroom.tabsp.databinding.AudioMatrixBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.factory.AudioMatrixFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.ButterKnife;

/**
 * 音频矩阵设备界面
 */
@Route(path = CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_AUDIO_MATRIX)
public class AudioMatrixDispatch extends AbstractFragmentDispatcher {
    private View contentView;
    private BindingAdapter adapter;
    private BaseActivity activity;
    private AudioMatrixBinding binding;
    private String type;
    private String prevType;
    private DeviceType deviceType;
    private String parentConnect;
    private List<AudioMatrixChannelDto> dtoList;

    @Override
    public int getLayout() {
        return R.layout.tabsp_custom_device_audio_matrix_layout;
    }


    @Override
    public void dispatch(View view) {
        super.dispatch(view);
        this.contentView = view;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
        }
        if (this.contentView != view && view != null) {
            this.dispatch(this.type, view);
        }
    }

    @Override
    public void dispatch(String type) {
        super.dispatch(type);
        this.prevType = this.type;
        this.type = type;
        deviceType = DeviceType.getCustomType(type);
        if (deviceType == null) {
            return;
        }
        if (this.contentView != null) {
            this.dispatch(type, contentView);
        }
    }

    @Override
    public void dispatch(String type, View view) {
        super.dispatch(type, view);
        this.contentView = view;
        RunningLog.run(this.toString() + " dispatch(View view)");
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        if (TextUtils.isEmpty(parentConnect)) {
            binding.backBtn.setVisibility(View.GONE);
        } else {
            binding.deviceSettingText.setText(deviceType.getDeviceTypeName());
            binding.backBtn.setVisibility(View.VISIBLE);
        }
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        if (adapter == null) {
            adapter = new BindingAdapter(R.layout.tabsp_custom_audio_matrix_item_layout, new AudioMatrixFactory(activity));
        }
        if (devices != null && devices.size() > 0) {
            IDevice device = devices.get(0);
            if (dtoList == null) {
                dtoList = getData();
                saveData(dtoList);
            }
            List<AudioMatrixChannelVo> list = new ArrayList<>(dtoList.size());
            for (AudioMatrixChannelDto dto : dtoList) {
                AudioMatrixChannelVo channel = new AudioMatrixChannelVo(dto);
                channel.setDevice(device);
                list.add(channel);
            }
            adapter.setData(list);
        } else {
            adapter.setData(Collections.emptyList());
        }
        adapter.notifyDataSetChanged();
        binding.setAdapter(adapter);
    }

    @Override
    public void onReady() {
        RunningLog.run(this.toString() + " onReady");
        if (lifeCycleState.get() == RELEASE) {
            List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
            if (adapter == null) {
                adapter = new BindingAdapter(R.layout.tabsp_custom_audio_matrix_item_layout, new AudioMatrixFactory(activity));
            }
            if (devices != null && devices.size() > 0) {
                IDevice device = devices.get(0);
                if (dtoList == null) {
                    dtoList = getData();
                    saveData(dtoList);
                }
                List<AudioMatrixChannelVo> list = new ArrayList<>(dtoList.size());
                for (AudioMatrixChannelDto dto : dtoList) {
                    AudioMatrixChannelVo channel = new AudioMatrixChannelVo(dto);
                    channel.setDevice(device);
                    list.add(channel);
                }
                adapter.setData(list);
            } else {
                adapter.setData(Collections.emptyList());
            }
            adapter.notifyDataSetChanged();
            binding.setAdapter(adapter);
        }
        register();
        super.onReady();
    }

    @Override
    public void dispatch(String type, String parent) {
        super.dispatch(type, parent);
        RunningLog.run(this.toString() + " dispatch(String type, String parent)");
        parentConnect = parent;
        this.dispatch(type);
    }

    @Override
    public void release() {
        super.release();
        RunningLog.run(this.toString() + " release");
        unregister();
    }

    @Override
    public void invisible() {
        super.invisible();
        RunningLog.run(this.toString() + " invisible");
    }

    @Subscribe
    public void channelUpdate(AudioMatrixChannelVo vo) {
        if (dtoList != null && dtoList.size() >= vo.getChannel()) {
            AudioMatrixChannelDto dto = dtoList.get(vo.getChannel() - 1);
            dto.setVolume(vo.getVolume());
            dto.setState(vo.getState());
            if (!dto.getName().equals(vo.getName())) {
                dto.setName(vo.getName());
                ((AudioMatrixChannelVo) adapter.getData().get(vo.getChannel() - 1)).setName(vo.getName());
                adapter.notifyDataSetChanged();
            }
            saveData(dtoList);
        }
    }

    @Subscribe
    public void onReset(AudioMatrixResetEvent event) {
        setDefaultSense();
    }

    private void saveData(List<AudioMatrixChannelDto> dtoList) {
        SharePrefUtil.saveValue(TabspApplication.getInstance(), "devicecontrol", "audiomatrix", JacksonUtil.objToJsonStr(dtoList));
    }

    private List<AudioMatrixChannelDto> getData() {
        String string = SharePrefUtil.getString(TabspApplication.getInstance(), "devicecontrol", "audiomatrix");
        List<AudioMatrixChannelDto> dtoList = null;
        if (!TextUtils.isEmpty(string)) {
            dtoList = JacksonUtil.jsonStrToList(string, AudioMatrixChannelDto.class);
        }
        if (dtoList == null) {
            dtoList = new ArrayList<>(8);
            for (int i = 1; i <= 8; i++) {
                AudioMatrixChannelDto channelDto = new AudioMatrixChannelDto();
                channelDto.setChannel(i);
                channelDto.setName("通道" + i);
                channelDto.setState(true);
                dtoList.add(channelDto);
            }
        }
        return dtoList;
    }

    private void register() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    private void unregister() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    void unregisterType(String type) {
        if (type != null) {
            DeviceNotifyEvent event = new DeviceNotifyEvent();
            StateEventType eventType = new StateEventType();
            eventType.type = 4;
            eventType.key = type;
            event.setEventType(eventType);
            EventBus.getDefault().post(event);
        }
    }

    /**
     * 设置音频矩阵默认场景
     */
    private void setDefaultSense() {
        String code = "481800020100004D";
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        SerialControl serialControl = new SerialControl();
        serialControl.setOperateMethod(3);
        SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
        controlDevice.setDeviceCode(devices.get(0).getDeviceCode());
        controlDevice.setDeviceType(devices.get(0).getDeviceTypeCode());
        controlDevice.setControlCmd(code);
        List<SerialControl.DevicesBean> controlDevices = Collections.singletonList(controlDevice);
        serialControl.setDevices(controlDevices);
        Controller.getInstance().sendMsg(CommuTag.SERIAL_CONTROL, JacksonUtil.objToJsonStr(serialControl));
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        if (devices != null && devices.size() > 0) {
            IDevice device = devices.get(0);
            if (dtoList == null) {
                dtoList = getData();
                saveData(dtoList);
            }
            List<AudioMatrixChannelVo> list = new ArrayList<>(dtoList.size());
            for (AudioMatrixChannelDto dto : dtoList) {
                AudioMatrixChannelVo channel = new AudioMatrixChannelVo(dto);
                channel.setDevice(device);
                list.add(channel);
            }
            adapter.setData(list);
        } else {
            adapter.setData(Collections.emptyList());
        }
        adapter.notifyDataSetChanged();
    }
}
