package cc.urowks.ulibrary.widget;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import cc.urowks.ulibrary.R;


/**
 * Created by wanghongjia on 2016/11/10.
 */
public class QYProgressDialog extends ProgressDialog {

    public QYProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
    }
    ImageView iv_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress);
        iv_progress = (ImageView) findViewById(R.id.iv_progress);
    }

    public void startAnimation(RotateAnimation animation) {
        iv_progress.startAnimation(animation);
    }

}
