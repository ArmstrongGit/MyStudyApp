package com.study;

import android.app.Application;

import hotfix.ExceptionCrashHandler;
import hotfix.FixDexManager;


/**
 * 作者： 苏晓伟 on 2017-8-30.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 ${ORGANIZATION_NAME}. All rights reserved.
 */

public class BaseApplicatuon extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //设置全局异常捕捉类
        ExceptionCrashHandler.getmInstance().init(this);


        //加载所有修复的dex包
        try {
            FixDexManager fixDexManager=new FixDexManager(this);
            fixDexManager.loadFixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
