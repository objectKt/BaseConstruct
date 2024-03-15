package com.android.launcher;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.launcher.R;

public class CustomDialog extends Dialog {

    private TextView mTitle;
    private TextView mContent;
    private Button mCancel;
    private Button mConfirm;

    private int mLayoutId;
    private OnConfirmListener mConfirmListener;
    private OnCancleListener mCancelListener;
    private String mTitleString;
    private String mContentString;

    /**
     * 构造方法
     * @param layoutId        布局文件id
     * @param confirmListener 点击确定对应的监听器
     * @param cancelListener  点击取消对应的监听器
     */
    public CustomDialog(Context context, int layoutId, OnConfirmListener confirmListener, OnCancleListener cancelListener) {
        super(context, R.style.MyDialog);
        mConfirmListener = confirmListener;
        mCancelListener = cancelListener;
        mLayoutId = layoutId;
    }

    //点击确定对应的监听器
    public interface OnConfirmListener {
        void onConfirm();
    }

    //点击取消对应的监听器
    public interface OnCancleListener {
        void onCancle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
    }
}