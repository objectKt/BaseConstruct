package com.android.launcher.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class FragmentBase<T extends IPresenter> extends Fragment {

    protected T mPresenter;
    protected boolean isShow = false;
    protected String TAG;
    private ExecutorService executorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(getContentLayoutId(), container, false);
            LogUtils.printI(this.getClass().getSimpleName(), "onCreateView------");
            TAG = this.getClass().getSimpleName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.printI(this.getClass().getSimpleName(), "onViewCreated------");
        registerEventBus();
        executorService = Executors.newCachedThreadPool();
        mPresenter = createPresenter();
        initView(view, savedInstanceState);
    }

    protected void registerEventBus() {
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTask(Runnable runnable) {
        if (executorService != null) {
            executorService.execute(runnable);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        disposeMessageEvent(event);
    }

    public abstract void disposeMessageEvent(MessageEvent event);

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getContentLayoutId();

    protected abstract T createPresenter();

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.printI(this.getClass().getSimpleName(), "onResume------");
        isShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.printI(this.getClass().getSimpleName(), "onPause------");
        isShow = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.printI(this.getClass().getSimpleName(), "onResume------");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.printI(this.getClass().getSimpleName(), "onResume------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus();
        try {
            if (mPresenter != null) {
                mPresenter.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.printI(this.getClass().getSimpleName(), "onDestroyView------");
    }

    protected void unregisterEventBus() {
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.printI(this.getClass().getSimpleName(), "onDestroy------");
    }
}