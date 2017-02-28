package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.view.View;

import com.cloverstudio.generalcore.http.images.GifLoader;
import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;

import pl.droidsonroids.gif.GifImageView;

/**
 * 网络gif图片加载demo
 * Created by wlei on 2017/2/24.
 */

public class NetGifImageLoadActivity extends BaseNavActivity {
    @Override
    public boolean needSliding() {
        return true;
    }

    @Override
    public int getBodyView() {
        return R.layout.activity_net_gifimage_load;
    }

    @Override
    public void setup() {
        findViewById(R.id.btnStartLoadGifImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GifImageView gifImageView = (GifImageView) findViewById(R.id.gifImageView);
                GifLoader.loadGif(
                        "http://s1.dwstatic.com/group1/M00/5E/A3/38f1038885e950e94799d3260ac795e8.gif",
                        gifImageView
                );
            }
        });
    }

    @Override
    public void initNavBar(NavigationBar navBar) {

        navBar.setNavTitle("网络gif图片加载demo");
    }
}
