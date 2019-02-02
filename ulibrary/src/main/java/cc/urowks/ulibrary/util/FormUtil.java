package cc.urowks.ulibrary.util;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import cc.urowks.ulibrary.R;

/**
 * Created by songjian on 2016/11/4.
 */
public class FormUtil {

    public static boolean isBtnUsable = false;
    public static boolean mIsChecked = true;

    //添加文本监听
    public static void setTextChangedListener(final EditText phoneEt, final EditText verifyCodeEt, final EditText passwordFirstEt, final EditText passwordSecondEt, final Button btn, final CheckBox checkBox) {
        isBtnUsable = false;
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        BtnUnusable(btn);
                        mIsChecked = false;
                    } else {
                        mIsChecked = true;
                        if (StringUtils.isPhone(getTrim(phoneEt)) && getLength(verifyCodeEt) >= 4 && getLength(passwordFirstEt) >= 4 && getLength(passwordFirstEt) == getLength(passwordSecondEt) && mIsChecked) {
                            BtnUsable(btn);
                        } else {
                            BtnUnusable(btn);
                        }
                    }
                }
            });
        }
        //监听手机号码输入
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                if (StringUtils.isPhone(phone) && getLength(verifyCodeEt) >= 4 && getLength(passwordFirstEt) >= 4 && getLength(passwordFirstEt) == getLength(passwordSecondEt) && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听验证码输入
        verifyCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4 && StringUtils.isPhone(getTrim(phoneEt)) && getLength(passwordFirstEt) >= 4 && getLength(passwordFirstEt) == getLength(passwordSecondEt) && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordFirstEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4 && StringUtils.isPhone(getTrim(phoneEt)) && getLength(verifyCodeEt) >= 4 && getLength(passwordSecondEt) == s.length() && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordSecondEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4 && StringUtils.isPhone(getTrim(phoneEt)) && getLength(verifyCodeEt) >= 4 && s.length() == getLength(passwordFirstEt) && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    //添加密码修改文本监听
    public static void setModifyPswTextChangedListener(final EditText originPsw, final EditText passwordFirstEt, final EditText passwordSecondEt, final Button btn) {
        isBtnUsable = false;
        //监听原始密码输入
        originPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String originPsw = s.toString();
                if (originPsw.length() >= 4 && getLength(passwordFirstEt) >= 4 && getLength(passwordFirstEt) == getLength(passwordSecondEt) && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordFirstEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4 && getLength(originPsw) >= 4 && getLength(passwordSecondEt) == s.length() && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordSecondEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4 && getLength(originPsw) >= 4 && s.length() == getLength(passwordFirstEt) && mIsChecked) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    //添加 授权文本监听
    public static void setTextChangedListener(final EditText phoneEt, final EditText reMarkName, final Button btn) {
        isBtnUsable = false;
        //监听手机号码输入
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                if (StringUtils.isPhone(phone) && getBytesLength(reMarkName) >= 4) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听密码输入
        reMarkName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getBytesLength(s) >= 4 && StringUtils.isPhone(getTrim(phoneEt))) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    //添加登录文本监听
    public static void setLoginTextChangedListener(final EditText account, final EditText passwordEt, final Button btn) {
        isBtnUsable = false;
        //监听手机号码或账号输入
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 6 && getBytesLength(passwordEt) >= 6) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
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
                if (getBytesLength(s) >= 6 && getLength(account) >= 6) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //添加文本监听
    public static void setAddLocksTextChangedListener(final EditText lockName, final EditText serialNumber, final Button btn) {
        isBtnUsable = false;
        //监听车位锁名称输入
        lockName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() >= 1 && getLength(serialNumber) >= 11) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听车位锁序列号输入
        serialNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 11 && getLength(lockName) >= 1) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //添加文本监听
    public static void setFeedBackTextChangedListener(final EditText contentEt, final TextView conutLeftTv, final Button btn) {
        isBtnUsable = false;
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conutLeftTv.setText(300 - s.length() + "字");
                if (s.length() > 0 && !TextUtils.isEmpty(s.toString().trim())) {
                    BtnUsable(btn);
                } else {
                    BtnUnusable(btn);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @NonNull
    private static String getTrim(EditText phoneEt) {
        return phoneEt.getText().toString().trim();
    }

    //获取输入内容长度
    private static int getLength(EditText editText) {
        return getTrim(editText).length();
    }

    //获取输入内容长度
    private static int getBytesLength(EditText editText) {
        try {
            return getTrim(editText).getBytes("gbk").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getLength(editText);
    }

    private static int getBytesLength(CharSequence s) {
        String string = s.toString();
        try {
            return string.getBytes("gbk").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string.length();
    }


    private static void BtnUnusable(Button btn) {
//        btn.setTextColor(ActivityUtil.mContext.getResources().getColor(R.color.button_font_color));
//        btn.setBackgroundResource(R.drawable.button_radian_border_dark_shape);
        btn.setClickable(false);
        isBtnUsable = false;
    }

    private static void BtnUsable(Button btn) {
//        btn.setTextColor(ActivityUtil.mContext.getResources().getColor(R.color.button_font_color));
//        btn.setBackgroundResource(R.drawable.button_radian_border_light_shape);
        btn.setClickable(true);
        isBtnUsable = true;
    }

}
