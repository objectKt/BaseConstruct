package com.android.launcher.base

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.permissionx.guolindev.PermissionX
import dc.library.utils.logcat.LogCat

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun stateChangeLogic(event: Lifecycle.Event)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                stateChangeLogic(event)
            }
        })
    }

    fun handleBluetoothPermission(bluetoothAdapter: BluetoothAdapter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permission = PermissionX.init(this)
            permission.permissions(Manifest.permission.BLUETOOTH_CONNECT)
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(deniedList, "需要授权蓝牙控制权限", "授权", "取消")
                }
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        LogCat.i("关闭蓝牙")
                        if (bluetoothAdapter.isEnabled) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                                @Suppress("DEPRECATION")
                                bluetoothAdapter.disable()
                            }
                        }
                    } else {
                        LogCat.e("被禁止了的权限: $deniedList")
                    }
                }
        } else {
            if (bluetoothAdapter.isEnabled) {
                @Suppress("DEPRECATION")
                bluetoothAdapter.disable()
            }
        }
    }
}