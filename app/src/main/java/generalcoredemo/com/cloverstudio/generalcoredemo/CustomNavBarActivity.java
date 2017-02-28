package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.view.View;

import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;
import com.cloverstudio.generalcore.utils.SystemTools;

/**
 * 自定义导航栏演示
 * Created by wlei on 2017/2/28.
 */

public class CustomNavBarActivity extends BaseNavActivity {
    @Override
    public boolean needSliding() {
        return true;
    }

    @Override
    public int getBodyView() {
        return R.layout.activity_custom_navbar;
    }

    @Override
    public void setup() {
        /*自定义导航栏的背景色*/
        findViewById(R.id.buttonChangeNavBarBgColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationBar()
                        .setNavBarBgColor(getResources().getColor(R.color.custom_navbar_bgcolor));
            }
        });
        /*设置导航栏的标题字体颜色*/
        findViewById(R.id.buttonChangeNavBarTitleColor).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationBar().setNavBarTitleColor(
                                getResources().getColor(R.color.custom_navbar_title_textcolor));
                    }
                });
        /*自定义导航栏左侧按钮，并加载网络图片*/
        findViewById(R.id.buttonChangeLeftBtnImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationBar().setLeftBtnImageByUri(
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488855680&di=748078b0504100af2d11eaf0810a2704&imgtype=jpg&er=1&src=http%3A%2F%2Ff12.topit.me%2Fo064%2F10064235471096485b.jpg",
                        R.drawable.home_nav_left_icon
                );
                getNavigationBar().setLeftBarItemOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemTools.showToast(CustomNavBarActivity.this, "头像被点击");
                    }
                });
            }
        });
        /*自定义导航栏左侧按钮，加载本地图片*/
        findViewById(R.id.buttonChangeLeftBtnImage2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationBar().setLeftBtnImage(R.mipmap.custom_navbar_leftbtn_img);
                getNavigationBar().setLeftBarItemOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemTools.showToast(CustomNavBarActivity.this, "左侧按钮被点击");
                    }
                });
            }
        });
        /*自定义导航栏title为view*/
        findViewById(R.id.buttonChangeNavbarTitleByImage).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationBar()
                                .setNavTitleByImage(R.mipmap.custom_navbar_titileview_logo);
                        getNavigationBar().setNavBarBgColor(
                                getResources().getColor(android.R.color.white));
                        getNavigationBar().hideLeftBtn();
                    }
                });
        /*显示右侧按钮，并将右侧按钮设置为文字样式*/
        findViewById(R.id.buttonShowRightLabelItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationBar().showRightBtn();
                getNavigationBar().setRightBtnLabel("点我");
                getNavigationBar()
                        .setRightBtnLabelTextcolor(
                                getResources().getColor(android.R.color.darker_gray));
                getNavigationBar().setRightBarItemOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemTools.showToast(CustomNavBarActivity.this, "右侧按钮被点击");
                    }
                });
            }
        });
        /*显示右侧按钮，并将右侧按钮设置为图片样式*/
        findViewById(R.id.buttonShowRightLabelItemByImg).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationBar().showRightBtn();
                        getNavigationBar().setRightBtnImage(R.mipmap.custom_navbar_leftbtn_img,
                                                            getResources().getDimensionPixelOffset(
                                                                    R.dimen.navbar_rightbtn_w),
                                                            getResources().getDimensionPixelOffset(
                                                                    R.dimen.navbar_rightbtn_h));
                        getNavigationBar().setRightBarItemOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SystemTools.showToast(CustomNavBarActivity.this, "右侧按钮被点击");
                            }
                        });
                    }
                });
    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.setNavTitle("自定义导航栏");
    }
}
