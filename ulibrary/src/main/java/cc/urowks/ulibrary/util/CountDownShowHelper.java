package cc.urowks.ulibrary.util;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * TextView及其子类显示倒计时辅助类，倒计时期间TextView不可点击，记时单位：秒
 * <p/>
 * Created by jiang on 2015/10/15.
 */
public class CountDownShowHelper extends CountDownTimer {

    // 秒（1000毫秒）
    private static final int SECOND = 1000;

    // 默认总时长
    private static final long DEFAULT_MILLIS_IN_FUTURE = 60 * SECOND;

    // 默认回调onTick(long)时间间隔
    private static final long DEFAULT_INTERVAL = SECOND;

    private TextView mTextView; // 需要显示倒计时的TextView
    private String normalText; // 非倒计时时显示的文本
    private String countDownText; // 在倒计时期间显示的文本

    private boolean isFormatStr; // 记录倒计时期间显示的文本是否包含%d
    private long tickCount; // 记录onTick()执行的次数

    /**
     * 构造方法，默认倒计时为60s
     *
     * @param textView 需要显示倒计时的TextView
     */
    public CountDownShowHelper(TextView textView) {
        this(textView, 0);
    }

    /**
     * 构造方法
     *
     * @param textView 需要显示倒计时的TextView
     * @param totalSec 倒计时秒数
     */
    public CountDownShowHelper(TextView textView, int totalSec) {
        this(textView, 0, null, null);
    }

    /**
     * 构造方法，默认倒计时为60s
     *
     * @param textView      需要显示倒计时的TextView
     * @param normalText    正常情况下显示的文本
     * @param countDownText 倒计时显示的文本，如果需要将倒计时剩余数和其他文本混合显示，需要使用格式化字符串，例如：
     *                      ‘%ds后重新发送’，显示效果：20s后重新发送
     */
    public CountDownShowHelper(TextView textView, String normalText, String countDownText) {
        this(textView, 0, normalText, countDownText);
    }

    /**
     * 构造方法
     *
     * @param textView      需要显示倒计时的TextView
     * @param totalSec      倒计时秒数
     * @param normalText    正常情况下显示的文本
     * @param countDownText 倒计时显示的文本，如果需要将倒计时剩余数和其他文本混合显示，需要使用格式化字符串，例如：
     *                      ‘%ds后重新发送’，显示效果：20s后重新发送
     */
    public CountDownShowHelper(TextView textView, int totalSec, String normalText, String countDownText) {
        super(totalSec <= 0 ? DEFAULT_MILLIS_IN_FUTURE : totalSec * SECOND, DEFAULT_INTERVAL);
        mTextView = textView;
        this.normalText = normalText;
        this.countDownText = countDownText;
        if (!TextUtils.isEmpty(countDownText)) {
            isFormatStr = countDownText.contains("%d");
        }
    }

    /**
     * 重置倒计时状态
     */
    public void reset() {
        cancel();
        recoverNormal();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setEnabled(false);
        long sec = millisUntilFinished / SECOND;
        if (TextUtils.isEmpty(countDownText)) {
            mTextView.setText(String.valueOf(sec));
        } else if (isFormatStr) {
            mTextView.setText(String.format(countDownText, sec));
        } else if (tickCount == 0) { // 如果是普通字符串不用每次都更新显示
            mTextView.setText(countDownText);
        }
        tickCount++;
    }

    @Override
    public void onFinish() {
        recoverNormal();
    }

    // 恢复成普通状态
    private void recoverNormal() {
        tickCount = 0;
        mTextView.setEnabled(true);
        mTextView.setText(normalText);
    }
}
