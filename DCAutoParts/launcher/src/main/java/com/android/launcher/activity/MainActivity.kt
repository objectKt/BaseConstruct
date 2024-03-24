package com.android.launcher.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.proxyFragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.android.launcher.R
import com.android.launcher.base.BaseActivity
import com.android.launcher.fragment.DashboardFragment
import com.github.fragivity.loadRoot
import dc.library.auto.bus_usb.UsbDeviceConnectManager
import dc.library.auto.manager.TTLSerialPortsManager
import dc.library.utils.ValUtil
import dc.library.utils.logcat.LogCat

class MainActivity : BaseActivity() {

    private lateinit var viewModel: ViewModelMain
    private var mBroadcastReceiver: BroadcastReceiver? = null
    private val usbDeviceManager = UsbDeviceConnectManager.getInit(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_lefts223)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idNavHostLeft223) as NavHostFragment
        navHostFragment.loadRoot(DashboardFragment::class)
    }

    override fun stateChangeLogic(event: Lifecycle.Event) {
        LogCat.d("生命周期 == 进入了 ${event.name}")
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                mBroadcastReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent) {
                        if (ValUtil.Action.INTENT_ACTION_GRANT_USB == intent.action) {
                            usbDeviceManager.mUsbPermission = if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
                                UsbDeviceConnectManager.UsbPermission.Granted else UsbDeviceConnectManager.UsbPermission.Denied
                            usbDeviceManager.connectUsb()
                        }
                    }
                }
            }

            Lifecycle.Event.ON_START -> ContextCompat.registerReceiver(
                this@MainActivity,
                mBroadcastReceiver,
                IntentFilter(ValUtil.Action.INTENT_ACTION_GRANT_USB),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )

            Lifecycle.Event.ON_RESUME -> usbDeviceManager.resumeConnect()

            Lifecycle.Event.ON_PAUSE -> {
                usbDeviceManager.pauseDisconnect()
            }

            Lifecycle.Event.ON_STOP -> unregisterReceiver(mBroadcastReceiver);

            Lifecycle.Event.ON_DESTROY -> TTLSerialPortsManager.closeAllPorts()
            else -> {}
        }
    }

    override fun onNewIntent(intent: Intent) {
        if ("android.hardware.usb.action.USB_DEVICE_ATTACHED" == intent.action) {
            // 通知 USB 管理器是否已经检测到 USB 设备
            LogCat.w("USB device detected")
        }
        super.onNewIntent(intent)
    }

    private fun doViewModelLogic() {
        viewModel = ViewModelProvider(this)[ViewModelMain::class.java]
        viewModel.loginStatus.observe(this) {
            LogCat.i("observe loginStatus $it")
        }
    }
}