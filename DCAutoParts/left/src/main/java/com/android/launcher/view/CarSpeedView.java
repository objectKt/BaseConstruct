package com.android.launcher.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.R;

import java.math.BigDecimal;


public class CarSpeedView extends View {

    public int color ;
    public float oil ;

    public CarSpeedView(Context context) {
        this(context, null);
    }

    public CarSpeedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CarSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SuppressLint("ResourceType")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        drawCarSpeed(canvas);
    }

    @SuppressLint("ResourceType")
    private void drawCarSpeed(Canvas canvas) {

        Paint  outerSmallArcPaint = new Paint();
        outerSmallArcPaint.setAntiAlias(true);//防止边缘锯齿
        outerSmallArcPaint.setStyle(Paint.Style.STROKE);
        outerSmallArcPaint.setStrokeCap(Paint.Cap.SQUARE);//设置线冒样式，取值有Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
        outerSmallArcPaint.setStrokeWidth(20);

//        if(alertOil){
//            outerSmallArcPaint.setColor(getResources().getColor(R.color.colorRed));
//        }else{
        getColor(oil);
        if (color==0){
            outerSmallArcPaint.setColor(getResources().getColor(R.color.coloroil));
        }else{
            outerSmallArcPaint.setColor(getResources().getColor(color));
        }
//        }
        outerSmallArcPaint.setAlpha(130);
        RectF mSmallRectFArc = new RectF(25, 25, 571, 571);
        float degree = getOilDegress(oil) ;
        canvas.drawArc(mSmallRectFArc, 115-degree, degree, false, outerSmallArcPaint);
        canvas.save();
        canvas.restore();

    }

    private void getColor(float oil) {
        if(oil<0.25f||new BigDecimal(oil).compareTo(new BigDecimal(0.25))==0){
            color = R.color.colorRed ;

        }else if(oil>0.25f && oil<0.5f || new BigDecimal(oil).compareTo(new BigDecimal(0.5))==0){
            color = R.color.colorGreen ;
        }else if(oil>0.5f && oil <0.75f || new BigDecimal(oil).compareTo(new BigDecimal(0.75))==0){
            color = R.color.colorGreen ;
        }else if(oil>0.75f && oil <1.0f || new BigDecimal(oil).compareTo(new BigDecimal(1.0))==0){
            color = R.color.colorGreen ;
        }
    }

    /**
     * 获取油表内容
     * @param oil
     * @return
     */
    private float getOilDegress(float oil) {

        float result = 0.0f ;
        if (new BigDecimal(oil).compareTo(new BigDecimal(0.0))==0){
            return 0.0f ;
        }

        if(oil<0.25f||new BigDecimal(oil).compareTo(new BigDecimal(0.25))==0){
            BigDecimal b1 =  new  BigDecimal(Double.toString(10.0f));
            BigDecimal b2 =  new BigDecimal(Double.toString(0.25f));
            BigDecimal param =  b1.divide(b2,2,BigDecimal.ROUND_HALF_UP) ;
            BigDecimal real= new BigDecimal(oil) ;
            BigDecimal re = real.multiply(param) ;
            result=re.floatValue();

        }else if(oil>0.25f && oil<0.5f || new BigDecimal(oil).compareTo(new BigDecimal(0.5))==0){

            BigDecimal b1 =  new  BigDecimal(Double.toString(24.0f));
            BigDecimal b2 =  new BigDecimal(Double.toString(0.5f));
            BigDecimal param =  b1.divide(b2,2,BigDecimal.ROUND_HALF_UP) ;

            BigDecimal real= new BigDecimal(oil) ;
            BigDecimal re = real.multiply(param) ;
            result=re.floatValue();
        }else if(oil>0.5f && oil <0.75f || new BigDecimal(oil).compareTo(new BigDecimal(0.75))==0){

            BigDecimal b1 =  new  BigDecimal(Double.toString(38.0f));
            BigDecimal b2 =  new BigDecimal(Double.toString(0.75f));
            BigDecimal param =  b1.divide(b2,2,BigDecimal.ROUND_HALF_UP) ;

            BigDecimal real= new BigDecimal(oil) ;
            BigDecimal re = real.multiply(param) ;
            result=re.floatValue();
        }else if(oil>0.75f && oil <1.0f || new BigDecimal(oil).compareTo(new BigDecimal(1.0))==0){

            BigDecimal b1 =  new  BigDecimal(Double.toString(52.0f));
            BigDecimal b2 =  new BigDecimal(Double.toString(1.0f));
            BigDecimal param =  b1.divide(b2,2,BigDecimal.ROUND_HALF_UP) ;

            BigDecimal real= new BigDecimal(oil) ;
            BigDecimal re = real.multiply(param) ;
            result=re.floatValue();

        }
        return result;
    }

    public void setOil(float oil){
        this.oil = oil ;
        invalidate();
    }

}