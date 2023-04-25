package com.sunmnet.mediaroom.tabsp.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PackageTool {
    private static final String TAG = PackageTool.class.getSimpleName();
    public static final String LAUNCHER_PACKAGE_NAME = "com.android.launcher3";
    private static PackageTool sHomeTool;
    private Context mContext;
    private PackageManager mPm;

    public static PackageTool getInstance(Context context) {
        PackageTool packageTool;
        synchronized (PackageTool.class) {
            if (sHomeTool == null) {
                sHomeTool = new PackageTool(context);
            }
            sHomeTool.mContext = context;
            packageTool = sHomeTool;
        }
        return packageTool;
    }

    private PackageTool(Context context) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
    }

    public void startExistsApp(String pkName) {
        try {
            List<ResolveInfo> homeActivities = new ArrayList<>();
            Method[] getHomeActivities = PackageManager.class.getDeclaredMethods();
            for (Method method : getHomeActivities) {
                if (method.getName().equals("getHomeActivities")) {
                    method.setAccessible(true);
                    method.invoke(this.mPm, homeActivities);
                    for (int i = 0; i < homeActivities.size(); i++) {
                        if (pkName.equals(homeActivities.get(i).activityInfo.packageName)) {
                            ActivityInfo appInfo = homeActivities.get(i).activityInfo;
                            ComponentName componentName = new ComponentName(appInfo.packageName, appInfo.name);
                            Intent intent = new Intent();
                            intent.setComponent(componentName);
                            this.mContext.startActivity(intent);
                            return;
                        }
                    }
                    return;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
