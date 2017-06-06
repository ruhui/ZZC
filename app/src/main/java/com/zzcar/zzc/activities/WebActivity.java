
package com.zzcar.zzc.activities;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.views.widget.NavBar;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_web)
public class WebActivity extends BaseActivity {

    @ViewById(R.id.webview)
    WebView mWebView;
    @ViewById(R.id.mNavbar)
    NavBar2 mHeaderView;

    private String webUrl = "";


    @AfterViews
    void initView() {
        String titlebar = getIntent().getStringExtra("titleBar");
        webUrl = getIntent().getStringExtra("webUrl");
        mHeaderView.setLeftMenuIcon(R.drawable.nav_icon_lift_default);

        mHeaderView.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                finish();
            }
        });

        mHeaderView.setTitle(titlebar);

        mWebView.getSettings().setJavaScriptEnabled(true);
        // mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mHeaderView.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                closeProgress();
            }
        });
        mWebView.loadUrl(webUrl);
    }


    @Override
    public void onNetChange(int netMobile) {

    }
}
