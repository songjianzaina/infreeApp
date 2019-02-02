package com.insworks.infreeapp.net;

import com.insworks.infreeapp.bean.TimeEntity;
import com.insworks.infreeapp.bean.WeatherEntity;

import cc.urowks.ulibrary.base.BaseBean;
import cc.urowks.ulibrary.bean.BaseHttpResponse;
import cc.urowks.ulibrary.net.retrofit2.BaseModel;
import cc.urowks.ulibrary.net.retrofit2.BasePresenter;
import cc.urowks.ulibrary.net.retrofit2.IBaseView;
import cc.urowks.ulibrary.widget.MultipleStatusView;
import io.reactivex.Observable;

/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2018/11/24 1:08 PM
 * desc   :
 */
public interface MainContract   {

    interface View extends IBaseView {
        void getNetData(BaseBean bean);
    }

    interface Model extends BaseModel {
        Observable<BaseHttpResponse<WeatherEntity>> getWeather(String city_code);
        Observable<BaseHttpResponse<TimeEntity>> getTime(int time);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getWeather(String city_code, MultipleStatusView multipleStatusView);
        public abstract void getTime(int time, MultipleStatusView multipleStatusView);
    }
}
