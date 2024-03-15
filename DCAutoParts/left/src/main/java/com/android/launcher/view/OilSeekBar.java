package com.android.launcher.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.R;

import java.math.BigDecimal;

/**
 *
 * 类描述：自定义带刻度线的SeekBar
 */
public class OilSeekBar extends View {

    public float width;
    public float height;
    private Paint mpaint;
    public float progress ;
    public int w,h ;

    public OilSeekBar(Context context) {
        this(context, null);
    }

    public OilSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public OilSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = right-left ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mpaint = new Paint();
        mpaint.setColor(Color.RED);
        mpaint.setAntiAlias(true);
        h = canvas.getHeight() ;
        RectF oval3 = new RectF(0, 0,
                w, h);// 设置个新的长方形

        canvas.save();
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.h_b);
        canvas.drawBitmap(bitmap2,null,oval3, mpaint);// 第二个參数是x半径。第三个參数是y半径
        canvas.restore();


        canvas.save();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.h_l);
        RectF oval4 = new RectF(0, 0, progress, h);// 设置个新的长方形
        canvas.drawBitmap(bitmap,null,oval4, mpaint);// 第二个參数是x半径。第三个參数是y半径
        canvas.restore();


        canvas.save();
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.h_k);
        canvas.drawBitmap(bitmap3,null,oval3, mpaint);// 第二个參数是x半径。第三个參数是y半径
        canvas.restore();

    }



    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
////        mWidth = mTextView.getWidth();
////        mHeight = mTextView.getHeight();
//
//        Log.d(TAG, "onWindowFocusChanged: width = " + mWidth + "   height = " + mHeight);
//    }

    /** 设置progressbar进度 */
    public void setProgress(int wd) {

        w =  w==0 ? 303:w ;
        BigDecimal b = new BigDecimal(w);
        BigDecimal p1 =new BigDecimal(20) ;

        BigDecimal param =  b.divide(p1,2,BigDecimal.ROUND_HALF_UP) ;

        if(wd<0){
            wd = 0 ;
        }
        if(wd>20){
            wd = 20;
        }
        BigDecimal pr = new BigDecimal(wd) ;
        BigDecimal result = pr.multiply(param);
        result = result.setScale(2,BigDecimal.ROUND_HALF_UP) ;
        this.progress = result.floatValue();
        postInvalidate();
    }
}
