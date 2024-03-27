package com.android.demos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class DemoActivity : AppCompatActivity() {

    private lateinit var mAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val cards = arrayOf(R.mipmap.ic_menu_bg, R.mipmap.ic_menu_sport, R.mipmap.ic_menu_bg, R.mipmap.ic_menu_sport, R.mipmap.ic_menu_bg, R.mipmap.ic_menu_sport) // 假设有10张卡片
        mAdapter = CardAdapter(cards)
        viewPager.adapter = mAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.setPageTransformer { page, position ->
            MultiPageTransformer()
//            val scale = 1 - abs(position)
//            val alpha = (scale * 255).toInt()
//            page.scaleX = scale
//            page.scaleY = scale
//            page.alpha = alpha / 255.0f
//            val scale = 1 - abs(position)
//            val alpha = (scale * 255).toInt()
//            page.scaleX = scale
//            page.scaleY = scale
//            page.alpha = alpha / 255.0f
//
//            // 如果页面在可见阈值之内，则显示
//            if (abs(position) <= 1.5f) {
//                page.visibility = View.VISIBLE
//            } else {
//                page.visibility = View.INVISIBLE
//            }
        }
        // 设置预加载页面的数量
        viewPager.offscreenPageLimit = 2
        // 添加页面变换监听器，用于处理选中效果
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 处理选中效果
                //updateCardElevation(position)
            }
        })
    }

    private fun updateCardElevation(position: Int) {
        // 获取当前页面的卡片
        val cardView = mAdapter.getViewHolderAtPosition(position)?.itemView as? CardView
        // 设置卡片的 elevation 属性，增加立体感
        cardView?.elevation = 8f
        // 获取中间位置的卡片
        val middlePage = mAdapter.getViewHolderAtPosition(mAdapter.itemCount / 2) ?: return
        // 设置中间卡片的 elevation 属性，增加投影效果
        (middlePage.itemView as CardView).elevation = 12f
    }
}

class MultiPageTransformer : ViewPager2.PageTransformer {
    private val NUM_PAGES = 5 // 同时显示的页面数量

    override fun transformPage(page: View, position: Float) {
        val scale: Float = 1 - abs(position) * 0.3f // 缩放比例
        val alpha: Float = (scale * 255).toInt() / 255.0f // 透明度

        // 设置页面的缩放
        page.scaleX = scale
        page.scaleY = scale

        // 设置页面的透明度
        page.alpha = alpha

        // 根据位置调整页面的 X 坐标，使其偏移屏幕
        val offset = (position * page.width * (1 - scale)).toInt()
        page.translationX = offset.toFloat()

        // 如果页面完全在屏幕左侧或右侧，设置其不可见
        if (position.coerceIn(-1f, 1f) < 0) {
            page.visibility = View.INVISIBLE
        } else {
            page.visibility = View.VISIBLE
        }
    }
}