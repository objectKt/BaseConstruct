package dc.library.ui.view.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import dc.library.ui.R;
import dc.library.ui.util.DensityUtil;

/**
 * 油表
 */
public class OilMeterView extends View {

    private static final String TAG = OilMeterView.class.getSimpleName();
    private Path bgPath;
    private Path currentPath;
    private Paint paint;
    private RectF rectF = new RectF();
    private float rectWidth;
    private float strokeWidth;
    private float marginLeft;
    private float marginTop;
    private float marginBottom;
    private int lineMargin;
    private DashPathEffect dashPathEffect;
    private float pathLength;
    private float totalAngle = 61f;
    private float currentAngle;
    private int progressColor;

    public OilMeterView(Context context) {
        this(context, null);
    }

    public OilMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OilMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OilMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        rectWidth = DensityUtil.dip2px(context, 410);
        strokeWidth = getResources().getDimension(R.dimen.dp_5);
        marginLeft = DensityUtil.dip2px(context, 2);
        marginTop = DensityUtil.dip2px(context, 45);
        marginBottom = DensityUtil.dip2px(context, 28);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        bgPath = new Path();
        currentPath = new Path();
        progressColor = Color.parseColor("#A5BA02");
        lineMargin = DensityUtil.dip2px(getContext(), 2f);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (rectF.width() == 0) {
            rectF.left = 0;
            rectF.right = getWidth() - strokeWidth;
            rectF.top = 0;
            rectF.bottom = getHeight() - strokeWidth;
            bgPath.addArc(rectF, -180 - 59.8f, -totalAngle);
            PathMeasure pathMeasure = new PathMeasure(bgPath, false);
            pathLength = pathMeasure.getLength();
            float fullLine = (pathLength - lineMargin * 3f) / 4f;
            Log.v(TAG, "length=" + pathLength + ", fullLine=" + fullLine);
            float[] invervals = {fullLine, lineMargin};
            dashPathEffect = new DashPathEffect(invervals, 0);
            paint.setPathEffect(dashPathEffect);
        }
        drawBg(canvas);
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        canvas.save();
        paint.setPathEffect(null);
        currentPath.reset();
        currentPath.addArc(rectF, -180 - 59.8f, -currentAngle);
        PathMeasure pathMeasure = new PathMeasure(currentPath, false);
        float length = pathMeasure.getLength();
        float fullLine = (pathLength - lineMargin * 3f) / 4f;
        Log.v(TAG, "length=" + length + ", fullLine=" + fullLine);
        if (length > fullLine) {
            float[] invervals = {fullLine, lineMargin};
            DashPathEffect currentDashPathEffect = new DashPathEffect(invervals, 0);
            paint.setPathEffect(currentDashPathEffect);
        }
        paint.setColor(progressColor);
        canvas.drawPath(currentPath, paint);
        canvas.restore();
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        paint.setPathEffect(null);
        paint.setColor(Color.parseColor("#CDD6DF"));
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(bgPath, paint);
        canvas.restore();
    }

    public void setProgress(float percent) {
        currentAngle = totalAngle * percent;
        Log.v(TAG, "setProgress---currentAngle=" + currentAngle + ",percent=" + percent);
        if (percent <= 0.13f) {
            progressColor = getResources().getColor(R.color.color_lib_red);
        } else {
            progressColor = getResources().getColor(R.color.color_lib_green);
        }
        invalidate();
    }
}