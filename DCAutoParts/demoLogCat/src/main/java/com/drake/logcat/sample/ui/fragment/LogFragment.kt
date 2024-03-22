package com.drake.logcat.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dc.library.ui.base.EngineFragment
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
            R.id.menu_verbose -> LogCat.v("Verbose")
            R.id.menu_debug -> LogCat.d("Debug")
            R.id.menu_info -> LogCat.i("Info")
            R.id.menu_warn -> LogCat.w("Warn")
            R.id.menu_error -> LogCat.e("Error")
            R.id.menu_wtf -> LogCat.wtf("Assert")
            R.id.menu_json -> LogCat.json(getString(R.string.json))
        }
        return super.onOptionsItemSelected(item)
    }

}