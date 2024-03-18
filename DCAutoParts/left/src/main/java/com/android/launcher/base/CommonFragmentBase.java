package com.android.launcher.base;

/**
 * @date： 2023/11/7
 * @author: 78495
*/
public abstract class CommonFragmentBase extends FragmentBase<IPresenter>{


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
