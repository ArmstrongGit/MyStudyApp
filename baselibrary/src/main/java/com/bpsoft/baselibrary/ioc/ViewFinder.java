package com.bpsoft.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * 作者： 苏晓伟 on 2017-9-5.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description: ViewFinderById的辅助类
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;


    public ViewFinder(View view) {
        this.mView=view;
    }

    public ViewFinder(Activity activity) {
        this.mActivity=activity;
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }


}
