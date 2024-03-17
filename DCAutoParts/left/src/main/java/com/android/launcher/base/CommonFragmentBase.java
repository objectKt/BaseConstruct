package com.android.launcher.base;

public abstract class CommonFragmentBase extends FragmentBase<IPresenter> {

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    public abstract void onRight();

    public abstract void onLeft();

    public abstract void onOk();

    public abstract void onUp();

    public abstract void onDown();

    public abstract void onBack();
}