package com.sunmnet.mediaroom.tabsp.bean;

import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.tabsp.interfaces.ICommonItem;

public class LectureItem extends AbstractItem<MatrixScene> {
    public LectureItem(MatrixScene scene, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground);
        this.t = scene;
    }

    public LectureItem(MatrixScene scene, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground, Object selectTextColor, Object unselectTextColor) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground, selectTextColor, unselectTextColor);
        this.t = scene;
    }

    public LectureItem(MatrixScene scene, Object selectIcon, Object unselectIcon) {
        super(selectIcon, unselectIcon, null, null);
        this.t = scene;
    }

    @Override
    public String getName() {
        return this.t.sceneName;
    }
}
