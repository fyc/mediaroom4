package com.sunmnet.mediaroom.device.version4;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.AbstractDeviceServiceImpl;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.protocol.DeviceControl;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.JsonUtils;

import org.greenrobot.eventbus.EventBus;
/**
 *设备控制指令
 * */
public class Version4DeviceService extends AbstractDeviceServiceImpl {
    ITransformer sender;

    public Version4DeviceService(ITransformer sender) {
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
        DeviceControl control = new DeviceControl();
        control.setControl(state);
        control.setDeviceCode(iDeviceInfo.getDeviceCode());
        control.setDeviceType(iDeviceInfo.getDeviceTypeCode());
        sender.sendMsg(CommuTag.DEVICE_STATE, JsonUtils.objectToJson(control));
    }

    @Override
    public void open(DeviceType deviceTypeCode) {
        super.open(deviceTypeCode);
        controllAll(deviceTypeCode, DeviceState.OPENED);
    }

    private void controllAll(DeviceType deviceType, DeviceState targetstate) {
        DeviceControl deviceControl = new DeviceControl();
        deviceControl.setDeviceCode(null);
        deviceControl.setControl(targetstate.getStateValue());
        deviceControl.setDeviceType(deviceType.getDeviceType());
        sender.sendMsg(CommuTag.DEVICE_STATE, JsonUtils.objectToJson(deviceControl));
        /*StringBuilder builder = new StringBuilder();
        if (DeviceTag.DEVICE_STUPC.equals(deviceType.getDeviceType())) {
            builder.append("62");
        } else {
            builder.append("61");
        }
        builder.append(deviceType.getDeviceType());
        builder.append(",");
        builder.append(targetstate.getStateValue());
        sendMessage(builder.toString());
        RunningLog.run("控制指令:" + builder.toString());*/
    }

    @Override
    public void close(DeviceType deviceTypeCode) {
        super.close(deviceTypeCode);
        controllAll(deviceTypeCode, DeviceState.CLOSED);
    }

    @Override
    public void setState(IDevice device, String state) {
        RunningLog.run("设置空调：" + device.getDeviceName() + "," + state);
        DeviceControl deviceControl = new DeviceControl();
        deviceControl.setDeviceCode(device.getDeviceCode());
        deviceControl.setControl(state);
        deviceControl.setDeviceType(device.getDeviceTypeCode());
        sender.sendMsg(CommuTag.DEVICE_STATE, JsonUtils.objectToJson(deviceControl));
    }

    @Override
    public void setState(IDevice device, DeviceState state) {
        RunningLog.run("设置空调：" + device.getDeviceName() + "," + state);
        DeviceControl deviceControl = new DeviceControl();
        deviceControl.setDeviceCode(device.getDeviceCode());
        deviceControl.setControl(state.getStateValue());
        deviceControl.setDeviceType(device.getDeviceTypeCode());
        sender.sendMsg(CommuTag.DEVICE_STATE, JsonUtils.objectToJson(deviceControl));
    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }
}
