package com.manu.module_one;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ModuleOneActivity.PATH)
public class ModuleOneActivity extends AppCompatActivity {

    public static final String PATH = "/module1/module-one";

    @BindView(R2.id.btnModuleTwoActivity)
    Button btnModuleTwoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_one_activity_one_module);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.btnModuleTwoActivity)
    public void onViewClicked() {
        ARouter.getInstance().build("/module2/module-two").navigation();
    }
}
