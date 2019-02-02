package cc.urowks.ulibrary.util;

import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * 应用Cookie管理工具
 * <p/>
 * Created by jiang on 2016/1/23.
 */
public class CookieUtil {
    /**
     * 清空应用Cookie缓存
     */
    public static void clear(Context context) {
        CookieStore cookieStore = new PersistentCookieStore(context);
        cookieStore.clear();
    }

    /**
     * 在本地Cookie中暂存数据，默认过期时间是15天，会覆盖已有的Cookie数据
     *
     * @param name  键
     * @param value 值
     */
    public static void set(String name, String value, Context context) {
        Date expireDate = new Date(Calendar.getInstance().getTimeInMillis()
                + 1000 * 60 * 60 * 24 * 15);
        set(name, value, expireDate, context);
    }

    /**
     * 在本地Cookie中暂存数据，会覆盖已有的Cookie数据
     *
     * @param name       键
     * @param value      值
     * @param expireDate 过期时间
     */
    public static void set(String name, String value, Date expireDate, Context context) {
        CookieStore cookieStore = new PersistentCookieStore(context);
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setExpiryDate(expireDate);
        cookieStore.addCookie(cookie);
    }

    /**
     * 从本地Cookie中获得暂存的数据.
     *
     * @param name 键
     * @return 如果没有对应的值将返回null
     */
    public static String get(String name, Context context) {
        CookieStore cookieStore = new PersistentCookieStore(context);
        for (Cookie cookie : cookieStore.getCookies()) {
            if (cookie.getName().equalsIgnoreCase(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 获取本地缓存的所有Cookie
     *
     * @param context 上下文
     * @return 返回所有的Cookie集合
     */
    public static List<Cookie> get(Context context) {
        CookieStore cookieStore = new PersistentCookieStore(context);
        return cookieStore.getCookies();
    }
}
