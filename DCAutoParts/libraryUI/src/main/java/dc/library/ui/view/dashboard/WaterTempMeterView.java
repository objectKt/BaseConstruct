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
import dc.library.ui.util.BigDecimalUtils;
import dc.library.ui.util.DensityUtil;

/**
 * 水温表
 */
public class WaterTempMeterView extends View {

    private static final String TAG = WaterTempMeterView.class.getSimpleName();
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
    private float totalAngle = 60.7f;
    private float currentAngle = 0.0f;
    private int progressColor;
    private int maxTemp = 80;
    private float fullLine;
    private float startAngle;
    private int normalColor;
    private int warnColor;

    public WaterTempMeterView(Context context) {
        this(context, null);
    }

    public WaterTempMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterTempMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WaterTempMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        rectWidth = DensityUtil.dip2px(context, 412);
        strokeWidth = getResources().getDimension(R.dimen.dp_5);
        marginLeft = DensityUtil.dip2px(context, 61);
        marginTop = DensityUtil.dip2px(context, 45);
        marginBottom = DensityUtil.dip2px(context, 28);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        bgPath = new Path();
        currentPath = new Path();
        startAngle = -180 - 59.2f;
        lineMargin = DensityUtil.dip2px(getContext(), 2f);
        normalColor = Color.parseColor("#A5BA02");
        warnColor = getResources().getColor(R.color.color_lib_red);
        progressColor = normalColor;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (rectF.width() == 0) {
            rectF.left = 0;
            rectF.right = getWidth() - strokeWidth;
            rectF.top = 0;
            rectF.bottom = getHeight() - strokeWidth;
            bgPath.addArc(rectF, startAngle, -totalAngle);
            PathMeasure pathMeasure = new PathMeasure(bgPath, false);
            pathLength = pathMeasure.getLength();
            fullLine = (pathLength - lineMargin) / 2f;
            float[] invervals = {fullLine, lineMargin};
            dashPathEffect = new DashPathEffect(invervals, 0);
            paint.setPathEffect(dashPathEffect);
        }
        drawBg(canvas);
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        canvas.save();
        currentPath.reset();
        currentPath.addArc(rectF, startAngle, -currentAngle);
        PathMeasure pathMeasure = new PathMeasure(currentPath, false);
        float length = pathMeasure.getLength();
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
        paint.setColor(Color.parseColor("#CDD6DF"));
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(bgPath, paint);
        canvas.restore();
    }

    public void setProgress(float temp) {
        if (temp > 110) {
            progressColor = warnColor;
        } else {
            progressColor = normalColor;
        }
        temp = temp - 50f;
        currentAngle = totalAngle * BigDecimalUtils.div(String.valueOf(temp), String.valueOf(maxTemp), 2);
        if (currentAngle > totalAngle) {
            currentAngle = totalAngle;
        } else if (currentAngle < 0f) {
            currentAngle = 0f;
        }
        Log.v(TAG, "setProgress---currentAngle=" + currentAngle + ",temp=" + temp);
        invalidate();
    }
}