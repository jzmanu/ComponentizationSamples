package com.manu.componentizationsamples.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Powered by jzman.
 * Created on 2018/9/27 0027.
 */
@Route(path = "/service/singleService")
public class SingleService implements IProvider {
    public static final String PATH = "/service/singleService";
    private Context mContext;

    //具体服务
    public void showMessage() {
        Toast.makeText(mContext, "这是对外提供的服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
        Log.i("SingleService", "SingleService has init");
    }
}
