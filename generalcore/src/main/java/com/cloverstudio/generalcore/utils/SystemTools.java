
package com.cloverstudio.generalcore.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cloverstudio.generalcore.R;
import com.cloverstudio.utils.libs.FileTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统实用工具
 *
 * @author wlei
 */
public class SystemTools {

    /**
     * 获取系统当前版本信息
     *
     * @param context     上下文
     * @param appPackname 应用对应的包名
     */
    public static PackageInfo getSystemVersion(Context context,
                                               String appPackname) throws Exception {
        if (context == null) {
            throw new Exception("context 不能为 null");
        }
        if (appPackname == null) {
            throw new Exception("appPackname 不能为 null");
        }
        if (appPackname.equals("")) {
            throw new Exception("SYSTEM_PACKAGE为空！");
        }
        PackageInfo packageInfo = null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(appPackname, 0);
            if (info != null) {
                packageInfo = info;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;

    }

    static Toast mToast;

    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message
     */
    public static final void toastMessage(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    makeToastAndShow(activity, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 显示Tosat提示
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (context == null) {
            return;
        }
        try {
            makeToastAndShow(context, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("InflateParams")
    private static void makeToastAndShow(final Context context, final String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
            //return;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_toast_info, null);
        mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(view);
        mToast.setDuration(Toast.LENGTH_SHORT);
        TextView info = (TextView) view.findViewById(R.id.info);
        info.setText(message);
        mToast.show();
    }

    /**
     * 强制关闭toast
     */
    public static void dismissToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showKeyBoard(View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) view
                    .getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyBoard(View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) view
                    .getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            try {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(
                                activity.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS
                        );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 按照文件名称返回文件对应的图片资源编号
     *
     * @param imageName
     * @return
     */
    public static int getImageResByImageName(String imageName) {
        if (imageName == null) {
            return -1;
        }
        @SuppressWarnings("rawtypes")
        Class drawable = R.drawable.class;
        Field field = null;
        int r_id;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id = -1;
        }
        return r_id;
    }

    /**
     * 返回json格式的数据
     *
     * @param fileName
     * @return
     */
    public static String getJsonData(Context context, String fileName) {
        if (context != null && fileName != null) {
            return FileTools.getStringFromAssetByName(context, fileName);
        } else {
            return "";
        }
    }

    static Rect frame;

    public static void initDevicesFrame(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    }

    public static Rect getDevicesFrame() {
        return frame;
    }

    public static int getScreenWidth(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            return width;
        }
        return 0;
    }

    public static int getScreenHeight(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;
            return height;
        }
        return 0;
    }

    /**
     * 获取ProgressDialog
     *
     * @param context
     * @param title
     * @param msg
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context, String title, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        if (title != null && !title.equals("")) {
            progressDialog.setTitle(title);
        }
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    /**
     * 从asset文件夹中读取对应的txt文件，并返回其中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getStringFromAssetByName(Context context, String fileName) {
        if (context == null || fileName == null) {
            return "";
        }
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            return new String(buffer, "utf-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 中文标点符号优化
     *
     * @param s
     * @return
     */
    public static String optimizationChinese(String s) {
        if (s == null) {
            return "";
        }
        if (s.contains(",")) {
            s = s.replaceAll(",", " ，");
        }
        if (s.contains("，")) {
            s = s.replaceAll("，", " ，");
        }
        if (s.contains("。")) {
            s = s.replaceAll("。", " 。");
        }
        if (s.contains("“")) {
            s = s.replaceAll("“", "“ ");
        }
        if (s.contains("”")) {
            s = s.replaceAll("”", " ”");
        }
        if (s.contains("、")) {
            s = s.replaceAll("、", " 、");
        }
        return s;
    }

    /**
     * 将图片保存到相册
     *
     * @param context
     * @param fileName
     * @param file
     */
    public static void setImageToMediaStore(Context context, String fileName, File file) {
        new DownLoadTask(context, fileName, file).execute();
    }

    static class DownLoadTask extends AsyncTask<Void, Void, Void> {
        private Context mContext;

        private String mFileName;

        private File mFile;

        public DownLoadTask(Context context, String fileName, File file) {
            mContext = context;
            mFileName = fileName;
            mFile = file;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // SystemTools.Log("将张片写入相册");
                MediaStore.Images.Media.insertImage(
                        mContext.getContentResolver(),
                        mFile.getAbsolutePath(),
                        mFileName,
                        null
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                    .parse("file://" + mFile.getAbsolutePath())));
            // SystemTools.Log("照片已保存到相册");
        }

    }

    /**
     * 获取html中的所有image的url
     *
     * @param content
     * @return
     */
    public static String[] getImgs(String content) {
        if (content == null) {
            return null;
        }
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String str = "";
        String[] images = null;
        String regEx_img = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);
                if ("".equals(str)) {
                    str = tempSelected;
                } else {
                    String temp = tempSelected;
                    str = str + "," + temp;
                }
            }
        }
        if (!"".equals(str)) {
            images = str.split(",");
        }
        return images;
    }

    /**
     * 生成存放在七牛服务器上的，文件的文件名
     *
     * @param tpl
     * @param suffix
     * @return
     */
    public static String getQINiuExpectKey(String tpl, String suffix) {
        if (null == tpl || null == suffix) {
            return "";
        }
        // 样式： ----/{yyyy}{mm}{dd}/{time}{rand:6}
        // 日期
        String tpl_date = changeMillsecondToDateStyle(new Date().getTime(), "yyyyMMdd");
        // 时间
        long tpl_time = new Date().getTime();
        // 6位随机数
        long tpl_randomNum = (int) ((Math.random() * 9 + 1) * 100000);
        String result = String.format(tpl, tpl_date, tpl_time, tpl_randomNum, suffix);
        return result;
    }

    /**
     * 将毫秒装换成时间
     *
     * @param millisecond
     * @param style
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private static String changeMillsecondToDateStyle(long millisecond, String style) {
        Date dat = new Date(millisecond);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(style);
        String sb = format.format(gc.getTime());
        return sb;
    }


    /**
     * 获取文件的类型
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    private static String getMimeType(String fileUrl) throws IOException {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileUrl);

        return type;
    }


    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 跳转到GPS设置页面
     *
     * @param activity
     */
    public static void sendToGPSSettingPage(Activity activity) {
        if (activity == null) {
            return;
        }
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent();
        try {
            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
            intent.setAction(android.provider.Settings.ACTION_SETTINGS);
            try {
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 字体高亮的颜色
    private static int hightLightColor = -1;

    /**
     * 将给定的字符串设置为高亮样式
     *
     * @param context
     * @param content
     * @param keywords
     * @return
     */
    public static SpannableStringBuilder getSpannableString(Context context, String content,
                                                            String... keywords) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        if (keywords.length > 0) {
            for (int i = 0; i < keywords.length; i++) {
                String each = keywords[i];
                setSpan(context, content, builder, each);
            }
        } else {
            return builder;
        }
        return builder;
    }

    /**
     * 在制定文字中，将给定的文字，进行高亮显示
     *
     * @param context
     * @param content
     * @param builder
     * @param keyword
     */
    private static void setSpan(Context context, String content, SpannableStringBuilder builder,
                                String keyword) {
        int index = content.indexOf(keyword);// 计算出关键字的起始位置
        if (index == -1) {// 没有找到关键字
            return;
        } else {
            // startIndex：计算当前迭代时，对比文字相对与总文字的起始位置
            int startIndex = builder.toString().indexOf(content);

            if (hightLightColor == -1) {
                hightLightColor = context.getResources().getColor(R.color.colorPrimary);
            }
            ForegroundColorSpan hightLightSpan = new ForegroundColorSpan(hightLightColor);
            builder.setSpan(
                    hightLightSpan,
                    startIndex + index,
                    startIndex + index + keyword.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            content = content.substring(index + keyword.length());
            setSpan(context, content, builder, keyword);
        }
    }

    /**
     * 在给定的文字内容中，为关键字加上特定的颜色
     *
     * @param content 文字内容
     * @param keyword 关键字
     * @param color   指定颜色
     * @return
     */
    public static SpannableStringBuilder addColorToString(SpannableStringBuilder content,
                                                          String keyword, int color) {
        int index = content.toString().indexOf(keyword);// 计算出关键字的起始位置
        if (index == -1) {// 没有找到关键字
            return content;
        }
        ForegroundColorSpan hightLightSpan = new ForegroundColorSpan(color);
        content.setSpan(
                hightLightSpan,
                index,
                index + keyword.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return content;
    }


    /**
     * 打印日志
     *
     * @param msg
     */
    @SuppressWarnings("unused")
    public static void Log(String msg) {
        if (msg == null || !SystemToolsHelper.isLogData()) {
            return;
        }
        LogM.showLog(msg);
    }


    /**
     * 打印日志
     *
     * @param object
     * @param msg
     */
    @SuppressWarnings("unused")
    public static void Log(Object object, String msg) {
        if (object == null || !SystemToolsHelper.isLogData()) {
            Log(msg);
            return;
        }
        LogM.showLog(object.toString() + " " + msg);
    }


    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str
                .replaceAll("【", "[")
                .replaceAll("】", "]")
                .replaceAll("！", "!")
                .replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 重启app
     *
     * @param c
     */
    public static void doRestart(Activity c) {
        try {
            if (c != null) {
                PackageManager pm = c.getPackageManager();
                if (pm != null) {
                    Intent mStartActivity = pm.getLaunchIntentForPackage(c.getPackageName());
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent.getActivity(
                                c,
                                mPendingIntentId,
                                mStartActivity,
                                PendingIntent.FLAG_CANCEL_CURRENT
                        );
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500, mPendingIntent);
                        c.finish();
                        System.exit(0);
                    } else {
                        SystemTools.Log("Was not able to restart application, mStartActivity null");
                    }
                } else {
                    SystemTools.Log("Was not able to restart application, PM null");
                }
            } else {
                SystemTools.Log("Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            SystemTools.Log("Was not able to restart application");
        }
    }


    /**
     * 将url转换成hashkey
     *
     * @param url
     * @return
     */
    public static String hashKeyFormUrl(String url) {
        String cacheKey = null;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    /**
     * 将double转换成包含%号的字符串
     *
     * @param percent
     * @return
     */
    public static String getChangeNumToPercentage(double percent) {
        // 获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        // 设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        // 最后格式化并输出
        return nt.format(percent);
    }

    /**
     * 统一activity的页面跳转
     *
     * @param activity
     * @param intent
     */
    public static void sendToNewActivity(Activity activity, Intent intent) {
        if (activity == null || intent == null) {
            return;
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    /**
     * 统一activity的页面跳转
     *
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void sendToNewActivity(Activity activity, Intent intent, int requestCode) {
        if (activity == null || intent == null) {
            return;
        }
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    /**
     * 统一activity的页面跳转
     *
     * @param fragment
     * @param intent
     * @param requestCode
     */
    public static void sendToNewActivity(Fragment fragment, Intent intent, int requestCode) {
        if (fragment == null || intent == null) {
            return;
        }
        fragment.startActivityForResult(intent, requestCode);
        fragment.getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    /**
     * 复制文本到剪切板
     *
     * @param context
     * @param str
     */
    public static void copyStrToClipboard(Context context, String str) {
        if (context == null) {
            return;
        }
        str = str == null ? "" : str;
        ClipboardManager clipboardManager = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", str);
        clipboardManager.setPrimaryClip(clipData);
    }

    private static long lastClickTime = 0;

    /**
     * 功能描述:判断是否为快速连续点击
     *
     * @return
     */
    public synchronized static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
