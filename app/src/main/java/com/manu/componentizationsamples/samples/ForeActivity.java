package com.manu.componentizationsamples.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.manu.componentizationsamples.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ForeActivity.PATH)
public class ForeActivity extends AppCompatActivity {
    public static final String PATH = "/test/foreActivity";

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fore);
        ButterKnife.bind(this);
        //加载 assets 目录下的 HTML 文件
        webView.loadUrl("file:///android_asset/scheme-test.html");
    }
}
