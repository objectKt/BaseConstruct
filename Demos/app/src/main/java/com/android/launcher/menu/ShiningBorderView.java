package com.android.launcher.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class ShiningBorderView extends View {

    private Paint mPaint;
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;

    public ShiningBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        // 初始化矩阵
        mGradientMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取视图宽度
        mViewWidth = w;
        // 创建线性渲染器
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // 设置矩阵平移
        mGradientMatrix.setTranslate(mTranslate, 0);
        mTranslate = (mTranslate + 1) % mViewWidth;
        mPaint.setShader(mLinearGradient);
        // 绘制边框
        canvas.drawRect(0, 0, mViewWidth, mViewWidth, mPaint);
        // 重绘视图
        postInvalidateDelayed(150);
    }
}