package com.sunmnet.mediaroom.tabsp.bean;

import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.tabsp.interfaces.ICommonItem;

public class MatrixInterfaceItem extends AbstractItem<MatrixInterface> implements ICommonItem {

    public MatrixInterfaceItem(MatrixInterface matrixInterface, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground);
        this.t = matrixInterface;
    }

    public MatrixInterfaceItem( MatrixInterface matrixInterface, Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground, Object selectTextColor, Object unselectTextColor) {
        super(selectIcon, unselectIcon, selectBackgound, unselectBackground, selectTextColor, unselectTextColor);
        this.t = matrixInterface;
    }

    public MatrixInterfaceItem( MatrixInterface matrixInterface, Object selectIcon, Object unselectIcon) {
        super(selectIcon, unselectIcon, null, null);
        this.t = matrixInterface;
    }

    @Override
    public String getName() {
        return t.inputName;
    }
}
