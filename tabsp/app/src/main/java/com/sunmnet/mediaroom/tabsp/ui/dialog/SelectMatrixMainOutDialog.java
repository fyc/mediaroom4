package com.sunmnet.mediaroom.tabsp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.databinding.DialogSelectMainOutScreenBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.MatrixMainOutAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by WangJincheng on 2021-09-02
 * 选择视频矩阵主显示通道对话框
 */

public class SelectMatrixMainOutDialog extends DialogFragment {
    private DialogSelectMainOutScreenBinding mBinding = null;
    private MatrixMainOutAdapter matrixMainOutAdapter;
    private List<MatrixInterface> outputList = Controller.getInstance().getMatrixConfig().getOutputInterface();
    private List<MatrixInterface> inputList = Controller.getInstance().getMatrixConfig().getInputInterface();

    private int selectPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        mBinding = DialogSelectMainOutScreenBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        matrixMainOutAdapter = new MatrixMainOutAdapter(requireContext(), outputList);
        mBinding.spSelectOut.setAdapter(matrixMainOutAdapter);
        mBinding.spSelectOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RunningLog.run("选择了" + position);
                selectPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String defaultChannel = SharePrefUtil.getString(
            requireContext(),
            TabspApplication.DB_NAME,
            TabspApplication.DEFAULT_MAIN_OUTPUT_CHANNEL);
        if (defaultChannel != null) {
            selectPosition = Integer.parseInt(defaultChannel) - 1;
            if (selectPosition < outputList.size()) {
                mBinding.spSelectOut.setSelection(selectPosition);
            }
        }

        mBinding.btnSave.setOnClickListener(v -> {
            SharePrefUtil.saveValue(
                    requireContext(),
                    TabspApplication.DB_NAME,
                    TabspApplication.DEFAULT_MAIN_OUTPUT_CHANNEL,
                    String.valueOf((selectPosition + 1)));
            selectMainOutputChannel(selectPosition + 1, inputList.size(), outputList.size());
            ToastUtil.show(requireContext(), "保存成功");
            dismiss();
        });

        mBinding.btnCancel.setOnClickListener(v -> dismiss());
    }

    /**
     * 选择主显示屏操作
     * @param channel Int
     */
    public static void selectMainOutputChannel(int channel, int inputCount, int outputCount) {
        if (inputCount == 0 || outputCount == 0) return;
        new Thread(() -> {
            if (channel <= outputCount) {
                Map<String, List<String>> interfaces = new HashMap<>();
                ArrayList<String> values = new ArrayList<>();
                for (int i = 0; i <= inputCount; i++) {
                    values.add(String.valueOf(i));
                }
                String keyName = String.valueOf(channel);
                interfaces.put(keyName, values);
                Controller.getInstance().edidControl(interfaces);
            } else {
                RunningLog.run("选择视频矩阵主屏通道不存在");
            }
        }).start();
    }
}
