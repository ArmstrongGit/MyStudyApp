package dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.*;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpsoft.baselibrary.R;

import java.lang.ref.WeakReference;

/**
 * 作者： 苏晓伟 on 2017-9-29.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:自定义万能Dialog
 */

public class AlertDialog extends Dialog{


    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        mAlert=new AlertController(this,getWindow());
    }


    public static class Builder{

        private final dialog.AlertController.AlertParams P;
        private  int mTheme;


        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * <p>
         * The default alert dialog theme is defined by
         * {@link android.R.attr#alertDialogTheme} within the parent
         * {@code context}'s theme.
         *
         * @param context the parent context
         */
        public Builder(@NonNull Context context) {
            this(context, R.style.dialog);
        }



        /**
         * Creates a builder for an alert dialog that uses an explicit theme
         * resource.
         * <p>
         * The specified theme resource ({@code themeResId}) is applied on top
         * of the parent {@code context}'s theme. It may be specified as a
         * style resource containing a fully-populated theme, such as
         * {@link R.style#Theme_AppCompat_Dialog}, to replace all
         * attributes in the parent {@code context}'s theme including primary
         * and accent colors.
         * <p>
         * To preserve attributes such as primary and accent colors, the
         * {@code themeResId} may instead be specified as an overlay theme such
         * as {@link R.style#ThemeOverlay_AppCompat_Dialog}. This will
         * override only the window attributes necessary to style the alert
         * window as a dialog.
         * <p>
         * Alternatively, the {@code themeResId} may be specified as {@code 0}
         * to use the parent {@code context}'s resolved value for
         * {@link android.R.attr#alertDialogTheme}.
         *
         * @param context the parent context
         * @param themeResId the resource ID of the theme against which to inflate
         *                   this dialog, or {@code 0} to use the parent
         *                   {@code context}'s default alert dialog theme
         */
        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new AlertController.AlertParams(
                    context,themeResId);
        }


        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @param view
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public AlertDialog.Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置布局内容的layoutid
         * @param layoutResId
         * @return
         */
        public AlertDialog.Builder setContentView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 设置文本
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId,CharSequence text){
            P.mTextArray.put(viewId,text);
            return this;
        }

        /**
         * 配置点击事件
         * @param view
         * @param listener
         * @return
         */
        public Builder setOnclickListener(int view, View.OnClickListener listener){
            P.mClickArray.put(view,listener);
            return this;
        }

        /**
         * 设置全屏
         * @return
         */
        public Builder fullWidth(){
          P.mWidth= ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出是否有动画
         * @return
         */
        public Builder formButtom(boolean isAnimation){
           if(isAnimation){
            P.mAnimation=R.style.dialog_from_bottom_anim;
           }
           P.mGravity= Gravity.BOTTOM;
            return this;
        }


        /**
         * 设置dialog的宽高
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width,int height){
            P.mWidth=width;
            P.mHeight=height;
            return this;
        }

        /**
         * 设置默认动画
         * @return
         */
//        public Builder addDefaultAnimation(){
//            P.mAnimation=R.style.dialog_scale_anim;
//            return this;
//        }

        /**
         * 设置自定义动画
         * @param styleAnimation
         * @return
         */
        public Builder adDefaultAnimations(int styleAnimation){
            P.mAnimation=styleAnimation;
            return this;
        }


        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public AlertDialog.Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
         * setOnDismissListener}.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public AlertDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public AlertDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public AlertDialog.Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public AlertDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            final AlertDialog dialog = new AlertDialog(P.mContext,P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }

    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId,text);
    }

    public  <T extends View> T getView(int viewId) {
       return mAlert.getView(viewId);
    }

    /**
     *
     * 设置点击事件
     * @param viewId
     * @param onClickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {

        mAlert.setOnclickListener(viewId,onClickListener);
    }

}
