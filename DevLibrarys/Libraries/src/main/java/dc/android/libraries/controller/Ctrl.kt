package dc.android.libraries.controller

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dc.android.libraries.adapter.CardAdapter

interface Ctrl {

    /**
     * 可分离写出的通用方法
     * @author hf
     */
    object Functions {

        fun initViewPagerMenu(cardAdapter: CardAdapter, viewPager: ViewPager2, recyclerView: View, padding: Int, animationTransform: ViewPager2.PageTransformer) {
            with(viewPager) {
                adapter = cardAdapter
                offscreenPageLimit = 5
                setPageTransformer(animationTransform)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        cardAdapter.getViewHolderAtPosition(position)
                    }
                })
                setCurrentItem(3, true)
            }
            oneScreenSomePager(recyclerView, padding)
        }

        private fun oneScreenSomePager(recyclerView: View, padding: Int) {
            if (recyclerView is RecyclerView) {
                recyclerView.setPadding(padding, 0, padding, 0)
                recyclerView.setClipToPadding(false)
            }
        }
    }
}