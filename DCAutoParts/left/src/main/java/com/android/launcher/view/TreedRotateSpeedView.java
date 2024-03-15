package com.android.launcher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.util.LogUtils;

/**
* @description: 前卫的发动机转速
* @createDate: 2023/6/20
*/
public class TreedRotateSpeedView extends View {

    private Paint paint;
    private SweepGradient gradient;
    private RectF rect;

    private static final int STROKE_WIDTH = 68;

    private float sweepAngle = 0.0f;
    private float startAngle = 180.0f;

    public TreedRotateSpeedView(Context context) {
        this(context, null);
    }

    public TreedRotateSpeedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreedRotateSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public TreedRotateSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit();
    }

    private void setupInit() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        rect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(startAngle, getWidth()/2, getHeight()/2);

        if(gradient == null){
            // 设置渐变颜色
            int[] colors = {Color.parseColor("#a030b3b5"), Color.parseColor("#ff30b3b5")};
            float[] positions = new float[2];
            positions[0] = 0.0f;
            positions[1] = 1.0f;
            gradient = new SweepGradient(getWidth() / 2.0f, getHeight() / 2.0f, colors, positions);
            paint.setShader(gradient);
        }

        rect.set(STROKE_WIDTH/2, STROKE_WIDTH/2, getWidth() - STROKE_WIDTH/2, getHeight() - STROKE_WIDTH/2);
        canvas.drawArc(rect,0,sweepAngle,false,paint);

        canvas.restore();
    }

    /**
    * @description:
     * @param rotateSpeed 发动机转速 0~6000
    * @createDate: 2023/6/20
    */
    public void setSweepAngle(int rotateSpeed){
        float ratio = 180.0f / 8000;
        sweepAngle = ratio * rotateSpeed;
        LogUtils.printI(TreedRotateSpeedView.class.getSimpleName(), "setSweepAngle----sweepAngle="+sweepAngle +", rotateSpeed="+rotateSpeed +", ratio="+ratio);
        invalidate();
    }
}
