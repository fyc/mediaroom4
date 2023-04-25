package com.sunmnet.mediaroom.tabsp.controll.version2;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MatrixInterfaceItem;
import com.sunmnet.mediaroom.tabsp.common.DiscussionHelper;
import com.sunmnet.mediaroom.tabsp.databinding.DiscussionBinding;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItem;
import com.sunmnet.mediaroom.tabsp.databinding.NumberItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.SmallItemHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnItemClick;
/**
 * 讨论模式
 * */
@Route(path = Version2Dispatcher.CONTROLLER_VERSION2_TALKING)
public class DiscussionDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    DiscussionBinding binding;
    AbstractHolder<?, MatrixInterfaceItem> prevInput;

    @OnItemClick({R.id.inputGrid, R.id.inputGroup})
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<?, MatrixInterfaceItem> holder = (AbstractHolder<?, MatrixInterfaceItem>) view.getTag();
        if (prevInput != holder) {
            if (prevInput != null) prevInput.setSelected(false);
            holder.setSelected(true);
            prevInput = holder;
        }
    }

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        binding.tagName.setText(TabspApplication.getInstance().getString(R.string.name_talking_mode));
        //加载本地配置
        /**
         * 加载左边数据
         * */
        List<MatrixInterfaceItem> items = DiscussionHelper.getInputItems();
        BindingAdapter adapter = new BindingAdapter(R.layout.tabsp_mode_item_layout, new ItemFactory());
        adapter.setData(items);
        binding.setInputAdapter(adapter);

        List<MatrixInterfaceItem> groups = DiscussionHelper.getInputGroups();
        adapter = new BindingAdapter(R.layout.tabsp_discusstion_group_item, new GroupItemFactory());
        adapter.setData(groups);
        binding.setInputGroupAdapter(adapter);

        /**
         * 加载右边数据
         * */
        items = DiscussionHelper.getOutputItems();
        adapter = new BindingAdapter(R.layout.tabsp_mode_item_layout, new ItemFactory());
        adapter.setData(items);
        binding.setOutputAdapter(adapter);

        groups = DiscussionHelper.getOutputGroups();
        adapter = new BindingAdapter(R.layout.tabsp_discusstion_group_item, new GroupItemFactory());
        adapter.setData(groups);
        binding.setOutputGroupAdapter(adapter);
    }


    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            DiscussionHelper.init(activity);
        } else return;
        this.dispatch(view);
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version2_discussion_layout;
    }

    private static class ItemFactory implements IHolder.HolderFactory {

        @Override
        public IHolder newHolder() {
            return new SmallItemHolder<ModeItem, MatrixInterfaceItem>() {
                @Override
                public void setProperty(ModeItem modeItem, MatrixInterfaceItem matrixInterfaceItem) {
                    this.property = matrixInterfaceItem;
                    this.binding = modeItem;
                    this.binding.setMode(matrixInterfaceItem);
                    setSelected(false);
                }
            };
        }
    }

    private static class GroupItemFactory implements IHolder.HolderFactory {

        @Override
        public IHolder newHolder() {
            return new SmallItemHolder<NumberItem, MatrixInterfaceItem>() {
                TextView logoText;

                @Override
                public void bindView(View view) {
                    logoText = view.findViewById(R.id.device_img);
                    holder = view.findViewById(R.id.device_image_holder);
                    textView = view.findViewById(R.id.device_name);
                }

                protected void loadIcon(boolean selected) {
                    logoText.setText(property.getEntity().nickName);
                    Object res = null;
                    if (selected) res = property.getSelectTextColor();
                    else res = property.getUnselectTextColor();
                    if (res != null)
                        this.logoText.setTextColor(Integer.parseInt(res.toString()));
                    else this.logoText.setTextColor(Color.WHITE);
                }

                protected void loadBackground(boolean selected) {
                    Object res = null;
                    if (selected) res = property.getSelectBackgound();
                    else res = property.getUnselectBackground();
                    if (res instanceof String) {
                        CommonUtil.loadBackgroundImage(TabspApplication.getInstance(), res.toString(), holder);
                    } else if (res instanceof Integer) {
                        holder.setBackgroundResource((int) res);
                    } else holder.setBackgroundColor(Color.TRANSPARENT);
                }

                protected void loadTextColor(boolean selected) {
                    //这里不设置实际标题文本颜色，改成icon里面的文本颜色
                }

                @Override
                public void setProperty(NumberItem modeItem, MatrixInterfaceItem matrixInterfaceItem) {
                    this.property = matrixInterfaceItem;
                    this.binding = modeItem;
                    this.binding.setItem(matrixInterfaceItem);
                    setSelected(false);
                }
            };
        }
    }
}
