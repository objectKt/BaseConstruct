package com.android.demos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.roundToInt

class DemoActivity : AppCompatActivity() {

    private lateinit var mAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val cards = arrayOf(R.mipmap.ic_menu_classic, R.mipmap.ic_menu_sport, R.mipmap.ic_menu_tech, R.mipmap.ic_menu_map, R.mipmap.ic_menu_maintain) // 假设有10张卡片
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

        // 添加页面变换监听器，用于处理选中效果
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            // 处理选中效果
            override fun onPageSelected(position: Int) {
                Log.e("大川汽配", "position selected = $position")
                mAdapter.getViewHolderAtPosition(position)
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

        //val padding = (DensityUtil.getScreenWidth(this) / 2.65f).roundToInt()
        val padding = 720
        Log.e("大川汽配", "padding $padding")
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