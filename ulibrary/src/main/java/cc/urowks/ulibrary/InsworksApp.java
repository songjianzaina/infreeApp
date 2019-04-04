package cc.urowks.ulibrary;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

import cc.urowks.ulibrary.util.ActivityManager;
import cc.urowks.ulibrary.util.CrashHandler;
import cc.urowks.ulibrary.util.LogUtils;


/**
 * Created by songjian on 216/10/19.
 */
public class InsworksApp extends android.support.multidex.MultiDexApplication {

    private static InsworksApp instance;
    private String runningEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }


    private void init() {
        CrashHandler.init(this, null);
        readEnvironmentConfig();
        setIsWriteLog();
        registerActivityLifecycleCallbacks(new OverallActivityLifecycleCallbacks());
        initRouter(this);
    }
    public static void initRouter(Application application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);
    }

    public static InsworksApp getInstance() {
        return instance;
    }


    /**
     * 获取应用运行环境，如果应用没有启动，获取该值没有意义，所有和运行环境相关的配置必须依赖于该值
     *
     * @return {@link Constant.EnvironmentVariables}
     */
    public String getRunningEnvironment() {
        return runningEnvironment == null ? Constant.EnvironmentVariables.ONLINE : runningEnvironment;
    }

    // 读取AndroidManifest里面配置的运行环境
    private void readEnvironmentConfig() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            runningEnvironment = appInfo.metaData.getString("environment", Constant.EnvironmentVariables.ONLINE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //设置是否打印log
    private void setIsWriteLog() {
        if (runningEnvironment.equals(Constant.EnvironmentVariables.ONLINE)) {
            LogUtils.setPrintLog(false);
        } else {
            LogUtils.setPrintLog(true);
        }
    }

    /**
     * 应用内所有Activity生命周期回调，可以在里面添加统一的界面生命周期统计
     */
    private class OverallActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

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
            ActivityManager.getInstance().removeActivity(activity);
        }

    }
}

