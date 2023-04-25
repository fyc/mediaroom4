package com.bozee.managerapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.bozee.managerappsdk.ConnectionManager;
import com.bozee.managerappsdk.DeviceFinder;
import com.bozee.managerappsdk.IAuthorizeListener;
import com.bozee.managerappsdk.IClassRoomRemoteCallback;
import com.bozee.managerappsdk.IConnectListener;
import com.bozee.managerappsdk.IMainActivityEvenListener;
import com.bozee.managerappsdk.IReceiveSettingListener;
import com.bozee.managerappsdk.IReceiveSystemInfoListener;
import com.bozee.managerappsdk.IRemoteCallback;
import com.bozee.managerappsdk.models.AnDisplayInfo;
import com.bozee.managerappsdk.models.ClientDevice;
import com.bozee.managerappsdk.models.PreferenceItemInfo;
import com.bozee.managerappsdk.models.ServerDevice;
import com.bozee.managerappsdk.models.SettingInfo;
import com.bozee.managerappsdk.models.SystemInfo;
import com.sunmnet.mediaroom.wirelessprojection.R;
import com.sunmnet.mediaroom.wirelessprojection.databinding.Contents;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/**
 * 使用前准备：
 * 1、导包：implementation 'io.netty:netty-all:4.1.43.Final'
 * 2、权限：
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * <uses-permission android:name="android.permission.INTERNET" />
 * <p>
 * 一般使用流程：
 * 1、先获取ConnectionManager实例
 * * ConnectionManager.getInstance()
 * 2、开始发现服务端。
 * 3、开始连接目标服务端。
 * 4、和目标服务端进行认证，认证成功后才能向目标服务端发起数据请求。
 * 5、为了保证正常接收服务端回调的数据，可先设置服务端回调的监听器。
 * 6、向服务端发起各种请求。
 */

public class WirelessActivity extends Activity {
    final String TAG = WirelessActivity.class.getName();
    ConnectionManager mConnectionManager;

    private EditText hostIpView;
    Contents content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = DataBindingUtil.setContentView(this, R.layout.activity_main);

