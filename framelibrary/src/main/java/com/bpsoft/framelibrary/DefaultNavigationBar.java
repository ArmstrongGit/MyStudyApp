package com.bpsoft.framelibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import navigationbar.AbsNavigationBar;

/**
 * 作者： 苏晓伟 on 2017-10-9.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public class DefaultNavigationBar extends
        AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {


    public DefaultNavigationBar(Builder.DefaultNavigationParams Params) {
        super(Params);
    }


    @Override
    public int bindLayoutId() {
        return R.layout.act_title;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.act_title,getParams().mTitle);
        setOnClickListener(R.id.rel_back,getParams().mRelBackListener);
    }




    public static class Builder extends AbsNavigationBar.Builder{

        DefaultNavigationParams p;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            p=new DefaultNavigationParams(context,parent);
        }

        public Builder(Context context) {
            super(context,null);
            p=new DefaultNavigationParams(context,null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar=new DefaultNavigationBar(p);
            return navigationBar;
        }

        //1.设置所以效果

        public DefaultNavigationBar.Builder setTitle(String title){
            p.mTitle=title;
            return this;
        }

        public DefaultNavigationBar.Builder setRelBackOnClickListener(View.OnClickListener listener){
            p.mRelBackListener=listener;
            return this;
        }

        public static class DefaultNavigationParams extends
                AbsNavigationBar.Builder.AbsNavigationParams{

            //2.所有效果放置

            public String mTitle;
            public View.OnClickListener mRelBackListener;
            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }

    }

}
