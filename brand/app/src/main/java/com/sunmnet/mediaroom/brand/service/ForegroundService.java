package com.sunmnet.mediaroom.brand.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import android.text.format.Formatter;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.brand.R;

import java.util.List;

/**
 * 前台服务，定时检查MainActivity是否在前台运行
 */
public class ForegroundService extends Service {

    private static final String pauseAction = "com.sunmnet.mediaroom.brand.ACTION_PAUSE_CHECK_FOREGROUND";

    private CheckThread thread;
    private DesktopNotificationReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        receiver = new DesktopNotificationReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(pauseAction);
//        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new CheckThread(this);
        thread.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            String channelId = "brand";
            String channelName = "brand_service";
            int channelImportance = NotificationManager.IMPORTANCE_NONE;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription("channel description");

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setSmallIcon(R.mipmap.app_logo);
            builder.setContentTitle("电子班牌正在运行");
            builder.setContentText("这里是前台服务通知");
            startForeground(231213, builder.build());
        } else {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.app_logo);
            builder.setContentTitle("电子班牌正在运行");
//        builder.setContentText("正在保护电子班牌在前台运行,点击暂停保护5分钟");
            builder.setContentText("这里是前台服务通知");
//        Intent broadcastIntent = new Intent(pauseAction);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
            startForeground(231213, builder.getNotification());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
//        try {
//            unregisterReceiver(receiver);
//            receiver = null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        super.onDestroy();
    }

    class CheckThread extends Thread {

        Context context;
        int pauseCount = 0;//暂停次数

        public CheckThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(20000);//20秒检查一次
//                    if (pauseCount <= 0)
                    checkForeground();
//                    else
//                        pauseCount--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void setPauseCount(int count) {
            pauseCount = count;
        }

        private void checkForeground() {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
            boolean isForeground = false;
            int pid = -1;
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
            if (processes == null)
                return;
            for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    pid = processInfo.pid;
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        isForeground = true;
                    }
                }
            }
            if (!isForeground) {
//                Intent bootStartIntent = new Intent(context, MainActivity.class);
//                bootStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(bootStartIntent);
//                RunningLog.run("MainActivity当前不在前台运行，启动MainActivity到前台");
                RunningLog.run("MainActivity当前不在前台运行");
            }
//            CommandExecution.CommandResult result = CommandExecution.execCommand("procrank | grep surfaceflinger", true);
//            if (result.result == 0) {
//                String[] resultArr = result.successMsg.split(" ");
//                int index = 0;
//                StringBuilder surfaceMem = new StringBuilder();
//                surfaceMem.append("surfaceflinger占用内存，");
//                for (String value : resultArr) {
//                    if (!value.equals("")) {
//                        switch (index) {
//                            case 1:
//                                surfaceMem.append("Vss： ").append(value).append("B， ");
//                                break;
//                            case 2:
//                                surfaceMem.append("Rss： ").append(value).append("B， ");
//                                break;
//                            case 3:
//                                surfaceMem.append("Pss： ").append(value).append("B， ");
//                                break;
//                            case 4:
//                                surfaceMem.append("Uss： ").append(value).append("B");
//                                break;
//                        }
//                        index += 1;
//                    }
//                }
//                RunningLog.info(surfaceMem.toString());
//            }
            if (pid > 0) {
                Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{pid});
                if (memoryInfos != null && memoryInfos.length > 0 && memoryInfos[0] != null) {
                    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                    activityManager.getMemoryInfo(memoryInfo);
                    StringBuilder builder = new StringBuilder();
                    builder.append("机器当前可用RAM内存:").append(Formatter.formatFileSize(context, memoryInfo.availMem));
                    builder.append(",").append("当前应用占用RAM内存：").append(memoryInfos[0].getTotalPss()).append("KB");
                    builder.append(",").append("navtive堆总内存：").append(Formatter.formatFileSize(context, Debug.getNativeHeapSize()));
                    builder.append(",").append("navtive堆已使用内存：").append(Formatter.formatFileSize(context, Debug.getNativeHeapSize()));
                    builder.append(",").append("navtive堆剩余内存：").append(Formatter.formatFileSize(context, Debug.getNativeHeapSize()));
                    Runtime runtime = Runtime.getRuntime();
                    builder.append(",").append("已使用的java-heap：").append(Formatter.formatFileSize(context, runtime.totalMemory() - runtime.freeMemory()));
                    builder.append(",").append("未使用的java-heap：").append(Formatter.formatFileSize(context, runtime.freeMemory()));
                    builder.append(",").append("已申请的java-heap：").append(Formatter.formatFileSize(context, runtime.totalMemory()));
                    RunningLog.info(builder.toString());

                }
            }
        }
    }

    class DesktopNotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            ToastUtil.show(context, "开始暂停保护5分钟");
            if (action.equals(pauseAction)) {
                if (thread != null) {
                    thread.setPauseCount(10);
                }
            }
        }
    }
}
