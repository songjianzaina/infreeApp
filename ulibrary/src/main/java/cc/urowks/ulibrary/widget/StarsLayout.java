package cc.urowks.ulibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cc.urowks.ulibrary.R;
import cc.urowks.ulibrary.util.ScreenUtils;


/**
 * Created by songjian on 2017/1/19.
 */

public class StarsLayout extends LinearLayout {

    public StarsLayout(Context context) {
        this(context, null);
    }

    public StarsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        for (int i = 0; i < 5; i++) {
            ImageView starView = new ImageView(getContext());
            starView.setBackgroundResource(R.mipmap.light_star_3x);
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = ScreenUtils.dp2px(5);
            starView.setLayoutParams(params);
            addView(starView);
        }
    }

    public void setLightStarCount(int count) {
        for (int i = 0; i < getChildCount(); i++) {
            if (i > count - 1) {
                getChildAt(i).setBackgroundResource(R.mipmap.dark_star_3x);
            } else {
                getChildAt(i).setBackgroundResource(R.mipmap.light_star_3x);

            }
        }
    }

}
