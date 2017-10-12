package com.bpsoft.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者： 苏晓伟 on 2017-9-5.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public class ViewUtil {

    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }


    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }

    public static void inject(View view,Object object){
        inject(new ViewFinder(view),object);
    }

    //兼容 上面三个方法 object-->反射需要执行的类
    private  static void inject(ViewFinder finder,Object object){
        injectFiled(finder,object);
        injectEvent(finder,object);
    }


    /**
     *
     * 注入事件
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类里面所有的方法
        Class<?> clazz= object.getClass();
        //获取所有属性包括私有和公有
        Method[] methods= clazz.getDeclaredMethods();










        //2.获取OnClick里面的value
        for (Method method : methods) {
            OnClick onClick=method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds=onClick.value();
                for (int viewId : viewIds) {
                    //3.findViewById找到view
                    View view=finder.findViewById(viewId);

                    //扩展功能点击的时候去检测网络
                    boolean isCheckNet=method.getAnnotation(CheckNet.class)!=null;

                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(method,object,isCheckNet));
                    }
                }
            }
        }


        //4.view.setOnClickListener



    }

    /**
     * 注入属性
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取类里面所有的属性

        Class<?> clazz= object.getClass();
        //获取所有属性包括私有和公有
        Field[] fields= clazz.getDeclaredFields();

        //2.获取ViewById的里面的value值
        for (Field field : fields) {
            ViewById viewById=field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的ID值-->R.id.xxx
                int viewId=viewById.value();
                //3.findViewById找到view
                View view= finder.findViewById(viewId);
                if (view != null) {
                    //能够注入所有的修饰符 private public
                    field.setAccessible(true);
                    //4.动态的注入找到的view
                    try {
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }



    }



    private static class DeclaredOnClickListener implements View.OnClickListener{

        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;
        public DeclaredOnClickListener(Method method, Object object,boolean isCheckNet) {

            this.mObject=object;
            this.mMethod=method;
            this.mIsCheckNet=isCheckNet;
        }

        @Override
        public void onClick(View v) {

            //需不需要检测网络
            if(mIsCheckNet){
                //需要检测
                if(!networkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(), "您已进入没网的异次元", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            //点击调用该方法
            try {
                //所以方法都可以，包括私有公有
                mMethod.setAccessible(true);
                //反射执行方法
                mMethod.invoke(mObject,v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                try {
                    mMethod.invoke(mObject,null);
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
    private static  boolean networkAvailable(Context context){
        try{
            //得到链接管理器对象
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo =connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo!=null&&activeNetworkInfo.isConnected()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
