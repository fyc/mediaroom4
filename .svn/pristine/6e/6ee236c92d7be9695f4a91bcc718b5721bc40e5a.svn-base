package com.sunmnet.mediaroom.tabsp.ui.adapter.factory;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.device.bean.protocol.SerialControl;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.databinding.CurtainDeviceInfoBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.DeviceHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CurtainDeviceFactory implements IHolder.HolderFactory {

    public CurtainDeviceFactory() {

    }

    @Override
    public IHolder newHolder() {
        IHolder holder = new Holder<CurtainDeviceInfoBinding>() {
            @Override
            public void setProperty(CurtainDeviceInfoBinding deviceInfoBinding, AbstractDevice device) {
                register(device);
                this.binding = deviceInfoBinding;
                deviceInfoBinding.setDevice(device);
                this.property = device;
                setProgressing(property.isProcessing());
                setSelected(property.isOpened());
            }
        };
        return holder;
    }

    private class Holder<T extends ViewDataBinding> extends DeviceHolder<T> {

        private TextView curtainUp;
        private TextView curtainPause;
        private TextView curtainDown;

        @Override
        public void bindView(View view) {
            super.bindView(view);
            curtainUp = view.findViewById(R.id.tv_up);
            curtainPause = view.findViewById(R.id.tv_pause);
            curtainDown = view.findViewById(R.id.tv_down);

            curtainUp.setOnClickListener(view1 -> {
                Controller.getInstance().close(getProperty());
            });

            curtainPause.setOnClickListener(view1 -> {
                Runnable runnable = this::sendCurtainPauseCommand;
                ThreadUtils.schedule(runnable, 0, TimeUnit.SECONDS);
            });

            curtainDown.setOnClickListener(view1 -> {
                Controller.getInstance().open(getProperty());
            });
        }

        /**
         * 发送窗帘暂停指令
         */
        private void sendCurtainPauseCommand() {
            CustomDevice customDevice = (CustomDevice) getProperty();
            List<CustomDevice.ComponentItem> functionList = customDevice.getFunctionList();
            for (CustomDevice.ComponentItem componentItem : functionList) {
                if (componentItem.getName().contains("暂停")) {
                    RunningLog.run("发送窗帘暂停指令");
                    SerialControl serialControl = new SerialControl();
                    serialControl.setOperateMethod(3);
                    List<SerialControl.DevicesBean> controlDevices = new LinkedList<>();
                    serialControl.setDevices(controlDevices);
                    SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
                    controlDevice.setDeviceCode(getProperty().getDeviceCode());
                    controlDevice.setDeviceType(getProperty().getDeviceTypeCode());
                    controlDevice.setControlCmd(componentItem.getCode());
                    controlDevices.add(controlDevice);
                    Controller.getInstance().sendMsg(CommuTag.SERIAL_CONTROL, JacksonUtil.objToJsonStr(serialControl));
                }
            }
        }
    }
}
