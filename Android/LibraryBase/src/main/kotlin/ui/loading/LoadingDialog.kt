package ui.loading

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.drake.engine.R
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.Wave

class LoadingDialog : Dialog {

    private lateinit var dialog: LoadingDialog

    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val loadingBar = findViewById<SpinKitView>(R.id.loadingBar)
        loadingBar.setIndeterminateDrawable(Wave())
        loadingBar.visibility = View.VISIBLE
        super.onWindowFocusChanged(hasFocus)
    }

    fun showDialog(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): LoadingDialog {
        return showDialog(context, null, cancelable, cancelListener)
    }

    fun showDialog(context: Context, message: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): LoadingDialog {
        dialog = LoadingDialog(context, R.style.LoadingDialog)
        dialog.setContentView(R.layout.dialog_loading)
        val m = dialog.findViewById(R.id.message) as TextView
        if (!TextUtils.isEmpty(message)) {
            m.text = message
        } else {
            m.visibility = View.GONE
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(cancelable)
        dialog.setOnCancelListener(cancelListener)
        if (dialog.window != null) {
            dialog.window!!.attributes.gravity = Gravity.CENTER
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.2f
            dialog.window!!.attributes = lp
        }
        dialog.show()
        return dialog
    }

    fun setText(msg: String) {
        (dialog.findViewById(R.id.message) as TextView).text = msg
    }

    fun dismissDialog() {
        if (::dialog.isInitialized && dialog.isShowing)
            dialog.dismiss()
    }
}