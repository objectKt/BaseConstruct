package com.android.launcher.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.launcher.R;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * 复位对话框
 * @date： 2023/11/21
 * @author: 78495
*/
public class ResetDialog extends AttachPopupView {

    private String title;

    private int selectPosition = 1;
    private Button confirmBt;
    private Button cancelBt;

    public ResetDialog(@NonNull Context context,String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        TextView titleTV = findViewById(R.id.titleTV);
        titleTV.setText(title);

        confirmBt = findViewById(R.id.confirmBt);
        confirmBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBt = findViewById(R.id.cancelBt);
        cancelBt.setOnClickListener(v -> dismiss());

        changePosition();
    }

    public void changePosition(){
        if(selectPosition == 0){
            selectPosition = 1;
            confirmBt.setBackgroundResource(0);
            cancelBt.setBackgroundResource(R.drawable.item_selector);
        }else if(selectPosition == 1){
            selectPosition = 0;
            confirmBt.setBackgroundResource(R.drawable.item_selector);
            cancelBt.setBackgroundResource(0);
        }
    }


    public int getSelectPosition() {
        return selectPosition;
    }



    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_restore;
    }
}
