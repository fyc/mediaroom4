package com.sunmnet.mediaroom.tabsp.record.impl.hik;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.HCNetSDKByJNA;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.impl.HikSDK;


/**
 * Created by dengzl on 2019/4/25.
 */

public class HikPlayView extends SurfaceView implements SurfaceHolder.Callback {
    private int m_iHeight = 0, m_iWidth = 0, m_iChan, m_lUserID;
    public int m_iPreviewHandle = -1;
    private SurfaceHolder m_hHolder;
    HikSDK sdk;

    public interface PlayListener {
        public void onPlaying();

        public void onReady();

        public void onPlayError();

        public void onPlayStop();
    }

    PlayListener listener;

    public void setPlayListener(PlayListener listener) {
        this.listener = listener;
    }

    public boolean bCreate = false;

    public HikPlayView(Context context) {
        super(context);
        init();
    }

    public HikPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HikPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        m_hHolder = this.getHolder();
        getHolder().addCallback(this);
    }

    public void setSdk(HikSDK sdk) {
        this.sdk = sdk;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        bCreate = true;
    }

    public boolean isbCreate() {
        return bCreate;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        RunningLog.run("surfaceChanged:" + arg1 + "," + arg2 + "," + arg3);
        if (arg1 > 0 && this.listener != null) {
            this.listener.onReady();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(m_iWidth - 1, m_iHeight - 1);
    }

    public void setParam(int nScreenSize) {
        m_iWidth = nScreenSize;
        m_iHeight = (m_iWidth * 3) / 4;
    }

    public void stop() {
        boolean isStop = this.sdk.NET_DVR_StopRealPlay(this.m_iPreviewHandle);
        if (isStop) {
            this.listener.onPlayStop();
        }
    }

    public void start(int m_lUserID, int iChan) {
        if (iChan < 0)
            return;
        while (!bCreate) {
            try {
                Thread.sleep(100);
                System.out.println("wait form HikPlayViewCreate");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (this.m_iPreviewHandle >= 0) this.stop();

        /*HCNetSDKByJNA.NET_DVR_PREVIEWINFO previewInfo = new HCNetSDKByJNA.NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = iChan;
        previewInfo.dwStreamType = 1; // substream
        previewInfo.bBlocked = 1;
        this.m_iChan = iChan;
        this.m_lUserID = m_lUserID;
        m_iPreviewHandle = this.sdk.NET_DVR_RealPlay_V40(m_lUserID, previewInfo, m_hHolder.getSurface(), null);*/
        NET_DVR_PREVIEWINFO previewInfo=new NET_DVR_PREVIEWINFO();
        /*NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();*/
        previewInfo.lChannel =iChan;
        previewInfo.dwStreamType = 1; // substream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd=this.m_hHolder;
        this.m_iChan=iChan;
        this.m_lUserID=m_lUserID;
        m_iPreviewHandle = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_lUserID,
                previewInfo, null);
       // HCNetSDK.getInstance().NET_DVR_RealPlay_V40()
        if (m_iPreviewHandle < 0) {
            m_iPreviewHandle = -1;
            RunningLog.error(sdk.getLastError());
            this.listener.onPlayError();
        } else {
            this.listener.onPlaying();
        }
    }
/*
    public void start(int iUserID, int iChan) {
        if (iChan < 0)
            return;
        while (!bCreate) {
            try {
                Thread.sleep(100);
                System.out.println("wait form HikPlayViewCreate");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (this.m_iPreviewHandle >= 0) this.stop();
        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = iChan;
        previewInfo.dwStreamType = 1; // substream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd = this.m_hHolder;
        this.m_iChan = iChan;
        this.m_lUserID = iUserID;
        m_iPreviewHandle = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(iUserID,
                previewInfo, null);
        if (m_iPreviewHandle < 0) {
            m_iPreviewHandle = -1;
            RunningLog.error("播放失败,错误码：" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            this.listener.onPlayError();
        } else {
            this.listener.onPlaying();
        }
    }*/

    public void setM_iWidth(int m_iWidth) {
        this.m_iWidth = m_iWidth;
    }

    public void setM_iHeight(int m_iHeight) {
        this.m_iHeight = m_iHeight;
    }

}
