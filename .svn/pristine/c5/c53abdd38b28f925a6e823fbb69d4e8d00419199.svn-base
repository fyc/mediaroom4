package com.sunmnet.mediaroom.wirelessprojection;

import android.util.Log;

import com.bozee.managerappsdk.ConnectionManager;
import com.bozee.managerappsdk.models.AnDisplayInfo;
import com.bozee.managerappsdk.models.ClientDevice;
import com.bozee.managerappsdk.models.PreferenceItemInfo;
import com.bozee.managerappsdk.models.SystemInfo;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.util.JsonUtils;
import com.sunmnet.mediaroom.wirelessprojection.bean.WirelessParam;
import com.sunmnet.mediaroom.wirelessprojection.interfaces.CallbackAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class WirelessCallBacks implements CallbackAdapter {
    static final String TAG = WirelessCallBacks.class.getName();
    WirelessParam param;
    ConnectionManager manager;
    WirelessOperator wirelessOperator;

    public WirelessCallBacks(WirelessParam param, ConnectionManager manager, WirelessOperator wirelessOperator) {
        this.param = param;
        this.manager = manager;
        this.wirelessOperator = wirelessOperator;
    }

    private void postParam() {
        EventBus.getDefault().postSticky(this.param);
    }

    @Override
    public void onClassroomAllClientsOnOffScreenResult() {
        Log.i(TAG, "onClassroomAllClientsOnOffScreenResult");
        //manager.requestAnDisplayInfo();
    }

    @Override
    public void onClassRoomAllClientsWorkOnOutResult() {
        Log.i(TAG, "onClassRoomAllClientsWorkOnOutResult");
        //manager.requestAnDisplayInfo();

    }

    @Override
    public void onClassRoomAllClientLockedResult() {
        Log.i(TAG, "onClassRoomAllClientLockedResult");
        //manager.requestAnDisplayInfo();
    }

    @Override
    public void onUpdateLockStatus(String s, byte b) {
        Log.i(TAG, "onUpdateLockStatus-->" + s + ":" + b);
        //manager.requestAnDisplayInfo();
    }

    @Override
    public void onUpdateWorkOutStatus() {
        Log.i(TAG, "onUpdateWorkOutStatus");
        // manager.requestAnDisplayInfo();

    }

    @Override
    public void onClassRoomKickAllClientsResult() {
        Log.i(TAG, "onClassRoomKickAllClientsResult");

    }

    @Override
    public void processClassRoomKickClientResult() {
        Log.i(TAG, "processClassRoomKickClientResult");

    }

    @Override
    public void onReceiveAnDisplayInfo(AnDisplayInfo anDisplayInfo) {
        //Log.i(TAG,"onReceiveAnDisplayInfo-->" + anDisplayInfo.mDevices != null ? anDisplayInfo.mDevices.size() + " 条数据" : " 没有数据！");
        //Log.i(TAG,"数据：" + JsonUtils.objectToJson(anDisplayInfo));
        //EventBus.getDefault().postSticky(anDisplayInfo);
        this.param.setClientDevices(anDisplayInfo.mDevices);
    }

    @Override
    public void onReceiveDeviceConnected(ClientDevice clientDevice) {
        Log.i(TAG, clientDevice.mName + ":" + clientDevice.mStrIp + " 连接成功！！");
    }

    @Override
    public void onReceiveDeviceRequestConnected(String s) {
        Log.i(TAG, s + " 请求连接！！");
    }

    @Override
    public void onReceiveDeviceDisConnected(String s) {
        Log.i(TAG, s + " 连接成功！！");
    }

    @Override
    public void onReceiveDeviceInfoUpdated(ClientDevice clientDevice) {
        Log.i(TAG, "接收到：" + clientDevice.toString());
        this.param.setClientDevice(clientDevice);
    }

    @Override
    public void onReceiveDirectMirrorResponse() {
        Log.i(TAG, "onReceiveDirectMirrorResponse");
    }

    @Override
    public void onUpdatePermitStatus() {
        Log.i(TAG, "onUpdatePermitStatus");
    }

    @Override
    public void onReceiveClientDeviceThumbnailUpdated(String s, byte[] bytes) {
        //Log.i(TAG,"onReceiveClientDeviceThumbnailUpdated-->" + s);
    }

    @Override
    public void onReceiveTeacherDeviceStateChange(ClientDevice clientDevice) {
        Log.i(TAG, "接收到教师机状态改变：" + clientDevice.toString());
    }

    @Override
    public void onReceiveSharingModeChange(boolean b) {
        Log.i(TAG, "广播模式改变：" + b);
        param.setSingle(b);
        postParam();
        manager.requestAnDisplayInfo();
    }

    @Override
    public void onReceiveDeviceVolume(int i) {
        this.param.setVolumn(i);
    }

    @Override
    public void onReceiveRootDeviceLockStateChange(boolean b) {
        this.param.setLock(b);
        postParam();
        manager.requestAnDisplayInfo();
    }

    @Override
    public void onAuthorizeSuccess(String s) {
        Log.i(TAG, s + "  授权成功！！");
        this.param.setAuthorization(true);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    manager.requestAnDisplaySettingInfo();
                    Thread.sleep(100);
                    manager.requestAnDisplayInfo();
                    Thread.sleep(100);
                    manager.requestSystemInfo();
                    Thread.sleep(100);
                    manager.requestGetCurrentVolumeData();
                    List list = null;
                    do {
                        Thread.sleep(5000);
                        list = param.getClientDevices();
                    } while (list == null || list.size() <= 0);
                    RunningLog.run("客户端连接情况检查完毕！！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onAuthorizeFailed(int i) {
        Log.i(TAG, "  授权失败！！");
        this.param.setAuthorization(false);
        wirelessOperator.onAuthFail();
    }

    @Override
    public void onChannelInactive(int i) {
        Log.i(TAG, i + " 存活");
    }

    @Override
    public void refreshFinish() {
        Log.i(TAG, " 刷新成功！！");
    }

    @Override
    public void onReceiverSettingInfo(List<PreferenceItemInfo> list) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("onReceiverSettingInfo-->");
        for (int i = 0, size = list.size(); i < size; i++) {

            buffer.append(i + 1);
            buffer.append(":");
            buffer.append(list.get(i).toString());
            buffer.append(" ");
        }
        Log.i(TAG, buffer.toString());
    }

    @Override
    public void onReceiverSettingInfoChangeResult() {
        Log.i(TAG, "onReceiverSettingInfoChangeResult");
    }

    @Override
    public void onReceiverSettingInfoUpdate(List<PreferenceItemInfo> list) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("onReceiverSettingInfoUpdate--->");
        for (int i = 0, size = list.size(); i < size; i++) {
            buffer.append(i + 1);
            buffer.append(":");
            buffer.append(list.get(i).toString());
            buffer.append(" ");
        }
        Log.i(TAG, buffer.toString());
    }

    @Override
    public void onReceiverSystemInfo(SystemInfo systemInfo) {
        Log.i(TAG, "onReceiverSystemInfo：" + JsonUtils.objectToJson(systemInfo));
    }
}
