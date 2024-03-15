package com.android.launcher.meter;

import io.reactivex.rxjava3.annotations.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
* @description:
* @createDate: 2023/9/21
*/
public class MenuFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList;

    public MenuFragmentAdapter(Fragment fragment, List<Fragment> fragmentList) {
        super(fragment.getChildFragmentManager(),fragment.getLifecycle());
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public Fragment getFragment(int  position) {
        return fragmentList.get(position);
    }

}
