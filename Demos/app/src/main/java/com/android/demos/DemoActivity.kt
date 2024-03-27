package com.android.demos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
            ScalePageTransformer(true)
//            val scale = 1 - abs(position)
//            val alpha = (scale * 255).toInt()
//            page.scaleX = scale
//            page.scaleY = scale
//            page.alpha = alpha / 255.0f
//            val scale = 1 - abs(position)
            //val alpha = (scale * 255).toInt()
//            page.scaleX = scale
//            page.scaleY = scale
            //page.alpha = alpha / 255.0f

//            LogUtils.printI(TAG, "PageTransformer---position="+position);
//            page.pivotX = page.width.toFloat() / 2
//            page.pivotY = page.height.toFloat() / 2.0f
//            if (position == 0f) {
//                page.scaleX = 1.3f
//                page.scaleY = 1.3f
//                Handler(Looper.myLooper()!!).postDelayed({
//                    page.scaleX = 1.0f
//                    page.scaleY = 1.0f
//                    page.elevation = 0f
//                }, 500)
//            } else {
//                page.scaleX = 1.0f
//                page.scaleY = 1.0f
//                page.elevation = 0f
//            }

            // 如果页面在可见阈值之内，则显示
//            if (abs(position) <= 3.5f) {
//                page.visibility = View.VISIBLE
//            } else {
//                page.visibility = View.INVISIBLE
//            }
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

class ScalePageTransformer(
    private val isFill: Boolean
) : ViewPager2.PageTransformer {

    companion object {
        const val MAX_SCALE = 1.0f
        const val MIN_SCALE = 0.9f
    }

    private var isScaling = false

    override fun transformPage(view: View, position: Float) {
        var pos = position
        if (position < -1) {
            pos = -1.0f
        } else if (position > 1) {
            pos = 1.0f
        }

        val tempScale = if (pos < 0) 1 + pos else 1 - pos
        val slope = (MAX_SCALE - MIN_SCALE) / 1
        val scaleValue = MIN_SCALE + tempScale * slope

        if (!isScaling) {
            view.scaleX = scaleValue
            view.scaleY = scaleValue
            isScaling = true
        } else {
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.scale_anim)
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