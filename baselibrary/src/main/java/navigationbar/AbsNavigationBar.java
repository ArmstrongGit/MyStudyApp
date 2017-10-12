package navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者： 苏晓伟 on 2017-10-9.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:头部的Builder基类
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;

    private View mNavigationView;

    public AbsNavigationBar(P Params) {
        this.mParams = Params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    protected void setText(int viewId, String text) {
        TextView tv= findViewById(viewId);
        if(!TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    protected void setOnClickListener(int viewId, View.OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }

    public <T extends View> T findViewById(int viewId){
        return (T) mNavigationView.findViewById(viewId);
    }


    /**
     * 绑定和创建View
     */
    private void createAndBindView() {

        if(mParams.mParent==null){
            //获取Activity跟布局,view源码
            ViewGroup activityRoot=(ViewGroup)
                    ((Activity)mParams.mContext).getWindow().getDecorView();
            mParams.mParent=(ViewGroup)activityRoot.getChildAt(0);


        }

        if(mParams.mParent==null){
            return;
        }

        //1.创建view
        mNavigationView= LayoutInflater.from(mParams.mContext)
                .inflate(bindLayoutId(),mParams.mParent,false);
        //2.添加
        mParams.mParent.addView(mNavigationView,0);

        applyView();
    }




    //Builder设计模式 套路：AbsNavitationBar Builder Params
    public static abstract class Builder{

        public Builder(Context context, ViewGroup parent) {

        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams{
            public  Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext=context;
                this.mParent=parent;
            }
        }


    }

}
