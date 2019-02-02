package cc.urowks.ulibrary.util;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * 手机屏幕工具类
 * <p/>
 * Created by jiang on 2015/10/19.
 */
public class ScreenUtils {

    /**
     * 获取手机屏幕高度
     *
     * @param context 上下文
     * @return 屏幕的高度
     */
    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取手机屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度，如果界面没有呈现将返回0
     */
    public static int getStatusBarHeight(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 把dp单位的值转换为px单位的值
     */
    public static int dp2px(int dp) {
        float density = ActivityUtil.mContext.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);
        return px;
    }
}
