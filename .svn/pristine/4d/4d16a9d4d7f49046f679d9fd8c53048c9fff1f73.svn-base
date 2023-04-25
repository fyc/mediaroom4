package com.sunmnet.mediaroom.common.cmd;

import android.os.SystemClock;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SetTime {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("缺少时间戳参数");
            return;
        }
        String arg0 = args[0];
        long timeMillis = TypeUtil.objToLongDef(arg0, -1);
        if (timeMillis < 0) {
            System.out.println("无效的时间戳参数： " + arg0);
            return;
        }
        SimpleDateFormat dateFormat = DateUtil.getDateFormat(DateUtil.DEFAULT_FORMAT);
        System.out.println("修改前时间： " + dateFormat.format(new Date()));
        System.out.println("修改时间戳数据： " + timeMillis);
        boolean success = SystemClock.setCurrentTimeMillis(timeMillis);
        if (success) {
            System.out.println("时间修改成功，修改后时间： " + dateFormat.format(new Date()));
            System.exit(0);
        } else {
            System.out.println("时间修改失败");
            System.exit(1);
        }
    }
}
