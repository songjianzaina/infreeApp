package cc.urowks.ulibrary.net.async_http.api;

import cc.urowks.ulibrary.Constant;
import cc.urowks.ulibrary.InsworksApp;

/**
 * 数据接口基类
 */
public abstract class Api {

    private static final String HOST_LOCAL = "";
    private static final String HOST_DEV = "http://t.weather.sojson.com/api/";
    private static final String HOST_TEST = "http://onlybox-app-backend-dev.obaymax.com";
    private static final String HOST_STAGING = "";
    private static final String HOST_ONLINE = "http://139.129.106.167";

    /**
     * 获取服务器主机地址
     *
     * @return 服务器地址
     */
    public static String getServerHost() {
        switch (InsworksApp.getInstance().getRunningEnvironment()) {
            case Constant.EnvironmentVariables.LOCAL:
                return HOST_LOCAL;

            case Constant.EnvironmentVariables.DEV:
                return HOST_DEV;

            case Constant.EnvironmentVariables.TEST:
                return HOST_TEST;

            case Constant.EnvironmentVariables.STAGING:
                return HOST_STAGING;

            case Constant.EnvironmentVariables.ONLINE:
            default:
                return HOST_ONLINE;
        }
    }

    public static String getAbsoluteUrl(String relativeUrl) {
        return getServerHost() + relativeUrl;
    }
}
