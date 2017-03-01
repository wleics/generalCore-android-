package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.app.Application;

import com.cloverstudio.generalcore.http.images.ImageLoader;
import com.cloverstudio.generalcore.utils.AppFileSystem;
import com.cloverstudio.generalcore.utils.NavigationBarHelper;
import com.cloverstudio.generalcore.utils.SystemToolsHelper;

/**
 * 自定义的应用上下文
 * Created by wlei on 2017/2/24.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //对系统帮助类进行设置
        SystemToolsHelper.setLogTag("generalCoreDemo");
        //应用发布时，使用此选项关闭日志打印
        //        SystemToolsHelper.disableLogData();
        //初始化应用的文件系统
        AppFileSystem.initFileSystem("generalCoreDemo");
        //初始化网络图片加载器
        ImageLoader.initialize(this);
        //设置导航栏的默认背景颜色
        NavigationBarHelper
                .setNavigationBarDefBackgroundColor(this, R.color.navigationBar_defcolor);


    }
}
