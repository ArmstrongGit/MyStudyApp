package com.study.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * 作者： 苏晓伟 on 2017-8-23.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 上海商哲信息技术有限公司. All rights reserved.
 */

public class BaseTranslucentActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT
                &&android.os.Build.VERSION.SDK_INT<android.os.Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置虚拟导航栏为透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @SuppressLint("NewApi")
    public void setOrChangeTranslucentColor(Toolbar toolbar,View bottomNavigationBar, int translucentPrimaryColor){
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT
                &&android.os.Build.VERSION.SDK_INT<android.os.Build.VERSION_CODES.LOLLIPOP){
            if(toolbar!=null){
                //1.先设置toolbar的高度
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(this);
                params.height += statusBarHeight ;
                toolbar.setLayoutParams(params );
                //2.设置paddingTop，以达到状态栏不遮挡toolbar的内容。
                toolbar.setPadding(
                        toolbar.getPaddingLeft(),
                        toolbar.getPaddingTop()+getStatusBarHeight(this),
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                //设置顶部的颜色
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
            if(bottomNavigationBar!=null){
                //解决低版本4.4+的虚拟导航栏的
                if(hasNavigationBarShow(getWindowManager())){
                    ViewGroup.LayoutParams p = bottomNavigationBar.getLayoutParams();
                    p.height += getNavigationBarHeight(this);
                    bottomNavigationBar.setLayoutParams(p);
                    //设置底部导航栏的颜色
                    bottomNavigationBar.setBackgroundColor(translucentPrimaryColor);
                }
            }
        }else if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(translucentPrimaryColor);
            getWindow().setStatusBarColor(translucentPrimaryColor);
        }else{
            //<4.4的，不做处理
        }
    }


    private int getNavigationBarHeight(Context context) {
        return getSystemComponentDimen(this, "navigation_bar_height");
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        return getSystemComponentDimen(this, "status_bar_height");
    }

    private static int getSystemComponentDimen(Context context, String dimenName){
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField(dimenName).get(object).toString();
            int height = Integer.parseInt(heightStr);
            //dp--->px
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private static boolean hasNavigationBarShow(WindowManager wm){
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        //获取内容展示部分的高度
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels;
        int widthPixels2 = outMetrics.widthPixels;
        int w = widthPixels-widthPixels2;
        int h = heightPixels-heightPixels2;
        System.out.println("~~~~~~~~~~~~~~~~h:"+h);
        return  w>0||h>0;//竖屏和横屏两种情况。
    }


}
