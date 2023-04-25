package com.sunmnet.mediaroom.tabsp.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.RegisterInfo;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.databinding.DialogFaultDescriptionBinding;

import java.io.IOException;

import okhttp3.Response;

/**
 * Create by WangJincheng on 2021-12-15
 * 故障描述对话框
 */

public class FaultDescriptionDialog extends DialogFragment {

    private final static String ENVIRONMENTAL_EQUIPMENT_FAILURE = "环境设备故障";
    private final static String MULTIMEDIA_DEVICE_FAILURE = "多媒体设备故障";
    private final static String AUDIO_DEVICE_FAILURE = "音频设备故障";
    private final static String EQUIPMENT_CIRCUIT_FAILURE = "设备线路故障";
    private final static String OTHER_EQUIPMENT_ISSUES = "其他设备问题";

    private DialogFaultDescriptionBinding mBinding = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        mBinding = DialogFaultDescriptionBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        mBinding.btnCancel.setOnClickListener(v -> dismiss());
        mBinding.btnSubmit.setOnClickListener(v -> {
            int checkId = mBinding.rgFaultDescription.getCheckedRadioButtonId();
            if (checkId != -1) {
                switch (checkId) {
                    case R.id.environmental_equipment_failure:
                        submitFault(ENVIRONMENTAL_EQUIPMENT_FAILURE);
                        break;
                    case R.id.multimedia_device_failure:
                        submitFault(MULTIMEDIA_DEVICE_FAILURE);
                        break;
                    case R.id.audio_device_failure:
                        submitFault(AUDIO_DEVICE_FAILURE);
                        break;
                    case R.id.equipment_circuit_failure:
                        submitFault(EQUIPMENT_CIRCUIT_FAILURE);
                        break;
                    case R.id.other_equipment_issues:
                        submitFault(OTHER_EQUIPMENT_ISSUES);
                        break;
                }
            } else {
                ToastUtil.show(getContext(), "请选择其中一项!");
            }
        });
    }

    /**
     * 提交故障描述
     * @param faultDescribe
     */
    private void submitFault(String faultDescribe) {
        mBinding.btnSubmit.setEnabled(false);
        mBinding.btnCancel.setEnabled(false);
        RegisterInfo registerInfo = TabspApplication.getInstance().getConfig().getRegisterInfo();
        if (registerInfo != null) {
            ThreadUtils.execute(() -> {
                try {
                    String url = "http://" + registerInfo.getPlatformIp() + ":" + registerInfo.getPlatformPort() + "/device-svr/api/addTroubleOrder";
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("classroomCode", registerInfo.getClassroomCode());
                    jsonObject.addProperty("reportingId", "");
                    jsonObject.addProperty("troubleDescribe", faultDescribe);
                    Response response = OkHttpUtil.postJson(url, jsonObject.toString());
                    requireActivity().runOnUiThread(() -> {
                        mBinding.btnSubmit.setEnabled(true);
                        mBinding.btnCancel.setEnabled(true);
                    });
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        JsonObject resJson = new JsonParser().parse(res).getAsJsonObject();
                        boolean isSuccess = resJson.get("success").getAsBoolean();
                        if (isSuccess) {
                            ToastUtil.show(requireContext(), "提交成功！");
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(() -> {
                                if (isVisible()) {
                                    dismiss();
                                }
                            }, 2000);
                        } else {
                            String message = resJson.get("msg").getAsString();
                            ToastUtil.show(requireContext(), "提交失败！" + message);
                        }
                    } else {
                        ToastUtil.show(requireContext(), "提交失败！" + response.message());
                    }
                } catch (IOException e) {
                    requireActivity().runOnUiThread(() -> {
                        mBinding.btnSubmit.setEnabled(true);
                        mBinding.btnCancel.setEnabled(true);
                    });
                    e.printStackTrace();
                }
            });
        }
    }
}
