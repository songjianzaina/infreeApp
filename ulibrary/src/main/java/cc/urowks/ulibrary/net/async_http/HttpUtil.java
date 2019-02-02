package cc.urowks.ulibrary.net.async_http;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cc.urowks.ulibrary.InsworksApp;
import cc.urowks.ulibrary.UserManager;
import cc.urowks.ulibrary.util.LogUtils;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * http网络请求工具
 * <p/>
 * Created by jiangyujiang on 16/6/2.
 */
public class HttpUtil {
    private static final String TAG = "HttpUtil";
    public static final String TOKEN_NAME = "user-token";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private HttpUtil() {

    }

    public static RequestHandle get(String url, AsyncHttpResponseHandler responseHandler) {
        return get(url, null, responseHandler);
    }

    public static RequestHandle get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        LogUtils.i(TAG, "Get请求 Url: " + url);
        String token = UserManager.getInstance().getToken();
        LogUtils.i(TAG, "Get请求 Token: " + token);
        LogUtils.i(TAG, "Get请求 Params: " + (params == null ? null : params.toString()));
        if (!TextUtils.isEmpty(token)) {
            client.addHeader(TOKEN_NAME, token);
            LogUtils.i(TAG,"用户 token="+token);
        }
        
        return client.get(url, params, responseHandler);
    }

    public static RequestHandle post(String url, AsyncHttpResponseHandler responseHandler) {
        return post(url, null, responseHandler);
    }

    public static RequestHandle post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        LogUtils.i(TAG, "Post请求 Url: " + url);
        String token = UserManager.getInstance().getToken();
        LogUtils.i(TAG, "Post请求 Token: " + token);
        LogUtils.i(TAG, "Post请求 Params: " + (params == null ? null : params.toString()));
        if (!TextUtils.isEmpty(token)) {
            client.addHeader(TOKEN_NAME, token);
        }
        if (null != params) {
            params.setUseJsonStreamer(true);
        }
        return client.post(url, params, responseHandler);
    }

    public static RequestHandle post(String url, Object params, AsyncHttpResponseHandler responseHandler) {
        LogUtils.i(TAG, "Post请求 Url: " + url);
        String token = UserManager.getInstance().getToken();
        LogUtils.i(TAG, "Post请求 Token: " + token);
        LogUtils.i(TAG, "Post请求 Params: " + params.toString());
        if (!TextUtils.isEmpty(token)) {
            client.addHeader(TOKEN_NAME, token);
        }
        HttpEntity entity = new StringEntity(params.toString(), "UTF-8");
        return client.post(InsworksApp.getInstance(), url, entity, RequestParams.APPLICATION_JSON, responseHandler);
    }

    public static RequestHandle fileUpload(String url, String key, File file, AsyncHttpResponseHandler responseHandler) {
        LogUtils.i(TAG, "FileUpload Url: " + url);
        String token = UserManager.getInstance().getToken();
        LogUtils.i(TAG, "FileUpload Token: " + token);
        LogUtils.i(TAG, "FileUpload Path: " + file.getPath());
        if (!TextUtils.isEmpty(token)) {
            client.addHeader(TOKEN_NAME, token);
        }
        RequestParams params = new RequestParams();
        try {
            params.put(key, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return client.post(url, params, responseHandler);
    }
    //文件下载
    public static RequestHandle downloadFile(String url,FileAsyncHttpResponseHandler responseHandler) {
        LogUtils.i(TAG, "downloadFile Url: " + url);
        String token = UserManager.getInstance().getToken();
        LogUtils.i(TAG, "downloadFile Token: " + token);
        if (!TextUtils.isEmpty(token)) {
            client.addHeader(TOKEN_NAME, token);
        }
        return client.get(url,  responseHandler);
    }
}