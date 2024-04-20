//package lib.dc
//
//import android.app.Dialog
//import android.content.Context
//import android.content.DialogInterface
//import android.text.TextUtils
//import android.view.Gravity
//import android.view.View
//import android.widget.TextView
//import androidx.appcompat.widget.AppCompatTextView
//import com.github.ybq.android.spinkit.SpinKitView
//import com.github.ybq.android.spinkit.style.Wave/**/
///**/
//class TaskUILoadingDialog : Dialog {
//
//    lateinit var dialog: TaskUILoadingDialog
//
//    constructor(context: Context) : super(context)
//
//    constructor(context: Context, theme: Int) : super(context, theme)
//
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        val loadingBar = findViewById<SpinKitView>(R.id.taskLoadingWave)
//        loadingBar.setIndeterminateDrawable(Wave())
//        loadingBar.visibility = View.VISIBLE
//        super.onWindowFocusChanged(hasFocus)
//    }
//
//    fun showDialog(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): TaskUILoadingDialog {
//        return showDialog(context, null, "正在加載中", cancelable, cancelListener)
//    }
//
//    fun showDialog(context: Context, title: CharSequence?, content: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): TaskUILoadingDialog {
//        dialog = TaskUILoadingDialog(context, R.style.TaskUILoadingDialog)
//        dialog.setContentView(R.layout.task_ui_loading)
//        val titleView = dialog.findViewById(R.id.taskTitle) as AppCompatTextView
//        val contentView = dialog.findViewById(R.id.taskContent) as AppCompatTextView
//        if (!TextUtils.isEmpty(title)) {
//            titleView.text = title
//        } else {
//            titleView.visibility = View.GONE
//        }
//        if (!TextUtils.isEmpty(content)) {
//            contentView.text = content
//        } else {
//            contentView.visibility = View.GONE
//        }
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.setCancelable(cancelable)
//        dialog.setOnCancelListener(cancelListener)
//        if (dialog.window != null) {
//            dialog.window!!.attributes.gravity = Gravity.CENTER
////            val lp = dialog.window!!.attributes
////            lp.dimAmount = 0.2f
////            dialog.window!!.attributes = lp
//        }
//        dialog.show()
//        return dialog
//    }
//
//    fun setTaskContent(msg: String) {
//        (dialog.findViewById(R.id.taskContent) as TextView).text = msg
//    }
//
//    fun dismissDialog() {
//        if (::dialog.isInitialized && dialog.isShowing)
//            dialog.dismiss()
//    }
//}