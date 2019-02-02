package com.insworks.infreeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.insworks.infreeapp.R;

import cc.urowks.ulibrary.base.BaseActivity;
import cc.urowks.ulibrary.util.ActivityUtil;

public class SplashActivity extends BaseActivity {


    @Override
    protected boolean isStatusBarTintEnable() {
        return false;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtil.startActivity(LoginActivity.class);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}
