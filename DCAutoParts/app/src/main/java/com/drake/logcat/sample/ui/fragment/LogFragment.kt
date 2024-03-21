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

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.engine.base.EngineFragment
import dc.library.utils.logcat.LogCat
import com.drake.logcat.sample.R
import com.drake.logcat.sample.databinding.FragmentLogBinding

class LogFragment : EngineFragment<FragmentLogBinding>(R.layout.fragment_log) {

    override fun initView() {
        setHasOptionsMenu(true)
    }

    override fun initData() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_log, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_verbose -> dc.library.utils.logcat.LogCat.v("Verbose")
            R.id.menu_debug -> dc.library.utils.logcat.LogCat.d("Debug")
            R.id.menu_info -> dc.library.utils.logcat.LogCat.i("Info")
            R.id.menu_warn -> dc.library.utils.logcat.LogCat.w("Warn")
            R.id.menu_error -> dc.library.utils.logcat.LogCat.e("Error")
            R.id.menu_wtf -> dc.library.utils.logcat.LogCat.wtf("Assert")
            R.id.menu_json -> dc.library.utils.logcat.LogCat.json(getString(R.string.json))
        }
        return super.onOptionsItemSelected(item)
    }

}