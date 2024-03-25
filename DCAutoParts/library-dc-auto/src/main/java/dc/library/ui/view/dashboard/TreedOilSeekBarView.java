package dc.library.ui.view.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.math.BigDecimal;

import dc.library.auto.R;

public class TreedOilSeekBarView extends View {

    public float width;
    public float height;
    private Paint mPaint;
    public float mProgress;
    private Bitmap bitmap2;
    private Bitmap bitmap;
    private Bitmap bitmap3;

    public TreedOilSeekBarView(Context context) {
        this(context, null);
    }

    public TreedOilSeekBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreedOilSeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            mPaint = new Paint();
            mPaint.setColor(Color.RED);
            mPaint.setAntiAlias(true);
            bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.lib_pic_h_b);
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lib_pic_h_l);
            bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.lib_pic_h_k);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF oval3 = new RectF(0, 0, getWidth(), getHeight());// 设置个新的长方形
        canvas.save();
        canvas.drawBitmap(bitmap2, null, oval3, mPaint);// 第二个參数是x半径。第三个參数是y半径
        canvas.restore();
        canvas.save();
        float drawProgress = computeDrawProgress(mProgress);
        RectF oval4 = new RectF(0, 0, drawProgress, getHeight());// 设置个新的长方形
        canvas.drawBitmap(bitmap, null, oval4, mPaint);// 第二个參数是x半径。第三个參数是y半径
        canvas.restore();
        canvas.save();
        canvas.drawBitmap(bitmap3, null, oval3, mPaint);// 第二个參数是x半径。第三个參数是y半径
        canvas.restore();
    }

    private float computeDrawProgress(float progress) {
        BigDecimal b = new BigDecimal(getWidth());
        BigDecimal p1 = new BigDecimal(20);
        BigDecimal param = b.divide(p1, 2, BigDecimal.ROUND_HALF_UP);
        if (progress < 0) {
            progress = 0;
        }
        if (progress > 20) {
            progress = 20;
        }
        BigDecimal pr = new BigDecimal(progress);
        BigDecimal result = pr.multiply(param);
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.floatValue();
    }

    /**
     * 设置progressbar进度
     */
    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }
}