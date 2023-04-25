package com.sunmnet.mediaroom.tabsp.record.impl.hik;

import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.enums.TimeEventType;
import com.sunmnet.mediaroom.common.events.TimeEvent;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.HCNetSDKByJNA;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.HikPresentation;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.HikSDK;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.InteractivePresentation;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;
import com.sunmnet.mediaroom.util.StringUtils;
import com.sunmnet.mediaroom.util.bean.course.CourseDto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

public class HikRecord extends AbstractCirculateRecord {

    HikSDK sdk = null;
    String playUrl;

    public HikRecord(RecordEntity bean) {
        super(bean);
        EventBus.getDefault().register(this);
        this.sdk = new HikSDK(bean.getUserName(), bean.getPassword(), bean.getHost(), bean.getPort());
        sdk.init();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    public HikSDK getSdk() {
        return this.sdk;
    }

    @Override
    public void release() {
        super.release();
        EventBus.getDefault().unregister(this);
    }

    CourseDto courseDto;

    @Subscribe
    public void onClassChange(TimeEvent classChangeEvent) {
        if (classChangeEvent.getEventType() == TimeEventType.CLASS_ON) {
            //正在上课
            courseDto = CourseHelper.getDefault().getCourseDto();
        }
    }

    @Subscribe(sticky = true)
    public void onLogin(HikSDK sdk) {
        int playHandle = sdk.getPlayerHandle();
        if (playHandle != -1) {
            recordStatus = new Status(RecordStatus.CONNECTED);
            notifyStatusChanged();
            //播放句柄加载成功!!
            this.playUrl = playHandle + "";
            //加载录播类型
            int type = sdk.getDeviceType();
            IRecordPresentation presentation = null;
            if (type != -1) {
                presentation = new InteractivePresentation();
            } else presentation = new HikPresentation();
            EventBus.getDefault().postSticky(presentation);
            refresh();
        }
    }

    @Override
    public IRecordStatus getRecordState() {
        IRecordStatus status = null;
        HCNetSDKByJNA.NET_DVR_RECORD_STATUS outputBuffer = new HCNetSDKByJNA.NET_DVR_RECORD_STATUS();
        outputBuffer.write();
        boolean isSuccess = this.sdk.GET_STDCONFIG(HCNetSDKByJNA.NET_DVR_GET_RECORD_STATUS, outputBuffer);
        if (isSuccess) {
            outputBuffer.read();
            status = createCommonStatus(outputBuffer);
        } else status = new Status(RecordStatus.ERROR);
        return status;
    }

    private IRecordStatus createCommonStatus(HCNetSDKByJNA.NET_DVR_RECORD_STATUS status) {
        IRecordStatus state = null;
        int type = status.byRecordStatus;
        switch (type) {
            case 0://暂停
                state = new Status(RecordStatus.PAUSE,
                        new String(status.byRecUUID).trim(),
                        status.dwRecordingTime);
                break;
            case 2://录制中
                state = new Status(RecordStatus.RECODING,
                        new String(status.byRecUUID).trim(),
                        status.dwRecordingTime);
                break;
            case 3://空闲
                state = new Status(RecordStatus.IDLE);
                break;
            default:
                state = new Status(RecordStatus.ERROR);
                break;
        }
        return state;
    }

    @Override
    public boolean start(String uuid) {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        Date date = new Date();
        CourseHelper helper = CourseHelper.getDefault();
        CourseTime courseTime = helper.getCourseTime(date);
        String courseName = "", teacherName = "", description = "非上课时间的启动录播";
        //所在学期第几周
        int weekOfTerm = 0;//CourseHolder.getInstance().weekOfTerm();
        //周几
        int weekOfDay = DateUtil.getDayOfWeekNow();
        String title = DateUtil.getNowDateString("yyyyMMdd-HH:mm:ss");
        title += "_" + weekOfDay + "_" + weekOfDay;
        CourseSchedule course=null;
        String courseNo="";
        if (courseTime != null) {
            course = helper.getCourseSchedule(date, weekOfTerm, courseTime.getClassNo());
        }
        if (course != null) {
            teacherName = course.getTeacherName();
            courseName = course.getCourseName();
            courseNo=course.getClassNo();
            description = "上课中";
        } else {
            courseName = "休息中";
            RunningLog.warn("海康录播-->开始录播-->检查课表-->" + DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss ") + " 没有课程!!");
            courseNo="-1";
        }
        title += "_" + courseNo;
        title += "_" + weekOfTerm;
        title += "_" + courseName;
        if (teacherName==null) teacherName="未知";
        if (description==null) description="无";
        return recordAction(uuid, title, teacherName, description, HikSDK.RECORD_CMD_START);
    }


    private String formatCommand(int command) {
        String cmd = "";
        switch (command) {
            case HikSDK.RECORD_CMD_START:
                cmd = "开始录播";
                break;
            case HikSDK.RECORD_CMD_STOP:
                cmd = "停止录播";
                break;
            case HikSDK.RECORD_CMD_PAUSE:
                cmd = "暂停录播";
                break;
            case HikSDK.RECORD_CMD_CONTINUE:
                cmd = "继续录播";
                break;
        }
        return cmd;
    }

    public static byte[] getGBKByteFromUtf8(String utf8Str) {
        byte[] arr = null;
        try {
            arr = utf8Str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            arr = utf8Str.getBytes();
        }
        return arr;
    }

    private boolean recordAction(String uuid, String courseName, String teacherName, String description, int cmd) {
        boolean isSuccess = false;
        RecordResult optResult = new RecordResult();
        if (!StringUtils.isEmpty(uuid)) {
            HCNetSDKByJNA.NET_DVR_MANUAL_CURRICULUM_CFG struManualCurriculunCfg = new HCNetSDKByJNA.NET_DVR_MANUAL_CURRICULUM_CFG();
            byte[] arr = getGBKByteFromUtf8(uuid);
            System.arraycopy(arr, 0, struManualCurriculunCfg.byRecUUID, 0, arr.length);
            arr = getGBKByteFromUtf8(courseName);
            System.arraycopy(arr, 0, struManualCurriculunCfg.byCourseName, 0, arr.length);
            arr = getGBKByteFromUtf8(teacherName);
            System.arraycopy(arr, 0, struManualCurriculunCfg.byInstructorName, 0, arr.length);
            arr = getGBKByteFromUtf8(description);
            System.arraycopy(arr, 0, struManualCurriculunCfg.byCourseDescription, 0, arr.length);
            struManualCurriculunCfg.byCmdType = (byte) cmd;
            struManualCurriculunCfg.dwSize = struManualCurriculunCfg.size();
            struManualCurriculunCfg.write();
            isSuccess = this.sdk.STD_CONTROL(HCNetSDKByJNA.NET_DVR_MANUAL_CURRICULUM_CONTROL, struManualCurriculunCfg);
            String optName = formatCommand(cmd);
            //String log = formatCommand(cmd);
            if (isSuccess) {
                optName += "控制成功!!";
                RunningLog.run(optName);
                optResult.setSuccess(true);
            } else {
                optName += sdk.getLastError();
                RunningLog.error(optName);
                optResult.setMessage(optName);
                optResult.setSuccess(false);
            }

        } else {
            optResult.setMessage("参数错误,控制必须带参数UUID！！");
            optResult.setSuccess(false);
            setOperatingResult(optResult);
            RunningLog.error("参数错误!!");
        }
        EventBus.getDefault().post(optResult);
        return isSuccess;
    }

    @Override
    public boolean pause(String uuid) {
        return this.recordAction(uuid, "", "", "", HikSDK.RECORD_CMD_PAUSE);
    }

    @Override
    public boolean goon(String uuid) {
        return this.recordAction(uuid, "", "", "", HikSDK.RECORD_CMD_CONTINUE);
    }

    @Override
    public boolean stop(String uuid) {
        return this.recordAction(uuid, "", "", "", HikSDK.RECORD_CMD_STOP);
    }

}
