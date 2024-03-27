package com.android.demos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView

class DemoActivity : AppCompatActivity() {

    private lateinit var mAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val cards = (1..10).toList() // 假设有10张卡片

        // 创建Adapter
        mAdapter = CardAdapter(cards)

        // 设置ViewPager2的Adapter
        viewPager.adapter = mAdapter

        // 设置布局管理器，这里使用 LinearLayoutManager 作为示例
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 添加页面变换监听器，用于处理选中效果
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 处理选中效果
                updateCardElevation(position)
            }
        })
    }

    private fun updateCardElevation(position: Int) {
        // 获取当前页面的卡片
        val cardView = mAdapter.getViewHolderAtPosition(position)?.itemView as? MaterialCardView
        // 设置卡片的 elevation 属性，增加立体感
        cardView?.elevation = 8f

        // 获取中间位置的卡片
        val middlePage = mAdapter.getViewHolderAtPosition(mAdapter.itemCount / 2) ?: return
        // 设置中间卡片的 elevation 属性，增加投影效果
        (middlePage.itemView as MaterialCardView).elevation = 12f
    }
}