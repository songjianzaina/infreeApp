package cc.urowks.ulibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import cc.urowks.ulibrary.bean.UserInfoBean;
import cc.urowks.ulibrary.util.ActivityManager;

/**
 * 用户管理
 */
public class UserManager {
    private static final String CACHE_KEY = "info";
    private static final String SHARE_CACHE_KEY = "shareUseInfo";
    private static final String GUIDE_PAGE = "guide_page";
    private static final String COPYRIGHT = "copyright";

    private static UserManager instance;
    private UserInfoBean userInfoBean;

    private UserManager() {

    }

    public synchronized static UserManager getInstance() {
        if (null == instance) {
            instance = new UserManager();
        }
        return instance;
    }

    public void updateUserInfo(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        try {
            userInfoBean = JSON.parseObject(json, UserInfoBean.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return;
        }
        getPreferences().edit().putString(CACHE_KEY, json).apply();
    }

    /**
     * 写入共享授权用户信息
     *
     * @param json
     */
    public void updateShareUserInfo(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        getPreferences().edit().putString(SHARE_CACHE_KEY, json).apply();
    }


    public void updateUserInfo(UserInfoBean userInfo) {
        if (null == userInfo) {
            return;
        }
        userInfoBean = userInfo;
        String json = JSON.toJSONString(userInfo);
        getPreferences().edit().putString(CACHE_KEY, json).apply();
    }

    public void updateGuideLog(boolean isOver) {
        getGuidePreferences().edit().putBoolean(GUIDE_PAGE, isOver).apply();
    }

    @Nullable
    public UserInfoBean readUserInfo() {
        if (null == userInfoBean) {
            String json = getPreferences().getString(CACHE_KEY, null);
            try {
                userInfoBean = JSON.parseObject(json, UserInfoBean.class);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return userInfoBean;
    }

    @Nullable
    public String getToken() {
        if (null == userInfoBean) {
            readUserInfo();
        }
        return userInfoBean == null ? null : userInfoBean.getToken();
    }

    public void clearUserInfo() {
        userInfoBean = null;
        getPreferences().edit().clear().apply();
    }

    public boolean hasUserInfo() {
        return getPreferences().getString(CACHE_KEY, null) != null;
    }

    public boolean isOverGuide() {
        return getGuidePreferences().getBoolean(GUIDE_PAGE, false) != false;
    }

    public void logout(Class toActivity) {
        Activity activity = ActivityManager.getInstance().getCurrentActivity();
        if (null == activity) return; // 为null说明没有界面打开
        clearUserInfo();
        ActivityManager.getInstance().finishAllActivity();
        Intent intent = new Intent(activity, toActivity);
        activity.startActivity(intent);
    }


    private SharedPreferences getPreferences() {
        return InsworksApp.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
    }
    private SharedPreferences getGuidePreferences() {
        return InsworksApp.getInstance().getSharedPreferences("guide", Context.MODE_PRIVATE);
    }

    //更新软件服务协议
    public void updateCopyrightUrl(String url) {
        getPreferences().edit().putString(COPYRIGHT, url).apply();
    }

    //获取软件服务协议
    public String readCopyrightUrl() {
        return getPreferences().getString(COPYRIGHT, null);
    }
}
