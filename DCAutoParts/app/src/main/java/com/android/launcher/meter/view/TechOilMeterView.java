package com.android.launcher.meter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.launcher.R;
import com.android.launcher.util.CarConstants;
import com.android.launcher.util.LogUtils;

import java.math.BigDecimal;

/**
* @description: 当前油量
* @createDate: 2023/7/15
*/
public class TechOilMeterView extends View {

    public int color;
    public float oil;
    private Paint outerSmallArcPaint;
    private RectF mSmallRectFArc;
    private float degree;

    public TechOilMeterView(Context context) {
        this(context, null);
    }

    public TechOilMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TechOilMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupInit(context);
    }

    private void setupInit(Context context) {
        outerSmallArcPaint = new Paint();
        outerSmallArcPaint.setAntiAlias(true);//防止边缘锯齿
        outerSmallArcPaint.setStyle(Paint.Style.STROKE);
        outerSmallArcPaint.setStrokeCap(Paint.Cap.SQUARE);//设置线冒样式，取值有Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
        outerSmallArcPaint.setStrokeWidth(16);
    }

    @Override
    @SuppressLint("ResourceType")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawCarSpeed(canvas);
    }

    @SuppressLint("ResourceType")
    private void drawCarSpeed(Canvas canvas) {

//        if(alertOil){
//            outerSmallArcPaint.setColor(getResources().getColor(R.color.colorRed));
//        }else{
        getColor(oil);
        if(mSmallRectFArc==null){
            mSmallRectFArc = new RectF(0, 0, getWidth(), getHeight() - 31);
        }

        if (color == 0) {
            outerSmallArcPaint.setColor(getResources().getColor(R.color.coloroil));
        } else {
            outerSmallArcPaint.setColor(getResources().getColor(color));
        }
//        }
        outerSmallArcPaint.setAlpha(130);

        canvas.save();
//        canvas.drawArc(mSmallRectFArc, 120 - degree, degree, false, outerSmallArcPaint);
        canvas.drawArc(mSmallRectFArc, 116f - degree, degree, false, outerSmallArcPaint);
        canvas.restore();

    }

    private void getColor(float oil) {
        if (oil <  CarConstants.OIL_BOX_WARN || new BigDecimal(oil).compareTo(new BigDecimal( CarConstants.OIL_BOX_WARN)) == 0) {
            color = R.color.colorRed;
        } else {
            color = R.color.colorGreen;
        }
    }

    /**
     * 获取油表内容
     *
     * @param oil
     * @return
     */
    private float getOilDegress(float oil) {

        float result = 0.0f;
        if (new BigDecimal(oil).compareTo(new BigDecimal(0.0)) == 0) {
            return 0.0f;
        }

        if (oil < 0.25f || new BigDecimal(oil).compareTo(new BigDecimal(0.25)) == 0) {
            BigDecimal b1 = new BigDecimal(Double.toString(10.0f));
            BigDecimal b2 = new BigDecimal(Double.toString(0.25f));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal real = new BigDecimal(oil);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();

        } else if (oil > 0.25f && oil < 0.5f || new BigDecimal(oil).compareTo(new BigDecimal(0.5)) == 0) {

            BigDecimal b1 = new BigDecimal(Double.toString(24.0f));
            BigDecimal b2 = new BigDecimal(Double.toString(0.5f));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);

            BigDecimal real = new BigDecimal(oil);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();
        } else if (oil > 0.5f && oil < 0.75f || new BigDecimal(oil).compareTo(new BigDecimal(0.75)) == 0) {

            BigDecimal b1 = new BigDecimal(Double.toString(38.0f));
            BigDecimal b2 = new BigDecimal(Double.toString(0.75f));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);

            BigDecimal real = new BigDecimal(oil);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();
        } else if (oil > 0.75f && oil < 1.0f || new BigDecimal(oil).compareTo(new BigDecimal(1.0)) == 0) {

            BigDecimal b1 = new BigDecimal(Double.toString(52.0f));
            BigDecimal b2 = new BigDecimal(Double.toString(1.0f));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);

            BigDecimal real = new BigDecimal(oil);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();

        }
        return result;
    }

    public void setOil(float oil) {

        this.oil = oil;
        degree = getOilDegress(oil);
        LogUtils.printI(TechOilMeterView.class.getSimpleName(), "degree="+degree);
        invalidate();
    }


}
