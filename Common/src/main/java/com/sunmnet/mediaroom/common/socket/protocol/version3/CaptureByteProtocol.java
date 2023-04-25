package com.sunmnet.mediaroom.common.socket.protocol.version3;

import android.util.Base64;

import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.config.DeviceConfig;
import com.sunmnet.mediaroom.common.impl.AbstractCallback;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.util.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class CaptureByteProtocol implements ProtocolHandler<String, String> {


    @Override
    public SocketResult<String> handle(IoSession session, String message) {
        DeviceInfo device = DeviceConfig.getInstance().getDeviceInfo();
        if (device != null) {
            AppUtil.capture(device, new CaptureCallback(session));
        }
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.DEBUG_SNAPSHOT_IMEDIALY2;
    }

    static class CaptureCallback extends AbstractCallback<String> {

        IoSession ioSession;

        CaptureCallback(IoSession ioSession) {
            this.ioSession = ioSession;
        }

        @Override
        protected void onCallbackSuccess(String s) {
            File file = new File(s);
            if (file.exists()) {
                try {
                    FileInputStream is = new FileInputStream(file);
                    String fileName = file.getName();
                    byte[] data = null;
                    int length = -1;
                    byte[] loader = new byte[10240];
                    while ((length = is.read(loader)) != -1) {
                        if (data == null) {
                            data = new byte[length];
                            System.arraycopy(loader, 0, data, 0, length);
                        } else {
                            byte[] temp = new byte[data.length];
                            System.arraycopy(data, 0, temp, 0, data.length);
                            data = new byte[data.length + length];
                            System.arraycopy(temp, 0, data, 0, temp.length);
                            System.arraycopy(loader, 0, data, temp.length, length);
                        }
                    }
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    JSONObject object = new JSONObject();
                    int fileSize = data.length;
                    object.put("fileName", fileName);
                    object.put("fileSize", fileSize);
                    object.put("stream", base64);
                    ioSession.write(object.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void onFail(String callbackMsg) {
            RunningLog.warn("截图异常....");
        }
    }
}
