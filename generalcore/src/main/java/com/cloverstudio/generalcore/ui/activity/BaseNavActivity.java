
package com.cloverstudio.generalcore.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.cloverstudio.generalcore.R;
import com.cloverstudio.generalcore.http.images.ImageLoader;
import com.cloverstudio.generalcore.ui.view.NavigationBar;
import com.cloverstudio.generalcore.utils.NavigationBarHelper;
import com.cloverstudio.generalcore.utils.SlidrHelper;


/**
 * 导航条基类,继承自该基类的activity都支持滑动关闭，但是如果需要支持滑动关闭，
 * 则需要在配置activity时，指定theme为:MyMaterialThemeSlidr
 *
 * @author wlei 2015-6-2
 */
public abstract class BaseNavActivity extends FragmentActivity {
    private NavigationBar mNavBar;

    private FrameLayout rootContianer;

    private SlidrHelper mSlidrHelper;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_base_nav);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.body);
        if (getBodyView() != -1) {
            frameLayout.addView(LayoutInflater.from(this).inflate(getBodyView(), null));
        }

        // 对是否需要支持滑动关闭进行设置
        if (needSliding()) {
            mSlidrHelper = SlidrHelper.getInstance(this);
            mSlidrHelper.initSlidr();
            getRootViewContianer().setBackgroundColor(
                    getResources().getColor(R.color.background_material_light));
        } else {
            getRootViewContianer().setBackgroundColor(
                    getResources().getColor(android.R.color.white));
        }

        //对导航栏进行设置
        setupNavBar();

        //进行具体实现页面的相关设置
        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            ImageLoader.cancelImageLoadByTag(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对导航栏进行设置
     */
    private void setupNavBar() {
        //对导航栏进行设置
        mNavBar = (NavigationBar) findViewById(R.id.navigationBar);
        //设置默认的返回按钮操作
        mNavBar.setLeftBarItemOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置导航栏的背景颜色
        if (NavigationBarHelper.getNavigationBarDefBackgroundColor() != -1) {
            mNavBar.setNavBarBgColor(NavigationBarHelper.getNavigationBarDefBackgroundColor());
        }

        initNavBar(mNavBar);
    }

    /**
     * 设置滑动代理
     *
     * @param slidrDelegate
     */
    public void setSlidDelegate(SlidrHelper.SlidrDelegate slidrDelegate) {
        if (slidrDelegate == null) {
            return;
        }
        if (mSlidrHelper != null) {
            mSlidrHelper.setDelegate(slidrDelegate);
        }
    }

    public void lockSliding() {
        if (mSlidrHelper != null) {
            mSlidrHelper.lock();
        }
    }

    public void unlockSliding() {
        if (mSlidrHelper != null) {
            mSlidrHelper.unLock();
        }
    }

    /**
     * 是否需要支持滑动关闭
     *
     * @return
     */
    public abstract boolean needSliding();

    /**
     * 返回需要显示的视图
     *
     * @return
     */
    public abstract int getBodyView();

    /**
     * 页面初始化
     */
    public abstract void setup();

    /**
     * 对导航条进行初始化
     *
     * @param navBar
     */
    public abstract void initNavBar(NavigationBar navBar);

    /**
     * 返回顶层容器
     *
     * @return
     */
    public FrameLayout getRootViewContianer() {
        if (rootContianer == null) {
            rootContianer = (FrameLayout) findViewById(R.id.rootContianer);
        }
        return rootContianer;
    }

    /**
     * 返回导航栏
     *
     * @return
     */
    public NavigationBar getNavigationBar() {
        return mNavBar;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
