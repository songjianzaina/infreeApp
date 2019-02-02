package com.insworks.infreeapp.net;

import com.insworks.infreeapp.bean.WeatherEntity;

import cc.urowks.ulibrary.net.retrofit2.RxObserverListener;
import cc.urowks.ulibrary.widget.MultipleStatusView;

/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2018/11/26 11:08 AM
 * desc   : presenter-负责业务逻辑处理
 */
public class MainPresenter extends MainContract.Presenter {

    @Override
    public void getWeather(String city_code, final MultipleStatusView multipleStatusView) {
        if (multipleStatusView != null) {
            multipleStatusView.showLoading();
        }
        rxManager.addObserver(RetrofitManager.getInstance().doRequest(mModel.getWeather(city_code), new RxObserverListener<WeatherEntity>(mView) {
            @Override
            public void onSuccess(WeatherEntity result) {
                if (multipleStatusView != null) {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            multipleStatusView.showContent();
                        }
                    }, 2000);
                }
                mView.getNetData(result);
            }
        }));
    }

    @Override
    public void getTime(int time, MultipleStatusView multipleStatusView) {

    }

}
