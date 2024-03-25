package com.android.launcher.activity

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import com.android.launcher.can.util.AutoUtilCan
import com.drake.channel.receiveEvent
import com.drake.net.time.Interval
import com.permissionx.guolindev.PermissionX
import dc.library.auto.event.EventModel
import dc.library.auto.event.EventTag
import dc.library.auto.init.AsyncStepHandlePermissions
import dc.library.auto.init.SyncStepFindUsb
import dc.library.auto.task.XTask
import dc.library.auto.task.api.step.ConcurrentGroupTaskStep
import dc.library.auto.task.core.ITaskChainEngine
import dc.library.auto.task.core.param.ITaskResult
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter
import dc.library.auto.task.core.step.impl.TaskCommand
import dc.library.auto.task.thread.pool.cancel.ICanceller
import dc.library.utils.logcat.LogCat
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit

/**
 * 启动页面（奔驰 LOGO 欢迎页面）
 * 同时处理部分模块的初始化
 * @author hf
 */
class StartingActivity : BaseActivity() {

    private var mTaskIsFinishSucceed: Boolean = false
    private var mTaskCancel: ICanceller? = null

    override fun stateChangeLogic(event: Lifecycle.Event) {
        LogCat.d("生命周期 == 进入了 ${event.name}")
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                receiveEvent<EventModel>(EventTag.BLUETOOTH_PERMISSION_HANDLE) {
                    LogCat.i("收到事件：请求权限")
                    handleBluetoothPermission()
                }
            }

            Lifecycle.Event.ON_START -> startSomeInitTask()

