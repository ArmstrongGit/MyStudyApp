package com.study;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;

/**
 * 作者： 苏晓伟 on 2017-8-9.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 ${ORGANIZATION_NAME}. All rights reserved.
 */

public class MApp extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();


    }

    public static Context getAppContext(){
        return mContext;
    }

}
