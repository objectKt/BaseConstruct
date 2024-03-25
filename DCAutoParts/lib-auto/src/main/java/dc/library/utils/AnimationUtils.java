package dc.library.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class AnimationUtils {

    /**
     * 闪烁动画
     */
    public static void startBlinkAnim(View view) {
        view.clearAnimation();
        int duration = 1500; // 动画持续时间
        int repeatCount = Animation.INFINITE; // 无限循环
        float fromAlpha = 1f; // 动画开始时的透明度
        float toAlpha = 0f; // 动画结束时的透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setRepeatCount(repeatCount);
        view.startAnimation(alphaAnimation);
    }
}