package com.android.launcher.meter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.launcher.R;
import com.android.launcher.util.CarConstants;
import com.android.launcher.util.DensityUtil;
import com.android.launcher.util.LogUtils;

/**
 * 油表
 *
 * @date： 2023/11/16
 * @author: 78495
 */
public class OilMeterView extends View {

    private static final String TAG = OilMeterView.class.getSimpleName();

    private Path bgPath;
    private Path currentPath;
    private Paint paint;

    private RectF rectF = new RectF();
    private float rectWidth;
    private float strokeWidth;
    private float marginLeft;
    private float marginTop;
    private float marginBottom;
    private int lineMargin;
    private DashPathEffect dashPathEffect;
    private float pathLength;

    private float totalAngle = 61f;
    private float currentAngle;
    private int progressColor;

    public OilMeterView(Context context) {
        this(context, null);
    }

    public OilMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OilMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OilMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        rectWidth = DensityUtil.dip2px(context, 410);
        strokeWidth = getResources().getDimension(R.dimen.dp_5);
        marginLeft = DensityUtil.dip2px(context, 2);
        marginTop = DensityUtil.dip2px(context, 45);
        marginBottom = DensityUtil.dip2px(context, 28);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        bgPath = new Path();
        currentPath = new Path();

        progressColor = Color.parseColor("#A5BA02");

        lineMargin = DensityUtil.dip2px(getContext(), 2f);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (rectF.width() == 0) {
            rectF.left = 0;
            rectF.right = getWidth() - strokeWidth;
            rectF.top = 0;
            rectF.bottom = getHeight() - strokeWidth;
            bgPath.addArc(rectF, -180 - 59.8f, -totalAngle);
            PathMeasure pathMeasure = new PathMeasure(bgPath, false);
            pathLength = pathMeasure.getLength();


            float fullLine = (pathLength - lineMargin * 3f) / 4f;

            LogUtils.printI(TAG, "length=" + pathLength + ", fullLine=" + fullLine);
            float[] invervals = {fullLine, lineMargin};
            dashPathEffect = new DashPathEffect(invervals, 0);
            paint.setPathEffect(dashPathEffect);
        }

        drawBg(canvas);
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {


        canvas.save();

        paint.setPathEffect(null);
        currentPath.reset();
//        canvas.translate(marginLeft, marginTop);
        currentPath.addArc(rectF, -180 - 59.8f, -currentAngle);
        PathMeasure pathMeasure = new PathMeasure(currentPath, false);
        float length = pathMeasure.getLength();

        float fullLine = (pathLength - lineMargin * 3f) / 4f;

        LogUtils.printI(TAG, "length=" + length + ", fullLine=" + fullLine);
        if (length > fullLine) {
            float[] invervals = {fullLine, lineMargin};
            DashPathEffect currentDashPathEffect = new DashPathEffect(invervals, 0);
            paint.setPathEffect(currentDashPathEffect);
        }
        paint.setColor(progressColor);
        canvas.drawPath(currentPath, paint);

        canvas.restore();
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        paint.setPathEffect(null);
//        canvas.translate(marginLeft, marginTop);

        paint.setColor(Color.parseColor("#CDD6DF"));
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(bgPath, paint);

        canvas.restore();
    }


    public void setProgress(float percent) {
        currentAngle = totalAngle * percent;
        LogUtils.printI(TAG,"setProgress---currentAngle="+currentAngle+",percent="+percent);
        if (percent <=  CarConstants.OIL_BOX_WARN) {
            progressColor = getResources().getColor(R.color.colorRed);
        } else {
            progressColor = getResources().getColor(R.color.colorGreen);
        }
        invalidate();


    }
}
