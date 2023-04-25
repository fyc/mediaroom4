package com.sunmnet.mediaroom.tabsp.bean;

import java.io.Serializable;

public class AudioMatrixChannelDto implements Serializable {
    protected int channel;
    protected boolean state;
    protected int volume;
    protected String name;

    public AudioMatrixChannelDto() {
    }

    public AudioMatrixChannelDto(int channel, boolean state, int volume, String name) {
        this.channel = channel;
        this.state = state;
        this.volume = volume;
        this.name = name;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
