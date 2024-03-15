package com.android.launcher.meter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.R;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.DensityUtil;
import com.android.launcher.util.LogUtils;

/**
 * 水温View
 *
 * @date： 2023/11/6
 * @author: 78495
 */
public class WaterTempLineView extends View {

    private static final String TAG = WaterTempLineView.class.getSimpleName();

    private Paint mPaint;
    private int progressColor;

    private DashPathEffect dashPathEffect;

    private int lineMargin;

    private float currentTemp = 0f;

    private float strokeWidth;

    private int maxTemp = 80;

    private Path bgPath;
    private Path currentPath;
    private int normalColor;
    private int warnColor;

    public WaterTempLineView(Context context) {
        super(context, null);
    }

    public WaterTempLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterTempLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WaterTempLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        normalColor = Color.parseColor("#A5BA02");
        warnColor = getResources().getColor(R.color.colorRed);
        progressColor = normalColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (dashPathEffect == null) {

            bgPath.moveTo(0, getHeight() / 2);
            bgPath.lineTo(getWidth(), getHeight() / 2);

            currentPath.moveTo(0, getHeight() / 2);

            float fullLine = (getWidth() - lineMargin) / 2f;

//            LogUtils.printI(TAG, "length=" + getWidth() + ", fullLine=" + fullLine);
            float[] invervals = {fullLine, lineMargin};
            dashPathEffect = new DashPathEffect(invervals, 0);
            mPaint.setPathEffect(dashPathEffect);
        }

        drawBgRect(canvas);

        drawSelectRect(canvas);

    }

    private void drawSelectRect(Canvas canvas) {

        canvas.save();
        if(currentTemp >0f){
            currentPath.reset();
            currentPath.moveTo(0, getHeight() / 2);
            mPaint.setColor(progressColor);
            currentPath.lineTo(currentTemp, getHeight() / 2);
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

    public void setTemp(float temp) {

        if(temp > 110){
            progressColor = warnColor;
        }else{
            progressColor = normalColor;
        }

        temp = temp - 50f;

        currentTemp =  getWidth() * BigDecimalUtils.div(String.valueOf(temp),String.valueOf(maxTemp),2);
        if(currentTemp > getWidth()){
            currentTemp = getWidth();
        }else if(currentTemp < 0f){
            currentTemp = 0f;
        }
        invalidate();
    }

}
