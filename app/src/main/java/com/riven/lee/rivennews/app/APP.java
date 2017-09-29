package com.riven.lee.rivennews.app;

import android.app.Activity;
import android.app.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rivenLee
 * 2017/9/27.
 * O -))))>
 */

public class APP extends Application{

    private static APP instance;
    private Set<Activity> allActivities;

    public static synchronized APP getInstance(){

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        /**
         * 绕过Activity 生命周期  强制关闭
         */
//        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
