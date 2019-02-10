package cc.urowks.ulibrary.logic.click;

import android.view.View;

import static cc.urowks.ulibrary.util.NetworkUtil.isNetworkConnected;


/**
 * Created by aaron on 2015/6/17.
 */
public abstract class OnClickNetworkListener extends BaseClickListener {

    @Override
    public void onClick(View v) {
        boolean isNetworkOk = isNetworkConnected(v.getContext());

        if (isNetworkOk) {
            onNetworkClick(v);
        } else {
            onNoNetworkClick(v);
        }
    }

    // 点击事件--有网络
    public abstract void onNetworkClick(View v);

    // 点击事件--没有网络
    public abstract void onNoNetworkClick(View v);
}