            else -> {}
        }
    }

    // do not need do anything
    override fun steerWheelKeyboard(type: AutoUtilCan.Type) {}

    private fun startSomeInitTask() {
        val groupTaskStep = XTask.getConcurrentGroupTask().apply {
            addTask("任务:初始化常用全局数据量")
            addTask("任务:初始化声音播放器")
        }
        val engine = XTask.getTaskChain()
        engine.addTask(SyncStepFindUsb())
        engine.addTask(groupTaskStep)
        //engine.addTask(AsyncStepSerialPort())
        engine.addTask(AsyncStepHandlePermissions())
        engine.setTaskChainCallback(taskChainCallback(System.currentTimeMillis()))
        mTaskCancel = engine.start()
    }

    private fun taskChainCallback(timestampBegin: Long) = object : TaskChainCallbackAdapter() {

        override fun onTaskChainStart(engine: ITaskChainEngine) {
            mTaskIsFinishSucceed = false
            LogCat.i("=== begin")
        }

        override fun onTaskChainCompleted(engine: ITaskChainEngine, result: ITaskResult) {
            mTaskCancel?.cancel()
            mTaskIsFinishSucceed = true
            LogCat.i("=== finish 总共耗时: ${System.currentTimeMillis() - timestampBegin} ms")
        }

        override fun onTaskChainError(engine: ITaskChainEngine, result: ITaskResult) {
            mTaskIsFinishSucceed = false
            LogCat.i("=== 任务链失败 -- Error 总共耗时: ${System.currentTimeMillis() - timestampBegin} ms")
            mTaskCancel?.cancel()
        }
    }

    private fun gotoMainActivity() {
        val interval = Interval(10, 1, TimeUnit.SECONDS).life(this)
        interval.subscribe {
            if (mTaskIsFinishSucceed) {
                this.stop()
            }
        }.finish {
            if (mTaskIsFinishSucceed) {
                val intent = Intent(this@StartingActivity, MainActivity::class.java)
                startActivity(intent)
                this@StartingActivity.finish()
            } else {
                restartApp(this@StartingActivity)
            }
        }.start()
    }

    private fun ConcurrentGroupTaskStep.addTask(des: String = "") {
        addTask(XTask.getTask(object : TaskCommand() {
            override fun run() {
                Thread.sleep(500)
            }
        }).apply { name = des })
    }

    private fun handleBluetoothPermission() {
        val bluetoothManager: BluetoothManager = this@StartingActivity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        bluetoothAdapter?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // 先处理权限授权再进行蓝牙禁用
                val permission = PermissionX.init(this@StartingActivity)
                permission.permissions(Manifest.permission.BLUETOOTH_CONNECT)
                    .onExplainRequestReason { scope, deniedList ->
                        scope.showRequestReasonDialog(deniedList, "需要授权蓝牙控制权限", "授权", "取消")
                    }
                    .request { allGranted, _, deniedList ->
                        if (allGranted) {
                            if (bluetoothAdapter.isEnabled) {
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                                    LogCat.i("正在关闭蓝牙")
                                    @Suppress("DEPRECATION")
                                    bluetoothAdapter.disable()
                                    gotoMainActivity()
                                }
                            } else {
                                LogCat.i("设备蓝牙已处于禁用状态")
                                gotoMainActivity()
                            }
                        } else {
                            LogCat.e("警告：被用户禁止了的权限: $deniedList")
                            restartApp(this@StartingActivity)
                        }
                    }
            } else {
                // 设备版本低，不需要权限
                if (bluetoothAdapter.isEnabled) {
                    @Suppress("DEPRECATION")
                    bluetoothAdapter.disable()
                    gotoMainActivity()
                } else {
                    LogCat.i("设备蓝牙已处于禁用状态")
                    gotoMainActivity()
                }
            }
        } ?: {
            LogCat.e("本机本身已经不支持蓝牙！")
            gotoMainActivity()
        }
    }
}
/* 有 USB 设备 + 权限畅通的运行日志
---------------------------- PROCESS STARTED (14254) for package com.android.launcher ----------------------------
11:41:21.614  D  生命周期 == 进入了 ON_CREATE ...(StartingActivity.kt:44)
11:41:21.768  D  生命周期 == 进入了 ON_START ...(StartingActivity.kt:44)
11:41:21.784  I  === begin ...(StartingActivity.kt:86)
11:41:21.785  I  === SYNC --- 开始 任务:查找 USB 接口设备 ...(null:7)
11:41:21.807  I  USB Devices : Ch34x ...(UsbDevicesFinder.kt:48)
11:41:21.820  I  === ASYNC --- 开始 任务:初始化声音播放器 ...(null:7)
11:41:21.821  I  === ASYNC --- 开始 任务:初始化常用全局数据量 ...(null:7)
11:41:21.876  D  生命周期 == 进入了 ON_RESUME ...(StartingActivity.kt:44)
11:41:22.338  I  === ASYNC --- 开始 任务:初始化串口 TTL ...(null:7)
11:41:22.486  I  === ASYNC --- 开始 任务:处理必要的权限 ...(null:7)
11:41:22.490  I  收到事件：请求权限 ...(StartingActivity.kt:48)
11:41:22.495  I  设备蓝牙已处于禁用状态 ...(StartingActivity.kt:176)
11:41:22.507  I  === finish 总共耗时: 725 ms ...(StartingActivity.kt:92)
11:41:22.549  D  生命周期 == 进入了 ON_PAUSE ...(StartingActivity.kt:44)
11:41:22.957  D  生命周期 == 进入了 ON_CREATE ...(MainActivity.kt:38)
11:41:23.073  I  进入了 DashboardFragment ...(DashboardFragment.kt:23)
11:41:23.160  D  生命周期 == 进入了 ON_START ...(MainActivity.kt:38)
11:41:23.174  D  生命周期 == 进入了 ON_RESUME ...(MainActivity.kt:38)
11:41:23.209  W  USB init Auth /dev/bus/usb/002/002 ...(UsbDeviceConnectManager.kt:143)
11:41:23.508  D  生命周期 == 进入了 ON_PAUSE ...(MainActivity.kt:38)
11:41:23.673  D  生命周期 == 进入了 ON_RESUME ...(MainActivity.kt:38)
11:41:23.742  D  生命周期 == 进入了 ON_STOP ...(StartingActivity.kt:44)
11:41:23.763  D  生命周期 == 进入了 ON_DESTROY ...(StartingActivity.kt:44)
11:41:23.764  I  清除事件：请求蓝牙权限 ...(StartingActivity.kt:59)
11:41:23.827  I  --- USB connected --- ...(UsbDeviceConnectManager.kt:166)
11:41:23.837  I  writeThread port = <Ch340SerialPort device_name=/dev/bus/usb/002/002 device_id=2002 port_number=0> && port.isOpen = true ...(UsbDeviceConnectManager.kt:69)
 */

