package com.sunmnet.mediaroom.tabsp.common;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Handler;

import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.litesuits.common.utils.AppUtil;
import com.litesuits.common.utils.FileUtil;
import com.litesuits.common.utils.ShellUtil;
import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.bean.UpgradeInfo;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.EthernetUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.FileGetter;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.device.bean.RegisterInfo;
import com.sunmnet.mediaroom.device.bean.TabspRegisterInfo;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;
import com.sunmnet.mediaroom.devicebean.new2.components.param.CameraParam;
import com.sunmnet.mediaroom.devicebean.new2.components.param.InternetParam;
import com.sunmnet.mediaroom.socket.design.log.deal.DateUtils;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.TabspNetworkUtil;
import com.sunmnet.mediaroom.tabsp.bean.NetworkBean;
import com.sunmnet.mediaroom.tabsp.bean.NetworkInterface;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.bean.UpgradeResult;
import com.sunmnet.mediaroom.tabsp.bean.enums.SoftType;
import com.sunmnet.mediaroom.tabsp.eventbus.events.LockEvent;
import com.sunmnet.mediaroom.tabsp.eventbus.events.UpgradeEvent;
import com.sunmnet.mediaroom.tabsp.impl.TabspInstaller;
import com.sunmnet.mediaroom.tabsp.impl.TabspZipUtils;
import com.sunmnet.mediaroom.tabsp.record.RecordMaker;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordType;
import com.sunmnet.mediaroom.util.FileUtils;
import com.sunmnet.mediaroom.util.JsonUtils;
import com.sunmnet.mediaroom.util.MD5Util;
import com.sunmnet.mediaroom.util.bean.course.CourseDto;
import com.sunmnet.mediaroom.wirelessprojection.WirelessOperator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 系统性事件处理器
 * 中控连接控制/ 更新系统时间/  重启/关机/升级 事件处理等
 */
public class SystemEventHandler {
    Context context;
    TabspRegisterInfo registerInfo;
    TabspConfig tabspConfig;
    Handler handler;

