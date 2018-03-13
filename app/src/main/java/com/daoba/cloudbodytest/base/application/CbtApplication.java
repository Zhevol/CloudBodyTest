package com.daoba.cloudbodytest.base.application;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Application类，可以在里面初始化相关的全局变量
 *
 * @author LongJian
 * @date 2018/3/13 0013
 */

public class CbtApplication extends Application {

    /**
     * Application 实例
     */
    private static CbtApplication mCbtApplication;

    /**
     * Activity的集合
     */
    private List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        mCbtApplication = this;
        activityList = new ArrayList<>();
    }

    /**
     * 获取本应用的 Application 的实例
     *
     * @return CbtApplication 实例
     */
    public static CbtApplication getInstance() {
        return mCbtApplication;
    }

    /**
     * 将 Activity 添加到 {@code activityList} 中.
     *
     * @param activity Activity 实例
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * app的退出管理，关闭所有的Activity并退出程序
     */
    public void exitApp() {
        try {
            for (Activity activity : activityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
            activityList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
