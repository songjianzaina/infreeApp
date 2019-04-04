package com.insworks.infreeapp.net;

import com.insworks.infreeapp.bean.TimeEntity;
import com.insworks.infreeapp.bean.WeatherEntity;

import cc.urowks.ulibrary.bean.BaseHttpResponse;
import io.reactivex.Observable;

/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2018/11/26 11:08 AM
 * desc   : model-负责数据
 */
public class MainModel implements MainContract.Model {
    @Override
    public Observable<BaseHttpResponse<WeatherEntity>> getWeather(String city) {
        return RequestService.getInstance().getRequestService().getWeather(city);


    }

    @Override
    public Observable<BaseHttpResponse<TimeEntity>> getTime(int time) {
        return RequestService.getInstance().getRequestService().getTime(time);
    }
}
