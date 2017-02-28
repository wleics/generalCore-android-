
package com.cloverstudio.generalcore.http;

import android.os.Handler;
import android.os.Looper;

import com.cloverstudio.generalcore.http.base.HttpRequest;
import com.cloverstudio.generalcore.http.base.ReqBase;
import com.cloverstudio.generalcore.http.delegate.HttpOperateDelegate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 通用网络请求辅助类
 *
 * @author wlei 2014-10-13
 */
public class HttpOperateHelper<T> {

    /**
     * app网络请求对应的域名
     */
    private static final String APP_DOMAIN_NAME = "";

    /**
     * app网络请求域名对应的ip地址，该ip地址对应如果采用自动重试模式，
     * 在使用自动重试时，将用ip地址替换对应的域名
     */
    private static final String APP_DOMAIN_IP = "";


    /**
     * 任务代理
     */
    @SuppressWarnings("rawtypes")
    private HttpOperateDelegate mDelegate;

    /**
     * 返回对象的类型
     */
    private T mClass;

    /**
     * http请求失败
     */
    final int HTTP_RESULT_FLAG_FAILED = 0;

    /**
     * http请求成功
     */
    final int HTTP_RESULT_FLAG_COMPLETE = 1;

    /**
     * http请求出错
     */
    final int HTTP_RESULT_FLAG_WARN = 2;


    Handler mDelivery;

    /**
     * 当网络出现异常时，是否需要自动重试一次网络请求
     */
    private boolean autoRetry;

    public HttpOperateHelper() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    /**
     * 执行通用的网络请求
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param class1    请求成功后的返回类型
     * @param autoRetry 当请求失败后，是否需要自动重试
     * @param delegate  请求完成后的回调
     */
    @SuppressWarnings("rawtypes")
    public static void execute(String url, Map<String, Object> params,
                               Class<? extends ReqBase> class1, boolean autoRetry,
                               HttpOperateDelegate delegate) {
        if (class1 == null) {
            return;
        }
        HttpOperateHelper<Object> helper = new HttpOperateHelper<>();
        helper.autoRetry = autoRetry;
        helper.executeSelf(url, params, class1, delegate);
    }

    /**
     * 执行通用http请求（默认执行请求失败后，自动重试）
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param class1   请求成功后的返回类型
     * @param delegate 请求完成后的回调
     */
    @SuppressWarnings("rawtypes")
    public static void execute(String url, Map<String, Object> params,
                               Class<? extends ReqBase> class1,
                               HttpOperateDelegate delegate) {
        execute(url, params, class1, true, delegate);
    }

    private void executeSelf(final String url,
                             final Map<String, Object> params, final T class1,
                             @SuppressWarnings("rawtypes")
                             final HttpOperateDelegate delegate) {
        this.mClass = class1;
        this.mDelegate = delegate;
        OkHttpClient mOkHttpClient = getOkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();

        // 此处可能没值，所以需要判断
        try {
            if (params != null && params.containsKey(HttpRequest.REQUEST_KEY_DATA)) {
                builder.add(
                        HttpRequest.REQUEST_KEY_DATA,
                        (String) params.get(HttpRequest.REQUEST_KEY_DATA)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        builder.add("deviceType", "mobile");
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                executeForRequestFailure(url, params, class1, delegate);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(Response response) throws IOException {
                final String json = response.body().string();
                try {
                    Object obj = null;
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    obj = gson.fromJson(json, (Class<T>) mClass);
                    if (obj != null) {
                        ReqBase reqBase = (ReqBase) obj;
                        int resultCode = reqBase.getResultCode();
                        String errorMsg = reqBase.getResultErrorMsg();
                        if (resultCode != 1) {
                            doForHttpPostCompleteButWithError(errorMsg);
                        } else {
                            int flag = reqBase.getResultFlag();
                            switch (flag) {
                                case HTTP_RESULT_FLAG_COMPLETE:
                                    doForHttpPostComplete(obj);
                                    break;
                                default:
                                    doForHttpPostCompleteButWithError(((ReqBase) obj)
                                                                              .getResultErrorMsg());
                                    break;
                            }
                        }
                    } else {
                        // doForHttpPostError();
                        executeForRequestFailure(url, params, class1, delegate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // doForHttpPostError();
                    executeForRequestFailure(url, params, class1, delegate);
                }
            }

            /**
             * http请求执行错误
             */
            private void doForHttpPostError() {
                mDelivery.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mDelegate != null) {
                            mDelegate.executeError();
                        }
                    }
                });
            }

            /**
             * http请求执行完成，但是出现错误
             *
             * @param msg
             */
            private void doForHttpPostCompleteButWithError(final String msg) {
                mDelivery.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mDelegate != null) {
                            mDelegate.executeCompltetButHashError(msg);
                        }
                    }
                });
            }

            /**
             * http请求执行完成
             *
             * @param obj
             */
            private void doForHttpPostComplete(final Object obj) {
                mDelivery.post(new Runnable() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        if (mDelegate != null) {
                            mDelegate.executeComplete(obj);
                        }
                    }
                });
            }

            /**
             * 网络请求出错时，执行的操作
             *
             * @param url
             * @param params
             * @param class1
             * @param delegate
             */
            private void executeForRequestFailure(final String url,
                                                  final Map<String, Object> params, final T class1,
                                                  @SuppressWarnings("rawtypes")
                                                  final HttpOperateDelegate delegate) {
                // 如果网络访问出错，首先将域名替换为ip地址，然后重新进行一次网络请求
                // 如果当前的地址已经被修改为ip地址了，则执行错误处理逻辑
                // 当autoRetry设置为true时，才会执行自动重试！！
                if (autoRetry) {
                    if (url.contains(APP_DOMAIN_NAME)) {
                        //SystemTools.Log("执行对 " + url + " 的请求失败了");
                        String newUrl = url.replace(APP_DOMAIN_NAME, APP_DOMAIN_IP);
                        //SystemTools.Log("重新执行对 " + newUrl + " 的请求");
                        executeSelf(newUrl, params, class1, delegate);
                    } else {
                        //SystemTools.Log("执行对 " + url + " 的请求失败了");
                        doForHttpPostError();
                    }
                } else {
                    //SystemTools.Log("执行对 " + url + " 的请求失败了");
                    doForHttpPostError();
                }
            }
        });
    }

    // Object mObject;

    private OkHttpClient getOkHttpClient() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        return mOkHttpClient;
    }


}
