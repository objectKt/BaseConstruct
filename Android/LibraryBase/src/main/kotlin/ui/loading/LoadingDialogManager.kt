package ui.loading

import android.content.Context

interface LoadingDialogManager {

    val loadingDialog: LoadingDialog

    fun startLoadingDialog(context: Context, message: String = "正在加载", cancelable: Boolean = true) {
        endLoadingDialog()
        loadingDialog.showDialog(context, message, cancelable, null)
    }

    fun endLoadingDialog(tag: String = "") {
        loadingDialog.dismissDialog()
    }
}