package com.sunmnet.mediaroom.wirelessprojection.bean;

import com.bozee.managerappsdk.models.ClientDevice;

public class WirelessDevice {
    ClientDevice client;
    WirelessState state = WirelessState.DISCONNECTED;

    public WirelessDevice(ClientDevice client) {
        this.client = client;
    }

    public ClientDevice getClient() {
        return client;
    }

    public void setClient(ClientDevice client) {
        this.client = client;
    }

    public WirelessState getState() {
        return state;
    }

    public void setState(WirelessState state) {
        this.state = state;
    }
}
