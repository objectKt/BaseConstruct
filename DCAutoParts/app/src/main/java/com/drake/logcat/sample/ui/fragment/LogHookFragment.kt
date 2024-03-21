/*
 * Copyright (C) 2018 Drake, https://github.com/liangjingkanji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.drake.logcat.sample.ui.fragment

import com.drake.engine.base.EngineFragment
import dc.library.utils.logcat.LogCat
import dc.library.utils.logcat.LogHook
import dc.library.utils.logcat.LogInfo
import com.drake.logcat.sample.R
import com.drake.logcat.sample.databinding.FragmentLogHookBinding

class LogHookFragment : EngineFragment<FragmentLogHookBinding>(R.layout.fragment_log_hook) {

    override fun initView() {
        dc.library.utils.logcat.LogCat.addHook(object : dc.library.utils.logcat.LogHook {
            override fun hook(info: dc.library.utils.logcat.LogInfo) {
                // 拦截器是全局的, 所有日志都会经过于此
                // ... 上次或者保存日志
                if (info.msg == "需要被取消的日志") info.msg = null // 取消日志的输出
            }
        })
        binding.btnPrint.setOnClickListener {
            dc.library.utils.logcat.LogCat.w("需要被取消的日志") // 该日志被拦截器取消
        }
    }

    override fun initData() {
    }
}