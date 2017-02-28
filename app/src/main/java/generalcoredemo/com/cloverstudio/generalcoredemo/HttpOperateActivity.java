package generalcoredemo.com.cloverstudio.generalcoredemo;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.cloverstudio.generalcore.http.HttpOperateHelper;
import com.cloverstudio.generalcore.http.base.HttpRequest;
import com.cloverstudio.generalcore.http.delegate.HttpOperateDelegate;
import com.cloverstudio.generalcore.ui.activity.BaseNavActivity;
import com.cloverstudio.generalcore.ui.view.NavigationBar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import generalcoredemo.com.cloverstudio.generalcoredemo.entity.AppArticle;
import generalcoredemo.com.cloverstudio.generalcoredemo.entity.ArticleResponse;

/**
 * 演示网络请求操作的demo
 * Created by wlei on 2017/2/24.
 */

public class HttpOperateActivity extends BaseNavActivity {
    @Override
    public boolean needSliding() {
        return true;
    }

    @Override
    public int getBodyView() {
        return R.layout.activity_http_operate;
    }

    @Override
    public void setup() {
        TextView httpResult = (TextView) findViewById(R.id.httpResult);
        httpResult.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.btnExecuteHttpOperate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView httpResult = (TextView) findViewById(R.id.httpResult);
                httpResult.setText("数据获取中...");
                findViewById(R.id.btnExecuteHttpOperate).setClickable(false);
                Map<String, Object> params = new HashMap<String, Object>();
                AppArticle appArticle = new AppArticle();
                // 如果登陆用户的id为默认用户id，则将userid设置为空字符串
                String userid = "";
                appArticle.setCreate_by(userid);
                appArticle.setId("8b5b7445397b4eef83407fcea0d8e296");
                params.put(HttpRequest.REQUEST_KEY_DATA, new Gson().toJson(appArticle));
                HttpOperateHelper
                        .execute("http://www.newsfans.cn:8000/web/rest/article/view", params,
                                 ArticleResponse.class,
                                 new HttpOperateDelegate<ArticleResponse>() {
                                     @Override
                                     public void executeComplete(ArticleResponse object) {
                                         if (object!=null){
                                             String json = new Gson().toJson(object);
                                             TextView httpResult = (TextView) findViewById(R.id.httpResult);
                                             String str = "服务端返回的数据为:\n"+json;
                                             httpResult.setText("");
                                             httpResult.setText(str);
                                         }
                                         findViewById(R.id.btnExecuteHttpOperate).setClickable(true);
                                     }

                                     @Override
                                     public void executeCompltetButHashError(
                                             String error) {
                                         findViewById(R.id.btnExecuteHttpOperate).setClickable(true);
                                         TextView httpResult = (TextView) findViewById(R.id.httpResult);
                                         httpResult.setText("请求出错："+error);
                                     }

                                     @Override
                                     public void executeError() {
                                         findViewById(R.id.btnExecuteHttpOperate).setClickable(true);
                                         TextView httpResult = (TextView) findViewById(R.id.httpResult);
                                         httpResult.setText("网络连接出错");
                                     }
                                 }
                        );
            }
        });
    }

    @Override
    public void initNavBar(NavigationBar navBar) {
        navBar.setNavTitle("网络请求操作演示");
    }
}
