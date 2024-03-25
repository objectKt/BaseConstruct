package dc.library.ui.base

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class EngineBottomSheetDialogFragment<B : ViewDataBinding> : BottomSheetDialogFragment(), OnClickListener {

    val binding: B
        get() {
            return DataBindingUtil.bind(requireView())!!
        }

    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    lateinit var rootView: FrameLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DataBindingUtil.bind<B>(view)
        rootView = dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        behavior = BottomSheetBehavior.from(rootView)

        try {
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("Engine", "Initializing failure", e)
        }
    }

    /**
     * 设置背景透明
     */
    fun setBackgroundTransparent() {
        rootView.setBackgroundColor(Color.TRANSPARENT)
    }

    abstract fun initView()
    abstract fun initData()
    override fun onClick(v: View) {}

    var onDismissListener: DialogInterface.OnDismissListener? = null
    var onCancelListener: DialogInterface.OnCancelListener? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener?.onCancel(dialog)
    }
}
