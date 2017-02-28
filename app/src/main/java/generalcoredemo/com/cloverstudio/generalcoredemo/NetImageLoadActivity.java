package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.view.View;

import com.cloverstudio.generalcore.http.images.ImageLoader;
import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 用于演示网络图片的加载
 * Created by wlei on 2017/2/24.
 */

public class NetImageLoadActivity extends BaseNavActivity {
    @Override
    public boolean needSliding() {
        return true;
    }

    @Override
    public int getBodyView() {
        return R.layout.activity_net_image_load;
    }

    @Override
    public void setup() {

        findViewById(R.id.btnStartLoadImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDraweeView image = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
                    ImageLoader.loadImage(
                            "http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/08/ChMkJ1itCneIU5LeAAZFMxK_RwwAAaGfAIO9A8ABkVL127.jpg",
                            image, ScalingUtils.ScaleType.FIT_XY
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.setNavTitle("网络图片加载");
    }
}
