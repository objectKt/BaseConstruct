package dc.library.ui.view.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dc.library.auto.R;

public class SelectedMenuView extends View {

    private Paint mPaint;
    private float strokeWidth;
    private float radius;
    private float roundRadius;

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
        mPaint.setStyle(Paint.Style.STROKE);
        strokeWidth = context.getResources().getDimension(R.dimen.dp_2);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(context.getResources().getColor(R.color.color_lib_white, null));
        radius = getResources().getDimension(R.dimen.dp_4);
        roundRadius = getResources().getDimension(R.dimen.dp_6);
        mPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        float margin = strokeWidth / 2.0f + radius;
        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF(margin, margin, getWidth() - margin, getHeight() - margin);
        canvas.drawRoundRect(rectF, roundRadius, roundRadius, mPaint);
        canvas.restore();
    }
}
