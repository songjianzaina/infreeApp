package cc.urowks.ulibrary.bean;

import cc.urowks.ulibrary.base.BaseBean;

/**
 * 网络请求，服务端返回的错误实体类
 */
public class ResponseBean extends BaseBean {

    /**
     * 业务成功
     */
    public static final int OK_CODE = 0;
    /**
     * 用户信息过期，需要重新登录
     */
    public static final int LOGOUT_CODE = 99;
    private String data; // 数据字段
    private int code; // 业务状态码
    private int httpStatus; // http请求状态码
    private String exception; // 错误描述
    private String path; // 路径
    private String error;
    private String errorCode;
    private int returnCode;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp; // 时间戳

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
