package dc.library.utils;

import android.graphics.PorterDuff;
import android.widget.ImageView;

public class IconUtils {

    /**
     * @param color 0：表示icon本来的颜色
     */
    public static void setColor(ImageView imageView, int color) {
        if (color == 0) {
            imageView.setColorFilter(0);
        } else {
            imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}