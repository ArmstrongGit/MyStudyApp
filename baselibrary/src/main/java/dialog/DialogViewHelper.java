package dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 作者： 苏晓伟 on 2017-9-29.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:DialogView的辅助处理类
 */

class DialogViewHelper {

    private View mContentView=null;
    //防止霸气侧漏
    private SparseArray<WeakReference<View>> mViews;
    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        mContentView= LayoutInflater.from(mContext).inflate(mViewLayoutResId,null);

    }

    public DialogViewHelper() {
        mViews=new SparseArray<>();
    }

    /**
     * 设置布局View
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    public void setText(int viewId, CharSequence text) {
        //减少findviewbyid次数
        TextView tv= getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> weakReference=mViews.get(viewId);
        View view=null;
        if(weakReference!=null){
            view=weakReference.get();

        }
        if (view == null) {
            view=mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId,new WeakReference<View>(view));
            }
        }

        return (T)view;
    }

    /**
     *
     * 设置点击事件
     * @param viewId
     * @param onClickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {

        View view=getView(viewId);
        if(view!=null) {
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     *
     * 获取ContentView
     * @return
     */
    public View getContentView() {
        return mContentView;
    }
}
