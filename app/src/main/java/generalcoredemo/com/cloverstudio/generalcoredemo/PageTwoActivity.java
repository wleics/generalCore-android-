package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.view.View;

import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;

/**
 * 演示用第二页
 * Created by wlei on 2017/2/24.
 */

public class PageTwoActivity extends BaseNavActivity {
    @Override
    public boolean needSliding() {
        return true;
    }

    @Override
    public int getBodyView() {
        return R.layout.layout_page_two;
    }

    @Override
    public void setup() {

    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.setNavTitle("侧滑关闭Activity");
        navBar.setLeftBarItemOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
