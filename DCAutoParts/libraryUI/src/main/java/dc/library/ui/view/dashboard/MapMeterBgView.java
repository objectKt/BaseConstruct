package dc.library.ui.view.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import dc.library.ui.util.DensityUtil;

public class MapMeterBgView extends View {

    private Paint paint;
    private int margin;
    private float marginLeft;

    public MapMeterBgView(@NonNull Context context) {
        this(context, null);
    }

    public MapMeterBgView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapMeterBgView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MapMeterBgView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        margin = DensityUtil.dip2px(context, 30);
        marginLeft = DensityUtil.dip2px(context, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.save();
        paint.setColor(Color.parseColor("#B0000000"));
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setColor(Color.WHITE);
        float radius = (getHeight() - margin * 2) / 2f;
        canvas.drawCircle(radius + marginLeft, getHeight() / 2f, radius, paint);
        canvas.drawCircle(getWidth() - radius - marginLeft, getHeight() / 2f, radius, paint);
        int top = DensityUtil.dip2px(getContext(), 110);
        int bottom = getHeight() - DensityUtil.dip2px(getContext(), 60);
        canvas.drawRect(new RectF(radius, top, getWidth() - radius - margin, bottom), paint);
        paint.setXfermode(null);
        canvas.restore();
    }

    @NotNull
    private Bitmap getSrcBitmap() {
        Bitmap bitmapSrc = Bitmap.createBitmap(getHeight() / 2, getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvasSrc = new Canvas(bitmapSrc);
        canvasSrc.drawARGB(0, 0, 0, 0);
        canvasSrc.drawCircle(bitmapSrc.getWidth() / 2, bitmapSrc.getWidth() / 2, bitmapSrc.getWidth() / 4, paint);
        return bitmapSrc;
    }

    @NotNull
    private Bitmap getDstBitmap(Paint paint) {
        Bitmap bitmapDst = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasDst = new Canvas(bitmapDst);
        canvasDst.drawARGB(0, 0, 0, 0);
        canvasDst.drawRect(new RectF(0, 0, canvasDst.getWidth(), canvasDst.getHeight()), paint);
        return bitmapDst;
    }
}