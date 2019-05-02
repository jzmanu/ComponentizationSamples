package com.manu.componentizationsamples.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.manu.componentizationsamples.R;

@Route(path = FirstActivity.PATH)
public class FirstActivity extends AppCompatActivity {

    public static final String PATH = "/test/firstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }
}
