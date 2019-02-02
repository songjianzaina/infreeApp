package cc.urowks.ulibrary.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AutoScaleHeightImageView extends ImageView {

	public AutoScaleHeightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Drawable drawable = getDrawable();	// 获取src属性指定的图片
		if (drawable != null) {
			int width = drawable.getMinimumWidth();		// 获取图片真实的宽
			int height = drawable.getMinimumHeight();	// 获取图片真实的高
			float scale = (float) height / width;	//算高和宽的比例   （ 两个整数直接相同，结果拿不到小数）
			
			int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
			int heightMeasure = (int) (widthMeasure * scale);
			
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasure, MeasureSpec.EXACTLY);
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}
