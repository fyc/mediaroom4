package com.sunmnet.mediaroom.tabsp.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.BaseDialogFragment;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.AudioMatrixChannelVo;
import com.sunmnet.mediaroom.tabsp.databinding.TabspCustomEditChannelDialogBinding;

import org.greenrobot.eventbus.EventBus;

/**
 * Create by WangJincheng on 2021/6/7
 * 修改通道名称弹窗
 */

public class EditChannelNameDialog extends BaseDialogFragment {

    private TabspCustomEditChannelDialogBinding binding;

    private AudioMatrixChannelVo audioMatrixChannelVo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tabsp_custom_edit_channel_dialog, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cancel.setOnClickListener(view1 -> dismiss());
        binding.confirm.setOnClickListener(view1 -> {
            String s = binding.edtDialogChannelName.getText().toString();
            if (!s.isEmpty()) {
                if (this.audioMatrixChannelVo != null) {
                    this.audioMatrixChannelVo.setName(s);
                    EventBus.getDefault().post(this.audioMatrixChannelVo);
                    dismiss();
                }
            } else {
                ToastUtil.show(binding.getRoot().getContext(), "通道名不能为空");
            }
        });
    }

    public void setAudioMatrixChannelVo(AudioMatrixChannelVo audioMatrixChannelVo) {
        this.audioMatrixChannelVo = audioMatrixChannelVo;
    }
}
