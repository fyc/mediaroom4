package com.sunmnet.mediaroom.wirelessprojection.bean;

import com.bozee.managerappsdk.models.AnDisplayInfo;
import com.bozee.managerappsdk.models.ClientDevice;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.util.JsonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WirelessParam {
    int volumn;
    boolean lock;
    boolean isAuthorization = false;
    boolean isSingle = false;
    boolean isConnected=false;
    Map<String, ClientDevice> deviceMap;

    public List<ClientDevice> getClientDevices() {
        if (this.deviceMap != null) return new ArrayList<>(this.deviceMap.values());
        return new ArrayList<>();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isAuthorization() {
        return isAuthorization;
    }

    public void setAuthorization(boolean authorization) {
        isAuthorization = authorization;
    }

    public void setClientDevices(List<ClientDevice> clientDevices) {
        if (this.deviceMap == null) {
            this.deviceMap = new HashMap<>();
        }
        if (clientDevices.size() < deviceMap.size()) {
            RunningLog.run("设备移除！！！");
            clearNotExits(clientDevices);
        }
        for (ClientDevice device : clientDevices
        ) {
            if (this.deviceMap.containsKey(device.mStrIp)) {
                copy(this.deviceMap.get(device.mStrIp), device);
            } else {
                RunningLog.run("设备增加！" + JsonUtils.objectToJson(device));
                ClientDevice copy = copy(device);
                this.deviceMap.put(copy.mStrIp, copy);
            }
        }
        EventBus.getDefault().postSticky(new AnDisplayInfo(new ArrayList<>(this.deviceMap.values())));
    }

    private void clearNotExits(List<ClientDevice> clientDevices) {
        Map<String, ClientDevice> maps = new HashMap<>();
        for (ClientDevice device : clientDevices) {
            maps.put(device.mStrIp, device);
        }
        List<ClientDevice> cache = new ArrayList<>(this.deviceMap.values());
        for (ClientDevice client : cache
        ) {
            if (!maps.containsKey(client.mStrIp)) {
                this.deviceMap.remove(client.mStrIp);
            }
        }
    }

    private void copy(ClientDevice from, ClientDevice to) {
        to.mWorkOutStatus = from.mWorkOutStatus;
        to.mClientType = from.mClientType;
        to.mDisplayStatus = from.mDisplayStatus;
        to.mIsSelect = from.mIsSelect;
        to.mPermitStatus = from.mPermitStatus;
        to.mLockStatus = from.mLockStatus;
        to.mThumbnailBytes = from.mThumbnailBytes;
        to.mStrIp = from.mStrIp;
        to.mName = from.mName;
    }

    private ClientDevice copy(ClientDevice device) {
        ClientDevice exist = new ClientDevice();
        copy(device, exist);
        return exist;
    }

    public void setClientDevice(ClientDevice device) {
        if (deviceMap != null) {
            if (deviceMap.containsKey(device.mStrIp)) {
                ClientDevice exist = this.deviceMap.get(device.mStrIp);
                exist.mWorkOutStatus = device.mWorkOutStatus;
                exist.mClientType = device.mClientType;
                exist.mDisplayStatus = device.mDisplayStatus;
                exist.mIsSelect = device.mIsSelect;
                exist.mPermitStatus = device.mPermitStatus;
                exist.mLockStatus = device.mLockStatus;
                exist.mThumbnailBytes = device.mThumbnailBytes;
                AnDisplayInfo displayInfo = new AnDisplayInfo(new ArrayList<ClientDevice>(deviceMap.values()));
                EventBus.getDefault().postSticky(displayInfo);
            }
        }
    }

    public int getVolumn() {
        return volumn;
    }

    public void setVolumn(int volumn) {
        this.volumn = volumn;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
