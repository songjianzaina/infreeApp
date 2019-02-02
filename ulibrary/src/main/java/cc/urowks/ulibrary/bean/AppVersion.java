package cc.urowks.ulibrary.bean;

import cc.urowks.ulibrary.base.BaseBean;

/**
 * Created by songjian on 2016/8/26.
 */
public class AppVersion extends BaseBean{

    private String desc;//下载描述
    private String downloadUrl;//下载地址
    private int enforceFlag;//是否强制更新 1强制，0不强制
    private int versionCode;//版本号
    private String versionName;//版本名称

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getEnforceFlag() {
        return enforceFlag;
    }

    public void setEnforceFlag(int enforceFlag) {
        this.enforceFlag = enforceFlag;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
