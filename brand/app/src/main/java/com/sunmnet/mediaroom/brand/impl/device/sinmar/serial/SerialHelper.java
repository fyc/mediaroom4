package com.sunmnet.mediaroom.brand.impl.device.sinmar.serial;

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import android_serialport_api.SerialPort;

/**
 * @author benjaminwan
 * 串口辅助工具类
 */
public class SerialHelper {
    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private SendThread mSendThread;
    private String sPort = null;
    private int iBaudRate = 9600;
    private boolean _isOpen = false;
    private byte[] _bLoopData = new byte[]{0x30};
    private int iDelay = 500;

    private ComDataListener mComDataListener;

    //----------------------------------------------------
    public SerialHelper(String sPort, int iBaudRate) {
        this.sPort = sPort;
        this.iBaudRate = iBaudRate;
    }

    public SerialHelper() {
        this("/ttyS0", 9600);
    }

    public SerialHelper(String sPort) {
        this(sPort, 9600);
    }

    public SerialHelper(String sPort, String sBaudRate) {
        this(sPort, Integer.parseInt(sBaudRate));
    }

    //----------------------------------------------------
    public void open() throws Exception, Error {
        Log.i(TAG, "open: " + Build.VERSION.SDK_INT);
        mSerialPort = new SerialPort(new File(sPort), iBaudRate, 0);
        mOutputStream = mSerialPort.getOutputStream();
        mInputStream = mSerialPort.getInputStream();
        mReadThread = new ReadThread();
        mReadThread.start();
        mSendThread = new SendThread();
        mSendThread.setSuspendFlag();
        mSendThread.start();
        _isOpen = true;
    }

    //----------------------------------------------------
    public void close() {
        Log.i(TAG, "close: ");
        if (mReadThread != null) {
            mReadThread.interrupt();
            mReadThread = null;
        }
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
        _isOpen = false;
    }

    //----------------------------------------------------
    public void send(byte[] bOutArray) {
        try {
            Log.i(TAG, "write1" + Arrays.toString(bOutArray));
            mOutputStream.write(bOutArray);
            mOutputStream.flush();
            Log.i(TAG, "write2");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "e: " + e.toString());
        }
    }

    private static final String TAG = "SerialHelper";

    //----------------------------------------------------
    public void sendHex(String sHex) {
        Log.i(TAG, "sendHex: " + sHex);
        byte[] bOutArray = MyFunc.HexToByteArr(sHex);
        send(bOutArray);
    }

    //----------------------------------------------------
    public void sendTxt(String sTxt) {
        Log.i(TAG, "sendTxt: " + sTxt);
        byte[] bOutArray = sTxt.getBytes();
        send(bOutArray);
    }

    //----------------------------------------------------
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    Log.i(TAG, "ReadThread: ");
                    if (mInputStream == null) {
                        Log.i(TAG, "mInputStream == null");
                        return;
                    }
                    byte[] buffer = new byte[512];
                    int size = mInputStream.read(buffer);
                    Log.i(TAG, "ReadThread: " + size);
                    if (size > 0) {
                        ComBean ComRecData = new ComBean(sPort, buffer, size);
                        onDataReceived(ComRecData);
                    }
                    SystemClock.sleep(50);
                } catch (Exception e) {
                    Log.e(TAG, "e: " + e.toString());
                    e.printStackTrace();
                    return;
                } catch (Error error) {
                    Log.e(TAG, "e: " + error.toString());
                    error.printStackTrace();
                    return;
                }
            }
        }
    }

    //----------------------------------------------------
    private class SendThread extends Thread {
        public boolean suspendFlag = false;// 控制线程的执行

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                synchronized (this) {
                    Log.i(TAG, "suspendFlag: " + suspendFlag);
                    while (suspendFlag) {
                        SystemClock.sleep(50);
                    }
                }
                send(getbLoopData());
                try {
                    Thread.sleep(iDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //线程暂停
        public void setSuspendFlag() {
            this.suspendFlag = true;
        }

        //唤醒线程
        public synchronized void setResume() {
            this.suspendFlag = false;
            notify();
        }
    }

    //----------------------------------------------------
    public int getBaudRate() {
        return iBaudRate;
    }

    public boolean setBaudRate(int iBaud) {
        if (_isOpen) {
            return false;
        } else {
            iBaudRate = iBaud;
            return true;
        }
    }

    public boolean setBaudRate(String sBaud) {
        int iBaud = Integer.parseInt(sBaud);
        return setBaudRate(iBaud);
    }

    //----------------------------------------------------
    public String getPort() {
        return sPort;
    }

    public boolean setPort(String sPort) {
        if (_isOpen) {
            return false;
        } else {
            this.sPort = sPort;
            return true;
        }
    }

    //----------------------------------------------------
    public boolean isOpen() {
        return _isOpen;
    }

    //----------------------------------------------------
    public byte[] getbLoopData() {
        return _bLoopData;
    }

    //----------------------------------------------------
    public void setbLoopData(byte[] bLoopData) {
        this._bLoopData = bLoopData;
    }

    //----------------------------------------------------
    public void setTxtLoopData(String sTxt) {
        this._bLoopData = sTxt.getBytes();
    }

    //----------------------------------------------------
    public void setHexLoopData(String sHex) {
        this._bLoopData = MyFunc.HexToByteArr(sHex);
    }

    //----------------------------------------------------
    public int getiDelay() {
        return iDelay;
    }

    //----------------------------------------------------
    public void setiDelay(int iDelay) {
        this.iDelay = iDelay;
    }

    //----------------------------------------------------
    public void startSend() {
        if (mSendThread != null) {
            mSendThread.setResume();
        }
    }

    //----------------------------------------------------
    public void stopSend() {
        if (mSendThread != null) {
            mSendThread.setSuspendFlag();
        }
    }

    public void initOutputStream() {
        mOutputStream = mSerialPort.getOutputStream();
    }

    public OutputStream getmOutputStream() {
        return mOutputStream;
    }

    //----------------------------------------------------
    public void onDataReceived(ComBean ComRecData) {
        RunningLog.run("串口接收数据：" + Arrays.toString(ComRecData.bRec));
        if (register.get() && mComDataListener != null) {
            mComDataListener.onDataReceived(ComRecData);
        }
    }

    private AtomicBoolean register = new AtomicBoolean(false);

    public void register(ComDataListener listener) {
        register.compareAndSet(false, true);
        mComDataListener = listener;
    }

    public void unregister() {
        register.compareAndSet(true, false);
        mComDataListener = null;
    }
}