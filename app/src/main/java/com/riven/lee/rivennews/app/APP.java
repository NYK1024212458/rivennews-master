package com.riven.lee.rivennews.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.riven.lee.rivennews.R;

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

        initRegisterActivityLifecycleCallbacks();
    }

    private void initRegisterActivityLifecycleCallbacks() {
        /**
         * Activity生命周期管理回调方法
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(final Activity activity) {
                Log.d("onActivityStarted",activity.getTitle()+"");

                //这里全局给Activity设置toolbar和title
                if(activity.findViewById(R.id.ll_toolbar)!=null){
                    if(activity instanceof AppCompatActivity){
                        ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.ll_toolbar));
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.ll_toolbar));
                        activity.getActionBar().setDisplayShowTitleEnabled(false);
                    }
                }
                if (activity.findViewById(R.id.toolbar_title) != null) { //找到 Toolbar 的标题栏并设置标题名
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }
                if (activity.findViewById(R.id.toolbar_back) != null) { //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
                    activity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.onBackPressed();
                        }
                    });
                }
                /**
                 * 添加activity管理栈
                 */
                if (allActivities == null) {
                    allActivities = new HashSet<>();
                }
                allActivities.add(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d("onActivityDestroyed",activity.getTitle()+"");
                if (allActivities != null) {
                    allActivities.remove(activity);
                }
            }
        });
    }


    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
            allActivities = null;
        }
        /**
         * 绕过Activity 生命周期  强制关闭
         */
//        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
