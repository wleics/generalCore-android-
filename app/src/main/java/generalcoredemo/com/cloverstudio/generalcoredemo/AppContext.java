package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.app.Application;

import com.cloverstudio.generalcore.http.images.ImageLoader;
import com.cloverstudio.generalcore.utils.AppFileSystem;
import com.cloverstudio.generalcore.utils.NavigationBarHelper;

/**
 * 自定义的应用上下文
 * Created by wlei on 2017/2/24.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化应用的文件系统
        AppFileSystem.initFileSystem("generalCoreDemo");
        //初始化网络图片加载器
        ImageLoader.initialize(this);
        //设置导航栏的默认背景颜色
        NavigationBarHelper.setNavigationBarDefBackgroundColor(this,R.color.navigationBar_defcolor);
    }
}
