package dc.library.ui.view.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import dc.library.auto.R;

public class MapBottomBgView extends View {

    public MapBottomBgView(Context context) {
        this(context, null);
    }

    public MapBottomBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapBottomBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MapBottomBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        float moveMargin = getResources().getDimension(R.dimen.dp_80);
        Log.v(MapBottomBgView.class.getSimpleName(), "moveMargin=" + moveMargin);
        RectF leftArc = new RectF(moveMargin, 0f, moveMargin + getHeight(), getHeight());
        float rightCircleLeft = getWidth() - moveMargin - getHeight();
        RectF rightArc = new RectF(rightCircleLeft, 0f, rightCircleLeft + getHeight(), getHeight());
        Path path = new Path();
        path.moveTo(0f, getHeight() / 2);
        path.lineTo(moveMargin, getHeight() / 2f);
        path.addArc(leftArc, 30f, 120f);
        path.lineTo(leftArc.right, getHeight());
        path.close();
        canvas.save();
        canvas.drawPath(path, paint);
        canvas.restore();
    }
}