        hostIpView = findViewById(R.id.host_ip);
        //先获取实例
        mConnectionManager = ConnectionManager.getInstance();
        content.setValue(this);
    }

    /**
     * 开启发现服务端广播
     * 设置好相关参数后，mDeviceFinder.start();
     */
    DeviceFinder mDeviceFinder;
    private static final int BROADCAST_PORT = 11105;

    public void startServerFinder(View view) {
        Log.i(TAG, "发现服务端开始");
        //设置服务端设备发现回调Listener
        DeviceFinder.IDeviceFinderListener deviceFinderListener = new DeviceFinder.IDeviceFinderListener() {
            @Override
            public void onFindDevice(ServerDevice serverItem) {
                Log.i(TAG, "发现服务端 --> " + serverItem);
            }
        };
        //新建DeviceFinder
        mDeviceFinder = new DeviceFinder(this);
        //设置刚定义的回调Listener
        mDeviceFinder.setDeviceFinderListener(deviceFinderListener);
        //获取本局域网广播地址
        InetAddress broadcastAddress = mDeviceFinder.getBroadcastAddress(this);
        //设置广播地址、端口号，端口号需要和服务端广播的端口的一样
        mDeviceFinder.setBroadcastAddress(broadcastAddress);
        //开启广播
        mDeviceFinder.start();
    }

    /**
     * 停止发现服务端
     * mDeviceFinder.stop();
     */
    public void stopServerFinder(View view) {
        if (null != mDeviceFinder) {
            //停止广播
            mDeviceFinder.stop();
            mDeviceFinder = null;
        }
    }

    /**
     * 连接server
     * ConnectionManager.connect(目标服务端IP，和服务端约定好的连接端口号（并非上述的广播端口号）， 回调的Listener)
     */
    //如目标服务端IP地址是：192.168.1.146
    String mIp = "172.16.24.143";
    private static final int SERVER_PORT = 10023;

    public void connectServerFinder(final View view) {
        //ConnectionManager.connect(目标服务端IP，和服务端约定好的连接端口号（并非上述的广播端口号）， 回调Listener)
        String host = hostIpView.getText().toString();
        if (!"".equals(host)) {
            mIp = host;
        }

        mConnectionManager.connect(mIp, SERVER_PORT, new IConnectListener() {
            @Override
            public void onConnectStarted() {
                Log.i(TAG, "连接服务端 开始 ， IP： " + mIp);
            }

            @Override
            public void onConnectSuccess() {
                Log.i(TAG, "连接服务端 成功");
                //一般连接成功后停止发现服务端的广播
                if (null != mDeviceFinder) {
                    //停止广播
                    mDeviceFinder.stop();
                }
                setAllListener();
            }

            @Override
            public void onConnectFailed(int errorCode) {
                Log.d(TAG, "连接服务端 失败");
            }
        });
    }

    /**
     * 断开server
     * mConnectionManager.stopConnection();
     */
    public void disConnectServerFinder(View view) {
        Log.i(TAG, "断开server");
        mConnectionManager.stopConnection();
    }

    /**
     * 请求服务端认证
     * mConnectionManager.requestServerAuthorize(约定好的KEY, MD5加密后的密码, 协议版本号, 回调的Listener);
     */
    String mAppKey = "com.bozee.adminclient";
    String mPassword = getMd5("123456");

    public static String getMd5(String text) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(text.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    int PROTOCOL_VERSION = 1;

    public void requestServerAuthorize(View view) {
        mConnectionManager.requestAuthorize(mAppKey, mPassword, PROTOCOL_VERSION);
    }

    /**
     * 取消认证
     * mConnectionManager.cancelServerAuthorize();
     */
    public void cancelAuthorize(View view) {
        mConnectionManager.cancelAuthorize();
    }

    /**
     * 请求设备信息
     * mConnectionManager.requestAnDisplayClientsInfo(回调的Listener);
     */
    public void requestAnDisplayInfo(View view) {


        mConnectionManager.requestAnDisplayInfo();
    }

    ClientDevice mClientDevice;

    /**
     * 请求更改上屏权限 --> 询问
     * mConnectionManager.requestUpdateDevicePermitStatus(需要设置的设备的IP, 上屏权限类型, 回调的Listener);
     */
    public void requestUpdatePermitStatusAsk(View view) {
        if (null != mClientDevice) {
            mConnectionManager.requestUpdatePermitStatus(mClientDevice.mStrIp, ClientDevice.ASK);
        }
    }

    /**
     * 请求更改上屏权限 --> 一直同意
     * mConnectionManager.requestUpdateDevicePermitStatus(需要设置的设备的IP, 上屏权限类型, 回调的Listener);
     */
    public void requestUpdatePermitStatusAlwaysAccepted(View view) {
        if (null != mClientDevice) {
            mConnectionManager.requestUpdatePermitStatus(mClientDevice.mStrIp, ClientDevice.ACCEPTED);
        }
    }

    /**
     * 请求更改上屏权限 --> 一直拒绝
     * mConnectionManager.requestUpdateDevicePermitStatus(需要设置的设备的IP, 上屏权限类型, 回调的Listener);
     */
    public void requestUpdatePermitStatusAlwaysRejected(View view) {
        if (null != mClientDevice) {
            mConnectionManager.requestUpdatePermitStatus(mClientDevice.mStrIp, ClientDevice.REJECTED);
        }
    }

    /**
     * 请求更新锁定状态 --> 锁定
     * mConnectionManager.requestUpdateClassRoomLockStatus(需要设置的设备的IP, 锁定类型, 回调的Listener);
     */
    public void requestUpdateLockStatusLocked(View view) {

        if (null != mClientDevice) {
            mConnectionManager.requestUpdateLockStatus(mClientDevice.mStrIp, ClientDevice.LOCKED);
        }
    }

    /**
     * 请求更新锁定状态 --> 未锁定
     * mConnectionManager.requestUpdateClassRoomLockStatus(需要设置的设备的IP, 锁定类型, 回调的Listener);
     */
    public void requestUpdateLockStatusUnLocked(View view) {
        if (null != mClientDevice)
            mConnectionManager.requestUpdateLockStatus(mClientDevice.mStrIp, ClientDevice.UNLOCKED);
    }

    /**
     * 请求更新某个客户端的授课状态 --> 授课状态
     * 授课类型
     * ClientDevice.WORK_ON	授课状态
     * ClientDevice.WORK_OUT	讨论状态（即未授课）
     * mConnectionManager.requestUpdateClassRoomWorkOutStatus(需要设置的设备的IP, 授课类型, 回调的Listener);
     */
    public void requestUpdateClassRoomWorkOn(View view) {
        if (null != mClientDevice)
            mConnectionManager.requestClassroomStudentDeviceWorkOnOffData(mClientDevice.mStrIp, ClientDevice.WORK_ON);
    }

    /**
     * 请求更新某个客户端的授课状态 --> 讨论状态
     * 授课类型
     * ClientDevice.WORK_ON	授课状态
     * ClientDevice.WORK_OUT	讨论状态（即未授课）
     * mConnectionManager.requestUpdateLockStatus(需要设置的设备的IP, 授课类型, 回调的Listener);
     */
    public void requestUpdateClassRoomWorkOut(View view) {
        if (null != mClientDevice)
            mConnectionManager.requestClassroomStudentDeviceWorkOnOffData(mClientDevice.mStrIp, ClientDevice.WORK_OUT);
    }

    /**
     * 请求切换设备投屏状态 --> 进行投屏
     * 投屏状态类型 ： ClientDevice.CLIENTS_ON_SCREEN 进行投屏 , ClientDevice.CLIENTS_OFF_SCREEN 断开投屏
     * mConnectionManager.requestUpdateDeviceScreenStatus(需要设置的设备的IP, 投屏状态类型, 回调的Listener);
     */
    public void requestSwitchDeviceStateSharing(View view) {
        if (null != mClientDevice)
            mConnectionManager.requestSwitchDeviceState(mClientDevice.mStrIp, true);
    }

    /**
     * 请求切换设备投屏状态 --> 断开投屏
     * 投屏状态类型 ： ClientDevice.CLIENTS_ON_SCREEN 进行投屏 , ClientDevice.CLIENTS_OFF_SCREEN 断开投屏
     * mConnectionManager.requestUpdateDeviceScreenStatus(需要设置的设备的IP, 投屏状态类型, 回调的Listener);
     */
    public void requestSwitchDeviceStateReady(View view) {
        if (null != mClientDevice)
            mConnectionManager.requestSwitchDeviceState(mClientDevice.mStrIp, false);
    }

    /**
     * 一键展示
     * 展示类型：ClientDevice.ALL_CLIENTS_ON_SCREEN 展示， ClientDevice.ALL_CLIENTS_OFF_SCREEN 隐藏
     * mConnectionManager.requestSwitchClassroomAllClientsScreenStatus(展示类型, 回调的Listener);
     */
    public void requestClassroomAllClientsOnScreen(View view) {
        mConnectionManager.requestClassroomAllClientsOnOffScreen(ClientDevice.ALL_CLIENTS_ON_SCREEN);
    }

    /**
     * 一键隐藏（即取消一键展示）
     * 展示类型：ClientDevice.ALL_CLIENTS_ON_SCREEN 展示， ClientDevice.ALL_CLIENTS_OFF_SCREEN 隐藏
     * mConnectionManager.requestSwitchClassroomAllClientsScreenStatus(展示类型, 回调的Listener);
     */
    public void requestClassroomAllClientsOffScreen(View view) {
        mConnectionManager.requestClassroomAllClientsOnOffScreen(ClientDevice.ALL_CLIENTS_OFF_SCREEN);
    }

    /**
     * 一键授课
     * mConnectionManager.requestSwitchClassRoomAllClientsWorkStatus(ClientDevice.ALL_CLIENTS_WORK_ON, 回调的Listener);
     */
    public void requestClassRoomAllClientsWorkOn(View view) {
        mConnectionManager.requestClassRoomAllClientsWorkOnOut(ClientDevice.ALL_CLIENTS_WORK_ON);
    }

    /**
     * 一键讨论
     * mConnectionManager.requestSwitchClassRoomAllClientsWorkStatus(ClientDevice.ALL_CLIENTS_WORK_OUT, 回调的Listener);
     */
    public void requestClassRoomAllClientsWorkOut(View view) {
        mConnectionManager.requestClassRoomAllClientsWorkOnOut(ClientDevice.ALL_CLIENTS_WORK_OUT);
    }

    /**
     * 一键锁定
     * mConnectionManager.requestSwitchClassRoomAllClientsLockStatus(ClientDevice.ALL_CLIENTS_LOCKED, 回调的Listener);
     */
    public void requestAllClientsLock(View view) {
        mConnectionManager.requestAllClientsLock(ClientDevice.LOCKED);
    }

    /**
     * 一键解锁
     * mConnectionManager.requestSwitchClassRoomAllClientsLockStatus(ClientDevice.ALL_CLIENTS_UNLOCKED, 回调的Listener);
     */
    public void requestAllClientsUnLock(View view) {
        mConnectionManager.requestAllClientsLock(ClientDevice.UNLOCKED);
    }

    /**
     * 请求设置信息
     * PreferenceItemInfo类
     * key : string;  // Preference的key
     * value : string;  // 对应的value
     * valueType : int;   // value类型：整型(VALUE_TYPE_INT), 布尔(VALUE_TYPE_BOOLEAN), 字符串(VALUE_TYPE_STRING)
     * preferenceType : int;   // EditTextPreference(PREFERENCE_TYPE_EDIT_TEXT), SwitchPreference(PREFERENCE_TYPE_SWITCH), ListPreference(PREFERENCE_TYPE_LIST)
     * entriesList : [string];   // 针对 ListPreference 类型，一般在请求设置信息时会收到服务端返回其支持的entriesList, 请求更改Setting信息时可不用设置该项
     * <p>
     * mConnectionManager.requestAnDisplaySettingInfo(回调的Listener);
     */
    public void requestAnDisplaySettingInfo(View view) {
        mConnectionManager.requestAnDisplayInfo();
    }

    /**
     * PreferenceItemInfo类
     * key : string;  // Preference的key
     * value : string;  // 对应的value
     * valueType : int;   // value类型：整型(VALUE_TYPE_INT), 布尔(VALUE_TYPE_BOOLEAN), 字符串(VALUE_TYPE_STRING)
     * preferenceType : int;   // EditTextPreference(PREFERENCE_TYPE_EDIT_TEXT), SwitchPreference(PREFERENCE_TYPE_SWITCH), ListPreference(PREFERENCE_TYPE_LIST)
     * entriesList : [string];   // 针对 ListPreference 类型，一般在请求设置信息时会收到服务端返回其支持的entriesList, 请求更改Setting信息时可不用设置该项
     * <p>
     * 请求更改Setting信息
     * mConnectionManager.requestUpdateSettingInfo(SettingInfo, 回调的Listener);
     */
    int i = 1;

    public void requestAnDisplaySettingInfoChange(View view) {
        //例如现在要把 key为 airplay_resolution (Airplay) 的 Preference 的value(分辨率)在为1 (1080) 和 2 (720)切换
        String key = "airplay_resolution";
        String value;
        if (i % 2 == 1) {
            value = "1";
        } else {
            value = "2";
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        SettingInfo settingInfo = new SettingInfo();
        PreferenceItemInfo preferenceItemInfo = new PreferenceItemInfo();
        preferenceItemInfo.mKey = key;
        preferenceItemInfo.mValue = value;
        preferenceItemInfo.mEntriesList = list;
        preferenceItemInfo.mPreferenceType = PreferenceItemInfo.PREFERENCE_TYPE_LIST;
        preferenceItemInfo.mValueType = PreferenceItemInfo.VALUE_TYPE_BOOLEAN;
        settingInfo.mPreferenceItemInfoList.add(preferenceItemInfo);

        mConnectionManager.requestAnDisplaySettingInfoChange(settingInfo);
    }

    /**
     * 请求系统信息
     * SystemInfo类
     * mSystemVersion : string; //系统的版本
     * mAnDisplayVersion : string;//传屏接收程序的版本
     * mConfigureVersion : string;//传屏配置程序的版本
     * mSystemPlatform : string;//系统的平台
     * <p>
     * mConnectionManager.requestAnDisplaySystemInfo(回调的Listener);
     */
    public void requestSystemInfo(View view) {
        mConnectionManager.requestSystemInfo();
    }

    /**
     * 请求一键踢人
     * mConnectionManager.requestClassroomKickAllClients(回调的Listener);
     */
    public void requestClassroomKickAllClients(View view) {
        mConnectionManager.requestClassroomKickAllClients();
    }

    /**
     * 请求让连接某个小组的设备下屏，即踢人
     * mConnectionManager.requestClassroomKickClient(需要踢人的小组IP, 回调的Listener);
     */
    public void requestClassroomKickClient(View view) {
        mConnectionManager.requestClassroomKickClient(mClientDevice.mStrIp);
    }


    /**
     * 设置监听 CallbackDeviceConnectedListener , 收到 新设备连接 时进行回调
     * mConnectionManager.setCallbackDeviceConnectedListener(回调的Listener)
     */
//    public void setCallbackDeviceConnectedListener(View view) {
//        Log.i(TAG,"设置CallbackDeviceConnectedListener");
//        mConnectionManager.setCallbackDeviceConnectedListener(new ICallbackDeviceConnectedListener() {
//            @Override
//            public void onReceiveDeviceConnected(ClientDevice device) {
//                Log.i(TAG,"收到 新设备连接：" + device.toString());
//            }
//        });
//    }

    /**
     * 设置监听 设备断开连接 Listener ， 收到 设备断开连接 时进行回调
     * mConnectionManager.setCallbackDeviceDisconnectedListener(回调的Listener)
     */
//    public void setCallbackDeviceDisconnectedListener(View view) {
//        Log.i(TAG,"设置 CallbackDeviceDisconnectedListener");
//        mConnectionManager.setCallbackDeviceDisconnectedListener(new ICallbackDeviceDisconnectedListener() {
//            @Override
//            public void onReceiveDeviceDisConnected(String ip) {
//                Log.i(TAG,"设备断开连接 : " + ip);
//            }
//        });
//    }


    /**
     * 设置监听 ICallbackDeviceInfoUpdatedListener , 收到 设备信息更新 时进行回调
     * mConnectionManager.setCallbackDeviceInfoUpdatedListener(回调的Listener)
     */
//    public void setCallbackDeviceInfoUpdatedListener(View view) {
//        Log.i(TAG,"设置CallbackDeviceInfoUpdatedListener");
//        mConnectionManager.setCallbackDeviceInfoUpdatedListener(new ICallbackDeviceInfoUpdatedListener() {
//            @Override
//            public void onReceiveDeviceInfoUpdated(ClientDevice device) {
//                Log.i(TAG,"收到 设备信息更新：" + device.toString());
//            }
//        });
//    }
//
//    ArrayList<ClientDevice> mDeviceList = new ArrayList<>();
//    int resultCode;
//    String mRequestIp;

    /**
     * 设置监听 setCallbackRequestClientMirrorOnListener , 收到 设备请求上屏的信息 时进行回调
     * mConnectionManager.setCallbackRequestClientMirrorOnListener(回调的Listener)
     * <p>
     * 对上屏请求处理后，需要返回结果给服务端
     * 上屏结果类型：
     * 本次同意：ClientDevice.ACCEPT_ONCE ， 本次拒绝：ClientDevice.REFUSE_ONCE
     * 一直同意：ClientDevice.ALWAYS_ACCEPT ， 一直拒绝：ClientDevice.ALWAYS_REFUSE
     * mConnectionManager.fireRequestConnectResult(请求上屏的设备IP, 上屏结果类型);
     */
//    public void setCallbackRequestClientMirrorOnListener(View view) {
//        Log.i(TAG,"设置CallbackRequestClientMirrorOnListener");
//        mConnectionManager.setCallbackRequestClientMirrorOnListener(new ICallbackRequestClientMirrorOnListener() {
//            @Override
//            public void onReceiveDeviceRequestConnected(final String ip) {
//                Log.i(TAG,"收到 设备请求上屏的信息 ：" + ip);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRequestIp = ip;
//
//                        String requestConnectName = null;
//
//                        final GeneralDialog generalDialog = new GeneralDialog(WirelessActivity.this);
//
//                        for (ClientDevice device :
//                                mDeviceList) {
//                            if (device.mStrIp.equals(ip)) {
//                                requestConnectName = device.mName;
//                            }
//                        }
//
//                        generalDialog.setTitle(ip);
//                        generalDialog.setMsg(requestConnectName);
//                        generalDialog.setCancelable(false);
//
//
//                        generalDialog.setClickListener(new GeneralDialog.OnBtnClickListener() {
//                            @Override
//                            public void ok() {
//
//
//                                resultCode = ClientDevice.ACCEPT_ONCE;
//
//                                generalDialog.dismiss();
//                                mConnectionManager.fireRequestConnectResult(mRequestIp, resultCode);
//                            }
//
//                            @Override
//                            public void cancel() {
//
//                                resultCode = ClientDevice.REFUSE_ONCE;
//
//                                generalDialog.dismiss();
//                                mConnectionManager.fireRequestConnectResult(mRequestIp, resultCode);
//                            }
//                        });
//                        generalDialog.setRequestMsgVisible(View.VISIBLE);
//                        generalDialog.show();
//
//                    }
//                });
//            }
//        });
//    }

    /**
     * 设置监听 setCallbackAnDisplaySettingUpdateListener  , 收到 收到设置更新 时进行回调
     * mConnectionManager.setCallbackAnDisplaySettingUpdateListener(回调的Listener)
     */
//    public void setCallbackAnDisplaySettingUpdateListener(View view) {
//        Log.i(TAG,"设置 CallbackAnDisplaySettingUpdateListener");
//        mConnectionManager.setCallbackAnDisplaySettingUpdateListener(new ICallbackAnDisplaySettingUpdateListener() {
//            @Override
//            public void onReceiverSettingInfoUpdate(List<PreferenceItemInfo> preferenceItemInfoList) {
//                for (PreferenceItemInfo info :
//                        preferenceItemInfoList) {
//                    Log.i(TAG,"收到设置更新 ：" + info.toString());
//                }
//            }
//        });
//    }

    /**
     * 设置监听 IChannelInactiveListener  , 在 管理APP和服务端通信断开 时进行回调
     * mConnectionManager.setChannelInactiveListener(回调的Listener)
     */
//    public void setChannelInactiveListener(View view) {
//        Log.i(TAG,"设置 IChannelInactiveListener");
//        mConnectionManager.setChannelInactiveListener(new IChannelInactiveListener() {
//            @Override
//            public void onChannelInactive() {
//                Log.i(TAG,"通信断开");
//            }
//        });
//    }


    /**
     * 设置监听 ICallbackClientDeviceThumbnailUpdatedListener , 收到 客户端屏幕预览图数据 时进行回调
     */
//    public void setCallbackClientDeviceThumbnailUpdatedListener(View view) {
//        Log.i(TAG,"设置 ICallbackClientDeviceThumbnailUpdatedListener");
//        final ImageView thumbnailImage = findViewById(R.id.thumbnail);
//        mConnectionManager.setCallbackClientDeviceThumbnailUpdatedListener(new ICallbackClientDeviceThumbnailUpdatedListener() {
//
//            @Override
//            public void onReceiveClientDeviceThumbnailUpdated(String s, byte[] bytes) {
//                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        thumbnailImage.setImageBitmap(bitmap);
//                    }
//                });
//            }
//        });
//    }
    private void setAllListener() {

        mConnectionManager.setClassRoomRemoteCallback(new IClassRoomRemoteCallback() {
            @Override
            public void onClassroomAllClientsOnOffScreenResult() {
                Log.d(TAG, "onClassroomAllClientsOnOffScreenResult");

            }

            @Override
            public void onClassRoomAllClientsWorkOnOutResult() {

                Log.d(TAG, "onClassRoomAllClientsWorkOnOutResult");
            }

            @Override
            public void onClassRoomAllClientLockedResult() {

                Log.d(TAG, "onClassRoomAllClientLockedResult");
            }

            @Override
            public void onUpdateLockStatus(String s, byte b) {
                Log.d(TAG, "onUpdateLockStatus " + s);
            }

            @Override
            public void onUpdateWorkOutStatus() {

                Log.d(TAG, "onClassRoomAllClientLockedResult");
            }

            @Override
            public void onClassRoomKickAllClientsResult() {

                Log.d(TAG, "onClassRoomKickAllClientsResult");
            }

            @Override
            public void processClassRoomKickClientResult() {

                Log.d(TAG, "processClassRoomKickClientResult");
            }

            @Override
            public void onReceiveAnDisplayInfo(AnDisplayInfo anDisplayInfo) {

                Log.d(TAG, "onReceiveAnDisplayInfo");
            }

            @Override
            public void onReceiveDeviceConnected(ClientDevice clientDevice) {

                Log.d(TAG, "onReceiveDeviceConnected : " + clientDevice.mName + "  ip: " + clientDevice.mStrIp);
            }

            @Override
            public void onReceiveDeviceRequestConnected(String s) {

                Log.d(TAG, "onReceiveDeviceRequestConnected " + s);
            }

            @Override
            public void onReceiveDeviceDisConnected(String s) {

                Log.d(TAG, "onReceiveDeviceDisConnected " + s);
            }

            @Override
            public void onReceiveDeviceInfoUpdated(ClientDevice clientDevice) {

                Log.d(TAG, "onReceiveDeviceInfoUpdated : " + clientDevice.mName + "  ip: " + clientDevice.mStrIp);

            }

            @Override
            public void onReceiveDirectMirrorResponse() {

                Log.d(TAG, "onReceiveDirectMirrorResponse ");
            }

            @Override
            public void onUpdatePermitStatus() {

                Log.d(TAG, "onUpdatePermitStatus ");
            }

            @Override
            public void onReceiveClientDeviceThumbnailUpdated(String s, byte[] bytes) {

//                Log.d(TAG,"onReceiveClientDeviceThumbnailUpdated  "+s);
            }

            @Override
            public void onReceiveTeacherDeviceStateChange(ClientDevice clientDevice) {

                Log.d(TAG, "onReceiveTeacherDeviceStateChange : " + clientDevice.mName + "  ip: " + clientDevice.mStrIp);

            }

            @Override
            public void onReceiveSharingModeChange(boolean b) {

                Log.d(TAG, "onReceiveSharingModeChange  " + b);
            }

            @Override
            public void onReceiveDeviceVolume(int i) {

                Log.d(TAG, "onReceiveDeviceVolume  " + i);
            }

            @Override
            public void onReceiveRootDeviceLockStateChange(boolean b) {
                Log.d(TAG, "onReceiveRootDeviceLockStateChange  " + b);
            }
        });
        mConnectionManager.setRemoteCallback(new IRemoteCallback() {
            @Override
            public void onReceiveAnDisplayInfo(AnDisplayInfo anDisplayInfo) {
                List<ClientDevice> devList = anDisplayInfo.mDevices;
                StringBuilder sb = new StringBuilder("-- receive andisplay info size : " + devList.size() + "; ");
                boolean b = false;
                for (ClientDevice d : devList) {
                    sb.append(d.mStrIp + "  " + d.mName);
                    if (!b) {
                        mClientDevice = d;
                        b = true;
                    }
                }

                Log.d(TAG, "onReceiveAnDisplayInfo  " + sb.toString());
            }

            @Override
            public void onReceiveDeviceConnected(ClientDevice clientDevice) {
                Log.d(TAG, "onReceiveDeviceConnected : " + clientDevice.mName + "  ip: " + clientDevice.mStrIp);

            }

            @Override
            public void onReceiveDeviceRequestConnected(String s) {
                Log.d(TAG, "onReceiveDeviceRequestConnected : " + s);

            }

            @Override
            public void onReceiveDeviceDisConnected(String s) {
                Log.d(TAG, "onReceiveDeviceDisConnected : " + s);

            }

            @Override
            public void onReceiveDeviceInfoUpdated(ClientDevice clientDevice) {
                Log.d(TAG, "onReceiveDeviceInfoUpdated : " + clientDevice.mName + "  ip: " + clientDevice.mStrIp);

            }

            @Override
            public void onReceiveDirectMirrorResponse() {
                Log.d(TAG, "onReceiveDirectMirrorResponse ");
            }

            @Override
            public void onUpdatePermitStatus() {

                Log.d(TAG, "onUpdatePermitStatus ");
            }

            @Override
            public void onReceiveClientDeviceThumbnailUpdated(String s, byte[] bytes) {

//                Log.d(TAG,"onReceiveClientDeviceThumbnailUpdated "+s);
            }

            @Override
            public void onReceiveTeacherDeviceStateChange(ClientDevice clientDevice) {
                Log.d(TAG, "onReceiveTeacherDeviceStateChange : " + clientDevice.mName + "  ip: " + clientDevice.mStrIp);

            }

            @Override
            public void onReceiveSharingModeChange(boolean b) {

                Log.d(TAG, "onReceiveSharingModeChange " + b);
            }

            @Override
            public void onReceiveDeviceVolume(int i) {

                Log.d(TAG, "onReceiveDeviceVolume " + i);
            }

            @Override
            public void onReceiveRootDeviceLockStateChange(boolean b) {

                Log.d(TAG, "onReceiveRootDeviceLockStateChange " + b);
            }
        });
        mConnectionManager.setAuthorizeListener(new IAuthorizeListener() {
            @Override
            public void onAuthorizeSuccess(String s) {

                Log.d(TAG, "onAuthorizeSuccess " + s);
            }

            @Override
            public void onAuthorizeFailed(int i) {

                Log.d(TAG, "onAuthorizeFailed " + i);
            }
        });
        mConnectionManager.setReceiveSettingListener(new IReceiveSettingListener() {
            @Override
            public void onReceiverSettingInfo(List<PreferenceItemInfo> list) {
                StringBuilder sb = new StringBuilder("");
                for (PreferenceItemInfo i : list) {
                    sb.append(i.mKey + "  " + i.mValue);
                }

                Log.d(TAG, "onReceiverSettingInfo " + sb.toString());
            }

            @Override
            public void onReceiverSettingInfoChangeResult() {

                Log.d(TAG, "onReceiverSettingInfoChangeResult ");
            }

            @Override
            public void onReceiverSettingInfoUpdate(List<PreferenceItemInfo> list) {
                StringBuilder sb = new StringBuilder("");
                for (PreferenceItemInfo i : list) {
                    sb.append(i.mKey + "  " + i.mValue);
                }


                Log.d(TAG, "onReceiverSettingInfoUpdate " + sb.toString());
            }
        });
        mConnectionManager.setMainActivityEvenListener(new IMainActivityEvenListener() {
            @Override
            public void onChannelInactive(int i) {

                Log.d(TAG, "onChannelInactive " + i);
            }

            @Override
            public void refreshFinish() {

                Log.d(TAG, "refreshFinish");
            }
        });
        mConnectionManager.setReceiveSystemInfoListener(new IReceiveSystemInfoListener() {
            @Override
            public void onReceiverSystemInfo(SystemInfo systemInfo) {
                Log.d(TAG, "onReceiverSystemInfo: andisplay service" + systemInfo.mAnDisplayVersion + " andisplay config " + systemInfo.mConfigureVersion);
            }
        });

    }

    public void requestTeacherStop(View view) {
        mConnectionManager.requestClassroomUpdateTeacherDeviceStateData(ClientDevice.TEACHER_DEVICE_CONNECTED);
    }

    public void requestTeacherRecovery(View view) {
        mConnectionManager.requestClassroomUpdateTeacherDeviceStateData(ClientDevice.TEACHER_DEVICE_SHARING);
    }

    public void requestAppWriter(View view) {
        mConnectionManager.requestOpenToolAppData(ClientDevice.APP_WHITEBOARD);
    }

    public void requestAppMark(View view) {
        mConnectionManager.requestOpenToolAppData(ClientDevice.APP_MARK);
    }

    public void requestAppFiles(View view) {
        mConnectionManager.requestOpenToolAppData(ClientDevice.APP_FILE_FINDER);
    }

    public void requestWorkOnModeSingle(View view) {
        mConnectionManager.requestClassroomUpdateSharingModeData(true);
    }

    public void requestWorkOnModeMulti(View view) {
        mConnectionManager.requestClassroomUpdateSharingModeData(false);
    }

    public void requestUpdateDeviceVolume(View view) {
        int vol = (int) (Math.random() * 15 + 1);
        mConnectionManager.requestUpdateVolumeData(vol);
    }

    public void requestDeviceVolume(View view) {
        mConnectionManager.requestGetCurrentVolumeData();
    }

    public void requestUnlockRootDevice(View view) {
        mConnectionManager.requestLockRootDevice(false);
    }

    public void requestLockRootDevice(View view) {
        mConnectionManager.requestLockRootDevice(true);
    }
}
