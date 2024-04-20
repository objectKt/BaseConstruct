//package lib.dc
//
//import android.content.Context
//
//interface TaskUILoadingDialogManager {
//
//    val taskUILoading: TaskUILoadingDialog
//
//    fun startTaskLoading(context: Context, title: String = "正在執行的任務", content: String = "", cancelable: Boolean = true) {
//        endTaskLoading()
//        taskUILoading.showDialog(context, title, content, cancelable, null)
//    }
//
//    fun endTaskLoading(tag: String = "") {
//        taskUILoading.dismissDialog()
//    }
//}