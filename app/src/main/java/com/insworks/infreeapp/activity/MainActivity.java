package com.insworks.infreeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.insworks.infreeapp.R;
import com.insworks.infreeapp.net.MainContract;
import com.insworks.infreeapp.net.MainModel;
import com.insworks.infreeapp.net.MainPresenter;

import cc.urowks.ulibrary.base.BaseActivity;
import cc.urowks.ulibrary.base.BaseBean;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {


    @Override
    protected boolean isStatusBarTintEnable() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_b;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

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
    public void onClick(View v) {

    }

    @Override
    public void getNetData(BaseBean bean) {

    }
}
