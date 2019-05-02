package com.manu.componentizationsamples.samples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.manu.componentizationsamples.R;

@Route(path = ThreeActivity.PATH)
public class ThreeActivity extends AppCompatActivity {

    public static final String PATH = "/test/threeActivity";
    public static final String PARAM_RESULT = "param_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        Intent intent = getIntent();
        //setResult
        intent.putExtra(PARAM_RESULT,"这是返回携带的参数");
        setResult(RESULT_OK,intent);
    }
}
