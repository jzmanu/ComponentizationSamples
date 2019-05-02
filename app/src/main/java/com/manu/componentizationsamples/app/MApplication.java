package com.manu.componentizationsamples.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.manu.componentizationsamples.BuildConfig;


/**
 * Powered by jzman.
 * Created on 2018/9/26 0026.
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //必须在初始化ARouter之前配置
        if (BuildConfig.DEBUG){
            //日志开启
            ARouter.openLog();
            //调试模式开启，如果在install run模式下运行，则必须开启调试模式
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
