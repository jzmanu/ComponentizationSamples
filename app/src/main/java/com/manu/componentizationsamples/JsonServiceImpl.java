package com.manu.componentizationsamples;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;

/**
 * 完成Uri参数种json的解析
 * Powered by jzman.
 * Created on 2018/9/26 0026.
 */

@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return JSON.parseObject(input,clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return JSON.toJSONString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return JSON.parseObject(input,clazz);
    }

    @Override
    public void init(Context context) {

    }
}
