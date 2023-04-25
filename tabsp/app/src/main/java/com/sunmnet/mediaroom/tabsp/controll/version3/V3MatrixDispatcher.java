package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.matrix.MatrixView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixInterfaceBinding;
import com.sunmnet.mediaroom.tabsp.databinding.V3MatrixBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.MatrixInterfaceHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import java.util.List;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_DEVICE_MATRIX)
public class V3MatrixDispatcher extends AbstractFragmentDispatcher implements MatrixView.OnMatrixSelectListener, AdapterView.OnItemClickListener {
    BaseActivity activity;
    V3MatrixBinding binding;

    @Override
    public void dispatch(View view) {
        binding = DataBindingUtil.bind(view);
        MatrixConfig config = Controller.getInstance().getMatrixConfig();
        TextView textView = new TextView(view.getContext());
        int wh = view.getContext().getResources().getDimensionPixelSize(R.dimen.px_100);
        textView.setWidth(wh);
        textView.setHeight(wh);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.circle_default_drawable);
        binding.matrixview3.addCenterView(textView);
        binding.matrixview3.apply(config);
        binding.matrixview3.setOnMatrixSelectListener(this);
        HolderAdapter adapter = new HolderAdapter(R.layout.tabsp_matrix_interface_item_layout, new IHolder.HolderFactory() {
            @Override
            public IHolder newHolder() {
                return new MatrixInterfaceHolder(SELECTED_TEXT_COLOR, UNSELECTED_TEXT_COLOR);
            }
        });
        adapter.setData(config.getInputInterface());
        binding.setInputAdapter(adapter);
        binding.matrix3InputGrid.setOnItemClickListener(this);
        binding.matrix3InputGrid.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.matrix3InputGrid.performItemClick(binding.matrix3InputGrid.getChildAt(0), 0, binding.matrix3InputGrid.getItemIdAtPosition(0));
            }
        }, 50);
    }

    int SELECTED_TEXT_COLOR, UNSELECTED_TEXT_COLOR;

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            SELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.common_text_color);
            UNSELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(activity, R.attr.device_black_text_color);
            this.dispatch(view);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_matrix_for_free;
    }

    @Override
    public void onMatrixSelected(String input, String output) {
        Controller.getInstance().controll(input, output);
    }

    @Override
    public void onMatrixSelected(String input, List<MatrixInterface> interfaces) {
        String[] outputs = new String[interfaces.size()];
        for (int i = 0, size = interfaces.size(); i < size; i++) {
            outputs[i] = interfaces.get(i).input;
        }
        Controller.getInstance().controll(input, outputs);
    }

    AbstractHolder prevInputSelected;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<MatrixInterfaceBinding, MatrixInterface> holder = (AbstractHolder) view.getTag();
        if (holder != prevInputSelected) {
            if (prevInputSelected != null) prevInputSelected.setSelected(false);
            holder.setSelected(true);
            prevInputSelected = holder;
            binding.matrixview3.setInput(holder.getProperty());
        }
    }

}
