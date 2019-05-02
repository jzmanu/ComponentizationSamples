package com.manu.componentizationsamples.service;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Powered by jzman.
 * Created on 2018/9/27 0027.
 */
public class ServiceManage {
    @Autowired
    SingleService singleService;

    public ServiceManage(){
        //通过依赖注入的方式获取服务
        ARouter.getInstance().inject(this);
    }

    public void getService(){
        singleService.showMessage();
    }
}
