package dc.library.ui.view.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import dc.library.auto.R;
import dc.library.utils.DensityUtil;

/**
 * 菜单油表 View
 */
public class OilMeterLineView extends View {

    private static final String TAG = OilMeterLineView.class.getSimpleName();
    private Paint mPaint;
    //每条线所占的百分百
    private static final int RATIO = 25;
    private int progressColor;
    private DashPathEffect dashPathEffect;
    private int lineMargin;
    private float currentOil = 0f;
    private float strokeWidth;
    private Path bgPath;
    private Path currentPath;

    public OilMeterLineView(Context context) {
        super(context, null);
    }

    public OilMeterLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OilMeterLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OilMeterLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        strokeWidth = getResources().getDimension(R.dimen.dp_5);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        bgPath = new Path();
        currentPath = new Path();
        lineMargin = DensityUtil.dip2px(getContext(), 2f);
        progressColor = Color.parseColor("#A5BA02");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dashPathEffect == null) {
            bgPath.moveTo(0, getHeight() / 2);
            bgPath.lineTo(getWidth(), getHeight() / 2);
            currentPath.moveTo(0, getHeight() / 2);
            float fullLine = (getWidth() - lineMargin * 3f) / 4f;
            Log.v(TAG, "length=" + getWidth() + ", fullLine=" + fullLine);
            float[] invervals = {fullLine, lineMargin};
            dashPathEffect = new DashPathEffect(invervals, 0);
            mPaint.setPathEffect(dashPathEffect);
        }
        drawBgRect(canvas);
        drawSelectRect(canvas);
    }

    private void drawSelectRect(Canvas canvas) {
        canvas.save();
        if (currentOil > 0f) {
            mPaint.setColor(progressColor);
            currentPath.reset();
            currentPath.moveTo(0, getHeight() / 2);
            currentPath.lineTo(currentOil, getHeight() / 2);
            canvas.drawPath(currentPath, mPaint);
        }
        canvas.restore();
    }

    private void drawBgRect(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#CDD6DF"));
        canvas.drawPath(bgPath, mPaint);
        canvas.restore();
    }

    public void setCurrentOil(float percent) {
        currentOil = getWidth() * percent;
        if (percent <= 0.13f) {
            progressColor = getResources().getColor(R.color.color_lib_red);
        } else {
            progressColor = getResources().getColor(R.color.color_lib_green);
        }
        invalidate();
    }
}