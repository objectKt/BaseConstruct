package dc.library.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import kotlin.Deprecated;
import kotlin.ReplaceWith;

/**
 * 使用最高页面height的ViewPager
 */
@Deprecated(message = "使用FixedViewPager替代", replaceWith = @ReplaceWith(expression = "FixedViewPager", imports = {"com.drake.engine.widget.FixedViewPager"}))
public class ViewPagerForMaxHeight extends ViewPager {
    public ViewPagerForMaxHeight(Context context) {
        super(context);
    }

    public ViewPagerForMaxHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}