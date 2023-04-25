package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.media.StreamControl;
import com.sunmnet.mediaroom.brand.impl.camera.MonitorCamera;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TemplateMonitorFragment extends Fragment {

    private StreamControl streamControl;
    private TabLayout tabLayout;
    private List<CameraDeviceResponse.Result> cameraList;

    public static TemplateMonitorFragment newInstance() {
        Bundle args = new Bundle();
        TemplateMonitorFragment fragment = new TemplateMonitorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_monitor, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.tabLayout);
        streamControl = view.findViewById(R.id.video);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (cameraList != null && tab != null && tab.getPosition() < cameraList.size()) {
                    String url = MonitorCamera.create(cameraList.get(tab.getPosition())).getPlayUrl();
                    streamControl.setVideoList(Collections.singletonList(url));
                    streamControl.refreshControlData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMonitor();
    }

    private void getMonitor() {
        if (!DeviceApp.getApp().isRegistered()) {
            return;
        }
        //调用接口获取教室的监控摄像头数据
        RunningLog.run("正在请求教室的监控摄像头数据：" + DeviceApp.getApp().getClassroomCode());
        ApiManager.getDeviceApi()
                .getCameraByClassroom(DeviceApp.getApp().getClassroomCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<CameraDeviceResponse>() {
                    @Override
                    public void onNext(CameraDeviceResponse response) {
                        if (response == null) {
                            RunningLog.debug("获取教室的摄像头数据解析出错：null");
                            return;
                        }
                        if (!response.isSuccess() || response.getObj() == null) {
                            RunningLog.debug("获取教室的摄像头失败，success: " + response.isSuccess() + ".obj:" + response.getObj());
                            return;
                        }
                        if (response.getObj().size() == 0) {
                            ToastUtil.show(getContext(), "该教室无监控摄像头");
                            cameraList = null;
                            tabLayout.setVisibility(View.GONE);
                        }
                        cameraList = new ArrayList<>(response.getObj());
                        tabLayout.setVisibility(View.VISIBLE);
                        tabLayout.removeAllTabs();
                        for (CameraDeviceResponse.Result result : cameraList) {
                            tabLayout.addTab(tabLayout.newTab().setText(result.getDeviceName()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.error("请求教室的监控摄像头数据失败：" + e.getMessage());
                    }
                });
    }
}
