package com.android.launcher.meter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.launcher.R;
import com.android.launcher.util.LogUtils;

/**
 * 注意力辅助系统
 *
 * @date： 2023/11/21
 * @author: 78495
 */
public class AttentionAidSysView extends View {

    private Paint mPaint;
    private float margin;

    private int currentColor;

    public AttentionAidSysView(Context context) {
        this(context, null);
    }

    public AttentionAidSysView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttentionAidSysView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AttentionAidSysView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setInit(context);
    }

    private void setInit(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        margin = context.getResources().getDimension(R.dimen.dp_16);

        currentColor = context.getResources().getColor(R.color.attention_aid_sys_green);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPaint.setPathEffect(null);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp_2));
        mPaint.setColor(Color.parseColor("#80ffffff"));
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - mPaint.getStrokeWidth(), mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setColor(currentColor);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp_6));
        Path path = new Path();
        float radius = getWidth() / 2f - mPaint.getStrokeWidth() * 2f;
        path.addCircle(getWidth() / 2f, getHeight() / 2f, radius, Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        final int lineCount = 5;
        float lineLength = (length - margin * (lineCount + 1)) / lineCount;

        LogUtils.printI(AttentionAidSysView.class.getSimpleName(), "lineLength=" + lineLength + ", margin=" + margin + ", length=" + length);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{lineLength, margin}, 0);
        mPaint.setPathEffect(dashPathEffect);
        canvas.drawPath(path, mPaint);
        canvas.restore();
    }

    public void changeColor(int color) {
        currentColor = getResources().getColor(color);
        invalidate();
    }
}
