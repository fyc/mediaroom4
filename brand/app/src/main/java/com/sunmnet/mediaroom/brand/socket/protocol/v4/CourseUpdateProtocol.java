package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.network.EasyCallback;
import com.sunmnet.mediaroom.brand.common.network.EasyOkHttp;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.FileUtils;
import com.sunmnet.mediaroom.util.bean.course.CourseDto;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;


public class CourseUpdateProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.info("接收到课程表更新信息:\n" + message);
        if (message == null || TextUtils.isEmpty(message.getMsg())) {
            return SocketResult.fail();
        }
        String url = UrlUtil.getFullHttpUrl(DeviceApp.getApp().getPlServerAddr(), message.getMsg());
        EasyOkHttp.create(DeviceApp.getApp())
                .url(url)
                .callback(new EasyCallback() {
                    @Override
                    protected void onSuccess(String result) {
                        CourseDto courseDto = CourseHelper.getDefault().loadCourse(result);
                        if (courseDto == null || TextUtils.isEmpty(courseDto.getCourseTime())) {
                            RunningLog.error("课程表下载数据错误：" + result);
                            ToastUtil.show(DeviceApp.getApp().getApplicationContext(), "课程表下载数据错误：" + result);
                            return;
                        }
                        RunningLog.run("课程表下载完毕，重新加载课程表数据");
                        ToastUtil.show(DeviceApp.getApp().getApplicationContext(), "课程表下载完毕");
                        FileUtils.writeFile(FileResourceUtil.getCourseFilePath(), result);
                        CourseHelper.getDefault().setCourse(result);
                        EventBus.getDefault().post(new RefreshControlEvent());
                    }

                    @Override
                    protected void onFail(String errorMsg) {

                    }
                }).get();
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_COURSE_UPDATE;
    }

}