    public SystemEventHandler(Context context, TabspConfig config, Handler handler) {
        this.context = context;
        this.tabspConfig = config;
        this.handler = handler;
        //注册自身，监听事件
        EventBus.getDefault().register(this);
        ThreadUtils.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                registerInfo = tabspConfig.getRegisterInfo();
                //初始化时检查是否曾经在app内修改过IP地址,开线程去更新IP地址，更新完成后调用注册事件来进行连接
                //并且检查在上一次程序结束是否是因为远程更新发生的
                if (checkIpAddress()) {
                    onRegister(tabspConfig.getRegisterInfo());
                    checkSoftware(tabspConfig.getUpgradeInfo());
                }
            }
        });
    }

    /**
     * 比较当前地址和已经设置过的地址是否一致
     */
    private boolean isIpSettle(NetworkInterface local, NetworkInterface loaded) {
        return local.getLocalIP().equals(loaded.getLocalIP())
                && local.getNetmask().equals(loaded.getNetmask())
                && local.getGateway().equals(loaded.getGateway())
                && local.getDns1().equals(loaded.getDns1());
    }

    /**
     * 检查IP地址是否有误
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean checkIpAddress() {
        NetworkBean bean = new NetworkBean();
        if (tabspConfig.getNetwork() != null) {
            NetworkInterface localSetting = tabspConfig.getNetwork();
            TabspNetworkUtil.loadNetwork(AndroidNetworkUtil.NetworkType.ETHERNET, bean);
            boolean isIpSettle = isIpSettle(localSetting, bean);
            if (!isIpSettle) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSet = EthernetUtil.setEthernetStaticIp(TabspApplication.getInstance(),
                                localSetting.getLocalIP(),
                                localSetting.getNetmask(),
                                localSetting.getGateway(),
                                localSetting.getDns1());
                        RunningLog.run("IP设置结果:" + isSet);
                    }
                });
                RunningLog.run("正在设置IP。睡眠10秒等待IP设置完成");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            RunningLog.run("[IP检查]:未设置过IP地址信息！默认使用本地地址打开网络！");
            return true;
        }
    }

    /**
     * 课程表加载成功后启动课表服务
     */
    @Subscribe(sticky = true)
    public synchronized void onCourseLoaded(CourseDto deviceJson) {
        CourseHelper.getDefault().setCourse(deviceJson);
    }

    /**
     * 检查软件版本是否刚刚升级过
     */
    private void checkSoftware(UpgradeInfo upgradeInfo) {
        if (upgradeInfo != null && upgradeInfo.isUpgrading()) {
            RunningLog.run("软件执行过更新，开始同步版本信息至数据中心！");
            upgradeInfo.setUpgrading(false);
            upgradeResponse(upgradeInfo.getUuid(), upgradeInfo.getUpgradeResponsePath(), UpgradeInfo.UPGRADE_SUCCESS, null);
        } else RunningLog.debug("软件未执行更新，不提交软件版本！！");
    }

    /**
     * 音量设置或其他设置
     */
    @Subscribe(sticky = true)
    public void onOtherSetting(OtherSetting otherSetting) {
        switch (otherSetting.getEventType()) {
            case -1:
                if (otherSetting.getSyncTime() != null) {
                    try {
                        setTime(otherSetting.getSyncTime());
                    } catch (Exception e) {
                        RunningLog.warn(e);
                    }
                    return;
                }
                break;
            case 1:
                updateCache();
                break;
        }
    }

    /**
     * 文件下载回调
     *
     * @param fileGetter 文件下载路径以及文件的类型
     */
    @Subscribe
    public void onFileGetter(final FileGetter fileGetter) {
        OkHttpUtil.DownloadListener downloadListener = new OkHttpUtil.DownloadListener() {
            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed(Exception e) {
                RunningLog.error(e);
            }

            @Override
            public void onDownloadFailed(String msg) {

            }

            @Override
            public void onDownloadSuccess(File file) {

                try {
                    String fileMd5 = MD5Util.getFileMD5String(file);
                    if (fileMd5 != null && fileMd5.equals(fileGetter.getMd5())) {
                        switch (fileGetter.getAction()) {
                            case 0:
                                RunningLog.run("配置文件下载成功！！");
                                loadConfig(file);
                                break;
                            case 1:
                                RunningLog.run("升级");
                                break;
                        }
                    } else {
                        RunningLog.warn("文件验证失败！！下载到的文件md5为：" + fileMd5 + ",配置中的md5为：" + fileGetter.getMd5());
                    }
                    file.delete();
                } catch (IOException e) {
                    RunningLog.error(e);
                    RunningLog.warn("文件MD5验证失败，处理对象：" + JsonUtils.objectToJson(fileGetter));
                }
            }
        };
        String getFileName = FileUtils.getFileNameWithoutExtension(fileGetter.getUrl());
        getFileName += ".";
        getFileName += FileUtils.getFileExtension(fileGetter.getUrl());
        String downloadUrl = fileGetter.getUrl();
        OkHttpUtil.downloadFileByGet(downloadUrl,
                null, null,
                TabspApplication.getInstance().getConfig().getExclusivePath(),
                getFileName,
                downloadListener);
    }

    /**
     * 获取保存的升级信息
     */
    private UpgradeInfo getUpgradeInfo() {
        UpgradeInfo upgradeInfo = this.tabspConfig.getUpgradeInfo();
        if (upgradeInfo == null) {
            upgradeInfo = new UpgradeInfo();
            TabspApplication.getInstance().getConfig().setUpgradeInfo(upgradeInfo);
        }
        return upgradeInfo;
    }

    /**
     * 设置系统时间
     */
    private void setTime(String message) {
        try {
            getTimeZone(message);
            if (ShellUtil.hasRootPermission())
                getTimeZone(message);
            else {
                Date serverTime = DateUtils.convertDate(message, "yyyy-MM-dd HH:mm:ss");
                setSystemClock(serverTime);
            }
        } catch (Exception e) {
            RunningLog.error(e);
        }

    }

    /**
     * dateStr 北京时间的字符串
     */
    private void getTimeZone(String dateStr) {
        String format = "yyyy-MM-dd HH:mm:ss";
        Date date = DateUtils.convertDate(dateStr, format);
        dateStr = "yyyyMMdd.HHmmss";
        AlarmUtils.cancelAllAlarms(context);
        updateTime(DateUtils.getTimeStrByFormate(date, dateStr));
    }

    private int updateTime(String time) {
        String command = "/system/bin/date -s " + time;
        return ShellUtil.execCommand(command, false).result;
    }

    private void setSystemClock(Date serverTime) {
        RunningLog.debug("时间设置前：" + DateUtils.getCurrentTime());
        Calendar c = Calendar.getInstance();
        c.setTime(serverTime);
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            try {
                ((AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.debug("时间设置出错：" + e.getMessage());
            }
        }
        RunningLog.debug("时间设置后：" + DateUtils.getCurrentTime());
    }

    /**
     * 初始化连接
     */
    private void initConnection(RegisterInfo registerInfo) {
        if (registerInfo != null)
            Controller.init(context).setControllCenterInfo(tabspConfig.getVersionType(), registerInfo.getCoreIp(), Integer.parseInt(registerInfo.getCorePort()));
    }

    private void upgradeResponse(String uuid, String responseUrl, String upgradeResult, Object message) {
        if (responseUrl == null || uuid == null) return;
        UpgradeResult result = new UpgradeResult();
        result.setClassroomCode(tabspConfig.getClassRoomCode());
        result.setFailureCause(message != null ? message.toString() : null);
        result.setResult(upgradeResult);
        PackageInfo packageInfo = new AppUtil().getPackageInfo(BaseApplication.getInstance());
        result.setVersion(packageInfo.versionName);
        result.setUuid(uuid);
        String json = JacksonUtil.objToJsonStr(result);
        RunningLog.run("提交更新结果参数：" + json);
        try {
            Response response = OkHttpUtil.postJson(responseUrl, json);
            String res = null;
            if (response.body() != null) {
                res = response.body().string();
                RunningLog.debug("提交更新结果返回数据：" + res);
            }
            if (response.isSuccessful() && !TextUtils.isEmpty(res)) {
                JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
                boolean isSuccess = jsonObject.get("success").getAsBoolean();
                if (isSuccess) {
                    RunningLog.run(responseUrl + "回调成功");
                } else {
                    RunningLog.warn(responseUrl + "版本更新失败！！错误信息：" + jsonObject.get("msg").getAsString());
                }
            } else {
                RunningLog.warn(responseUrl + "-->返回版本信息有错误！！");
            }
        } catch (IOException e) {
            RunningLog.error(e);
        } catch (Exception e) {
            RunningLog.error(e);
        }
    }

    private void onRegistered() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "注册成功！！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isSameString(String original, String compare) {
        return original != null && !original.equals(compare);
    }

    /**
     * 注册事件
     */
    @Subscribe
    public void onRegister(TabspRegisterInfo registerInfo) {
        RunningLog.run("收到注册信息:" + (registerInfo == null ? "" : registerInfo.toString()));
        if (this.registerInfo == null) {
            if (registerInfo != null) {
                this.registerInfo = registerInfo;
                tabspConfig.setRegisterInfo(this.registerInfo);
                initConnection(registerInfo);
                updateCache();
                onRegistered();
            }
        } else {
            if (this.registerInfo.getCoreIp() != null && this.registerInfo.getCorePort() != null && registerInfo != null && registerInfo.getCoreIp() != null && registerInfo.getCorePort() != null) {
                Event<Object, EventType> event = new Event();
                if (!isSameString(this.registerInfo.getCoreIp(), registerInfo.getCoreIp())
                        || !isSameString(this.registerInfo.getCorePort(), registerInfo.getCorePort())
                        || !isSameString(this.registerInfo.getPlatformIp(), registerInfo.getPlatformIp())
                        || !isSameString(this.registerInfo.getPlatformPort(), registerInfo.getPlatformPort())
                        || !isSameString(this.registerInfo.getClassroomCode(), registerInfo.getClassroomCode())
                        || !isSameString(this.registerInfo.getClassroomName(), registerInfo.getClassroomName())
                        || !isSameString(this.registerInfo.getDeviceCode(), registerInfo.getDeviceCode())
                ) {
                    event.setMessage("注册成功！");
                    onRegistered();
                    event.setEventType(EventType.EVENT_LOCK);
                    this.registerInfo = registerInfo;
                    tabspConfig.setRegisterInfo(this.registerInfo);
                    updateCache();
                    ThreadUtils.execute(new Runnable() {
                        @Override
                        public void run() {
                            RunningLog.run("切换了中控地址。3秒后重连！");
                            LockEvent lockEvent = new LockEvent(EventType.EVENT_LOCK);
                            EventBus.getDefault().post(lockEvent);
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            initConnection(registerInfo);
                        }
                    });
                } else {
                    RunningLog.run("注册信息：" + JsonUtils.objectToJson(this.registerInfo));
                    initConnection(this.registerInfo);
                }
            }
        }
    }

    /**
     * 更新系统缓存
     */
    public void updateCache() {
        //更新注册信息，写入本地缓存
        SharePrefUtil.saveValue(context, TabspApplication.DB_NAME, TabspApplication.DB_CONFIG_KEY, JsonUtils.objectToJson(this.tabspConfig));
    }

    /**
     * 软件更新操作
     */
    @Subscribe
    public void onUpgradeEvent(final Event<Object, EventType> event) {
        if (event.getEventType() instanceof EventType) {
            EventType eventType = event.getEventType();
            switch (eventType) {
                case UPGRADE_FAILED:
                    RunningLog.warn("升级失败！！" + event.getMessage());
                    UpgradeInfo setFailUpgrade = TabspApplication.getInstance().getConfig().getUpgradeInfo();
                    if (setFailUpgrade == null) {
                        setFailUpgrade = new UpgradeInfo();
                        TabspApplication.getInstance().getConfig().setUpgradeInfo(setFailUpgrade);
                    }
                    setFailUpgrade.setUpgrading(false);
                    upgradeResponse(setFailUpgrade.getUuid(), setFailUpgrade.getUpgradeResponsePath(), UpgradeInfo.UPGRADE_FAILURE, event.getMessage());
                    updateCache();
                    break;
                case UPGRADE_SUCCESS:
                    break;
                case UPGRADE_READY:
                    JsonElement jsonEle = null;
                    JsonParser parser = new JsonParser();
                    if (event.getMessage() != null && (jsonEle = parser.parse(event.getMessage().toString())) != null) {
                        final JsonObject upgrade = jsonEle.getAsJsonObject();
                        final UpgradeEvent upgradeEvent = new UpgradeEvent();
                        upgradeEvent.setDownnloadPath(upgrade.get("requestAction").getAsString());
                        upgradeEvent.setMd5(upgrade.get("md5").getAsString());
                        upgradeEvent.setResponsePah(upgrade.get("responseUrl").getAsString());
                        if (upgradeEvent != null) {
                            ToastUtil.show(context, "开始升级", false);
                            RunningLog.run("开始更新软件,下载地址：" + upgradeEvent.getDownnloadPath() + "。下载回调地址：" + upgradeEvent.getResponsePah());
                            ThreadUtils.execute(new Runnable() {
                                @Override
                                public void run() {
                                    //更新本地版本数据
                                    synchronized (tabspConfig) {
                                        UpgradeInfo upgradeInfo = getUpgradeInfo();
                                        upgradeInfo.setUpgrading(true);
                                        upgradeInfo.setUuid(upgrade.get("uuid").getAsString());
                                        upgradeInfo.setUpgradeResponsePath(upgradeEvent.getResponsePah());
                                        upgradeInfo.setFileMd5(upgradeEvent.getMd5());
                                        PackageInfo packageInfo = new AppUtil().getPackageInfo(TabspApplication.getInstance());
                                        upgradeInfo.setPrevVersionName(packageInfo != null ? packageInfo.versionName : "");
                                        if (packageInfo != null) {
                                            ToastUtil.show(context, "升级软件版本:" + packageInfo.versionName, false);
                                        }
                                        //保存本地版本数据
                                        updateCache();
                                        //准备下载文件，进行安装
                                        OkHttpUtil.DownloadListener listener = new TabspInstaller();
                                        String directory = TabspApplication.getInstance().getConfig().getExclusivePath();
                                        String fileName = "";
                                        fileName += "upgrade";
                                        fileName += ".apk";
                                        File file = new File(fileName);
                                        if (file.exists()) file.delete();
                                        OkHttpUtil.downloadFileByGet(upgradeEvent.getDownnloadPath(), null, null, directory, fileName, listener);

                                    }
                                }
                            });
                        } else {
                            ToastUtil.show(context, "升级失败！！更新信息不完整！！", false);
                            RunningLog.warn("升级失败！！更新信息不完整！！");
                        }
                    }
                    break;
                case CONFIG_CHANGED:
                    //重新加载配置
                    if (event.getMessage() instanceof File) {
                        loadConfig((File) event.getMessage());
                    }
                    break;
            }
        }
    }

    WirelessOperator operator = null;

    @Subscribe(priority = 5)
    public void onLoadDevice(DeviceLoadedEvent loadedEvent) {
        List<IDevice> wirelesss = Controller.getInstance().getDevicesByDeviceType(DeviceType.WIRELESS);
        if (wirelesss != null && wirelesss.size() > 0) {
            //加载无线投屏
            BaseDeviceDto wireless = (BaseDeviceDto) wirelesss.get(0).getProperty();
            InternetParam internetParam = (InternetParam) wireless.getComponents().get("INTERNET_PARAM");
            String ip = null, port = null, key = null, password = null;
            if (internetParam != null) {
                ip = internetParam.getDeviceIp();
                port = internetParam.getDevicePort() + "";
            }
            CameraParam userParam = (CameraParam) wireless.getComponents().get("CAMERA_PARAM");
            if (userParam != null) {
                key = userParam.getUserName();
                password = userParam.getPassWord();
            }
            // new WirelessOperator(ip, port, key, password);
            if (operator == null) {
                if (!isEmpty(ip)) {
                    if (!isEmpty(port)) {
                        if (!isEmpty(key)) {
                            if (!isEmpty(password)) {
                                operator = new WirelessOperator(ip, Integer.parseInt(port), key, password);
                            } else operator = new WirelessOperator(ip, Integer.parseInt(port), key);
                        } else {
                            operator = new WirelessOperator(ip, Integer.parseInt(port));
                        }
                    } else {
                        operator = new WirelessOperator(ip);
                    }
                } else {
                    RunningLog.warn("无线投屏无法加载到IP地址！！");
                }
                if (operator != null) {
                    EventBus.getDefault().postSticky(operator);
                }
            }
        }
        //获取录播设备判断是否显示录播菜单
        wirelesss = Controller.getInstance().getDevicesByDeviceType(DeviceType.RECORDED);
        if (wirelesss != null && wirelesss.size() > 0) {
            //加载录播
            onRecord((BaseDeviceDto) wirelesss.get(0).getProperty());
        }
    }

    private boolean isEmpty(String str) {
        return str == null || "".equals(str) || "".equals(str.trim()) || "null".equals(str);
    }

    /**
     * 加载平台配置
     *
     * @param configFile 压缩文件
     */
    private void loadConfig(File configFile) {

        if (configFile.exists()) {
            String depressPath = null;
            try {
                depressPath = TabspZipUtils.decompress(configFile.getPath());
            } catch (Exception e) {
                RunningLog.error(e);
                return;
            }
            if (depressPath != null && new File(depressPath).exists()) {
                File directory = new File(depressPath);
                File newFile = new File(directory.getParent(), "config");
                if (newFile.exists()) {
                    if (newFile.isDirectory())
                        CommonUtil.deleteDirectory(newFile.getAbsolutePath());
                    else FileUtils.deleteFile(newFile.getAbsolutePath());
                }

                String string = "解压后配置文件夹为：" + depressPath + ",原文件是否存在：" + newFile.exists();
                boolean isRename = directory.renameTo(newFile);
                string += "，重命名为：" + newFile.getPath();
                string += ",重命名结果：" + isRename;
                RunningLog.run(string);
                depressPath = newFile.getPath();
                File configJson = new File(depressPath, "config.json");
                if (configJson.exists()) {
                    String str = FileUtil.getFileOutputString(configJson.getPath());
                    str = str.replace("\n", "");
                    str = str.replace("\r", "");
                    str = str.replace("\r\n", "");
                    RunningLog.run("配置文件：" + str);
                    if (str != null && str.length() > 0) {
                        SysSpTempConfigFileDto configFileDto = (SysSpTempConfigFileDto) JsonUtils.jsonToBean(str, SysSpTempConfigFileDto.class);
                        updateConfig(configFileDto, depressPath);
                        ThreadUtils.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                RunningLog.run("重新加载配置！");
                                LockEvent event = new LockEvent(EventType.ACTIVITY_RELOAD);
                                EventBus.getDefault().post(event);
                            }
                        });
                    } else RunningLog.warn("配置加载异常！！config.json中没有数据！！");
                } else RunningLog.warn("配置加载异常！！压缩文件中不存在config.json");
            } else RunningLog.warn("配置加载异常，解压路径丢失！！");

        } else {
            RunningLog.warn("配置加载异常！！，" + configFile.getAbsolutePath() + " 不存在！！");
        }
        configFile.delete();
    }

    /**
     * 更新缓存中的Logo或者背景图
     */
    private String initImageCache(@DrawableRes int drawable, String filePath, String directory) {
        if (filePath == null) return null;
        filePath = filePath.replace("\\", "/");
        File file = new File(directory, filePath);
        if (file.exists()) {
            CommonUtil.resourceInitialize(drawable, file.getAbsolutePath());
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 合并实际路径
     */
    private String combinePath(String path, String file) {
        if (!TextUtils.isEmpty(file)) {
            return path + file;
        }
        return null;
    }

    /**
     * 把平台的配置更新到本地缓存中
     */
    private void updateConfig(SysSpTempConfigFileDto fileDto, String absolutePath) {
        SysSpTempConfigFileDto.CustomConfig customConfig = fileDto.getCustomConfig();
        this.tabspConfig.setLoginLogo(initImageCache(R.drawable.tabsp_logo, customConfig.getLoginPageLogo(), absolutePath));
        this.tabspConfig.setBaseLogo(initImageCache(R.drawable.tabsp_main_logo, customConfig.getHomePageLogo(), absolutePath));
        this.tabspConfig.setBaseLoginBackground(initImageCache(R.drawable.tabsp_login_background, fileDto.getBackground(), absolutePath));
        if (fileDto.getTemplateType() != null) {
            SoftType softType = this.tabspConfig.getSoftType();
            switch (fileDto.getTemplateType()) {
                case 2:
                    this.tabspConfig.setSoftType(SoftType.VERSION_2);
                    break;
                case 3:
                    this.tabspConfig.setSoftType(SoftType.VERSION_3);
                    break;
                default:
                    this.tabspConfig.setSoftType(SoftType.VERSION_1);
                    break;
            }
            ((TabspApplication) context).setDispatcher(softType, this.tabspConfig.getSoftType());
        }
        if (fileDto.getLectureModeList() != null) {
            for (int i = 0; i < fileDto.getLectureModeList().size(); i++) {
                SysSpTempConfigFileDto.LectureMode mode = fileDto.getLectureModeList().get(i);
                mode.setSelectedIcon(combinePath(absolutePath, mode.getSelectedIcon()));
                mode.setUnselectedIcon(combinePath(absolutePath, mode.getUnselectedIcon()));
            }
        }
        if (fileDto.getDiscussionModeList() != null) {
            for (int i = 0; i < fileDto.getDiscussionModeList().size(); i++) {
                SysSpTempConfigFileDto.DiscussMode mode = fileDto.getDiscussionModeList().get(i);
                mode.setSelectedIcon(combinePath(absolutePath, mode.getSelectedIcon()));
                mode.setUnselectedIcon(combinePath(absolutePath, mode.getUnselectedIcon()));
            }
        }
        String login = customConfig.getLoginSet();
        if (login != null) {
            if (login.contains("1")) {
                this.tabspConfig.setAccount(true);
            } else this.tabspConfig.setAccount(false);
            if (login.contains("2")) {
                this.tabspConfig.setSwipCard(true);
            } else this.tabspConfig.setSwipCard(false);
            if (login.contains("3")) {
                this.tabspConfig.setQrcode(true);
            } else this.tabspConfig.setQrcode(false);
        }
        this.tabspConfig.setClassOnCountdown(customConfig.getOnekeyClassBeginCountdown());
        this.tabspConfig.setClassOverCountdown(customConfig.getOnekeyClassOverCountdown());
        this.tabspConfig.setRate(customConfig.isDeviceEvaluateSet() == 1 ? true : false);
        List<SysSpTempConfigFileDto.MenuSetting> menus = fileDto.getMenuSettingList();
        if (menus != null) {
            Collections.sort(menus, new Comparator<SysSpTempConfigFileDto.MenuSetting>() {
                @Override
                public int compare(SysSpTempConfigFileDto.MenuSetting o1, SysSpTempConfigFileDto.MenuSetting o2) {
                    return o1.getSortIndex() - o2.getSortIndex();
                }
            });
            TabspApplication.getInstance().getDispatcher().setCompareMenu(menus);
            tabspConfig.setMenuNames(menus);
        }
        tabspConfig.setConfiguration(fileDto);
        updateCache();
    }

    /**
     * 本地网络改变时，保存已改变的数据
     */
    @Subscribe
    public void onNetworkChange(NetworkInterface networkInterface) {
        this.tabspConfig.setNetwork(networkInterface);
        updateCache();
    }

    /**
     * 加载录播设备信息，并执行初始化
     */
    public void onRecord(BaseDeviceDto record) {
        String brand = record.getDeviceBrand() != null ? record.getDeviceBrand().toUpperCase() : "";
        String mode = record.getDeviceModel();
        RecordEntity recordEntity = new RecordEntity();
        InternetParam internetParam = (InternetParam) record.getComponents().get("INTERNET_PARAM");
        if (internetParam != null) {
            recordEntity.setHost(internetParam.getDeviceIp());
            recordEntity.setPort(internetParam.getDevicePort() + "");
        }
        CameraParam userParam = (CameraParam) record.getComponents().get("CAMERA_PARAM");
        if (userParam != null) {
            recordEntity.setUserName(userParam.getUserName());
            recordEntity.setPassword(userParam.getPassWord());
        }
        switch (brand) {
            case "HIK":
            case "海康":
                recordEntity.setRecordType(RecordType.HIK);
                break;
            case "REACH":
            case "锐取":
                recordEntity.setRecordType(RecordType.REACH);
                break;
            case "Zonekey":
            case "中庆":
                recordEntity.setRecordType(RecordType.Zonekey);
                break;
            case "Zixu":
            case "紫旭":
                recordEntity.setRecordType(RecordType.Zixu);
                break;
            case "Dayang":
            case "大洋":
                recordEntity.setRecordType(RecordType.Dayang);
                break;
            case "AVA":
            case "奥威亚":
                recordEntity.setRecordType(RecordType.AVA);
                break;
            case "VANLON":
            case "凡龙":
                recordEntity.setRecordType(RecordType.VANLON);
                break;
            case "HBE":
            case "翰博尔":
                recordEntity.setRecordType(RecordType.HBE);
                break;
            case "XIWOO":
            case "希沃":
                recordEntity.setRecordType(RecordType.XIWOO);
                if (userParam != null) {
                    Map<String, String> recordParams = new HashMap<>();
                    recordParams.put("aislePort",userParam.getAislePort().toString());
                    recordEntity.setParams(recordParams);
                }
                break;
            case "Inxedu":
            case "因酷":
                recordEntity.setRecordType(RecordType.Inxedu);
                break;
            case "Simple":
            case "简易":
                recordEntity.setRecordType(RecordType.Simple);
                break;
            default:
                recordEntity.setRecordType(RecordType.DEFAULT);
                break;
        }
        RecordMaker.initRecord(recordEntity);
    }

    public void release() {
        EventBus.getDefault().unregister(this);
    }
}
