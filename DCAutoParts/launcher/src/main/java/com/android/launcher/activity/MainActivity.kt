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
import com.amap.api.maps.offlinemap.OfflineMapManager.OfflineMapDownloadListener
import com.android.launcher.R
import com.android.launcher.can.util.AutoUtilCan
import com.android.launcher.fragment.DashboardFragment
import com.github.fragivity.loadRoot
import dc.library.auto.bus_usb.UsbDeviceConnectManager
import dc.library.auto.manager.ManagerTTLSerialPorts
import dc.library.utils.ValUtil
import dc.library.utils.logcat.LogCat
import dc.library.utils.serialize.intent.bundle

class MainActivity : BaseActivity(), OfflineMapDownloadListener {

    private val pageName: String by bundle()
    private val pageType: Int by bundle()

    private lateinit var viewModel: ViewModelMain
    private var mBroadcastReceiver: BroadcastReceiver? = null
    private val mUsbDeviceManager = UsbDeviceConnectManager.getInit(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_lefts223)
        LogCat.i("pageType = $pageType pageName = $pageName")
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idNavHostLeft223) as NavHostFragment
        navHostFragment.loadRoot(DashboardFragment::class)
    }

    // <editor-fold desc="# 处理方向盘下发的操作">

    override fun steerWheelKeyboard(type: AutoUtilCan.Type) {
        when (type) {
            AutoUtilCan.Type.UP -> {

            }

            AutoUtilCan.Type.DOWN -> {

            }

            AutoUtilCan.Type.LEFT -> {

            }

            AutoUtilCan.Type.RIGHT -> {}
            AutoUtilCan.Type.OK -> {}
            AutoUtilCan.Type.BACK -> {}
        }
    }

    // </editor-fold>

    override fun stateChangeLogic(event: Lifecycle.Event) {
        LogCat.d("生命周期 == 进入了 ${event.name}")
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                mBroadcastReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent) {
                        if (ValUtil.Action.INTENT_ACTION_GRANT_USB == intent.action) {
                            mUsbDeviceManager.mUsbPermission = if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
                                UsbDeviceConnectManager.UsbPermission.Granted else UsbDeviceConnectManager.UsbPermission.Denied
                            mUsbDeviceManager.connectUsb()
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

            Lifecycle.Event.ON_RESUME -> mUsbDeviceManager.resumeConnect()

            Lifecycle.Event.ON_PAUSE -> mUsbDeviceManager.pauseDisconnect()

            Lifecycle.Event.ON_STOP -> unregisterReceiver(mBroadcastReceiver);

            Lifecycle.Event.ON_DESTROY -> ManagerTTLSerialPorts.closeAllPorts()

            else -> {}
        }
    }

    override fun onNewIntent(intent: Intent) {
        if ("android.hardware.usb.action.USB_DEVICE_ATTACHED" == intent.action) {
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

    // <editor-fold desc="# 地图相关接口回调">

    // status: 0:正在下载， 4：下载成功， 1： 解压中
    override fun onDownload(status: Int, completeCode: Int, name: String) {
        LogCat.d("Map onDownload --- status=$status, completeCode=$completeCode, name=$name")
    }

    // newVersion - true 表示有更新，说明官方有新版或者本地未下载
    // name - 被检测更新的城市的名字
    override fun onCheckUpdate(newVersion: Boolean, name: String) {
        LogCat.d("Map onCheckUpdate --- newVersion=$newVersion, name=$name")
    }

    override fun onRemove(success: Boolean, name: String, describe: String) {
        LogCat.d("Map onRemove --- name=$name, describe=$describe")
    }

    // </editor-fold>
}