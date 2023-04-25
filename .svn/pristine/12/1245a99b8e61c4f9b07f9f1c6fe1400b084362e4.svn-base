package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;
import android.util.Base64;

import com.sunmnet.mediaroom.brand.utils.FaceHelper;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FaceAuthProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.run("人脸识别模块授权: " + message);
        if (message == null || TextUtils.isEmpty(message.getMsg())) {
            return SocketResult.fail();
        }
        byte[] bytes = Base64.decode(message.getMsg(), Base64.DEFAULT);
        File file = new File(FaceHelper.getModelPath(), FaceHelper.hostKeyName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FaceHelper.getInstance().init();
        RunningLog.run("face init result: " + FaceHelper.getInstance().isInited());
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_AUTH_FACE;
    }

}
