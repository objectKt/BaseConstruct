package com.dc.auto.library.global;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 在 module 中使用 applicationContext
 */
public class AndroidContext {

    private Context context = null;

    // 私有化构造函数，防止外部实例化
    private AndroidContext() {
    }

    // 静态实例变量，存储唯一的单例对象
    @SuppressLint("StaticFieldLeak")
    private static AndroidContext instance;

    // 公共静态方法，获取单例对象
    public static AndroidContext getInstance() {
        if (instance == null) {
            instance = new AndroidContext();
        }
        return instance;
    }

    public Context getApplicationContext() {
        if (context == null) {
            throw new RuntimeException("Please call [AndroidContext.init(context)] first");
        } else {
            return context;
        }
    }

    public void init(Context context) {
        if (this.context != null) return;
        this.context = context.getApplicationContext();
    }
}