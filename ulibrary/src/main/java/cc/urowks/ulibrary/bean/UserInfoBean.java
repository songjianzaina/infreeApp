package cc.urowks.ulibrary.bean;

import cc.urowks.ulibrary.base.BaseBean;

/**
 * 用户信息
 * <p/>
 * Created by jiangyujiang on 16/6/2.
 */
public class UserInfoBean extends BaseBean {
    private String id;

    private String mobile;//用户手机号码

    private String createTime;

    private String token;

    private String nickname;//用户昵称

    private String iconUrl;//用户头像

    private String smartBoxCount;//已绑定箱子的个数

    public String getSmartBoxCount() {
        return smartBoxCount;
    }

    public void setSmartBoxCount(String smartBoxCount) {
        this.smartBoxCount = smartBoxCount;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
