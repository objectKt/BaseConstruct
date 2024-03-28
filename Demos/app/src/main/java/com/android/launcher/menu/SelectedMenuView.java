package com.android.launcher.menu;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.demos.R;

public class SelectedMenuView extends View {

    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    private ValueAnimator mAnimator;
    private Animator.AnimatorListener mAnimatorListener;

    private float strokeWidth;
    private float radius;
    private float roundRadius;

    private static final int DEFAULT_DURATION = 2000;
    private static final int DEFAULT_COLOR = Color.RED;

    public SelectedMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectedMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SelectedMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        mRectF = new RectF();
        mPaint.setStyle(Paint.Style.STROKE);
        strokeWidth = context.getResources().getDimension(R.dimen.strokeWidth);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(context.getResources().getColor(R.color.ffffff));
        radius = getResources().getDimension(R.dimen.dp_4);
        roundRadius = getResources().getDimension(R.dimen.dp_6);
        mPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID));

        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(DEFAULT_DURATION);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addListener(mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // 开始动画时将视图设为可见
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束时将视图设为不可见
                setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // 动画取消时将视图设为不可见
                setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // 动画重复时将视图设为可见
                setVisibility(View.VISIBLE);
            }
        });
        // 开始动画
        mAnimator.start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // 计算绘制区域
        int width = getWidth();
        int height = getHeight();
        mRectF.set(0, 0, width, height);
        // 绘制边框
        mPath.reset();
        mPath.moveTo(mRectF.left + mRectF.width() / 2, mRectF.top);
        mPath.lineTo(mRectF.left + mRectF.width(), mRectF.top + mRectF.height() / 2);
        mPath.lineTo(mRectF.left + mRectF.width(), mRectF.bottom - mRectF.height() / 2);
        mPath.lineTo(mRectF.left + mRectF.width() / 2, mRectF.bottom);
        mPath.lineTo(mRectF.left, mRectF.bottom - mRectF.height() / 2);
        mPath.lineTo(mRectF.left, mRectF.top + mRectF.height() / 2);
        mPath.close();

        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath, mPaint);
//        canvas.save();
//        float margin = strokeWidth / 2.0f + radius;
//        @SuppressLint("DrawAllocation")
//        RectF rectF = new RectF(margin, margin, getWidth() - margin, getHeight() - margin);
//        canvas.drawRoundRect(rectF, roundRadius, roundRadius, mPaint);
//        canvas.restore();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        // 当视图可见时开始动画，不可见时停止动画
        if (visibility == View.VISIBLE) {
            mAnimator.start();
        } else {
            mAnimator.cancel();
        }
    }
}