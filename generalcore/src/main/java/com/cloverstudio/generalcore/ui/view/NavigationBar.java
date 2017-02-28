
package com.cloverstudio.generalcore.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloverstudio.generalcore.R;
import com.cloverstudio.generalcore.utils.MyImageLoader;


/**
 * 导航栏
 *
 * @author wlei
 */
public class NavigationBar extends LinearLayout {
    private Context context;

    private CircleImageView backImg;

    private Button navLeftBtn;

    private TextView navTitle;

    private ImageView rightBtnImg;

    private Button rightBtn;

    private View container;

    private FrameLayout framenLa_id;

    public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_nav_bar, this, true);
        backImg = (CircleImageView) findViewById(R.id.backImg);
        navLeftBtn = (Button) findViewById(R.id.navLeftBtn);
        navTitle = (TextView) findViewById(R.id.navTitle);
        rightBtnImg = (ImageView) findViewById(R.id.rightBtnImg);
        rightBtn = (Button) findViewById(R.id.rightBtn);
        container = findViewById(R.id.container);
        framenLa_id = (FrameLayout) findViewById(R.id.framenLa_id);

    }

    /**
     * 设置导航条title的颜色
     *
     * @param color
     */
    public void setNavBarTitleColor(int color) {
        navTitle.setTextColor(color);
    }

    /**
     * 设置导航栏的背景颜色
     *
     * @param color
     */
    public void setNavBarBgColor(int color) {
        container.setBackgroundColor(color);
    }

    /**
     * 设置导航栏背景透明度
     *
     * @param alpha
     */
    public void setNavBgAlpha(float alpha) {
        container.setAlpha(alpha);
    }

    /**
     * 设置导航栏背景图片
     *
     * @param resid
     */
    public void setNavBarBg(int resid) {
        container.setBackgroundResource(resid);
    }

    /**
     * 设置导航栏标题
     *
     * @param title
     */
    public void setNavTitle(String title) {
        if (title == null) {
            return;
        }
        navTitle.setText(title);
        navTitle.setVisibility(View.VISIBLE);
        findViewById(R.id.navImageTitle).setVisibility(View.GONE);
    }

    /**
     * 用图片来设置导航栏标题
     *
     * @param imageResID
     */
    public void setNavTitleByImage(int imageResID) {
        findViewById(R.id.navImageTitle).setVisibility(View.VISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.navImageTitle);
        imageView.setImageResource(imageResID);
        // 将文字标题设置为隐藏
        navTitle.setVisibility(View.GONE);
    }

    /**
     * 设置左侧按钮被点击后执行的操作
     *
     * @param onClickListener
     */
    public void setLeftBarItemOnClick(OnClickListener onClickListener) {
        navLeftBtn.setOnClickListener(onClickListener);
    }

    /**
     * 设置右侧按钮被点击后执行的操作
     *
     * @param onClickListener
     */
    public void setRightBarItemOnClick(OnClickListener onClickListener) {
        rightBtn.setOnClickListener(onClickListener);
        rightBtnImg.setOnClickListener(onClickListener);
    }

    /**
     * 隐藏左侧按钮
     */
    public void hideLeftBtn() {
        navLeftBtn.setVisibility(View.GONE);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBackBtn() {
        backImg.setVisibility(View.GONE);
        navLeftBtn.setVisibility(View.GONE);
    }

    /**
     * 显示左侧按钮
     */
    public void showLeftBtn() {
        navLeftBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左侧按钮的图标
     *
     * @param resid
     */
    public void setLeftBtnImage(int resid) {
        if (resid > 0) {
            backImg.setImageResource(resid);
            backImg.setBorderWidth(0);
            backImg.setBorderColor(getResources().getColor(android.R.color.transparent));
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) backImg
                    .getLayoutParams();
            params.width = getResources().getDimensionPixelOffset(R.dimen.backimg_image_width);
            params.height = getResources().getDimensionPixelOffset(R.dimen.backimg_image_height);
            backImg.setLayoutParams(params);
        }
    }

    /**
     * 设置左侧按钮的大小
     *
     * @param width  按钮宽度
     * @param height 按钮高度
     */
    public void setLeftBtnImageSize(int width, int height) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) backImg
                .getLayoutParams();
        params.width = width;
        params.height = height;
        backImg.setLayoutParams(params);
    }

    /**
     * 显示与快报类似的返回按钮
     */
    public void showKuaibaoLikeBackBtn() {
        findViewById(R.id.kuaiBaoLikeBackBtn).setVisibility(View.VISIBLE);
        findViewById(R.id.backImg).setVisibility(View.GONE);
    }

    /**
     * 按照给定的uri设置左侧图标
     *
     * @param uri         网络图片的url地址
     * @param defImgResId 默认图片的资源id
     */
    public void setLeftBtnImageByUri(String uri, int defImgResId) {
        if (uri != null && !uri.equals("")) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) backImg
                    .getLayoutParams();
            params.width = getResources().getDimensionPixelOffset(R.dimen.useravatar_image_width);
            params.height = getResources().getDimensionPixelOffset(R.dimen.useravatar_image_height);
            backImg.setLayoutParams(params);
            try {
//                backImg.setImageResource(R.drawable.home_nav_left_icon);// 默认图片
                backImg.setImageResource(defImgResId);// 默认图片
                MyImageLoader.loadImage(uri, backImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示右侧的按钮
     */
    public void showRightBtn() {
        rightBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 显示右侧的图片按钮
     */
    public void showRightImageBtn() {
        rightBtnImg.setVisibility(View.VISIBLE);
    }

    /**
     * 启用、禁用右侧按钮
     *
     * @param enable
     */
    public void setRightBtnEnable(boolean enable) {
        rightBtn.setEnabled(enable);
    }

    /**
     * 隐藏右侧按钮
     */
    public void hideRightBtn() {
        rightBtn.setVisibility(View.GONE);
        rightBtnImg.setVisibility(View.GONE);
    }

    /**
     * 给右侧导航按钮设置一个背景图片
     *
     * @param resid
     */
    public void setRightBtnImage(int resid) {
        if (resid > 0) {
            ImageView rightBtnImg = (ImageView) findViewById(R.id.rightBtnImg);
            rightBtnImg.setImageResource(resid);
            rightBtnImg.setVisibility(View.VISIBLE);
            setRightBtnLabel("");// 设置图片后，隐藏按钮上的文字
        } else {
            rightBtnImg.setVisibility(View.GONE);
        }
    }

    /**
     * 给右侧导航按钮设置一个背景图片,并指定图片的大小
     *
     * @param resid
     * @param width
     * @param height
     */
    public void setRightBtnImage(int resid, int width, int height) {
        if (resid > 0) {
            ImageView rightBtnImg = (ImageView) findViewById(R.id.rightBtnImg);
            rightBtnImg.setImageResource(resid);
            rightBtnImg.setVisibility(View.VISIBLE);
            setRightBtnLabel("");// 设置图片后，隐藏按钮上的文字
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.gravity = Gravity.CENTER;
            rightBtnImg.setLayoutParams(params);
            rightBtnImg.setVisibility(View.VISIBLE);
        } else {
            rightBtnImg.setVisibility(View.GONE);
        }
        rightBtn.setVisibility(View.GONE);
    }

    /**
     * 显示右侧按钮显示的文字
     *
     * @param label
     */
    public void setRightBtnLabel(String label) {
        if (label == null) {
            return;
        }
        rightBtn.setText(label);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtnImg.setVisibility(View.GONE);
    }

    /**
     * 设置右侧按钮左侧的图片
     *
     * @param resID
     * @param imgWidth
     * @param imgHeight
     */
    public void setRightBtnLabelLeftImage(int resID, int imgWidth, int imgHeight) {
        Drawable drawable1 = getResources().getDrawable(resID);
        drawable1.setBounds(0, 0, imgWidth, imgHeight);// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
        rightBtn.setCompoundDrawables(drawable1, null, null, null);// 只放左边
    }

    /**
     * 设置右侧按钮的文字颜色
     *
     * @param color
     */
    public void setRightBtnLabelTextcolor(int color) {
        rightBtn.setTextColor(color);
    }

    /**
     * 设置右侧EX按钮可见
     */
    public void showRightEXLabel() {
        TextView ex_right_label = (TextView) findViewById(R.id.ex_right_label);
        ex_right_label.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧EX按钮的背景
     *
     * @param resid
     */
    public void setRightEXLabelBg(int resid) {
        TextView ex_right_label = (TextView) findViewById(R.id.ex_right_label);
        ex_right_label.setBackgroundResource(resid);
    }

    /**
     * 设置右侧EX按钮的标题
     *
     * @param text
     */
    public void setRightEXLabelText(String text) {
        TextView ex_right_label = (TextView) findViewById(R.id.ex_right_label);
        ex_right_label.setText(text);
    }

    /**
     * 设置右侧EX按钮的颜色
     *
     * @param color
     */
    public void setRightExLabelTextColor(int color) {
        TextView ex_right_label = (TextView) findViewById(R.id.ex_right_label);
        ex_right_label.setTextColor(color);
    }

    /**
     * 设置右侧EX按钮被点击后执行的操作
     *
     * @param onClickListener
     */
    public void setRightEXBtnOnClick(OnClickListener onClickListener) {
        if (onClickListener == null) {
            return;
        }
        TextView ex_right_label = (TextView) findViewById(R.id.ex_right_label);
        ex_right_label.setOnClickListener(onClickListener);
    }

    /**
     * 设置右侧的被景
     *
     * @param backR
     */
    public void setRightBtnLabelBackgroundResource(int backR) {
        rightBtn.setBackgroundResource(backR);
    }

    /**
     * 设置右侧按钮的文字大小
     *
     * @param size
     */
    public void setRightBtnLabelTextSize(int size) {
        rightBtn.setTextSize(size);
    }

    /**
     * 显示更多按钮
     *
     * @param onClickListener 更多按钮的点击事件
     */
    public void showMoreBtn(OnClickListener onClickListener) {
        View showMoreBtn = findViewById(R.id.showMoreBtn);
        showMoreBtn.setVisibility(View.VISIBLE);
        if (onClickListener != null) {
            showMoreBtn.setOnClickListener(onClickListener);
        }
    }

    /**
     * 隐藏更多按钮
     */
    public void hideMoreBtn() {
        findViewById(R.id.showMoreBtn).setVisibility(View.GONE);
    }

    /**
     * 显示更多按钮
     */
    public void showMoreBtn() {
        findViewById(R.id.showMoreBtn).setVisibility(View.VISIBLE);
    }

    /**
     * 显示分享按钮
     *
     * @param onClickListener 分享按钮的点击事件
     */
    public void showShareBtn(OnClickListener onClickListener) {
        View shareBtnContianer = findViewById(R.id.shareBtnContianer);
        shareBtnContianer.setVisibility(View.VISIBLE);
        if (onClickListener != null) {
            View shareBtn = findViewById(R.id.shareBtn);
            shareBtn.setOnClickListener(onClickListener);
        }
    }

    /**
     * 显示白色主题
     */
    public void whiteTheme() {
        setNavBarBg(R.drawable.news_detail_page_nav_bg);
        setLeftBtnImage(R.drawable.base_nav_back_btn_dark);
        setRightBtnLabelTextcolor(getResources().getColor(R.color.nav_textcolor_gray));
        setNavBarTitleColor(getResources().getColor(R.color.nav_textcolor_gray));
    }

    /**
     * 设置透明主题
     */
    public void transparentTheme() {
        container.setBackgroundColor(getResources().getColor(R.color.transparent_theme_navbar_bg));
        setLeftBtnImage(R.drawable.base_nav_back_btn);
        setRightBtnLabelTextcolor(Color.WHITE);
        setNavBarTitleColor(Color.WHITE);
    }

    /**
     * 重置首页的导航栏的高度
     */
    public void resetNavbarHeightForMainPage() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelOffset(R.dimen.mainpage_navbar_height)
        );
        container.setLayoutParams(params);
        // 中间图片
        View navImageTitle = findViewById(R.id.navImageTitle);
        FrameLayout.LayoutParams fParams = new FrameLayout.LayoutParams(
                getResources()
                        .getDimensionPixelOffset(R.dimen.mainpage_navimagetitle_img_w),
                getResources()
                        .getDimensionPixelOffset(R.dimen.mainpage_navimagetitle_img_h)
        );
        fParams.gravity = Gravity.CENTER;
        navImageTitle.setLayoutParams(fParams);
        // 右侧按钮
        FrameLayout.LayoutParams rParams = (FrameLayout.LayoutParams) rightBtnImg
                .getLayoutParams();
        rParams.height = getResources().getDimensionPixelOffset(R.dimen.mainpage_rightbtnimg_h);
        rParams.width = getResources().getDimensionPixelOffset(R.dimen.mainpage_rightbtnimg_w);
        rightBtnImg.setScaleType(ScaleType.CENTER_INSIDE);
    }

}
