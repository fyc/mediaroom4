package com.sunmnet.mediaroom.tabsp.controll.version2;

import android.databinding.DataBindingUtil;

import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.controll.version2.bean.DiscussModeItem;
import com.sunmnet.mediaroom.tabsp.databinding.DiscussItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.SmallItemHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 自定义讨论模式
 */
@Route(path = Version2Dispatcher.CONTROLLER_VERSION2_CUSTOM_TALKING)
public class CustomDiscussDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    int DFAULT_TEXT_COLOR, DEFAULT_SELECT_TEXT_COLOR;

    ItemHolder preInput;
    private View.OnClickListener inputClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null && v.getTag() instanceof ItemHolder) {
                ItemHolder holder = (ItemHolder) v.getTag();
                if (holder != preInput) {
                    if (preInput != null) preInput.setSelected(false);
                    preInput = holder;
                    holder.setSelected(true);
                    //清空input选中
                    for (ItemHolder item : outputHolders
                    ) {
                        item.setSelected(false);
                    }
                    outputHolders.clear();
                }
            }
        }
    };
    List<ItemHolder> outputHolders = new ArrayList<>();
    private View.OnClickListener outputClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null && v.getTag() instanceof ItemHolder) {
                ItemHolder holder = (ItemHolder) v.getTag();
                if (!outputHolders.contains(holder)) {
                    holder.setSelected(true);
                    outputHolders.add(holder);
                    //做操作
                } else {
                    outputHolders.remove(holder);
                    holder.setSelected(false);
                }
            }
        }
    };

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        List<SysSpTempConfigFileDto.DiscussMode> modes = ((Version2Dispatcher) TabspApplication.getInstance().getDispatcher()).getDiscussModes();
        View.OnClickListener clickListener = null;
        for (SysSpTempConfigFileDto.DiscussMode mode : modes
        ) {
            FrameLayout parent = view.findViewWithTag(mode.getKey());
            if (parent != null && parent.getChildCount() > 0) {
                View itemView = parent.getChildAt(0);
                if (mode.getKey().contains("input")) {
                    clickListener = inputClick;
                } else clickListener = outputClick;
                DiscussModeItem item = new DiscussModeItem(mode, mode.getSelectedIcon(), mode.getUnselectedIcon(),
                        R.drawable.device_on_corner_background, R.drawable.device_off_corner_background,
                        DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
                DiscussItem viewItem = DataBindingUtil.bind(itemView);
                if (viewItem != null) {
                    ItemHolder holder = new ItemHolder();
                    holder.bindView(itemView);
                    holder.setProperty(viewItem, item);
                    itemView.setTag(holder);
                    itemView.setOnClickListener(clickListener);
                }
            }
        }
    }


    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            DFAULT_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.colorPrimary);
            DEFAULT_SELECT_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.common_text_color);
        } else return;
        this.dispatch(view);
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version2_custom_discuss_layout;
    }

    private static class ItemHolder extends SmallItemHolder<DiscussItem, DiscussModeItem> {
        @Override
        public void setProperty(DiscussItem modeItem, DiscussModeItem item) {
            this.property = item;
            this.binding = modeItem;
            this.binding.setItem(item);
            setSelected(false);
        }
    }
}
