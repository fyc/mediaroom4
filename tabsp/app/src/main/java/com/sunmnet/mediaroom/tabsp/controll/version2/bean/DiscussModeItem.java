package com.sunmnet.mediaroom.tabsp.controll.version2.bean;

import com.sunmnet.mediaroom.tabsp.bean.AbstractItem;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.interfaces.ICommonItem;

public class DiscussModeItem extends AbstractItem<SysSpTempConfigFileDto.DiscussMode> implements ICommonItem {
    public DiscussModeItem(SysSpTempConfigFileDto.DiscussMode mode, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground);
        this.t = mode;
    }

    public DiscussModeItem(SysSpTempConfigFileDto.DiscussMode mode, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground, Object selectTextColor, Object unselectTextColor) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground, selectTextColor, unselectTextColor);
        this.t = mode;
    }

    @Override
    public String getName() {
        return t.getValue();
    }
}