/* 无 USB 设备 + 被用户禁止了的权限的运行日志
---------------------------- PROCESS STARTED (7560) for package com.android.launcher ----------------------------
10:53:59.550  D  生命周期 == 进入了 ON_CREATE ...(StartingActivity.kt:44)
10:53:59.734  D  生命周期 == 进入了 ON_START ...(StartingActivity.kt:44)
10:53:59.781  I  === begin ...(StartingActivity.kt:86)
10:53:59.782  I  === SYNC --- 开始 任务:查找 USB 接口设备 ...(null:7)
10:53:59.808  E  <no USB devices found> ...(UsbDevicesFinder.kt:35)
10:53:59.848  I  === ASYNC --- 开始 任务:初始化常用全局数据量 ...(null:7)
10:53:59.857  I  === ASYNC --- 开始 任务:初始化声音播放器 ...(null:7)
10:53:59.934  D  生命周期 == 进入了 ON_RESUME ...(StartingActivity.kt:44)
10:54:00.439  I  === ASYNC --- 开始 任务:初始化串口 TTL ...(null:7)
10:54:01.446  I  === ASYNC --- 开始 任务:处理必要的权限 ...(null:7)
10:54:02.941  I  收到事件：请求权限 ...(StartingActivity.kt:48)
10:54:03.066  I  === finish 总共耗时: 3286 ms ...(StartingActivity.kt:92)
10:54:03.074  D  生命周期 == 进入了 ON_PAUSE ...(StartingActivity.kt:44)
10:55:46.615  D  生命周期 == 进入了 ON_RESUME ...(StartingActivity.kt:44)
10:55:55.006  E  警告：被用户禁止了的权限: [android.permission.BLUETOOTH_CONNECT] ...(StartingActivity.kt:165)
10:55:55.011  E  应用重启了 --- StartingActivity 触发 ...(StartingActivity.kt:128)
10:55:55.603  D  生命周期 == 进入了 ON_PAUSE ...(StartingActivity.kt:44)
10:55:56.318  D  生命周期 == 进入了 ON_CREATE ...(StartingActivity.kt:44)
10:55:56.334  D  生命周期 == 进入了 ON_START ...(StartingActivity.kt:44)
10:55:56.340  I  === begin ...(StartingActivity.kt:86)
10:55:56.341  I  === SYNC --- 开始 任务:查找 USB 接口设备 ...(null:7)
10:55:56.350  E  <no USB devices found> ...(UsbDevicesFinder.kt:35)
10:55:56.357  I  === ASYNC --- 开始 任务:初始化常用全局数据量 ...(null:7)
10:55:56.377  I  === ASYNC --- 开始 任务:初始化声音播放器 ...(null:7)
10:55:56.403  D  生命周期 == 进入了 ON_RESUME ...(StartingActivity.kt:44)
10:55:56.886  I  === ASYNC --- 开始 任务:初始化串口 TTL ...(null:7)
10:55:57.888  I  === ASYNC --- 开始 任务:处理必要的权限 ...(null:7)
10:55:57.956  I  收到事件：请求权限 ...(StartingActivity.kt:48)
10:55:58.117  I  收到事件：请求权限 ...(StartingActivity.kt:48)
10:55:58.180  I  === finish 总共耗时: 1845 ms ...(StartingActivity.kt:92)
10:55:58.231  D  生命周期 == 进入了 ON_PAUSE ...(StartingActivity.kt:44)
10:55:58.321  D  生命周期 == 进入了 ON_STOP ...(StartingActivity.kt:44)
10:55:58.365  I  清除事件：请求蓝牙权限 ...(StartingActivity.kt:59)
10:55:58.592  D  生命周期 == 进入了 ON_DESTROY ...(StartingActivity.kt:44)
10:56:02.973  D  生命周期 == 进入了 ON_RESUME ...(StartingActivity.kt:44)
10:56:02.991  I  正在关闭蓝牙 ...(StartingActivity.kt:155)
10:56:03.303  D  生命周期 == 进入了 ON_PAUSE ...(StartingActivity.kt:44)
10:56:04.509  D  生命周期 == 进入了 ON_CREATE ...(MainActivity.kt:38)
10:56:04.630  I  进入了 DashboardFragment ...(DashboardFragment.kt:23)
10:56:04.751  D  生命周期 == 进入了 ON_START ...(MainActivity.kt:38)
10:56:04.763  D  生命周期 == 进入了 ON_RESUME ...(MainActivity.kt:38)
10:56:04.764  E  connectUsb() mDeviceItem null <no USB devices found> ...(UsbDeviceConnectManager.kt:78)
10:56:07.292  D  生命周期 == 进入了 ON_STOP ...(StartingActivity.kt:44)
10:56:07.294  I  清除事件：请求蓝牙权限 ...(StartingActivity.kt:59)
10:56:07.313  D  生命周期 == 进入了 ON_DESTROY ...(StartingActivity.kt:44)
 */