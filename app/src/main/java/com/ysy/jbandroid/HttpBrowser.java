package com.ysy.jbandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Sylvester on 17/5/10.
 */

public class HttpBrowser extends WebView {

    public HttpBrowser(Context context) {
        super(context);
        initSettings();
    }

    public HttpBrowser(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSettings() {
        // 声明WebSettings子类
        WebSettings settings = this.getSettings();

        // 如果访问的页面中要与Javascript交互，则WebView必须设置支持Javascript
        settings.setJavaScriptEnabled(true);

        // 设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        // 缩放操作
        settings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); // 隐藏原生的缩放控件

        // 其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 关闭WebView中缓存
        settings.setAllowFileAccess(true); // 设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8"); // 设置编码格式

        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void loadBlank() {
        super.loadUrl("about:blank");
    }

    @Override
    public void loadUrl(final String url) {
        super.loadUrl(url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SocketHttp socketHttp = new SocketHttp(url);
                if (socketHttp.request()) {
                    System.out.println("[Status]\n" + socketHttp.getStatus() + "\n" +
                            "[Header]\n" + socketHttp.getHeaderMap() + "\n" +
                            "[ErrorMsg]\n" + socketHttp.getErrorMsg() + "\n" +
                            "[Content]\n" + socketHttp.getContent()
                    );
                }
            }
        }).start();
    }
}
