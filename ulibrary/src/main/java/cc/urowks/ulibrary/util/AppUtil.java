package cc.urowks.ulibrary.util;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

import static cc.urowks.ulibrary.util.ActivityUtil.mContext;


/**
 * 应用相关工具
 */
public class AppUtil {

    public static int getVersionCode() {
        PackageInfo packageInfo = null;
        try {
            PackageManager manager = mContext.getPackageManager();
            String packageName =mContext.getPackageName();
            packageInfo = manager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return 0;
        }
        return packageInfo.versionCode;
    }

    public static String getVersionName() {
        PackageInfo packageInfo = null;
        try {
            PackageManager manager = mContext.getPackageManager();
            String packageName = mContext.getPackageName();
            packageInfo = manager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return null;
        }
        return packageInfo.versionName;
    }
    /**
     * 安装apk 隐士调用系统的安装界面
     *
     * @param file 要安装的apk文件
     */
    public static  void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
