package com.sunmnet.mediaroom.tabsp.bean;


public class SettingMode extends AbstractItem<Object> {

    public SettingMode(String name, int selected, int unselected, String mode) {
        super(null, null, null, null);
        this.selected = selected;
        this.unselected = unselected;
        this.name = name;
        this.mode = mode;
    }

    int selected;
    int unselected;
    String name;
    String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getUnselected() {
        return unselected;
    }

    public void setUnselected(int unselected) {
        this.unselected = unselected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
