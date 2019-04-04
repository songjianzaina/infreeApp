package com.insworks.infreeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.insworks.infreeapp.R;
import com.insworks.infreeapp.bean.WeatherEntity;
import com.insworks.infreeapp.net.MainContract;
import com.insworks.infreeapp.net.MainModel;
import com.insworks.infreeapp.net.MainPresenter;

import butterknife.BindView;
import cc.urowks.ulibrary.base.BaseActivity;
import cc.urowks.ulibrary.base.BaseBean;

public class WeatherActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {

    @BindView(R.id.tv_quality)
    TextView tv_quality;
    @BindView(R.id.tv_pm)
    TextView tv_pm;
    @BindView(R.id.tv_wendu)
    TextView tv_wendu;
    @BindView(R.id.tv_notice)
    TextView tv_notice;

    @Override
    protected boolean isStatusBarTintEnable() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMvpPresenter.getWeather("101030100", mMultipleStateView);

    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void getNetData(BaseBean bean) {
        WeatherEntity wea = (WeatherEntity) bean;
        tv_quality.setText("空气质量：" + wea.quality);
        tv_pm.setText("Pm10" + wea.pm10);
        tv_wendu.setText("温度：" + wea.wendu);
        tv_notice.setText("提醒：" + wea.ganmao);
    }
}
