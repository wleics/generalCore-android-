package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.cloverstudio.generalcore.http.images.ImageLoader;
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
        //使用picasso加载图片
        initBtnForOpenNewActivity(
                R.id.btnShowNetImageLoad2Activity,
                "com.cloverstudio.generalcoredemo.action.netImageLoadByPicassoActivity"
        );
        //获取应用的版本信息
        try {
            PackageInfo packageInfo = SystemTools
                    .getSystemVersion(
                            MainActivity.this, "generalcoredemo.com.cloverstudio.generalcoredemo");
            if (packageInfo != null) {
                TextView title = (TextView) findViewById(R.id.title);
                int versionCode = packageInfo.versionCode;
                String versionName = packageInfo.versionName;
                title.setText(title.getText().toString() + ":" + versionCode + "_" + versionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.btnClearImageCache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.clearCacheFiles(MainActivity.this);
                new MyTask().execute();
            }
        });

    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.hideBackBtn();
        navBar.setNavTitle(getString(R.string.app_name));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取缓存文件夹的大小
        new MyTask().execute();

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

    /**
     * 用于获取文件大小的异步任务
     */
    class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String size = ImageLoader.getCacheFileSize(MainActivity.this);
            return size;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView cacheFolderSize = (TextView) findViewById(R.id.cacheFolderSize);
            cacheFolderSize.setText("已缓存文件:" + s);
        }
    }
}
