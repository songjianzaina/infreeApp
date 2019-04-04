package com.insworks.infreeapp;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cc.urowks.ulibrary.InsworksApp;


/**
 * 主页框架
 * Created by songjian on 2016/10/19.
 */
public class InfreeApp extends InsworksApp {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouter(this);
    }

    public static void initRouter(Application application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);
    }
}

