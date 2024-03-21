package com.drake.engine.base

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class EngineDialog<B : ViewDataBinding>(context: Context, @StyleRes themeResId: Int = 0) : Dialog(context, themeResId), OnClickListener {

    lateinit var binding: B

    override fun setContentView(layoutResID: Int) {
        binding = DataBindingUtil.inflate(layoutInflater, layoutResID, null, false)
        setContentView(binding.root)
        init()
    }

    open fun init() {
        try {
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("Engine", "Initializing failure", e)
        }
    }

    protected abstract fun initView()
    protected abstract fun initData()
    override fun onClick(v: View) {}
}