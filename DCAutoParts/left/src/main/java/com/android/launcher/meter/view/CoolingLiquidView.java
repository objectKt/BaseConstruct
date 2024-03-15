package com.android.launcher.meter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.R;
import com.android.launcher.util.LogUtils;

/**
 * 冷却液
 *
 * @date： 2023/11/21
 * @author: 78495
 */
public class CoolingLiquidView extends View {

    private Paint mPaint;

    private RectF bgRect = new RectF();
    private RectF progressRect = new RectF();
    private float radius;

    private float progress;
    private float scaleHeight;
    private float scaleWidth;

    private int length = 80;

    public CoolingLiquidView(Context context) {
        this(context, null);
    }

    public CoolingLiquidView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoolingLiquidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CoolingLiquidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radius = context.getResources().getDimension(R.dimen.dp_8);
        scaleHeight = context.getResources().getDimension(R.dimen.dp_6);
        scaleWidth = context.getResources().getDimension(R.dimen.dp_2);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);
        drawProgress(canvas);

        drawScale(canvas);

    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(scaleWidth);
        canvas.drawLine(scaleWidth, radius, scaleWidth, radius + scaleHeight, mPaint);


        float interval = getWidth() / 4f;
        canvas.drawLine(interval, radius, interval, radius + scaleHeight, mPaint);
        canvas.drawLine(interval *2, radius, interval * 2, radius + scaleHeight, mPaint);
        canvas.drawLine(interval *3, radius, interval * 3, radius + scaleHeight, mPaint);

        canvas.drawLine(getWidth() - scaleWidth, radius, getWidth() - scaleWidth, radius + scaleHeight, mPaint);
        canvas.restore();
    }

    private void drawProgress(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        progressRect.set(0, 0, progress, radius);
        canvas.drawRoundRect(progressRect, radius, radius, mPaint);
        canvas.restore();
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#50ffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        bgRect.set(0, 0, getWidth(), radius);
        canvas.drawRoundRect(bgRect, radius, radius, mPaint);

        canvas.restore();
    }

    public void setProgress(int temp) {
        LogUtils.printI(CoolingLiquidView.class.getSimpleName(),"setProgress---temp="+temp);
        temp = temp - 50;
        if(temp < 0){
            temp = 0;
        }
        if(temp > length){
            temp = length;
        }
        float ratio = temp / (length * 1.0f);

        LogUtils.printI(CoolingLiquidView.class.getSimpleName(),"setProgress---ratio="+ratio);

        progress = getWidth() * ratio;

        invalidate();
    }
}
