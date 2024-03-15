package com.android.launcher.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.R;
import com.android.launcher.util.LogUtils;

import java.math.BigDecimal;

/**
* @description: 水温表
* @createDate: 2023/6/26
*/
public class EnergySpeedView extends View {

    //    public float degree ;
    public int waterTemp;

    public EnergySpeedView(Context context) {
        this(context, null);
    }

    public EnergySpeedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public EnergySpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ResourceType")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawCarSpeed(canvas);
    }

    @SuppressLint("ResourceType")
    private void drawCarSpeed(Canvas canvas) {

        Paint outerSmallArcPaint = new Paint();
        outerSmallArcPaint.setAntiAlias(true);//防止边缘锯齿
        outerSmallArcPaint.setStyle(Paint.Style.STROKE);
        outerSmallArcPaint.setStrokeCap(Paint.Cap.SQUARE);//设置线冒样式，取值有Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
        outerSmallArcPaint.setStrokeWidth(20);

        int color = waterTemp > 110 ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGreen);

        outerSmallArcPaint.setColor(color);
        outerSmallArcPaint.setAlpha(100);

        RectF mSmallRectFArc = new RectF(20, 25, 580, 575);
        float waterDegree = getWaterTempDegree(waterTemp);
        Log.i("waterTemp", waterDegree + "=======================");
        canvas.drawArc(mSmallRectFArc, 114 - waterDegree, waterDegree, false, outerSmallArcPaint);
//        canvas.drawArc(mSmallRectFArc, 0,355, true, outerSmallArcPaint);
//        Resources resources = App.getGlobalContext().getResources() ;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = false ;
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888 ;
//
//        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.pointer,options).copy(Bitmap.Config.ARGB_8888,true);;
//        bitmap.setDensity(resources.getDisplayMetrics().densityDpi);
//        int height = bitmap.getHeight();
//        int width = bitmap.getWidth();
//
//        Bitmap thumbImg = Bitmap.createScaledBitmap(bitmap,width,height, true);
//        canvas.rotate(degree,width/2, height/2);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setFilterBitmap(true);
//        canvas.drawBitmap(thumbImg,0, 0,paint);
        canvas.save();
        canvas.restore();
    }


    /**
     * 获取角度
     *
     * @param waterTemp
     * @return
     */
    private float getWaterTempDegree(int waterTemp) {
        float result = 0.0f;
        if (waterTemp < 50) {
            waterTemp = 50;
        }
        if (waterTemp > 130) {
            waterTemp = 130;
        }
        int degreeTemp = waterTemp - 50;
        if (degreeTemp <= 20) {
            BigDecimal b1 = new BigDecimal(Double.toString(6));
            BigDecimal b2 = new BigDecimal(Double.toString(20));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);

            BigDecimal real = new BigDecimal(degreeTemp);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();
        } else if (degreeTemp > 20 && degreeTemp <= 40) {
            BigDecimal b1 = new BigDecimal(Double.toString(22));
            BigDecimal b2 = new BigDecimal(Double.toString(40));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal real = new BigDecimal(degreeTemp);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();
        } else if (degreeTemp > 40 && degreeTemp <= 60) {
            BigDecimal b1 = new BigDecimal(Double.toString(38));
            BigDecimal b2 = new BigDecimal(Double.toString(60));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal real = new BigDecimal(degreeTemp);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();
        } else if (degreeTemp > 60 && degreeTemp <= 80) {

            BigDecimal b1 = new BigDecimal(Double.toString(50));
            BigDecimal b2 = new BigDecimal(Double.toString(80));
            BigDecimal param = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal real = new BigDecimal(degreeTemp);
            BigDecimal re = real.multiply(param);
            result = re.floatValue();
        }

        return result;
    }

//    public void setDegree(float degree){
//        this.degree = degree ;
//        invalidate();
//    }

    public void setWaterTemp(int waterTemp) {
        this.waterTemp = waterTemp;
        LogUtils.printI(EnergySpeedView.class.getSimpleName(), "setWaterTemp---waterTemp="+waterTemp);
        invalidate();
    }

}