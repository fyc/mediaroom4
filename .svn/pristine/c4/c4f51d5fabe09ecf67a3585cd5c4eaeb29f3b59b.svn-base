package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceSceneItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;

public class Version3MatrixSceneHolder extends AbstractHolder<DeviceSceneItem, DeviceScene> {
    DeviceSceneItem sceneItem;
    int selected, unselect;
    TextView textView;

    public Version3MatrixSceneHolder() {
    }

    public Version3MatrixSceneHolder(int selected, int unselect) {
        this.selected = selected;
        this.unselect = unselect;
    }

    @Override
    public void bindView(View view) {
        this.textView = (TextView) view;
    }

    @Override
    public void setProperty(DeviceSceneItem sceneItem, DeviceScene tabspScene) {
        this.sceneItem = sceneItem;
        this.property = tabspScene;
        sceneItem.setScene(property);
        setSelected(false);
    }

    @Override
    public DeviceSceneItem getViewDataBinding() {
        return this.sceneItem;
    }

    @Override
    public DeviceScene getProperty() {
        return property;
    }

    @Override
    public void setSelected(boolean selected) {
        textView.setBackgroundResource(selected ? R.drawable.scene_item_select_background : R.drawable.scene_item_background);
        int color = selected ? this.selected : unselect;
        if (color != 0) this.textView.setTextColor(color);
        this.sceneItem.sceneName.setEllipsize(selected ? TextUtils.TruncateAt.valueOf("MARQUEE") : TextUtils.TruncateAt.valueOf("END"));
    }
}
