package com.android.launcher.mileage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher.R;

/**
* @description:
* @createDate: 2023/9/15
*/
public class RestoreDialogView extends FrameLayout {

    private TextView titleTV;
    private TextView yesTV;
    private TextView noTV;

    private int selectPosition = 0;

    public RestoreDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RestoreDialogView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public RestoreDialogView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_restore_dialog,this,true);

        titleTV = findViewById(R.id.titleTV);
        titleTV.setText(context.getResources().getString(R.string.after_starting_up));
        TextView subTitleTV = findViewById(R.id.subTitleTV);
        subTitleTV.setText(context.getResources().getString(R.string.reset_or_not));

        yesTV = findViewById(R.id.yesTV);
        noTV = findViewById(R.id.noTV);
    }

    public void setTitle(String title){
        titleTV.setText(title);
    }

    public void changePosition(){
        if(selectPosition == 0){
            selectPosition = 1;
            yesTV.setBackgroundResource(0);
            noTV.setBackgroundResource(R.drawable.item_selector);
        }else if(selectPosition == 1){
            selectPosition = 0;
            yesTV.setBackgroundResource(R.drawable.item_selector);
            noTV.setBackgroundResource(0);
        }
    }

    public int getSelectPosition() {
        return selectPosition;
    }

}
