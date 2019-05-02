package com.manu.componentizationsamples.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.manu.componentizationsamples.R;
import com.manu.componentizationsamples.bean.ScoreBean;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = SixActivity.PATH)
public class SixActivity extends AppCompatActivity {
    public static final String PATH = "/test/sixActivity";
    @Autowired
    public String name;
    @Autowired
    public int age;
    @Autowired
    //如果要在Uri中传递自定义对象，在参数中要使用json字符串(encodeURI转码)传递，创建一个实现了SerializationService接口的类完成json的解析
    public ScoreBean score;
    @BindView(R.id.tvData)
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        ButterKnife.bind(this);
        //参数自动依赖注入
        ARouter.getInstance().inject(this);
        String info = "name=" + name + ",age=" + age + ",score=" + score;
        tvData.setText(info);
        Log.i("SixActivity", info);
    }
}
