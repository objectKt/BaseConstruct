package com.android.demos

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class DemoActivity : AppCompatActivity() {

    private lateinit var mAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val cards = arrayOf(R.mipmap.ic_menu_bg, R.mipmap.ic_menu_sport, R.mipmap.ic_menu_bg, R.mipmap.ic_menu_sport) // 假设有10张卡片
        mAdapter = CardAdapter(cards)
        viewPager.adapter = mAdapter
        viewPager.offscreenPageLimit = 5
        viewPager.setPageTransformer { page, position ->
            val transform = if (position == 0F) {
                AnimationUtils.loadAnimation(this, R.anim.scale_anim_menu_bigger)
            } else {
                AnimationUtils.loadAnimation(this, R.anim.scale_anim_menu_smaller)
            }
            transform.interpolator = AnticipateInterpolator()
            page.startAnimation(transform)
        }

        // 定义一个ValueAnimator来控制CardView的动画

        // 添加页面变换监听器，用于处理选中效果
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            private lateinit var cardView: CardView
            private lateinit var borderFlashAnimator: ValueAnimator

            // 处理选中效果
            override fun onPageSelected(position: Int) {
                val cardView = findViewById<CardView>(R.id.idCardView)
//                // 获取当前页面的卡片
//                val cardView = mAdapter.getViewHolderAtPosition(position)?.itemView as? CardView
//                val borderColorStateList = ContextCompat.getColorStateList(this@DemoActivity, R.color.border_flash_color)
//                cardView?.setCardBackgroundColor(borderColorStateList)
                // 定义边框闪烁的动画
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                // 根据滑动状态来控制动画
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                    }

                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                    }

                    ViewPager2.SCROLL_STATE_SETTLING -> {
                    }
                }
            }
        })


        val padding = (DensityUtil.getScreenWidth(this) / 2.65f).toInt()

        //一屏多页
        val recyclerView: View = viewPager.getChildAt(0)
        if (recyclerView is RecyclerView) {
            recyclerView.setPadding(padding, 0, padding, 0)
            recyclerView.setClipToPadding(false)
        }


//        menuAdapter.setOnItemClickListener((adapter, view, position) -> {
//            currentPosition = position;
//            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_FRAGMENT);
//            messageEvent.data = FragmentType.CLASSIC.getValue();
//            EventBus.getDefault().post(messageEvent);
//        });
        Handler(Looper.myLooper()!!).postDelayed(
            {
                viewPager.setCurrentItem(2, true)
            }, 200
        )
    }

}