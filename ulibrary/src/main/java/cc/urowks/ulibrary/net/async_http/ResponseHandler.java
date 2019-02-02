package cc.urowks.ulibrary.net.async_http;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import cc.urowks.ulibrary.bean.ResponseBean;
import cc.urowks.ulibrary.util.LogUtils;
import cz.msebera.android.httpclient.Header;

/**
 * 请求响应处理
 */
public abstract class ResponseHandler extends BaseJsonHttpResponseHandler {
    private static final String TAG = "ResponseHandler";

    public abstract void onSuccess(Object response);

    public abstract void onFailure(String message);

    public abstract Object parseSuccessResponse(String rawJsonData) throws Throwable;

    @Override
    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
        LogUtils.i(TAG, "请求成功 Url: " + getRequestURI());
        LogUtils.i(TAG, "请求成功 StatusCode: " + statusCode);
        LogUtils.i(TAG, "请求成功 Response: " + rawJsonResponse);
        ResponseBean responseBean = (ResponseBean) new ResponseBean().fromJson(rawJsonResponse);
       if (responseBean.getHttpStatus()!=200) {
           //验证码错误
            onFailure(responseBean.getException());
        }
        onSuccess(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
        LogUtils.i(TAG, "请求失败 Url: " + getRequestURI());
        LogUtils.i(TAG, "请求失败 StatusCode: " + statusCode);
        LogUtils.i(TAG, "请求失败 Response: " + rawJsonData);
        if (null == errorResponse) {
            onFailure("网络请求失败，请稍后重试");
        } else if (errorResponse instanceof ResponseBean) { // 在此处处理各种业务状态码
            ResponseBean response = (ResponseBean) errorResponse;
            if (ResponseBean.LOGOUT_CODE == response.getCode()) {
                onFailure("用户信息过期，请重新登录");
//                UserManager.getInstance().logout();// TODO: 2018/12/29 这里需要和其他moudle通信
            } else {
                onFailure(response.getException());
            }
        } else {
            onFailure(errorResponse.toString());
        }
    }

    @Override//该方法在子线程
    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
        LogUtils.i(TAG,"解析Response数据:"+rawJsonData);
        ResponseBean responseBean = JSON.parseObject(rawJsonData, ResponseBean.class);
        if (null == responseBean) return null;
        if (isFailure) {
            return responseBean;
        } else {
            return parseSuccessResponse(responseBean.getData());
        }
    }
}
