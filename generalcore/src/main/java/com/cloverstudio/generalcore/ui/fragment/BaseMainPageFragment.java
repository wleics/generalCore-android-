
package com.cloverstudio.generalcore.ui.fragment;

import android.support.v4.app.Fragment;

import com.cloverstudio.generalcore.ui.view.NavigationBar;

/**
 * 主fragment基类
 */
public abstract class BaseMainPageFragment extends Fragment {
    public MainPageFragmentDelegate delegate;

    public String mTag = "";

    public void setDelegate(MainPageFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    /**
     * 重置导航栏
     *
     * @param navigationBar
     */
    public abstract void resetNavBar(NavigationBar navigationBar);

    public interface MainPageFragmentDelegate {
        public void fragmentIsOnResume(String fragmentTag);
    }
}
