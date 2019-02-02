package com.insworks.infreeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.insworks.infreeapp.R;
import com.insworks.infreeapp.net.MainContract;
import com.insworks.infreeapp.net.MainModel;
import com.insworks.infreeapp.net.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import cc.urowks.ulibrary.base.BaseActivity;
import cc.urowks.ulibrary.base.BaseBean;
import cc.urowks.ulibrary.util.ActivityUtil;
import cc.urowks.ulibrary.util.LogUtils;
import cc.urowks.ulibrary.util.StringUtils;

public class LoginActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {


    @BindView(R.id.login_phone_et)
    EditText phoneEt;
    @BindView(R.id.login_password_et)
    EditText passwordEt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    private boolean isLoginBtnUsable;//登录按钮是否可用 默认不可用


    @Override
    protected boolean isStatusBarTintEnable() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        //监听手机号码输入
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                if (StringUtils.isPhone(phone) && passwordEt.getText().toString().trim().length() >= 4) {
                    loginBtnUsable();
                } else {
                    loginBtnUnusable();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听密码输入
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4  && StringUtils.isPhone(phoneEt.getText().toString().trim())) {
                    loginBtnUsable();
                } else {
                    loginBtnUnusable();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }




    private void loginBtnUnusable() {
        loginBtn.setTextColor(getResources().getColor(R.color.button_font_color));
        loginBtn.setBackgroundResource(R.color.forbidden_button_bg);
        loginBtn.setClickable(false);
        isLoginBtnUsable = false;
    }

    private void loginBtnUsable() {
        loginBtn.setTextColor(getResources().getColor(R.color.button_font_color));
        loginBtn.setBackgroundResource(R.color.primary_color);
        loginBtn.setClickable(true);
        isLoginBtnUsable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftInputView();
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.login_forget_password_tv, R.id.login_regiter_quickly_tv, R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget_password_tv:
                LogUtils.i(TAG, "点击 忘记密码");
                break;
            case R.id.login_regiter_quickly_tv:
                LogUtils.i(TAG, "点击 立即注册");
                break;
            case R.id.login_btn:
                LogUtils.i(TAG, "点击 登录");
                if (!isLoginBtnUsable) {
                    return;
                }
                ActivityUtil.startActivity(MainActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void getNetData(BaseBean bean) {

    }


}
