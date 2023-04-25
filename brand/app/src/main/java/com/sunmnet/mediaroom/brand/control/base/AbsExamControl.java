package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.broadcast.AlarmReceiver;
import com.sunmnet.mediaroom.brand.broadcast.BroadcastAction;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.interfaces.control.IExamControl;
import com.sunmnet.mediaroom.brand.bean.control.base.TextStyleControlProperty;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ExamTimetableUtil;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.util.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * h
 * Created by MOETAKU on 2018/5/22.
 */

public abstract class AbsExamControl<P extends TextStyleControlProperty> extends PrefixSuffixTextControl<P> implements IExamControl {

    protected final static HashMap<String, LoadExamTask> taskMap = new HashMap<>();

    protected String examTimetableId;

    public AbsExamControl(Context context) {
        super(context);
    }

    public AbsExamControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public AbsExamControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsExamControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setExamTimetableId(String examId) {
        this.examTimetableId = examId;
    }

    @Override
    public String getExamTimetableId() {
        return examTimetableId;
    }

    @Override
    public void refreshControlData() {
        if (DeviceApp.getApp().isRegistered()) {
            loadExam();
        }
    }

    protected void loadExam() {
        //调用接口获取数据，使用文件缓存
        if (TextUtils.isEmpty(examTimetableId)) {
            setText(null);
            RunningLog.run("考试时间表ID为空，不加载数据");
            return;
        }
        synchronized (taskMap) {
            LoadExamTask loadExamTask = taskMap.get(examTimetableId);
            if (loadExamTask == null) {
                loadExamTask = new LoadExamTask(examTimetableId, taskMap);
                taskMap.put(examTimetableId, loadExamTask);
            }

            loadExamTask.offerQueue(this);
            loadExamTask.start();
        }
    }

    protected abstract void setExamData(ExamDetailResponse.ExamDetail data);

    public static class LoadExamTask {
        String examTimetableId;
        AtomicBoolean loading = new AtomicBoolean();
        LinkedBlockingQueue<AbsExamControl> controlQueue;
        HashMap<String, LoadExamTask> taskMap;

        public LoadExamTask(String examTimetableId, HashMap<String, LoadExamTask> taskMap) {
            this.examTimetableId = examTimetableId;
            this.taskMap = taskMap;
            controlQueue = new LinkedBlockingQueue<>();
        }

        public boolean offerQueue(AbsExamControl examControl) {
            return controlQueue.offer(examControl);
        }

        public boolean isLoading() {
            return loading.get();
        }

        public void start() {
            if (TextUtils.isEmpty(examTimetableId)) {
                RunningLog.run("考试时间表ID为空，不加载数据");
                controlQueue.clear();
                return;
            }
            if (loading.get())
                return;
            if (!loading.compareAndSet(false, true)) {
                return;
            }
            RunningLog.run("正在请求考试时间表数据");
            RunningLog.debug("examTimetableId:" + examTimetableId);
            RunningLog.debug("classroomCode:" + DeviceApp.getApp().getClassroomCode());
            ApiManager.getInformationApi().getExamDetailList(examTimetableId, DeviceApp.getApp().getClassroomCode())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleTaskObserver<ExamDetailResponse>() {
                        @Override
                        public void onNext(ExamDetailResponse examDetailResponse) {
                            if (!loading.compareAndSet(true, false)) {
                                RunningLog.debug("请求考试时间表数据线程异常");
                                return;
                            }
                            if (examDetailResponse == null || !examDetailResponse.isSuccess() || examDetailResponse.getObj().size() == 0) {
                                RunningLog.debug("请求考试时间表数据无数据或数据格式有误");
                                RunningLog.debug("examDetailResponse:" + examDetailResponse);
                                if (examDetailResponse != null) {
                                    RunningLog.debug("examDetailResponse.isSuccess():" + examDetailResponse.isSuccess());
                                    if (examDetailResponse.getObj() != null) {
                                        RunningLog.debug("examDetailResponse.getObj().size():" + examDetailResponse.getObj().size());
                                    }
                                }
                                AbsExamControl control;
                                while ((control = controlQueue.poll()) != null) {
                                    control.setText(null);
                                }
                                taskMap.remove(examTimetableId);

                                if (examDetailResponse == null || !examDetailResponse.isSuccess()) {
                                    // 3分钟后再去刷新控件，来请求平台的考试时间表
                                    RunningLog.run("请求异常，3分钟后再去刷新控件");
                                    Date date = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3));
                                    Intent intent;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                        intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                                    } else {
                                        intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                                    }
                                    AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                                }

                                return;
                            }
                            //写缓存
                            String jsonStr = JacksonUtil.objToJsonStr(examDetailResponse);
                            RunningLog.run("考试时间表:" + jsonStr);
                            FileUtils.writeFile(FileResourceUtil.getExamFolderPath() + FileResourceUtil.getExamTimetableFileName(examTimetableId), jsonStr);

