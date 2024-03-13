package com.android.launcher.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.launcher.MainActivity
import dc.library.auto.global.ConstVal

/**
 * 实现本应用开机自启动
 */
class AutoStartReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action.equals(ConstVal.IntentAction.BOOT_COMPLETED)) {
                context?.let { ctx ->
                    val startIntent = Intent(ctx, MainActivity::class.java)
                    startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ctx.startActivity(startIntent)
                }
            }
        }
    }
}