package com.android.devlibrarys.demos

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import dc.android.libraries.R
import dc.android.libraries.adapter.CardAdapter
import dc.android.libraries.controller.Ctrl
import dc.android.libraries.util.DensityUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ViewPager2DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.android.devlibrarys.R.layout.activity_view_pager)
        initViewPager()
    }

    private fun initViewPager() {
        val cardAdapter = CardAdapter(arrayOf(R.mipmap.ic_menu_classic, R.mipmap.ic_menu_sport, R.mipmap.ic_menu_tech, R.mipmap.ic_menu_map, R.mipmap.ic_menu_maintain))
        val viewPager = findViewById<ViewPager2>(com.android.devlibrarys.R.id.view_pager)
        val padding = (DensityUtil.getScreenWidth(this) / 2.65f).roundToInt()
        Ctrl.Functions.initViewPagerMenu(cardAdapter, viewPager, viewPager.getChildAt(0), padding) { page, pos ->
            val anim = if (pos == 0F) R.anim.scale_anim_menu_bigger else R.anim.scale_anim_menu_smaller
            AnimationUtils.loadAnimation(this, anim).apply {
                this.interpolator = AnticipateInterpolator()
                page.startAnimation(this)
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            viewPager.setCurrentItem(3, true)
        }
    }
}