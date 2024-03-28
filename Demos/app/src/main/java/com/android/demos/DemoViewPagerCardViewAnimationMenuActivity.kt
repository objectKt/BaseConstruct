package com.android.demos

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.launcher.librarys.controller.Ctrl
import com.android.launcher.librarys.adapter.CardAdapter
import com.android.launcher.librarys.util.DensityUtil
import kotlin.math.roundToInt

class DemoViewPagerCardViewAnimationMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        initViewPager()
    }

    private fun initViewPager() {
        val cardAdapter =
            com.android.launcher.librarys.adapter.CardAdapter(arrayOf(R.mipmap.ic_menu_classic, R.mipmap.ic_menu_sport, R.mipmap.ic_menu_tech, R.mipmap.ic_menu_map, R.mipmap.ic_menu_maintain))
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val padding = (com.android.launcher.librarys.util.DensityUtil.getScreenWidth(this) / 2.65f).roundToInt()
        com.android.launcher.librarys.controller.Ctrl.Functions.initViewPagerMenu(cardAdapter, viewPager, viewPager.getChildAt(0), padding) { page, pos ->
            val anim = if (pos == 0F) R.anim.scale_anim_menu_bigger else R.anim.scale_anim_menu_smaller
            AnimationUtils.loadAnimation(this, anim).apply {
                this.interpolator = AnticipateInterpolator()
                page.startAnimation(this)
            }
        }
    }
}