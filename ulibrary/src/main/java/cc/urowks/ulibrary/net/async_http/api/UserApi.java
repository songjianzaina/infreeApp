package cc.urowks.ulibrary.net.async_http.api;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import cc.urowks.ulibrary.net.async_http.HttpUtil;
import cc.urowks.ulibrary.net.async_http.ResponseHandler;


/**
 * 用户相关数据接口
 * <p/>
 * Created by jiangyujiang on 16/6/1.
 */
public class UserApi extends Api {
    //获取短信验证码
    public static RequestHandle sendSmSVerifyCode(String telephone, ResponseHandler responseHandler) {
        String url = getAbsoluteUrl("/verificationCode/send");
        RequestParams params = new RequestParams();
        params.put("mobile", telephone);
        params.put("type", "sms");
        return HttpUtil.post(url, params, responseHandler);
    }

    //获取html页面
    public static RequestHandle getHtml(ResponseHandler responseHandler) {
        String url = getAbsoluteUrl("/semanticAnalysis/htmlUrl");
        return HttpUtil.get(url, responseHandler);
    }

    //app更新
    public static RequestHandle updateApp(ResponseHandler responseHandler) {
        String url = getAbsoluteUrl("/version/newVersion");
        RequestParams params = new RequestParams();
        params.put("terminal", "android");
        return HttpUtil.post(url, params, responseHandler);
    }

}
