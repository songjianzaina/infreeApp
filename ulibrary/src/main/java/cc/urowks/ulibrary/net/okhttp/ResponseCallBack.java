package cc.urowks.ulibrary.net.okhttp;

import android.text.TextUtils;

import com.google.gson.Gson;

import cc.urowks.ulibrary.bean.ResponseBean;
import cc.urowks.ulibrary.util.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wanghongjia.
 */

public abstract class ResponseCallBack<T extends ResponseBean> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Gson gson = new Gson();
        LogUtils.i("ResponseCallBack", "网络请求响应数据:" + gson.toJson(response));
        String errorCode = "";
        String error = "";
        ResponseBean responseBean = null;
        if (response.isSuccessful()) {
            responseBean = response.body();
            if (responseBean != null) {
                errorCode = responseBean.getErrorCode();
                error = responseBean.getError();
                if (TextUtils.isEmpty(errorCode) && TextUtils.isEmpty(error)) {
                    responseBean = response.body();
                    onSuccess(responseBean);
                } else {
                    responseBean = new ResponseBean();
                    responseBean.setErrorCode(errorCode);
                    responseBean.setError(error);
                    onFailure(responseBean);
                }
            } else {
                onSuccess(responseBean);
            }

        } else {

            try {
                responseBean = gson.fromJson(response.errorBody().string(), ResponseBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (responseBean != null) {
                errorCode = responseBean.getErrorCode();
                error = responseBean.getError();
                if (TextUtils.isEmpty(errorCode)) {
                    responseBean = response.body();
                    onSuccess(responseBean);
                } else {
                    responseBean = new ResponseBean();
                    responseBean.setErrorCode(errorCode);
                    responseBean.setError(error);
                    onFailure(responseBean);
                }
            } else {
                responseBean = new ResponseBean();
                responseBean.setError(ResponseStatus.NET_ERROR.getMsg());
                onFailure(responseBean);
            }
        }

        onComplete();


    }


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setError(ResponseStatus.NET_ERROR.getMsg());
        onFailure(responseBean);
        onComplete();

    }

    /**
     * 请求成功时回调
     *
     * @param responseBean 响应数据
     */
    public abstract void onSuccess(ResponseBean responseBean);

    /**
     * 请求失败时回调
     *
     * @param responseBean 响应数据
     */
    public abstract void onFailure(ResponseBean responseBean);


    /**
     * 请求完成
     */
    public void onComplete() {
    }

    /**
     * 业务执行状态
     */
    public enum ResponseStatus {

        SYSTEM_NOT_LOGIN(4, "请登录"),
        NET_ERROR(-1, "连接失败"),;

        private int code;
        private String msg;

        ResponseStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
