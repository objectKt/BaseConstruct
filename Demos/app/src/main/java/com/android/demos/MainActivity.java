package com.android.demos;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.demos.ui.RippleBackground;

public class MainActivity extends AppCompatActivity  {
    private Context context;

    RippleBackground rbBackground1;
    RippleBackground rbBackground2;
    TextView tvSingle;
    TextView tvMuti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbBackground1 = findViewById(R.id.rb_background1);
        rbBackground2 = findViewById(R.id.rb_background2);
        tvSingle = findViewById(R.id.tv_single);
        tvMuti = findViewById(R.id.tv_muti);
        initView();
    }

    private void initView() {
        context = getApplicationContext();
        rbBackground1.setVisibility(View.VISIBLE);
        rbBackground2.setVisibility(View.GONE);
        rbBackground1.startRippleAnimation();
        tvSingle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        tvMuti.setTextColor(Color.WHITE);
//        findViewById(R.id.iv_icon1).setOnClickListener(this);
//        findViewById(R.id.iv_icon2).setOnClickListener(this);
//        findViewById(R.id.tv_single).setOnClickListener(this);
//        findViewById(R.id.tv_muti).setOnClickListener(this);
//        if (rbBackground1.isRippleAnimationRunning()) {
//            rbBackground1.stopRippleAnimation();
//            return;
//        }
//        rbBackground1.startRippleAnimation();
//        if (rbBackground2.isRippleAnimationRunning()) {
//            rbBackground2.stopRippleAnimation();
//            return;
//        }
//        rbBackground2.startRippleAnimation();
    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_icon1:
//                if (rbBackground1.isRippleAnimationRunning()) {
//                    rbBackground1.stopRippleAnimation();
//                    return;
//                }
//                rbBackground1.startRippleAnimation();
//                break;
//            case R.id.iv_icon2:
//                if (rbBackground2.isRippleAnimationRunning()) {
//                    rbBackground2.stopRippleAnimation();
//                    return;
//                }
//                rbBackground2.startRippleAnimation();
//                break;
//            case R.id.tv_single:
//                rbBackground1.setVisibility(View.VISIBLE);
//                rbBackground2.setVisibility(View.GONE);
//                rbBackground1.startRippleAnimation();
//                tvSingle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
//                tvMuti.setTextColor(Color.WHITE);
//                break;
//            case R.id.tv_muti:
//                rbBackground1.setVisibility(View.GONE);
//                rbBackground2.setVisibility(View.VISIBLE);
//                rbBackground2.startRippleAnimation();
//                tvMuti.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
//                tvSingle.setTextColor(Color.WHITE);
//                break;
//        }
//    }
}
