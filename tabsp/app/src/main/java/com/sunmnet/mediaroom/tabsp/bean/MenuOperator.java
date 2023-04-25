package com.sunmnet.mediaroom.tabsp.bean;


public class MenuOperator extends AbstractItem<MenuEntity> {
    public MenuEntity getEntity() {
        return t;
    }

    public MenuOperator(MenuEntity entity, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground, Object selectTextColor, Object unselectTextColor) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground, selectTextColor, unselectTextColor);
        this.t = entity;
    }

    public MenuOperator(MenuEntity entity, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground);
        this.t = entity;
    }


}
