package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.TextView;

import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;
import com.cloverstudio.generalcore.utils.SystemTools;

public class MainActivity extends BaseNavActivity {


    @Override
    public boolean needSliding() {
        return false;
    }

    @Override
    public int getBodyView() {
        return R.layout.activity_main;
    }

    @Override
    public void setup() {
        SystemTools.Log("主页setup");
        //侧滑关闭页面演示
        initBtnForOpenNewActivity(
                R.id.btnShowOpenPageTwoActivity,
                "com.cloverstudio.generalcoredemo.action.pageTwoActivity"
        );
        //网络图片加载
        initBtnForOpenNewActivity(
                R.id.btnShowNetImageLoadActivity,
                "com.cloverstudio.generalcoredemo.action.netImageLoadActivity"
        );
        //网络gif图片的加载
        initBtnForOpenNewActivity(
                R.id.btnShowNetGifImageLoadActivity,
                "com.cloverstudio.generalcoredemo.action.netGifImageLoadActivity"
        );
        //自定义NavBar
        initBtnForOpenNewActivity(
                R.id.btnShowCustomNavBarActivity,
                "com.cloverstudio.generalcoredemo.action.customNavBarActivity"
        );
        //网络请求操作
        initBtnForOpenNewActivity(
                R.id.btnShowHttpOperateActivity,
                "com.cloverstudio.generalcoredemo.action.httpOperateActivity"
        );
        //获取应用的版本信息
        try {
            PackageInfo packageInfo = SystemTools
                    .getSystemVersion(MainActivity.this, "generalcoredemo.com.cloverstudio.generalcoredemo");
            if (packageInfo!=null){
                TextView title = (TextView) findViewById(R.id.title);
                int versionCode = packageInfo.versionCode;
                String versionName = packageInfo.versionName;
                title.setText(title.getText().toString() + ":" + versionCode + "_" + versionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.hideBackBtn();
        navBar.setNavTitle(getString(R.string.app_name));
    }

    /**
     * 按钮初始化
     *
     * @param btnResID
     * @param action
     */
    private void initBtnForOpenNewActivity(int btnResID, final String action) {
        findViewById(btnResID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action != null && !action.equals("")) {
                    SystemTools.sendToNewActivity(MainActivity.this, new Intent(
                            action));
                } else {
                    SystemTools.showToast(MainActivity.this, "出错了,页面无法跳转");
                }

            }
        });
    }
}
