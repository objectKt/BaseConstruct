package dc.library.ui.view.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Objects;

import dc.library.auto.R;
import dc.library.utils.BigDecimalUtils;

/**
 * 前卫仪表盘的 水温表
 */
public class TreedWaterTempView extends View {

    private static final String TAG = TreedWaterTempView.class.getSimpleName();

    public int waterTemp;

    private RectF rectF = new RectF();
    private RectF progressRectF = new RectF();
    private Paint mPaint;

    public TreedWaterTempView(Context context) {
        this(context, null);
    }

    public TreedWaterTempView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreedWaterTempView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//防止边缘锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置线冒样式，取值有Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
    }

    @SuppressLint("ResourceType")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.set(0, 0, getWidth(), getHeight());
        drawCarSpeed(canvas);
    }

    @SuppressLint("ResourceType")
    private void drawCarSpeed(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.color_lib_cf));
        canvas.drawRect(rectF, mPaint);
        int color = waterTemp > 110 ? getResources().getColor(R.color.color_lib_red) : getResources().getColor(R.color.color_lib_green);
        mPaint.setColor(color);
        mPaint.setAlpha(100);
        progressRectF.set(0, 0, getDrawValue(waterTemp), getHeight());
        canvas.drawRect(progressRectF, mPaint);
        canvas.save();
        canvas.restore();
    }

    /**
     * 获取角度
     */
    private float getDrawValue(int waterTemp) {
        int maxTemp = 130;
        int minTemp = 50;
        try {
            int scope = maxTemp - minTemp;
            if (waterTemp < 50) {
                waterTemp = 50;
            } else if (waterTemp > maxTemp) {
                waterTemp = maxTemp;
            }
            waterTemp = waterTemp - 50;
            Log.v(TreedWaterTempView.class.getSimpleName(), "waterTemp=" + waterTemp + ", width=" + getWidth());
            double ratio = BigDecimalUtils.div(String.valueOf(getWidth()), String.valueOf(scope), 2);
            float result = (float) BigDecimalUtils.mul(String.valueOf(waterTemp), String.valueOf(ratio));
            if (result < 0) {
                result = 0f;
            } else if (result > getWidth()) {
                result = getWidth();
            }
            Log.v(TreedWaterTempView.class.getSimpleName(), "ratio=" + ratio + ", result=" + result);
            return result;
        } catch (Exception e) {
            Log.e(TreedWaterTempView.class.getSimpleName(), Objects.requireNonNull(e.getMessage()));
        }
        return 0f;
    }

    public void setWaterTemp(int waterTemp) {
        this.waterTemp = waterTemp;
        invalidate();
    }
}