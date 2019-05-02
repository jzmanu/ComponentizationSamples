package com.manu.componentizationsamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.manu.componentizationsamples.samples.FirstActivity;
import com.manu.componentizationsamples.samples.ForeActivity;
import com.manu.componentizationsamples.samples.SecondActivity;
import com.manu.componentizationsamples.samples.ThreeActivity;
import com.manu.componentizationsamples.service.ServiceManage;
import com.manu.componentizationsamples.service.SingleService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnFirstActivity)
    Button btnFirstActivity;
    @BindView(R.id.btnSecondActivity)
    Button btnSecondActivity;
    @BindView(R.id.btnThreeActivity)
    Button btnThreeActivity;
    @BindView(R.id.btnForeActivity)
    Button btnForeActivity;
    @BindView(R.id.btnModuleOne)
    Button btnModuleOne;
    @BindView(R.id.btnModuleTwo)
    Button btnModuleTwo;
    @BindView(R.id.btnService)
    Button btnService;
    @BindView(R.id.btnServiceManage)
    Button btnServiceManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnFirstActivity, R.id.btnSecondActivity, R.id.btnThreeActivity, R.id.btnForeActivity,
            R.id.btnModuleOne, R.id.btnModuleTwo, R.id.btnService,R.id.btnServiceManage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnFirstActivity:
                //应用内跳转
                ARouter.getInstance()
                        .build(FirstActivity.PATH)
                        .navigation();
                break;
            case R.id.btnSecondActivity:
                //应用内携带参数跳转
                ARouter.getInstance()
                        .build(SecondActivity.PATH)
                        .withString(SecondActivity.PARAM, "这是跳转携带的参数")
                        .navigation();
                break;
            case R.id.btnThreeActivity:
                //Activity返回结果处理
                ARouter.getInstance()
                        .build(ThreeActivity.PATH)
                        .navigation(this, 100);
                break;
            case R.id.btnForeActivity:
                // 通过Uri跳转及参数解析
                ARouter.getInstance()
                        .build(ForeActivity.PATH)
                        .navigation();
                break;
            case R.id.btnModuleOne:
                //跳转Module-one
                ARouter.getInstance()
                        .build(Module.MODULE_ONE)
                        .navigation();
                break;
            case R.id.btnModuleTwo:
                //跳转Module-two
                ARouter.getInstance()
                        .build(Module.MODULE_TWO)
                        .navigation();
                break;
            case R.id.btnService:
        //通过服务类class调用
        ARouter.getInstance().navigation(SingleService.class).showMessage();
        //通过服务类Path调用
        ((SingleService) ARouter.getInstance().
                build(SingleService.PATH)
                .navigation())
                .showMessage();
                break;
            case R.id.btnServiceManage:
                //服务管理，通过依赖注入的方式获取服务
                ServiceManage manage = new ServiceManage();
                manage.getService();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Toast.makeText(MainActivity.this, data.getStringExtra(ThreeActivity.PARAM_RESULT), Toast.LENGTH_SHORT).show();
        }
    }

}
