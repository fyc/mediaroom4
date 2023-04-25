package com.sunmnet.mediaroom.brand;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.sunmnet.mediaroom.brand.broadcast.AlarmReceiver;
import com.sunmnet.mediaroom.brand.broadcast.BroadcastAction;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.bean.play.NotificationRule;
import com.sunmnet.mediaroom.brand.bean.play.ProgramRule;
import com.sunmnet.mediaroom.brand.bean.play.PublishingRule;
import com.sunmnet.mediaroom.brand.bean.play.TemplateProgramRule;
import com.sunmnet.mediaroom.brand.data.database.gen.NotificationRuleDBEntityDao;
import com.sunmnet.mediaroom.brand.data.database.gen.ProgramRuleDBEntityDao;
import com.sunmnet.mediaroom.brand.data.database.gen.TemplateProgramRuleDBEntityDao;
import com.sunmnet.mediaroom.brand.data.database.play.NotificationRuleDBEntity;
import com.sunmnet.mediaroom.brand.data.database.play.ProgramRuleDBEntity;
import com.sunmnet.mediaroom.brand.data.database.play.TemplateProgramRuleDBEntity;
import com.sunmnet.mediaroom.brand.enums.PlayType;
import com.sunmnet.mediaroom.brand.impl.play.AbstractPlayable;
import com.sunmnet.mediaroom.brand.impl.play.Notification;
import com.sunmnet.mediaroom.brand.impl.play.Program;
import com.sunmnet.mediaroom.brand.impl.play.TemplateProgram;
import com.sunmnet.mediaroom.brand.interfaces.play.IPlayable;
import com.sunmnet.mediaroom.brand.utils.DaoManager;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PlayableManager {

    private static PlayableManager instance;

    private final static Handler mHandler = new Handler(Looper.getMainLooper());

    private AbstractPlayable playingProgram;
    private Notification playingNotification;

    private final Map<String, ProgramRule> programRules;
    private final Map<String, NotificationRule> notificationRules;
    private final Map<String, TemplateProgramRule> templateProgramRules;

    public boolean isStartingProgram() {
        return playingProgram != null && (playingProgram.isPlaying() || playingProgram.getState() == IPlayable.PlayState.PREPLAY);
    }

    public boolean isPlayingNotification() {
        return playingNotification != null && playingNotification.isPlaying();
    }

    public static PlayableManager getInstance() {
        if (instance == null) {
            instance = new PlayableManager();
        }
        return instance;
    }

    private PlayableManager() {
        this.programRules = new HashMap<>();
        this.notificationRules = new HashMap<>();
        this.templateProgramRules = new HashMap<>();
        initPrograms();
        initNotifications();
        initTemplatePrograms();
    }

    private void initPrograms() {
        //查询数据库列出所有保存的节目信息
        ProgramRuleDBEntityDao programRuleDBEntityDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getProgramRuleDBEntityDao();
        List<ProgramRuleDBEntity> programRuleList = programRuleDBEntityDao.loadAll();
        for (ProgramRuleDBEntity rule : programRuleList) {
            programRules.put(rule.getId(), rule.mappingToPublishingRule());
        }
    }

    private void initNotifications() {
        //查询数据库列出所有保存的通知信息
        NotificationRuleDBEntityDao dao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getNotificationRuleDBEntityDao();
        List<NotificationRuleDBEntity> notificationRuleList = dao.loadAll();
        for (NotificationRuleDBEntity rule : notificationRuleList) {
            notificationRules.put(rule.getId(), rule.mappingToPublishingRule());
        }
    }

    private void initTemplatePrograms() {
        //查询数据库列出所有保存的通知信息
        TemplateProgramRuleDBEntityDao dao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getTemplateProgramRuleDBEntityDao();
        List<TemplateProgramRuleDBEntity> notificationRuleList = dao.loadAll();
        for (TemplateProgramRuleDBEntity rule : notificationRuleList) {
            templateProgramRules.put(rule.getId(), rule.mappingToPublishingRule());
        }
    }

    /**
     * 接收到平台发送过来的规则
     */
    public void receiveRule(PlayType type, String ruleString) {
        synchronized (PlayableManager.this) {
            RunningLog.run("开始处理规则信息");
            switch (type) {
                case PROGRAM:
                    ProgramRule programRule = JacksonUtil.jsonStrToBean(ruleString, ProgramRule.class);
                    if (programRule == null)
                        return;
                    ProgramRuleDBEntityDao programDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getProgramRuleDBEntityDao();
                    //按播放类型（普通，插播）清楚旧规则
                    if (programRule.isCleanOld() && programRule.getPlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                        cleanOldProgram(programRule.getPlayType());
                    }
                    programRules.put(programRule.getId(), programRule);
                    programDao.insertOrReplace(new ProgramRuleDBEntity(programRule));
                    break;
                case TEMPLATE_PROGRAM:
                    TemplateProgramRule templateProgramRule = JacksonUtil.jsonStrToBean(ruleString, TemplateProgramRule.class);
                    if (templateProgramRule == null)
                        return;
                    TemplateProgramRuleDBEntityDao templateDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getTemplateProgramRuleDBEntityDao();
                    //按播放类型（普通，插播）清楚旧规则
                    if (templateProgramRule.isCleanOld() && templateProgramRule.getPlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                        cleanOldProgram(templateProgramRule.getPlayType());
                    }
                    templateProgramRules.put(templateProgramRule.getId(), templateProgramRule);
                    templateDao.insertOrReplace(new TemplateProgramRuleDBEntity(templateProgramRule));
                    break;
                case NOTIFICATION:
                    NotificationRule notificationRule = JacksonUtil.jsonStrToBean(ruleString, NotificationRule.class);
                    if (notificationRule == null)
                        return;
                    NotificationRuleDBEntityDao notificationDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getNotificationRuleDBEntityDao();
                    //按播放类型（普通，插播）清楚旧规则
                    if (notificationRule.isCleanOld() && notificationRule.getPlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                        notificationDao.queryBuilder().where(NotificationRuleDBEntityDao.Properties.PlayType.eq(notificationRule.getPlayType())).buildDelete()
                                .executeDeleteWithoutDetachingEntities();
                        Iterator<Map.Entry<String, NotificationRule>> iterator = notificationRules.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, NotificationRule> entry = iterator.next();
                            if (entry.getValue().getPlayType() == notificationRule.getPlayType()) {
                                iterator.remove();
                            }
                        }
                    }
                    notificationRules.put(notificationRule.getId(), notificationRule);
                    notificationDao.insertOrReplace(new NotificationRuleDBEntity(notificationRule));
                    break;
            }
        }
        reload();
    }

    private void cleanOldProgram(int playType) {
        ProgramRuleDBEntityDao programDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getProgramRuleDBEntityDao();
        programDao.queryBuilder().where(ProgramRuleDBEntityDao.Properties.PlayType.eq(playType)).buildDelete()
                .executeDeleteWithoutDetachingEntities();
        Iterator<Map.Entry<String, ProgramRule>> iterator = programRules.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ProgramRule> entry = iterator.next();
            if (entry.getValue().getPlayType() == playType) {
                iterator.remove();
            }
        }
        TemplateProgramRuleDBEntityDao templateDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getTemplateProgramRuleDBEntityDao();
        templateDao.queryBuilder().where(TemplateProgramRuleDBEntityDao.Properties.PlayType.eq(playType)).buildDelete()
                .executeDeleteWithoutDetachingEntities();
        Iterator<Map.Entry<String, TemplateProgramRule>> iterator2 = templateProgramRules.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, TemplateProgramRule> entry = iterator2.next();
            if (entry.getValue().getPlayType() == playType) {
                iterator2.remove();
            }
        }
    }

    //检查需要当前播放的通知
    public void initPlayingNotification() {
        synchronized (PlayableManager.class) {
            RunningLog.run("正在查找当前需要播放的通知");
            if (isPlayingNotification()) {
                stopPlay(playingNotification);
            }
            ArrayList<Notification> notifications = new ArrayList<>();
            for (NotificationRule rule : notificationRules.values()) {
                Notification notification = new Notification(rule);
                if (notification.isWithinPlayTime() && notification.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                    notifications.add(notification);
                }
            }
            if (notifications.size() == 0) {
                RunningLog.run("当前没有通知需要播放");
                return;
            }
            if (notifications.size() == 1) {
                startPlay(notifications.get(0));
            } else {
                Notification play = null;
                for (Notification notification : notifications) {
                    if (notification.isInterCut()) {
                        play = notification;
                        break;
                    }
                }
                if (play == null)
                    play = notifications.get(0);
                startPlay(play);
            }
        }
    }

    /**
     * 初始化播放 节目/模板节目
     */
    public void initPlayingProgram() {
        synchronized (PlayableManager.class) {
            RunningLog.run("正在查找当前需要播放的节目");
            if (isStartingProgram()) {
                stopPlay(playingProgram);
            }
            //普通节目
            ArrayList<AbstractPlayable> programs = new ArrayList<>();
            for (ProgramRule rule : programRules.values()) {
                Program program = new Program(rule);
                if (program.isWithinPlayTime() && program.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                    programs.add(program);
                }
            }
            //模板节目
            for (TemplateProgramRule rule : templateProgramRules.values()) {
                TemplateProgram program = new TemplateProgram(rule);
                if (program.isWithinPlayTime() && program.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                    programs.add(program);
                }
            }
            if (programs.size() == 0) {
                RunningLog.run("当前没有节目需要播放");
                return;
            }
            if (programs.size() == 1) {
                startPlay(programs.get(0));
            } else {
                AbstractPlayable play = null;
                for (AbstractPlayable program : programs) {
                    if (program.isInterCut()) {
                        play = program;
                        break;
                    }
                }
                if (play == null)
                    play = programs.get(0);
                startPlay(play);
            }
        }
    }

    public void registerNextPlayProgramAlarm() {
        synchronized (PlayableManager.class) {
            RunningLog.run("正在查找下次播放的节目");
            ArrayList<AbstractPlayable> programs = new ArrayList<>();
            for (ProgramRule rule : programRules.values()) {
                Program program = new Program(rule);
                //不在当前播放时间段
                if (!program.isWithinPlayTime() && program.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY && program.isAlive()) {
                    programs.add(program);
                }
            }
            for (TemplateProgramRule rule : templateProgramRules.values()) {
                TemplateProgram program = new TemplateProgram(rule);
                if (!program.isWithinPlayTime() && program.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY && program.isAlive()) {
                    programs.add(program);
                }
            }
            if (programs.size() == 0) {
                RunningLog.run("下次没有节目播放");
                return;
            }
            AbstractPlayable play = null;
            for (AbstractPlayable program : programs) {
                if (play == null) {
                    play = program;
                    continue;
                }
                play = play.getPlayStartTime().before(program.getPlayStartTime()) ? play : program;
            }
            if (play == null) {
                RunningLog.run("下次没有节目播放");
                return;
            }
            RunningLog.run("下次播放节目：" + (play.getRule() == null ? null : play.getRule().getName()));
            RunningLog.run("开始注册下次播放节目的闹钟时间：" + DateUtil.formatDate(play.getPlayStartTime(), DateUtil.DEFAULT_FORMAT));
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                intent.setAction(BroadcastAction.ACTION_ALARM_START_PLAY_PROGRAM);
            } else {
                intent = new Intent(BroadcastAction.ACTION_ALARM_START_PLAY_PROGRAM);
            }
            play.readyPlay();
            AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.START_PLAY_PROGRAM, play.getPlayStartTime());
        }
    }

    public void registerNextPlayNotificationAlarm() {
        synchronized (PlayableManager.class) {
            RunningLog.run("正在查找下次播放的通知");
            ArrayList<Notification> notifications = new ArrayList<>();
            for (NotificationRule rule : notificationRules.values()) {
                Notification notification = new Notification(rule);
                //不在当前播放时间段
                if (!notification.isWithinPlayTime() && notification.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY && notification.isAlive()) {
                    notifications.add(notification);
                }
            }
            if (notifications.size() == 0) {
                RunningLog.run("下次没有通知播放");
                return;
            }
            Notification play = null;
            for (Notification notification : notifications) {
                if (play == null) {
                    play = notification;
                    continue;
                }
                play = play.getPlayStartTime().before(notification.getPlayStartTime()) ? play : notification;
            }
            if (play == null) {
                RunningLog.run("下次没有通知播放");
                return;
            }
            RunningLog.run("下次播放通知：" + (play.getRule() == null ? null : play.getRule().getName()));
            RunningLog.run("开始注册下次播放通知的闹钟时间：" + DateUtil.formatDate(play.getPlayStartTime(), DateUtil.DEFAULT_FORMAT));
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                intent.setAction(BroadcastAction.ACTION_ALARM_START_PLAY_NOTIFICATION);
            } else {
                intent = new Intent(BroadcastAction.ACTION_ALARM_START_PLAY_NOTIFICATION);
            }
            play.readyPlay();
            AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.START_PLAY_NOTIFICATION, play.getPlayStartTime());
        }
    }

    public void startPlay(PlayType playType, String id) {
        synchronized (PlayableManager.class) {
            if (playType == PlayType.PROGRAM) {
                ProgramRule rule = programRules.get(id);
                if (rule != null) {
                    Program program = new Program(rule);
                    if (program.isWithinPlayTime() && program.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                        startPlay(program);
                    }
                }
            } else if (playType == PlayType.NOTIFICATION) {
                NotificationRule rule = notificationRules.get(id);
                if (rule != null) {
                    Notification notification = new Notification(rule);
                    if (notification.isWithinPlayTime() && notification.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                        startPlay(notification);
                    }
                }
            } else if (playType == PlayType.TEMPLATE_PROGRAM) {
                TemplateProgramRule rule = templateProgramRules.get(id);
                if (rule != null) {
                    TemplateProgram templateProgram = new TemplateProgram(rule);
                    if (templateProgram.isWithinPlayTime() && templateProgram.getRulePlayStatus() == PublishingRule.PLAY_STATUS_PLAY) {
                        startPlay(templateProgram);
                    }
                }
            }
        }
    }

    public void startPlay(final IPlayable playable) {
        if (playable == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopPlay(playable);
                RunningLog.run("开始播放：" + (playable.getRule() == null ? null : playable.getRule().getName()));
                if (playable instanceof Program || playable instanceof TemplateProgram) {
                    playingProgram = (AbstractPlayable) playable;
                    playable.startPlay();
                    Intent intent;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                        intent.setAction(BroadcastAction.ACTION_ALARM_STOP_PLAY_PROGRAM);
                    } else {
                        intent = new Intent(BroadcastAction.ACTION_ALARM_STOP_PLAY_PROGRAM);
                    }
                    intent.putExtra("id", playable.getPlayId());
                    intent.putExtra("type", playable.getType());
                    AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.STOP_PLAY_PROGRAM, playable.getPlayEndTime());
                } else if (playable instanceof Notification) {
                    playingNotification = (Notification) playable;
                    playable.startPlay();
                    Intent intent;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
                        intent.setAction(BroadcastAction.ACTION_ALARM_STOP_PLAY_NOTIFICATION);
                    } else {
                        intent = new Intent(BroadcastAction.ACTION_ALARM_STOP_PLAY_NOTIFICATION);
                    }
                    intent.putExtra("id", playable.getPlayId());
                    intent.putExtra("type", playable.getType());
                    AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.STOP_PLAY_NOTIFICATION, playable.getPlayEndTime());
                }
            }
        });
    }

    public void stopPlay(String id, int type) {
        synchronized (PlayableManager.class) {
            if (type == PublishingRule.TYPE_PROGRAM || type == PublishingRule.TYPE_TEMPLATE_PROGRAM) {
                if (isStartingProgram() && playingProgram.getPlayId().equals(id)) {
                    stopPlay(playingProgram);
                }
            } else if (type == PublishingRule.TYPE_NOTIFICATION) {
                if (isPlayingNotification() && playingNotification.getPlayId().equals(id)) {
                    stopPlay(playingNotification);
                }
            }
        }
    }

    public void stopPlay(final IPlayable playable) {
        if (playable == null)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (playable instanceof Program || playable instanceof TemplateProgram) {
                    if (isStartingProgram() && playable.getPlayId().equals(playingProgram.getPlayId())) {
                        RunningLog.run("停止播放：" + (playingProgram.getRule() == null ? null : playingProgram.getRule().getName()));
                        playingProgram.stopPlay();
                    }
                } else if (playable instanceof Notification) {
                    if (isPlayingNotification() && playable.getPlayId().equals(playingNotification.getPlayId())) {
                        RunningLog.run("停止播放：" + (playingNotification.getRule() == null ? null : playingNotification.getRule().getName()));
                        playingNotification.stopPlay();
                    }
                }
            }
        });
    }

    public void stopPlay() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isStartingProgram()) {
                    RunningLog.run("停止播放：" + (playingProgram.getRule() == null ? null : playingProgram.getRule().getName()));
                    playingProgram.stopPlay();
                }
                if (isPlayingNotification()) {
                    RunningLog.run("停止播放：" + (playingNotification.getRule() == null ? null : playingNotification.getRule().getName()));
                    playingNotification.stopPlay();
                }
            }
        });
    }

    public void reload() {
        RunningLog.run("重新加载节目通知");
        initPlayingProgram();
        initPlayingNotification();
        registerNextPlayProgramAlarm();
        registerNextPlayNotificationAlarm();
    }

    public void cleanProgram() {
        synchronized (PlayableManager.this) {
            DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getProgramRuleDBEntityDao().deleteAll();
            programRules.clear();
            reload();
        }
    }

    public void cleanTemplateProgram() {
        synchronized (PlayableManager.this) {
            DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getTemplateProgramRuleDBEntityDao().deleteAll();
            templateProgramRules.clear();
            reload();
        }
    }

    public void cleanNotification() {
        synchronized (PlayableManager.this) {
            DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getNotificationRuleDBEntityDao().deleteAll();
            notificationRules.clear();
            reload();
        }
    }

    private void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != mHandler.getLooper().getThread()) {
            mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
