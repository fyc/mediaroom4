package com.sunmnet.mediaroom.tabsp.record.impl.hik.impl;

import android.view.Surface;
import android.view.SurfaceHolder;

import com.hikvision.netsdk.HCNetSDK;
import com.sun.jna.NativeLong;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.services.AbstractSDK;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class HikSDK extends AbstractSDK implements Runnable {
    public static final int TYPE_NORMAL = -1, TYPE_INTERACITVE = 1;
    public static final int RECORD_CMD_START = 0;
    public static final int RECORD_CMD_STOP = 1;
    public static final int RECORD_CMD_PAUSE = 2;
    public static final int RECORD_CMD_CONTINUE = 3;

    public HikSDK(String username, String password, String ip, String port) {
        super(username, password, ip, port);
    }

    @Override
    public void init() {
        ThreadUtils.execute(this);
    }

    @Override
    public boolean effection() {
        return init && this.lUserID != null && this.lUserID.intValue() != -1;
    }

    public NativeLong getUserID() {
        return this.lUserID;
    }

    //public NativeLong userId;
    boolean init = false;
    public HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40 deviceInfo;

    @Override
    public void run() {
        this.load();
        this.hcNetSDK = HCNetSDKByJNA.INSTANCE;
        init = hcNetSDK.NET_DVR_Init();
        if (init) {
            login();
        }
    }

    public boolean NET_DVR_StopRealPlay(int iRealHandle) {
        return this.hcNetSDK.NET_DVR_StopRealPlay(iRealHandle);
    }

    public int NET_DVR_RealPlay_V40(int lUserID, HCNetSDKByJNA.NET_DVR_PREVIEWINFO previewInfo, Surface surface, HCNetSDKByJNA.RealPlayCallBack callback) {
        HCNetSDKByJNA.INTER_PREVIEWINFO struPreviewInfo = new HCNetSDKByJNA.INTER_PREVIEWINFO();
        struPreviewInfo.bBlocked = previewInfo.bBlocked;
        struPreviewInfo.bPassbackRecord = previewInfo.bPassbackRecord;
        struPreviewInfo.byPreviewMode = previewInfo.byPreviewMode;
        struPreviewInfo.byProtoType = previewInfo.byProtoType;
        struPreviewInfo.dwLinkMode = previewInfo.dwLinkMode;
        struPreviewInfo.dwStreamType = previewInfo.dwStreamType;
        struPreviewInfo.lChannel = previewInfo.lChannel;
        if (surface != null) {
            if (!surface.isValid()) {
                return -1;
            }
        }
        return hcNetSDK.NET_DVR_RealPlay_V40(lUserID, struPreviewInfo, callback, surface);
        //return hcNetSDK.NET_DVR_RealPlay_V40(lUserID, struPreviewInfo, surface,CallBack);
    }
    private boolean check(String value){
        return value!=null&&!value.equals("")&&!value.equals("null");
    }
    private boolean check(){
        return check(getIp())&&check(getPort())&&check(getUsername())&&check(getPassword());
    }
    private void login() {
        if (check()){
            RunningLog.run("正在登录海康录播设备," + this.getIp() + ":" + this.getPort() + "--->" + this.getUsername() + "--->" + this.getPassword());
            HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40();
            HCNetSDKByJNA.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDKByJNA.NET_DVR_USER_LOGIN_INFO();
            System.arraycopy(ip.getBytes(), 0, loginInfo.sDeviceAddress, 0, ip.length());
            System.arraycopy(username.getBytes(), 0, loginInfo.sUserName, 0, username.length());
            System.arraycopy(password.getBytes(), 0, loginInfo.sPassword, 0, password.length());
            loginInfo.wPort = Short.parseShort(getPort()!=null?getPort():"0");
            loginInfo.write();
            int id = hcNetSDK.NET_DVR_Login_V40(loginInfo.getPointer(), deviceInfo.getPointer());
            if (id != -1) {
                this.lUserID = new NativeLong(id);
                RunningLog.run("登录成功!!");
                deviceInfo.read();
                this.deviceInfo = deviceInfo;
                EventBus.getDefault().postSticky(this);
            } else {
                this.deviceInfo = null;
                this.lUserID = null;
                RunningLog.error("登录失败，" + getLastError());
            }
        }else{
            RunningLog.run("海康录播设备参数-->" + this.getIp() + ":" + this.getPort() + "--->" + this.getUsername() + "--->" + this.getPassword()+"登录异常！！");
        }
    }

    public int getPlayerHandle() {
        int tunel = -1;
        String res = getRecordTerminals();
        RunningLog.run("正在获取播放句柄!" + res);
        try {
            if (res != null) {
                JSONObject terminal = XML.toJSONObject(res).getJSONObject("mirrorVideoChannelList");
                Object obj = terminal.get("mirrorVideoChannel");
                if (obj != null) {
                    if (obj instanceof JSONArray) {
                        JSONObject jsonObject = ((JSONArray) obj).getJSONObject(0);
                        tunel = jsonObject.getInt("id");
                    } else if (obj instanceof JSONObject) {
                        tunel = terminal.getJSONObject("mirrorVideoChannel").getInt("id");
                    }
                }
            } else {
                RunningLog.run("无法获取播放句柄");
                HCNetSDKByJNA.NET_DVR_DEVICEINFO_V30 deviceinfo = this.deviceInfo.struDeviceV30;
                if (deviceinfo != null) {
                    tunel = deviceinfo.byChanNum + deviceinfo.byIPChanNum + 1;
                    RunningLog.run("无法获取播放句柄，设置成:" + tunel + "不知道正常不正常");
                }
            }
        } catch (Exception e) {
            RunningLog.error(e);
        }
        RunningLog.run("播放通道口为:" + tunel);
        return tunel;
    }

    private String getRecordTerminals() {
        HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT inputCfg = new HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT();
        inputCfg.dwSize = inputCfg.size();
        String str = "GET /ISAPI/ContentMgmt/MirrorVideo/channels\r\n";
        HCNetSDKByJNA.BYTE_ARRAY byteArr = new HCNetSDKByJNA.BYTE_ARRAY(str.length());
        System.arraycopy(str.getBytes(), 0, byteArr.byValue, 0, str.length());
        byteArr.write();
        inputCfg.lpRequestUrl = byteArr.getPointer();
        inputCfg.dwRequestUrlLen = str.length();
        inputCfg.write();
        HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT struOutput = new HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT();
        struOutput.dwSize = struOutput.size();
        HCNetSDKByJNA.BYTE_ARRAY ptrOutputByte = new HCNetSDKByJNA.BYTE_ARRAY(1024 * 4);
        ptrOutputByte.write();
        struOutput.lpOutBuffer = ptrOutputByte.getPointer();
        struOutput.dwOutBufferSize = ptrOutputByte.size();
        HCNetSDKByJNA.BYTE_ARRAY ptrStatusByte = new HCNetSDKByJNA.BYTE_ARRAY(HCNetSDKByJNA.ISAPI_STATUS_LEN);
        struOutput.lpStatusBuffer = ptrStatusByte.getPointer();
        struOutput.dwStatusSize = HCNetSDKByJNA.ISAPI_STATUS_LEN;
        struOutput.write();
        boolean isSuccess = hcNetSDK.NET_DVR_STDXMLConfig(lUserID.intValue(), inputCfg, struOutput);
        if (isSuccess) {
            ptrOutputByte.read();
            String ss = new String(ptrOutputByte.byValue).trim();
            return ss;
        }
        return null;
    }

    public int getDeviceType() {
        int type = TYPE_NORMAL;
        RunningLog.run("初始化海康类型！！");
        HCNetSDKByJNA.BYTE_ARRAY outputXmlBuffer = new HCNetSDKByJNA.BYTE_ARRAY(10240);
        outputXmlBuffer.write();
        HCNetSDKByJNA.BYTE_ARRAY byteArr = new HCNetSDKByJNA.BYTE_ARRAY(10240);
        byteArr.write();
        boolean isGetRes = READ_STD_XML(this.lUserID.intValue(), null, outputXmlBuffer, byteArr, HCNetSDKByJNA.NET_DVR_GET_VCS_CAP);
        if (isGetRes) {
            outputXmlBuffer.read();
            String result = new String(outputXmlBuffer.byValue).trim();
            RunningLog.run("互动能力集获取成功!!" + result);
            try {
                JSONObject vcsCap = XML.toJSONObject(result).getJSONObject("VCSCap");
                int terminalNum = vcsCap.getInt("maxConferenceNum");
                if (terminalNum > 1) {
                    RunningLog.run("当前录播类型为互动录播");
                    type = TYPE_INTERACITVE;
                } else {
                    RunningLog.run("当前录播类型为普通录播");
                }
            } catch (Exception e) {
                RunningLog.error("互动能力集转换异常！！");
                RunningLog.error(e);
            }
        } else {
            RunningLog.error("互动能力获取失败," + getLastError());
        }
        return type;
    }
}
