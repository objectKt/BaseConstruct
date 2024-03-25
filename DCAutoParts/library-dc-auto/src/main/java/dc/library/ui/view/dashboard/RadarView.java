package dc.library.ui.view.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dc.library.auto.R;

/**
 * 雷达
 */
public class RadarView extends FrameLayout {

    private ImageView radarLeft1;
    private ImageView radarLeft2;
    private ImageView radarLeft3;
    private ImageView radarLeft4;
    private ImageView radarLeft5;
    private ImageView radarLeft6;
    private ImageView radarLeft7;
    private ImageView radarRight1;
    private ImageView radarRight2;
    private ImageView radarRight3;
    private ImageView radarRight4;
    private ImageView radarRight5;
    private ImageView radarRight6;
    private ImageView radarRight7;
    private String currentLeftLevel = "0";
    private String currentRightLevel = "0";

    public RadarView(@NonNull Context context) {
        this(context, null);
    }

    public RadarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RadarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setupInit(context);
    }

    private void setupInit(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_treed_meter_radar_view, this, true);
        radarLeft1 = view.findViewById(R.id.radar_left_1);
        radarLeft2 = view.findViewById(R.id.radar_left_2);
        radarLeft3 = view.findViewById(R.id.radar_left_3);
        radarLeft4 = view.findViewById(R.id.radar_left_4);
        radarLeft5 = view.findViewById(R.id.radar_left_5);
        radarLeft6 = view.findViewById(R.id.radar_left_6);
        radarLeft7 = view.findViewById(R.id.radar_left_7);
        radarRight1 = view.findViewById(R.id.radar_right_1);
        radarRight2 = view.findViewById(R.id.radar_right_2);
        radarRight3 = view.findViewById(R.id.radar_right_3);
        radarRight4 = view.findViewById(R.id.radar_right_4);
        radarRight5 = view.findViewById(R.id.radar_right_5);
        radarRight6 = view.findViewById(R.id.radar_right_6);
        radarRight7 = view.findViewById(R.id.radar_right_7);
    }

    /**
     * @description: 设置前车左侧雷达的预警等级
     * @createDate: 2023/6/21
     */
    public void setLeftRadarLevel(String level) {
        currentLeftLevel = level;
        switch (level) {
            case "0":
            case "2":
                if ("0".equals(currentRightLevel) && "0".equals(currentLeftLevel)) {
                    RadarView.this.setVisibility(View.INVISIBLE);
                }
                RadarView.this.setVisibility(View.INVISIBLE);
                radarLeft1.setVisibility(View.INVISIBLE);
                radarLeft2.setVisibility(View.INVISIBLE);
                radarLeft3.setVisibility(View.INVISIBLE);
                radarLeft4.setVisibility(View.INVISIBLE);
                radarLeft5.setVisibility(View.INVISIBLE);
                radarLeft6.setVisibility(View.INVISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "3":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.INVISIBLE);
                radarLeft3.setVisibility(View.INVISIBLE);
                radarLeft4.setVisibility(View.INVISIBLE);
                radarLeft5.setVisibility(View.INVISIBLE);
                radarLeft6.setVisibility(View.INVISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "4":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.VISIBLE);
                radarLeft3.setVisibility(View.INVISIBLE);
                radarLeft4.setVisibility(View.INVISIBLE);
                radarLeft5.setVisibility(View.INVISIBLE);
                radarLeft6.setVisibility(View.INVISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "5":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.VISIBLE);
                radarLeft3.setVisibility(View.VISIBLE);
                radarLeft4.setVisibility(View.INVISIBLE);
                radarLeft5.setVisibility(View.INVISIBLE);
                radarLeft6.setVisibility(View.INVISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "6":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.VISIBLE);
                radarLeft3.setVisibility(View.VISIBLE);
                radarLeft4.setVisibility(View.VISIBLE);
                radarLeft5.setVisibility(View.INVISIBLE);
                radarLeft6.setVisibility(View.INVISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "7":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.VISIBLE);
                radarLeft3.setVisibility(View.VISIBLE);
                radarLeft4.setVisibility(View.VISIBLE);
                radarLeft5.setVisibility(View.VISIBLE);
                radarLeft6.setVisibility(View.INVISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "8":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.VISIBLE);
                radarLeft3.setVisibility(View.VISIBLE);
                radarLeft4.setVisibility(View.VISIBLE);
                radarLeft5.setVisibility(View.VISIBLE);
                radarLeft6.setVisibility(View.VISIBLE);
                radarLeft7.setVisibility(View.INVISIBLE);
                break;
            case "9":
                RadarView.this.setVisibility(View.VISIBLE);
                radarLeft1.setVisibility(View.VISIBLE);
                radarLeft2.setVisibility(View.VISIBLE);
                radarLeft3.setVisibility(View.VISIBLE);
                radarLeft4.setVisibility(View.VISIBLE);
                radarLeft5.setVisibility(View.VISIBLE);
                radarLeft6.setVisibility(View.VISIBLE);
                radarLeft7.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * @description: 设置前车左侧雷达的预警等级
     * @createDate: 2023/6/21
     */
    public void setRightRadarLevel(String level) {
        currentRightLevel = level;
        switch (level) {
            case "0":
            case "2":
                if ("0".equals(currentRightLevel) && "0".equals(currentLeftLevel)) {
                    RadarView.this.setVisibility(View.INVISIBLE);
                }
                radarRight1.setVisibility(View.INVISIBLE);
                radarRight2.setVisibility(View.INVISIBLE);
                radarRight3.setVisibility(View.INVISIBLE);
                radarRight4.setVisibility(View.INVISIBLE);
                radarRight5.setVisibility(View.INVISIBLE);
                radarRight6.setVisibility(View.INVISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "3":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.INVISIBLE);
                radarRight3.setVisibility(View.INVISIBLE);
                radarRight4.setVisibility(View.INVISIBLE);
                radarRight5.setVisibility(View.INVISIBLE);
                radarRight6.setVisibility(View.INVISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "4":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.VISIBLE);
                radarRight3.setVisibility(View.INVISIBLE);
                radarRight4.setVisibility(View.INVISIBLE);
                radarRight5.setVisibility(View.INVISIBLE);
                radarRight6.setVisibility(View.INVISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "5":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.VISIBLE);
                radarRight3.setVisibility(View.VISIBLE);
                radarRight4.setVisibility(View.INVISIBLE);
                radarRight5.setVisibility(View.INVISIBLE);
                radarRight6.setVisibility(View.INVISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "6":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.VISIBLE);
                radarRight3.setVisibility(View.VISIBLE);
                radarRight4.setVisibility(View.VISIBLE);
                radarRight5.setVisibility(View.INVISIBLE);
                radarRight6.setVisibility(View.INVISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "7":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.VISIBLE);
                radarRight3.setVisibility(View.VISIBLE);
                radarRight4.setVisibility(View.VISIBLE);
                radarRight5.setVisibility(View.VISIBLE);
                radarRight6.setVisibility(View.INVISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "8":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.VISIBLE);
                radarRight3.setVisibility(View.VISIBLE);
                radarRight4.setVisibility(View.VISIBLE);
                radarRight5.setVisibility(View.VISIBLE);
                radarRight6.setVisibility(View.VISIBLE);
                radarRight7.setVisibility(View.INVISIBLE);
                break;
            case "9":
                setVisibility(View.VISIBLE);
                radarRight1.setVisibility(View.VISIBLE);
                radarRight2.setVisibility(View.VISIBLE);
                radarRight3.setVisibility(View.VISIBLE);
                radarRight4.setVisibility(View.VISIBLE);
                radarRight5.setVisibility(View.VISIBLE);
                radarRight6.setVisibility(View.VISIBLE);
                radarRight7.setVisibility(View.VISIBLE);
                break;
        }
    }
}