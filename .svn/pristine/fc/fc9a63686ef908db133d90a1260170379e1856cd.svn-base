package com.sunmnet.mediaroom.device.version3;

import android.nfc.Tag;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.AbstractDeviceServiceImpl;
import com.sunmnet.mediaroom.device.DeviceTag;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.util.tag.CommuTag;

import org.greenrobot.eventbus.EventBus;

/**
 * 设备操作服务，该版本适配3.0通信协议中的控制指令
 */
public class Version3ControllImpl extends AbstractDeviceServiceImpl {
    ITransformer sender;

    public Version3ControllImpl(ITransformer sender) {
        this.sender = sender;
        EventBus.getDefault().register(this);
    }

    @Override
    protected <T> void notifyProperty(T t) {
        EventBus.getDefault().post(t);
    }

    @Override
    public void open(IDevice deviceInfo) {
        deviceInfo.setDeviceState(DeviceState.OPENNING);
        doControll(deviceInfo, DeviceState.OPENED.getStateValue());
    }

    @Override
    public void close(IDevice deviceInfo) {
        deviceInfo.setDeviceState(DeviceState.CLOSING);
        doControll(deviceInfo, DeviceState.CLOSED.getStateValue());
    }

    private void doControll(IDevice iDeviceInfo, String state) {
        StringBuilder builder = new StringBuilder();
        String type = iDeviceInfo.getDeviceTypeCode();
        if (DeviceTag.DEVICE_STUPC.equals(type)) {
            builder.append(CommuTag.DEVICE_OPERATE_STU);
            builder.append(state);
            if (!TextUtils.isEmpty(iDeviceInfo.getDeviceCode())) {
                builder.append(",");
                builder.append(iDeviceInfo.getDeviceCode());
            }
        } else {
            builder.append(CommuTag.DEVICE_OPERATE);
            if (TextUtils.isEmpty(iDeviceInfo.getDeviceCode())) {
                builder.append(state);
                builder.append(",");
                builder.append(type);
            } else {
                builder.append(iDeviceInfo.getDeviceTypeCode());
                builder.append(",");
                builder.append(state);
                builder.append(",");
                builder.append(iDeviceInfo.getDeviceCode());
            }
        }
        builder.append(";");
        sendMessage(builder.toString());
        RunningLog.run("控制指令:"+builder.toString());
    }

    private void sendMessage(String message) {
        if (this.sender != null) this.sender.sendMsg(message);
    }

    @Override
    public void open(DeviceType deviceTypeCode) {
        super.open(deviceTypeCode);
        controllAll(deviceTypeCode, DeviceState.OPENED);
    }

    private void controllAll(DeviceType deviceType, DeviceState targetstate) {
        StringBuilder builder = new StringBuilder();
        if (DeviceTag.DEVICE_STUPC.equals(deviceType.getDeviceType())) {
            builder.append("62");
        } else {
            builder.append("61");
        }
        builder.append(deviceType.getDeviceType());
        builder.append(",");
        builder.append(targetstate.getStateValue());
        sendMessage(builder.toString());
        RunningLog.run("控制指令:"+builder.toString());
    }

    @Override
    public void close(DeviceType deviceTypeCode) {
        super.close(deviceTypeCode);
        controllAll(deviceTypeCode, DeviceState.CLOSED);
    }

    @Override
    public void setState(IDevice device, String state) {
        RunningLog.run("设置空调：" + device.getDeviceName() + "," + state);
    }

    @Override
    public void setState(IDevice iDevice, DeviceState state) {

    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }
}
