package com.android.demos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
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
            override fun onPageSelected(position: Int) {
                // 处理选中效果
                //updateCardElevation(position)
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
        Handler(Looper.myLooper()!!).postDelayed({
            viewPager.setCurrentItem(2, true)
        }, 200)
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

class MultiPageTransformer2 : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        // 计算页面的缩放和透明度
        val scaleFactor = 1 - abs(position) * 0.15f
        val alphaFactor = (scaleFactor * 255).toInt() / 255.0f

        // 获取页面的LayoutParams
        val layoutParams = page.layoutParams as ViewGroup.MarginLayoutParams

        // 设置页面的缩放
        page.scaleX = scaleFactor
        page.scaleY = scaleFactor
        page.alpha = alphaFactor

        // 根据位置调整页面的边距
        layoutParams.setMargins(
            if (position < 0) (page.width * (1 - scaleFactor) / 2).toInt() else 0,
            0,
            if (position > 0) (page.width * (1 - scaleFactor) / 2).toInt() else 0,
            0
        )
    }
}

class ScalePageTransformer(
    private val isFill: Boolean
) : ViewPager2.PageTransformer {

    companion object {
        const val MAX_SCALE = 1.2f
        const val MIN_SCALE = 0.9f
    }

    private var isScaling = false

    override fun transformPage(view: View, position: Float) {
        var pos = if (position < -1) {
            -1.0f
        } else if (position > 1) {
            1.0f
        } else {
            position
        }

        val tempScale = if (pos < 0) 1 + pos else 1 - pos
        val slope = (MAX_SCALE - MIN_SCALE) / 1
        val scaleValue = MIN_SCALE + tempScale * slope

        if (!isScaling) {
            view.scaleX = scaleValue
            view.scaleY = scaleValue
            isScaling = true
        } else {
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.scale_anim_menu_smaller)
            animation.duration = 300
            animation.fillAfter = true
            view.startAnimation(animation)
            isScaling = false
        }

        if (isFill) {
            view.setScaleX(scaleValue)
            view.setScaleY(scaleValue)
        }
    }
}