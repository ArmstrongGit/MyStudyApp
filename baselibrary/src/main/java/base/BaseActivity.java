package base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 作者： 苏晓伟 on 2017-9-28.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化布局layout
        setContentView();
        //初始化头部
        initTitle();
        //初始化界面
        initView();
        //初始化出具
        initData();
    }

    //初始化布局layout
    protected abstract void setContentView();

    //初始化头部
    protected abstract void initTitle();

    //初始化界面
    protected abstract void initView();

    //初始化出具
    protected abstract void initData();

    /**
     * 启动Activity
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    /**
     * findViewById
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T viewById(int viewId){
        return (T)findViewById(viewId);
    }

}
