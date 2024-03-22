package dc.library.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.drake.engine.R

/**
 * 默认禁言点击改变选中状态, 使用[filter]或者[checkable]配置是否可选中
 */
class FilterCheckBox : AppCompatCheckBox {

    var checkable = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FilterCheckBox)
        checkable = attributes.getBoolean(R.styleable.FilterCheckBox_filter_checkable, false)
        attributes.recycle()
    }

    private var filter: FilterCheckBox.() -> Boolean = { checkable }

    fun filter(block: FilterCheckBox.() -> Boolean) {
        this.filter = block
    }

    override fun toggle() {
        if (filter(this)) {
            super.toggle()
        }
    }
}