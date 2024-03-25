package dc.library.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 跑马灯效果
 */
class MarqueeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        ellipsize = TextUtils.TruncateAt.MARQUEE
        isSingleLine = true
    }

    override fun isFocused(): Boolean {
        return true
    }
}