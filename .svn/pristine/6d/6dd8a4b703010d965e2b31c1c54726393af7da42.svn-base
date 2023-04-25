package com.sunmnet.mediaroom.tabsp.ui.adapter.factory;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.HexUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.protocol.SerialControl;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.bean.AudioMatrixChannelVo;
import com.sunmnet.mediaroom.tabsp.bean.AudioMatrixResetEvent;
import com.sunmnet.mediaroom.tabsp.databinding.AudioMatrixDeviceBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.dialog.EditChannelNameDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public class AudioMatrixFactory implements IHolder.HolderFactory {

    private BaseActivity activity;

    public AudioMatrixFactory(BaseActivity activity) {
        this.activity = activity;
    }

    private class Holder extends AbstractHolder<AudioMatrixDeviceBinding, AudioMatrixChannelVo> implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {
        // 对象第一次调用
        private boolean isFirst = true;
        private static final String PREVIOUS_VOL = "previous_vol";
        private static final String LATEST_VOL = "latest_vol";
        private static final int MAX_VOL = 24;
        private static final int MIN_VOL = 0;
        // 音量操作值队列
        private LinkedBlockingQueue<HashMap<String, Integer>> taskQueue;
        // 音量操作线程
        private Thread taskThread;
        private AudioMatrixDeviceBinding audioMatrixDeviceBinding;

        public Holder() {
            this.taskQueue = new LinkedBlockingQueue<>();
            this.taskThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        HashMap<String, Integer> volData = taskQueue.take();
                        executeVolTask(volData.get(PREVIOUS_VOL), volData.get(LATEST_VOL));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    RunningLog.info("音频处理操作线程已停止");
                }
            });
            this.taskThread.start();
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }

        @Override
        public void bindView(View view) {
            super.bindView(view);
        }

        @Override
        public void setProperty(AudioMatrixDeviceBinding audioMatrixDeviceBinding, AudioMatrixChannelVo audioMatrixChannel) {
            super.setProperty(audioMatrixDeviceBinding, audioMatrixChannel);
            this.audioMatrixDeviceBinding = audioMatrixDeviceBinding;
            audioMatrixDeviceBinding.name.setText(audioMatrixChannel.getName());
            audioMatrixDeviceBinding.name.setOnLongClickListener(view -> {
                showEditChannelNameDialog();
                return false;
            });
            audioMatrixDeviceBinding.toggle.setChecked(audioMatrixChannel.getState());
            audioMatrixDeviceBinding.seekbar.setTouchEnable(false);
            audioMatrixDeviceBinding.seekbar.setMax(MAX_VOL);
            // audioMatrixDeviceBinding.seekbar.setProgress(audioMatrixChannel.getVolume());
            if (isFirst) {
                isFirst = false;
                // 一开始就默认为50%
                audioMatrixDeviceBinding.seekbar.setProgress(MAX_VOL / 2);
            }
            audioMatrixDeviceBinding.seekbar.setEnabled(audioMatrixChannel.getState());
            audioMatrixDeviceBinding.seekbar.setOnSeekBarChangeListener(this);
            audioMatrixDeviceBinding.toggle.setOnCheckedChangeListener(this);
            audioMatrixDeviceBinding.btnVolumnMinus.setOnClickListener(view -> {
                if (audioMatrixDeviceBinding.seekbar.isEnabled() && lastVolume >= 0) {
                    audioMatrixDeviceBinding.seekbar.setProgress(lastVolume - 1);
                    HashMap<String, Integer> volData = new HashMap<>();
                    volData.put(PREVIOUS_VOL, lastVolume);
                    volData.put(LATEST_VOL, audioMatrixDeviceBinding.seekbar.getProgress());
                    try {
                        taskQueue.put(volData);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lastVolume = audioMatrixDeviceBinding.seekbar.getProgress();

                    AudioMatrixChannelVo property = getProperty();
                    property.setVolume(audioMatrixDeviceBinding.seekbar.getProgress());

                    // EventBus.getDefault().post(property);
                }
            });
            audioMatrixDeviceBinding.btnVolumnPlus.setOnClickListener(view -> {
                if (audioMatrixDeviceBinding.seekbar.isEnabled() &&
                        lastVolume <= audioMatrixDeviceBinding.seekbar.getMax()) {
                    audioMatrixDeviceBinding.seekbar.setProgress(lastVolume + 1);

                    HashMap<String, Integer> volData = new HashMap<>();
                    volData.put(PREVIOUS_VOL, lastVolume);
                    volData.put(LATEST_VOL, audioMatrixDeviceBinding.seekbar.getProgress());
                    try {
                        taskQueue.put(volData);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lastVolume = audioMatrixDeviceBinding.seekbar.getProgress();

                    AudioMatrixChannelVo property = getProperty();
                    property.setVolume(audioMatrixDeviceBinding.seekbar.getProgress());

                    // EventBus.getDefault().post(property);
                }
            });

            lastVolume = audioMatrixDeviceBinding.seekbar.getProgress();
        }

        void volEnable() {
            String channel = HexUtil.byteToHex((byte) property.getChannel());
            String code = "48180004" + channel + "02004D";
            control(code);
        }

        void volDisable() {
            String channel = HexUtil.byteToHex((byte) property.getChannel());
            String code = "48180004" + channel + "01004D";
            control(code);
        }

        void volPlus() {
            String channel = HexUtil.byteToHex((byte) property.getChannel());
            String code = "48180005" + channel + "01004D";
            control(code);
        }

        void volMinus() {
            String channel = HexUtil.byteToHex((byte) property.getChannel());
            String code = "48180005" + channel + "02004D";
            control(code);
        }

        /**
         * 设置音量值
         * @param value 音量值
         */
        void volSetValue(int value) {
            if (value < MIN_VOL || value > MAX_VOL) return;
            String channel = HexUtil.byteToHex((byte) property.getChannel());
            String volValue = HexUtil.byteToHex((byte) value);
            String code = "48180006" + channel + volValue + "004D";
            control(code);
        }

        /**
         * 设置默认场景
         */
        void volSetDefaultSense() {
            String code = "481800020100004D";
            control(code);
        }

        private void control(String code) {
            SerialControl serialControl = new SerialControl();
            serialControl.setOperateMethod(3);
            SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
            controlDevice.setDeviceCode(property.getDevice().getDeviceCode());
            controlDevice.setDeviceType(property.getDevice().getDeviceTypeCode());
            controlDevice.setControlCmd(code);
            List<SerialControl.DevicesBean> controlDevices = Collections.singletonList(controlDevice);
            serialControl.setDevices(controlDevices);
            Controller.getInstance().sendMsg(CommuTag.SERIAL_CONTROL, JacksonUtil.objToJsonStr(serialControl));
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            lastVolume = seekBar.getProgress();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            AudioMatrixChannelVo property = getProperty();
            property.setVolume(seekBar.getProgress());
            // 暂时不用保存
            // EventBus.getDefault().post(property);
        }

        int lastVolume;

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!buttonView.isPressed())
                return;
            binding.seekbar.setEnabled(isChecked);
            AudioMatrixChannelVo property = getProperty();
            if (isChecked) {
                volEnable();
            } else {
                volDisable();
            }
            property.setState(isChecked);
            // 暂时不用保存
            // EventBus.getDefault().post(getProperty());
        }

        @Subscribe
        public void onReset(AudioMatrixResetEvent event) {
            reset();
        }

        /**
         * 显示修改通道名弹窗
         */
        private void showEditChannelNameDialog() {
            EditChannelNameDialog dialog = new EditChannelNameDialog();
            dialog.setAudioMatrixChannelVo(getProperty());
            dialog.show(activity.getSupportFragmentManager(), "editChannelNameDialog");
        }

        /**
         * 执行音量操作
         * @param previousVol 上一次音量
         * @param lastVol 最后一次音量
         */
        private void executeVolTask(int previousVol, int lastVol) {
            int count = Math.abs(lastVol - previousVol);
            if (lastVol > previousVol) {
                for (int i = 0; i < count; i++) {
                    volPlus();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (int i = 0; i < count; i++) {
                    volMinus();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 停止操作音量线程
         */
        public void stopVolTaskThread() {
            if (this.taskThread != null && this.taskThread.isAlive()) {
                this.taskThread.interrupt();
            }
        }

        /**
         * 重设音频设备
         */
        public void reset() {
            this.audioMatrixDeviceBinding.seekbar.setProgress(MAX_VOL / 2);
            lastVolume = MAX_VOL / 2;
        }
    }

    @Override
    public IHolder newHolder() {
        return new Holder();
    }
}