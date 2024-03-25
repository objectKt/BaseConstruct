package dc.library.ui.view.dashboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dc.library.auto.R;

public class SpeedCenterView extends FrameLayout {

    private String speedTVTxt;

    public SpeedCenterView(@NonNull Context context) {
        this(context, null);
    }

    public SpeedCenterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeedCenterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SpeedCenterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SpeedCenterView, defStyleAttr, 0);
        speedTVTxt = attributes.getString(R.styleable.SpeedCenterView_speed_tv_text);
        LayoutInflater.from(context).inflate(R.layout.layout_speed_center_view, this, true);
        TextView speedTV = findViewById(R.id.speedTV);
        speedTV.setText(speedTVTxt);
        TextView speedUnitTV = findViewById(R.id.speedUnitTV);
        speedUnitTV.setText("km/h");
        attributes.recycle();
    }
}