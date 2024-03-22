package com.drake.logcat.sample.ui.fragment

import dc.library.ui.base.EngineFragment
import dc.library.utils.logcat.LogCat
import dc.library.utils.logcat.LogHook
import dc.library.utils.logcat.LogInfo
import com.drake.logcat.sample.R
import com.drake.logcat.sample.databinding.FragmentLogHookBinding

class LogHookFragment : EngineFragment<FragmentLogHookBinding>(R.layout.fragment_log_hook) {

    override fun initView() {
        LogCat.addHook(object : LogHook {
            override fun hook(info: LogInfo) {
                // 拦截器是全局的, 所有日志都会经过于此
                // ... 上次或者保存日志
                if (info.msg == "需要被取消的日志") info.msg = null // 取消日志的输出
            }
        })
        binding.btnPrint.setOnClickListener {
            LogCat.w("需要被取消的日志") // 该日志被拦截器取消
        }
    }

    override fun initData() {
    }
}