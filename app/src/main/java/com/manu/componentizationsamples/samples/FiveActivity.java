package com.manu.componentizationsamples.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.manu.componentizationsamples.R;

/**
 * 使用Scheme跳转
 */
@Route(path = FiveActivity.PATH)
public class FiveActivity extends AppCompatActivity {

    public static final String PATH = "/test/fiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
    }
}
