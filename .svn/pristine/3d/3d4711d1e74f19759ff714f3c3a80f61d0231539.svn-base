package com.sunmnet.mediaroom.brand;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sunmnet.mediaroom.brand.bean.config.CustomConfig;
import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;
import com.sunmnet.mediaroom.brand.bean.deserializer.ControlPropertyDeserializer;
import com.sunmnet.mediaroom.brand.bean.event.RefreshConfigEvent;
import com.sunmnet.mediaroom.brand.bean.face.SyncFaceInfo;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.socket.BrandSocketServiceFactory;
import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.enums.SocketServiceType;
import com.sunmnet.mediaroom.common.enums.TabspVersion;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.brand.common.utils.OkHttpUtilCache;
import com.sunmnet.mediaroom.brand.data.file.FileConstant;
import com.sunmnet.mediaroom.brand.data.sharepref.BrandConfig;
import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.brand.service.ForegroundService;
import com.sunmnet.mediaroom.brand.service.TimedTaskService;
import com.sunmnet.mediaroom.brand.utils.FaceHelper;
import com.sunmnet.mediaroom.brand.utils.FaceSyncOperateHelper;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.service.BellService;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SocketServiceFactory;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.sphinx.face.utils.FaceDbUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainApplication extends DeviceApp {

    @Override
    protected void initUncaughtExceptionHandler() {
        CrashHandler.getInstance().init(this);
    }

    @Override
    protected void init() {
        super.init();
//        DisplayUtil.printDisplayInfo(this);
        initBrandDeviceType();
        initUtils();
        setTimeZone();
        startService();
        initConfig();
        initCourse();
        setVolume();
//        installDesktopApp();
//        debug();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RunningLog.run("Application Created");
        printDeviceVersion();
    }

    @Override
    protected void onAsyncInitialize() {
//        openAdb();
        OpenCVLoader.initDebug();
        FaceDbUtil.getInstances().init(DeviceApp.getApp());
        RunningLog.run("人脸数据库初始化完毕");
        FaceHelper.getInstance().init();
        RunningLog.run("人脸模块初始化完毕");
        syncFace();
    }

    @Override
    protected String initStoragePath() {
        return AppUtil.getMaxFreePath(getApplicationContext()) + "/" + FileConstant.APP_ROOT_FOLDER;
    }

    private void debug() {
//        device.setClassroomCode("T1-101");
//        device.setClassroomName("T1-101");
//        device.setPlatformIp("172.16.24.52");
//        device.setPlatformPort(80);
        device.setCoreIp("172.16.24.131");
        device.setCorePort(6002);
//        try {
//            InputStream is = getAssets().open("course.json");
//            byte[] bytes = new byte[is.available()];
//            is.read(bytes);
//            String s = new String(bytes);
//            CourseHelper.getDefault().setCourse(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void initUtils() {
        OkHttpUtilCache.initOkHttpUtil(this);
        //报错时，手动编译一下module
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(ControlProperty.class, new ControlPropertyDeserializer());
        JacksonUtil.getObjectMapper().registerModule(module);
    }

    private void setVolume() {
        //设置系统音量
        int musicVolume = TypeUtil.objToInt(getSharedPreferencesValue("musicVolume"));
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            RunningLog.run("设置系统媒体音量：" + musicVolume);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume, 0);
        }
    }

    private void initConfig() {
        refreshConfig(null);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshConfig(RefreshConfigEvent configEvent) {
        CustomConfig config = BrandConfig.getInstance().getCustomConfig(this);
        if (config != null && !TextUtils.isEmpty(config.getHomeScreen())) {
            int preTime = TypeUtil.objToInt(config.getSignInTimeBeforeClassBegin());
            int afterTime = TypeUtil.objToInt(config.getSignInTimeAfterClassBegin());
            if (preTime > 0) {
                CourseConfig.PRE_START_TIME = TimeUnit.MINUTES.toMillis(preTime);
            }
            if (afterTime > 0) {
                CourseConfig.SUF_START_TIME = TimeUnit.MINUTES.toMillis(afterTime);
            }
        }
    }

    private void initCourse() {
        File courseFile = new File(FileResourceUtil.getCourseFilePath());
        if (courseFile.exists() && isRegistered()) {
            try {
                CourseHelper.getDefault().setCourse(courseFile);
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.error("课程表加载出错: " + e.toString());
            }
        }
    }

    private void initBrandDeviceType() {
        //检测是否海康支持刷卡的新设备
        BrandDevice.getInstance().init();
    }

    private void openAdb() {
        BrandDevice.getInstance().openAdb();
    }


    @Override
    protected void initDeviceInfo() {
        super.initDeviceInfo();
        FileResourceUtil.setRootPath(new File(device.getFolderPath()).getParentFile().getAbsolutePath());
    }

    private void printDeviceVersion() {
        PackageInfo packageInfo = AppUtil.getPackageInfoByContext(this);
        if (packageInfo != null) {
            RunningLog.run("程序信息: VersionName: " + packageInfo.versionName + ", VersionCode" + packageInfo.versionCode);
        }
        RunningLog.run("设备信息：" + JacksonUtil.objToJsonStr(this.device));
        RunningLog.run("系统版本:" + Build.VERSION.RELEASE + " ,SDK API: " + Build.VERSION.SDK_INT);
    }

    private void setTimeZone() {
        try {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setTimeZone("Asia/Shanghai");
        } catch (Exception e) {
            RunningLog.error("设置时区失败：" + e.getMessage());
        }
    }

    private void startService() {

        SocketServiceFactory.getInstance().initFactory(new BrandSocketServiceFactory());
        SocketServiceFactory.getInstance().startService(SocketServiceType.LOCAL.getKey());
        SocketServiceFactory.getInstance().startService(SocketServiceType.DEBUG.getKey());
        SocketServiceFactory.getInstance().startService(SocketServiceType.LOG.getKey());
        RunningLog.init(SocketServiceFactory.getInstance().getService(SocketServiceType.LOG.getKey()));

        if (isRegistered() && !TextUtils.isEmpty(getCoreIp())) {
            Controller.init(getApplicationContext()).setControllCenterInfo(TabspVersion.VERSION_4, getCoreIp(), getCorePort(), TerminalMessage.BRAND);
        }

        Intent intent2 = new Intent(this, TimedTaskService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 适配安卓8，开机后，应用自启动后开启服务，对应服务也要加上通知栏通知
            startForegroundService(intent2);
        } else {
            startService(intent2);
        }

        Intent intent3 = new Intent(this, ForegroundService.class);
        startService(intent3);

        Intent intent5 = new Intent(this, BellService.class);
        startService(intent5);
    }

    @Override
    public void onLowMemory() {
        RunningLog.warn("警告：系统内存过低...");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        RunningLog.warn("应用正被杀死...");
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        RunningLog.run("onTrimMemory被调用了，level：" + level);
        super.onTrimMemory(level);
    }

    private void installDesktopApp() {
        ThreadUtils.execute(new Runnable() {

            @Override
            public void run() {
                // desktop/MR_BRAND_DESKTOP_3.5.1.180907001.apk
                String[] files = null;
                try {
                    files = getAssets().list("desktop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (files == null)
                    return;
                String assetsApkName = null;
                int assetsApkVersion = 0;
                for (String name : files) {
                    if (name == null || !name.startsWith("MR_BRAND_DESKTOP_"))
                        continue;
                    if (assetsApkName == null) {
                        assetsApkName = name;
                        assetsApkVersion = TypeUtil.objToInt(assetsApkName.split("\\.")[3]);
                    } else {
                        if (TypeUtil.objToInt(name.split("\\.")[3]) > assetsApkVersion) {
                            assetsApkName = name;
                            assetsApkVersion = TypeUtil.objToInt(assetsApkName.split("\\.")[3]);
                        }
                    }
                }
                PackageInfo packageInfo = null;
                try {
                    packageInfo = getPackageManager().getPackageInfo("com.sunmnet.sunmnetdesktop", 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (packageInfo == null || assetsApkVersion > packageInfo.versionCode) {
                    ToastUtil.show(MainApplication.this, "开始安装 电子班牌桌面管理");
                    String tempPath = FileResourceUtil.getTempFolderPath() + "desktopTemp.apk";
                    try {
                        AppUtil.copyFileFromAssetToLocalPath("desktop/" + assetsApkName, getApplicationContext(), tempPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    boolean instSuccess;
                    instSuccess = BrandDevice.getInstance().installApk(tempPath);
                    if (instSuccess) {
                        RunningLog.run("电子班牌桌面管理安装成功");
                    } else {
                        RunningLog.run("电子班牌桌面管理安装失败");
                    }
                }
            }
        });
    }

    private void syncFace() {
        FaceSyncOperateHelper faceOperateHelper = new FaceSyncOperateHelper();
        //已同步人脸信息的修改时间
        long remoteModify = faceOperateHelper.getRemoteModify();
        //正在使用的人脸信息的修改时间
        long currentModify = faceOperateHelper.getCurrentModify();
        if (remoteModify > currentModify) {
            //已同步人脸信息较新或一样，但正在使用的人脸信息较旧，则使用目前的进行更新
            RunningLog.run("本地人脸信息还未完成更新，继续更新人脸信息");
            SyncFaceInfo curFaceSyncInfo = faceOperateHelper.getFaceSyncInfo();
            if (curFaceSyncInfo == null)
                return;
            faceOperateHelper.syncFace(curFaceSyncInfo);
        }
    }

}
