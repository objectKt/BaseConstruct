package dc.library.ui.widget

import androidx.databinding.BindingAdapter

@BindingAdapter("checked")
fun dc.library.ui.widget.SmoothCheckBox.setCheckedBind(checked: Boolean) {
    this.isChecked = checked
}

@BindingAdapter("checked")
fun dc.library.ui.widget.SmoothCheckBox.setCheckedBind(checked: Any?) {
    this.isChecked = checked != null
}