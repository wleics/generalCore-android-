package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.view.View;
import android.widget.ImageView;

import com.cloverstudio.generalcore.http.images.ImageLoader;
import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;

/**
 * 使用picasso加载网络图片的demo
 * Created by wlei on 2017/3/1.
 */

public class NetImageLoadByPicassoActivity extends BaseNavActivity {
    @Override
    public boolean needSliding() {
        return true;
    }

    @Override
    public int getBodyView() {
        return R.layout.activity_net_image_load_by_picasso;
    }

    @Override
    public void setup() {
        findViewById(R.id.buttonLoadImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                ImageLoader.loadImageBy(
                        NetImageLoadByPicassoActivity.this,
                        "http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/08/ChMkJ1itCneIU5LeAAZFMxK_RwwAAaGfAIO9A8ABkVL127.jpg",
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        imageView,
                        ImageView.ScaleType.FIT_XY
                );
            }
        });
        findViewById(R.id.buttonLoadImage2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                ImageLoader.loadImageBy(
                        NetImageLoadByPicassoActivity.this,
                        "http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/0F/0F/ChMkJliip3WIOrI4AAeV6z24LWkAAZ8GQMqWEYAB5YD314.png",
                        R.drawable.placeholder,
                        imageView
                );
            }
        });
    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.setNavTitle("网络图片加载");
    }
}
