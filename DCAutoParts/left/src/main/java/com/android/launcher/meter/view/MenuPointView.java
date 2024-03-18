package com.android.launcher.meter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher.R;
import com.android.launcher.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MenuPointView extends FrameLayout {

    private RecyclerView contentRV;

    private PointAdapter pointAdapter;

    private int currentPosition = 0;

    public MenuPointView(@NonNull Context context) {
        this(context, null);
    }

    public MenuPointView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuPointView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MenuPointView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setupInit(context);
    }

    private void setupInit(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_menu_point, this, true);
        contentRV = findViewById(R.id.contentRV);
        contentRV.setLayoutManager(new LinearLayoutManager(context));
        pointAdapter = new PointAdapter(new ArrayList<>());
        contentRV.setAdapter(pointAdapter);
        contentRV.setItemAnimator(null);

    }

    public void setPointNumber(int number) {
        LogUtils.printI(MenuPointView.class.getSimpleName(), "setPointNumber---number=" + number);
        List<PointItem> items = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            items.add(new PointItem(false));
        }
        if (!items.isEmpty()) {
            items.get(currentPosition).setSelected(true);
        }
        pointAdapter.setNewData(items);
    }

    public void updatePosition(int position) {
        if (pointAdapter == null) {
            return;
        }
        if (currentPosition == position) {
            return;
        }

        pointAdapter.getItem(position).setSelected(true);
        pointAdapter.notifyItemChanged(position);

        pointAdapter.getItem(currentPosition).setSelected(false);
        pointAdapter.notifyItemChanged(currentPosition);
        currentPosition = position;
    }


//    private static class PointAdapter extends BaseQuickAdapter<PointItem, BaseViewHolder> {
//
//        public PointAdapter(@Nullable List<PointItem> data) {
//            super(R.layout.item_menu_point, data);
//        }
//
//        @Override
//        protected void convert(@NonNull BaseViewHolder helper, PointItem item) {
//
//            if (item.isSelected()) {
//                helper.setBackgroundRes(R.id.pointV, R.drawable.bg_point_selected);
//            } else {
//                helper.setBackgroundRes(R.id.pointV, R.drawable.bg_point_unselected);
//            }
//        }
//    }

    private class PointAdapter extends RecyclerView.Adapter<PointViewHolder> {

        List<PointItem> items = new ArrayList<>();

        public PointAdapter(List<PointItem> list) {
            this.items = list;
        }

        @NonNull
        @Override
        public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_menu_point, parent, false);

            return new PointViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
            PointItem pointItem = items.get(position);
            if (pointItem != null) {
                if (pointItem.isSelected()) {
                    holder.getPointView().setBackgroundResource(R.drawable.bg_point_selected);
                } else {
                    holder.getPointView().setBackgroundResource(R.drawable.bg_point_unselected);
                }
            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setNewData(List<PointItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        public PointItem getItem(int position) {
            return items.get(position);
        }
    }

    private class PointViewHolder extends RecyclerView.ViewHolder {

        View pointV;

        public PointViewHolder(@NonNull View itemView) {
            super(itemView);
            pointV = (View) itemView.findViewById(R.id.pointV);
        }

        public View getPointView() {
            return pointV;
        }
    }

    private static class PointItem {

        private boolean selected;


        public PointItem(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "selected=" + selected +
                    '}';
        }
    }
}
