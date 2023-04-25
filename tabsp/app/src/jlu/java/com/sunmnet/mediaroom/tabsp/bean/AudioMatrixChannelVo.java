package com.sunmnet.mediaroom.tabsp.bean;

import com.sunmnet.mediaroom.device.bean.IDevice;

public class AudioMatrixChannelVo extends AudioMatrixChannelDto {

    transient IDevice device;

    public AudioMatrixChannelVo() {
    }

    public AudioMatrixChannelVo(AudioMatrixChannelDto dto) {
        super(dto.channel, dto.state, dto.volume, dto.name);
    }

    public IDevice getDevice() {
        return device;
    }

    public void setDevice(IDevice device) {
        this.device = device;
    }
}
