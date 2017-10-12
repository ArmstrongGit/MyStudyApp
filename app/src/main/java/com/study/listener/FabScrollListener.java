package com.study.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.study.callback.HideScrollListener;

/**
 * 作者： 苏晓伟 on 2017-8-28.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 ${ORGANIZATION_NAME}. All rights reserved.
 */

public class FabScrollListener extends RecyclerView.OnScrollListener {

    private static final int THRESHOLD=20;
    private final HideScrollListener lisener;
    private int distance=0;
    private boolean visible=true;


    public FabScrollListener(HideScrollListener listener) {
        this.lisener=listener;
    }

    /**
     *
     * @param recyclerView
     * @param dx
     * @param dy y轴方向的增量
     *           有正和负
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //当正在执行动画的时候，就不再执行了
        if(distance>THRESHOLD&&visible){
            //隐藏动画
            visible=false;
            lisener.onHide();
            distance=0;

        }else if(distance<-20&&!visible){
            //显示动画
            visible=true;
            lisener.onShow();
            distance=0;
        }
        if(visible&&dy>0||(!visible&&dy<0)){
            distance+=dy;

        }
    }
}
