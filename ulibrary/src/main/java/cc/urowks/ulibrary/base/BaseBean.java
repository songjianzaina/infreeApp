package cc.urowks.ulibrary.base;


import java.io.Serializable;

import cc.urowks.ulibrary.util.JsonUtil;

/**
 * 数据实体基类
 * <p/>
 * Created by jiangyujiang on 16/5/31.
 */
public class BaseBean implements Serializable {

    /*将JvaBean转成json文本*/
    public String toJson() {
        return JsonUtil.toJSONString(this);

    }

    /**
     * 将Json文本转JavaBean
     *
     * @param json
     * @return
     */
    public Object fromJson(String json) {
        return JsonUtil.json2Bean(json, getClass());
    }
}