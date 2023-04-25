package com.sunmnet.mediaroom.tabsp.connector.version3.network;


import com.sunmnet.mediaroom.common.bean.SystemEventType;
import com.sunmnet.mediaroom.common.bean.UpgradeInfo;
import com.sunmnet.mediaroom.common.events.SystemEvent;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.common.socket.ServerHandlerThread;
import com.sunmnet.mediaroom.tabsp.connector.service.IServerReceiver;

import org.greenrobot.eventbus.EventBus;

public class PlatformInfoHandler implements IServerReceiver<ServerHandlerThread> {
    @Override
    public void onReceiv(String msg) {

    }

    @Override
    public void onReceiv(byte[] msg) {

    }

    @Override
    public void onReceiv(String s, ServerHandlerThread handlerThread) {
        if (s.indexOf(".apk") >= 0 && (s.indexOf("|") >= 0)) {
            String[] groups = s.split("\\|");
            if (groups.length == 4) {
                RunningLog.run("开始执行远程更新!!");
                String apkName = groups[0];
                String url = groups[1];
                String response = groups[2];
                String md5 = groups[3];
                url += "?fileName=" + apkName;
                UpgradeInfo upgradeInfo = new UpgradeInfo();
                upgradeInfo.setUpgradeFileDownloadPath(url);
                upgradeInfo.setUpgradeResponsePath(response);
                upgradeInfo.setFileMd5(md5);
                SystemEvent systemEvent = new SystemEvent(SystemEventType.UPGRADE_START, upgradeInfo);
                EventBus.getDefault().post(systemEvent);
                //Initializer.getInstance().reloadDeivice();
                /*Device device = Initializer.getInstance().getDevice();
                VersionInfo info=device.getInfo();
                info.setResponsePath("http://" + device.getPlIp() + ":" + device.getPlPort() + "/"+response);
                String path = "http://" + device.getPlIp() + ":" + device.getPlPort() +  url;
                String folder = device.getFolder();
                File file = new File(folder, "cache");
                if (!file.exists()) {
                    file.mkdirs();
                }
                String cachePath = file.getAbsolutePath() + "/"+apkName;
                String tipes="正在执行远程更新,";
                Intent intent=new Intent(Tag.ACTION_TOAST_MESSAGE);
                if(!Tag.SCREEN_STATE){
                    tipes+="切勿关闭屏幕！";
                    intent.putExtra(Tag.DEVICE_DATA,tipes);
                    CommandExecution.CommandResult result=CommandExecution.execCommand("input keyevent 26",Constant.HASROOT);
                    tipes+="屏幕唤醒结果："+ JsonUtils.objectToJson(result);
                }else{
                    tipes+="请勿操作!!";
                    intent.putExtra(Tag.DEVICE_DATA,tipes);
                    CommandExecution.CommandResult result=CommandExecution.execCommand("input tap 10 10",Constant.HASROOT);
                    tipes+="屏幕保持常亮结果："+JsonUtils.objectToJson(result);
                }
                BaseApplication.getApplication().sendBroadcast(intent);
                RunningLog.run(tipes);
                FileDownLoader.startDownload(path,apkName,file.getAbsolutePath(),new UpgradeFileDownLoadCallBack(md5,new UpgradeCallbak()));*/
            }
        } else {
            /*String tagName = s.substring(0, 2);
            AbstractPotocol potocol = PotocolManager.getPotocolHandlerByTag(tagName);
            if (potocol != null) {
                synchronized (potocol) {
                    if (potocol instanceof AbstractServerProtocol) {
                        ((AbstractServerProtocol) potocol).registerServerSender(new ServerSender(this.sender, serverHandlerThread));
                    }
                    potocol.setMessage(s);
                    potocol.handle();
                }
            }*/
        }
    }

    @Override
    public void onReceiv(byte[] msg, ServerHandlerThread handlerThread) {

    }
}
