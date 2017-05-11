package com.ysy.jbandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private HttpBrowser mBrowser;
    private EditText mAddEdt;
    private ImageView mGoImg;
    private ImageView mCtrlImg;
    private boolean isShowingWeb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.browser_toolbar));
        initViews();
    }

    private void initViews() {
        mBrowser = (HttpBrowser) findViewById(R.id.browser_webView);
        mGoImg = (ImageView) findViewById(R.id.browser_go_img);
        mAddEdt = (EditText) findViewById(R.id.browser_edt);
        mCtrlImg = (ImageView) findViewById(R.id.browser_ctrl_img);

        mGoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mAddEdt.getText().toString();
                mBrowser.loadUrl("http://" + url + "/");
                mCtrlImg.setImageResource(R.mipmap.ic_close_white_24dp);
                isShowingWeb = true;
            }
        });

        mCtrlImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingWeb) {
                    isShowingWeb = false;
                    mBrowser.loadBlank();
                    mCtrlImg.setImageResource(R.mipmap.ic_add_white_24dp);
                } else {
                    isShowingWeb = true;
                    mBrowser.loadUrl("http://www.hao123.com");
                    mCtrlImg.setImageResource(R.mipmap.ic_close_white_24dp);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBrowser.canGoBack()) {
            mBrowser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBrowser != null) {
            mBrowser.clearHistory();
            ((ViewGroup) mBrowser.getParent()).removeView(mBrowser);
            mBrowser.loadBlank();
            mBrowser.stopLoading();
            mBrowser.setWebViewClient(null);
            mBrowser.destroy();
            mBrowser = null;
        }
    }
}