                            ExamDetailResponse.ExamDetail examDetail = ExamTimetableUtil.getNearestExam(examDetailResponse.getObj());
                            examDetail = ExamTimetableUtil.filterTimeTable(examDetail);
                            if (examDetail != null) {
                                if (ExamTimetableUtil.isNow(examDetail)) {
                                    RunningLog.debug("当前考试时间：" + examDetail.getExamDate());
                                    ExamDetailResponse.ExamDetail nextExam = ExamTimetableUtil.getNextExam(examDetailResponse.getObj());
                                    if (nextExam != null) {
                                        RunningLog.debug("下一场考试时间：" + nextExam.getExamDate());
                                        Date curEnd = ExamTimetableUtil.getEndTime(examDetail);
                                        Date date = new Date(curEnd.getTime() + TimeUnit.SECONDS.toMillis(30));

                                        Intent intent;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                            intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        } else {
                                            intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        }
                                        AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                                    } else {
                                        RunningLog.debug("没有下一场考试信息");
                                        Date curEnd = ExamTimetableUtil.getEndTime(examDetail);
                                        Date date = new Date(curEnd.getTime() + TimeUnit.MINUTES.toMillis(5));

                                        Intent intent;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                            intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        } else {
                                            intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        }
                                        AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                                    }
                                } else {
                                    ExamDetailResponse.ExamDetail nextExam = ExamTimetableUtil.getNextExam(examDetailResponse.getObj());
                                    if (nextExam != null) {
                                        RunningLog.debug("考试时间不在当前时间，下一场考试时间：" + nextExam.getExamDate());
                                        Date nextEnd = ExamTimetableUtil.getEndTime(nextExam);
                                        Date date = new Date(nextEnd.getTime() - TimeUnit.SECONDS.toMillis(30));

                                        long timeDiff = date.getTime() - System.currentTimeMillis();
                                        if (timeDiff > 0) {
                                            examDetail = nextExam;
                                        }

                                        Intent intent;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                            intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        } else {
                                            intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        }
                                        AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                                    } else {
                                        RunningLog.debug("考试时间不在当前时间，没有下一场考试");
                                        Date curEnd = ExamTimetableUtil.getEndTime(examDetail);
                                        Date date = new Date(curEnd.getTime() + TimeUnit.MINUTES.toMillis(5));

                                        long timeDiff = date.getTime() - System.currentTimeMillis();
                                        if (timeDiff <= 0) {
                                            examDetail = null;
                                        }

                                        Intent intent;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                            intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        } else {
                                            intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                                        }
                                        AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                                    }
                                }
                            } else {
                                ExamDetailResponse.ExamDetail nextExam = ExamTimetableUtil.getNextExam(examDetailResponse.getObj());
                                if (nextExam != null) {
                                    Date startTime = ExamTimetableUtil.getStartTime(nextExam);
                                    RunningLog.debug("当前无考试信息，下一场考试时间：" + nextExam.getExamDate());
                                    //提前20分钟刷新
                                    Date date = new Date(startTime.getTime() - TimeUnit.MINUTES.toMillis(20));

                                    Intent intent;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                        intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                                    } else {
                                        intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                                    }
                                    AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                                } else {
                                    RunningLog.debug("当前无考试信息，无下一场考试信息");
                                }
                            }
                            AbsExamControl control;
                            while ((control = controlQueue.poll()) != null) {
                                control.setExamData(examDetail);
                            }
                            taskMap.remove(examTimetableId);
                        }

                        @Override
                        public void onError(Throwable e) {
                            RunningLog.debug("请求考试时间表失败：" + e.getMessage());
                            if (!loading.compareAndSet(true, false)) {
                                return;
                            }
                            RunningLog.run("网络加载考试时间表失败，尝试读取缓存数据");
                            File file = new File(FileResourceUtil.getExamFolderPath(), FileResourceUtil.getExamTimetableFileName(examTimetableId));
                            if (file.exists() && !FileResourceUtil.isExpired(file, 10, TimeUnit.HOURS)) {
                                RunningLog.run("读取考试时间表缓存文件数据");
                                String str = FileUtils.readFile(file, "utf-8").toString();
                                RunningLog.debug("考试时间表缓存文件数据：" + str);
                                ExamDetailResponse timetable = JacksonUtil.jsonStrToBean(str, ExamDetailResponse.class);
                                ExamDetailResponse.ExamDetail timeTableBean = ExamTimetableUtil.getNearestExam(timetable.getObj());
                                timeTableBean = ExamTimetableUtil.filterTimeTable(timeTableBean);
                                AbsExamControl control;
                                while ((control = controlQueue.poll()) != null) {
                                    control.setExamData(timeTableBean);
                                }
                            } else {
                                RunningLog.run("考试时间表缓存不存在，或已超过有效期");
                            }
                            taskMap.remove(examTimetableId);

                            // 3分钟后再去刷新控件，来请求平台的考试时间表
                            RunningLog.run("请求异常，3分钟后再去刷新控件");
                            Date date = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3));
                            Intent intent;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                                intent.setAction(BroadcastAction.ACTION_REFRESH_CONTROL);
                            } else {
                                intent = new Intent(BroadcastAction.ACTION_REFRESH_CONTROL);
                            }
                            AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.EXAM_CONTROL_REFRESH, date);
                        }
                    });
        }
    }
}
