package com.android.launcher.nav;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher.R;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")
public class HorizontalView extends RecyclerView {

//    public static final int NO_POSITION = HorizontalLayoutManager.NO_POSITION;

    private static final int DEFAULT_ORIENTATION = DSVOrientation.HORIZONTAL.ordinal();

    private HorizontalLayoutManager layoutManager;

    private List<ScrollStateChangeListener> scrollStateChangeListeners;
    private List<OnItemChangedListener> onItemChangedListeners;

    private boolean isOverScrollEnabled;

    public HorizontalView(Context context) {
        super(context);
        init(null);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        scrollStateChangeListeners = new ArrayList<>();
        onItemChangedListeners = new ArrayList<>();

        int orientation = DEFAULT_ORIENTATION;
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalView);
            orientation = ta.getInt(R.styleable.HorizontalView_dsv_orientation, DEFAULT_ORIENTATION);
            ta.recycle();
        }

        isOverScrollEnabled = getOverScrollMode() != OVER_SCROLL_NEVER;

        layoutManager = new HorizontalLayoutManager(
                getContext(), new ScrollStateListener(),
                DSVOrientation.values()[orientation]);
        setLayoutManager(layoutManager);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof HorizontalLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            throw new IllegalArgumentException("");
        }
    }


    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean isFling = super.fling(velocityX, velocityY);
        if (isFling) {
            layoutManager.onFling(velocityX, velocityY);
        } else {
            layoutManager.returnToCurrentPosition();
        }
        return isFling;
    }

    @Nullable
    public ViewHolder getViewHolder(int position) {
        View view = layoutManager.findViewByPosition(position);
        return view != null ? getChildViewHolder(view) : null;
    }

    /**
     * @return adapter position of the current item or -1 if nothing is selected
     */
    public int getCurrentItem() {
        return layoutManager.getCurrentPosition();
    }

    public void setItemTransformer(HorizontalScrollItemTransformer transformer) {
        layoutManager.setItemTransformer(transformer);
    }

    public void setItemTransitionTimeMillis(@IntRange(from = 10) int millis) {
        layoutManager.setTimeForItemSettle(millis);
    }

    public void setSlideOnFling(boolean result){
        layoutManager.setShouldSlideOnFling(result);
    }

    public void setSlideOnFlingThreshold(int threshold){
        layoutManager.setSlideOnFlingThreshold(threshold);
    }

    public void setOrientation(DSVOrientation orientation) {
        layoutManager.setOrientation(orientation);
    }

    public void setOffscreenItems(int items) {
        layoutManager.setOffscreenItems(items);
    }

    public void setClampTransformProgressAfter(@IntRange(from = 1) int itemCount) {
        if (itemCount <= 1) {
            throw new IllegalArgumentException("must be >= 1");
        }
        layoutManager.setTransformClampItemCount(itemCount);
    }



    public void addScrollStateChangeListener(@NonNull ScrollStateChangeListener scrollStateChangeListener) {
        scrollStateChangeListeners.add(scrollStateChangeListener);
    }


    public void addOnItemChangedListener(@NonNull OnItemChangedListener onItemChangedListener) {
        onItemChangedListeners.add(onItemChangedListener);
    }


    private void notifyScrollStart(int current) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScrollStart(current);
        }
    }

    private void notifyScrollEnd(int current) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScrollEnd(current);
        }
    }

    private void notifyScroll(float position,
                              int currentIndex, int newIndex) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScroll(position, currentIndex, newIndex);
        }
    }

    private void notifyCurrentItemChanged(int current) {
        for (OnItemChangedListener listener : onItemChangedListeners) {
            listener.onCurrentItemChanged(current);
        }
    }

    private void notifyCurrentItemChanged() {
        if (onItemChangedListeners.isEmpty()) {
            return;
        }
        int current = layoutManager.getCurrentPosition();
        notifyCurrentItemChanged(current);
    }

    private class ScrollStateListener implements HorizontalLayoutManager.ScrollStateListener {

        @Override
        public void onIsBoundReachedFlagChange(boolean isBoundReached) {
            if (isOverScrollEnabled) {
                setOverScrollMode(isBoundReached ? OVER_SCROLL_ALWAYS : OVER_SCROLL_NEVER);
            }
        }

        @Override
        public void onScrollStart() {
            if (scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = layoutManager.getCurrentPosition();
            notifyScrollStart(current);
        }

        @Override
        public void onScrollEnd() {
            if (onItemChangedListeners.isEmpty() && scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = layoutManager.getCurrentPosition();
            notifyScrollEnd(current);
            notifyCurrentItemChanged(current);
        }

        @Override
        public void onScroll(float currentViewPosition) {
            if (scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int currentIndex = getCurrentItem();
            int newIndex = layoutManager.getNextPosition();
            if (currentIndex != newIndex) {
                notifyScroll(currentViewPosition,
                        currentIndex, newIndex);
            }
        }

        @Override
        public void onCurrentViewFirstLayout() {
            post(new Runnable() {
                @Override
                public void run() {
                    notifyCurrentItemChanged();
                }
            });
        }

        @Override
        public void onDataSetChangeChangedPosition() {
            notifyCurrentItemChanged();
        }
    }

    public interface ScrollStateChangeListener {

        void onScrollStart(int adapterPosition);

        void onScrollEnd(int adapterPosition);

        void onScroll(float scrollPosition,
                      int currentPosition,
                      int newPosition);
    }

    public interface ScrollListener{

        void onScroll(float scrollPosition,
                      int currentPosition, int newPosition);
    }

    public interface OnItemChangedListener {
        /*
         * This method will be also triggered when view appears on the screen for the first time.
         * If data set is empty, viewHolder will be null and adapterPosition will be NO_POSITION
         */
        void onCurrentItemChanged(int adapterPosition);
    }
}
