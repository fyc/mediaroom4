package com.sunmnet.mediaroom.brand.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.broadcast.AlarmReceiver;
import com.sunmnet.mediaroom.brand.broadcast.BroadcastAction;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.PlayableManager;
import com.sunmnet.mediaroom.brand.data.database.gen.DaoSession;
import com.sunmnet.mediaroom.brand.data.database.play.AbsPublishingRuleDBEntity;
import com.sunmnet.mediaroom.brand.data.database.play.TemplateProgramRuleDBEntity;
import com.sunmnet.mediaroom.brand.utils.DaoManager;
import com.sunmnet.mediaroom.brand.data.database.play.NotificationRuleDBEntity;
import com.sunmnet.mediaroom.brand.data.database.play.ProgramRuleDBEntity;
import com.sunmnet.mediaroom.brand.bean.play.ProgramInfo;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.util.FileUtils;
import com.sunmnet.mediaroom.util.StringUtils;

import org.greenrobot.greendao.AbstractDao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务服务
 */
public class TimedTaskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (PlayableManager.class) {
                    DaoSession daoSession = DaoManager.getInstance().getDaoSession(DeviceApp.getApp());
                    List<ProgramRuleDBEntity> programRuleList = daoSession.getProgramRuleDBEntityDao().loadAll();
                    List<TemplateProgramRuleDBEntity> templateProgramRuleList = daoSession.getTemplateProgramRuleDBEntityDao().loadAll();
                    List<NotificationRuleDBEntity> notificationRuleList = daoSession.getNotificationRuleDBEntityDao().loadAll();

                    clearRules(programRuleList, daoSession.getProgramRuleDBEntityDao());
                    clearRules(templateProgramRuleList, daoSession.getTemplateProgramRuleDBEntityDao());
                    clearRules(notificationRuleList, daoSession.getNotificationRuleDBEntityDao());

                    clearProgramResource();

                    clearFile(FileResourceUtil.getTempFolderPath());
                    //清理日志文件
                    if (DeviceApp.getApp().getDevice() != null && DeviceApp.getApp().getDevice().getFolderPath() != null)
                        clearFile(DeviceApp.getApp().getDevice().getFolderPath(), 30);
                }
            }
        }).start();
        long millis = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12);//12小时执行一次
        Intent intent2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent2 = new Intent(this, AlarmReceiver.class);
            intent2.setAction(BroadcastAction.ACTION_TIMED_TASK);
        } else {
            intent2 = new Intent(BroadcastAction.ACTION_TIMED_TASK);
        }
        AlarmUtils.registerAlarm(this, intent2, R.id.START_TIMED_TASK_SERVICE, new Date(millis));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 适配安卓8， 开机后自动启动应用后，开启服务， 通知栏要显示通知，告知用户，不然会ANR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("timeTask", "time task",
                    NotificationManager.IMPORTANCE_NONE);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "timeTask").build();
            startForeground(1, notification);
        }
    }

    //清理数据库中的规则
    private <T extends AbsPublishingRuleDBEntity> void clearRules(List<T> list, AbstractDao<T, String> dao) {
        List<T> deleteList = new ArrayList<>();
        long millis = System.currentTimeMillis();
        for (T rule : list) {
            if (TimeUnit.MILLISECONDS.toDays(millis - rule.getEndTime().getTime()) > 7) {
                deleteList.add(rule);
            }
        }
        dao.deleteInTx(deleteList);
    }

    //清理没有使用的节目资源文件
    private void clearProgramResource() {
        DaoSession daoSession = DaoManager.getInstance().getDaoSession(DeviceApp.getApp());
        List<ProgramRuleDBEntity> programRuleList = daoSession.getProgramRuleDBEntityDao().loadAll();
        List<TemplateProgramRuleDBEntity> templateProgramRuleList = daoSession.getTemplateProgramRuleDBEntityDao().loadAll();
        List<String> resDirName = new ArrayList<>();
        for (ProgramRuleDBEntity rule : programRuleList) {
            for (ProgramInfo s : rule.getProgram()) {
                resDirName.add(s.getResFileMd5());
            }
        }
        for (TemplateProgramRuleDBEntity templateProgramRuleDBEntity : templateProgramRuleList) {
            resDirName.add(templateProgramRuleDBEntity.getResource().getResFileMd5());
        }
        File file = new File(FileResourceUtil.getProgramFolderPath());
        if (!file.exists() || !file.isDirectory()
                || file.listFiles() == null || file.listFiles().length == 0)
            return;
        File[] files = file.listFiles();
        for (File child : files) {
            if (child.isDirectory()) {
                String name = child.getName();
                if (TextUtils.isEmpty(name)) {
                    FileUtils.deleteFile(child.getAbsolutePath());
                } else {
                    boolean beUsed = false;
                    for (String dirName : resDirName) {
                        if (name.equalsIgnoreCase(dirName)) {
                            beUsed = true;
                            break;
                        }
                    }
                    if (!beUsed) {
                        FileUtils.deleteFile(child.getAbsolutePath());
                    }
                }
            }
        }
    }

    //清理文件
    private boolean clearFile(String path) {
        return clearFile(path, 3);
    }

    //清理文件
    private boolean clearFile(String path, int expiredDays) {
        long millis = System.currentTimeMillis();
        if (StringUtils.isBlank(path)) {
            return true;
        } else {
            File file = new File(path);
            if (!file.exists()) {
                return true;
            } else if (file.isFile()) {
                if (TimeUnit.MILLISECONDS.toDays(millis - file.lastModified()) >= expiredDays)
                    return file.delete();
                return true;
            } else if (!file.isDirectory()) {
                return false;
            } else {
                File[] childFiles = file.listFiles();
                for (File child : childFiles) {
                    if (child.isFile()) {
                        if (TimeUnit.MILLISECONDS.toDays(millis - child.lastModified()) >= expiredDays)
                            child.delete();
                    } else if (child.isDirectory()) {
                        clearFile(child.getAbsolutePath(), expiredDays);
                    }
                }
                return file.delete();
            }
        }
    }
}
