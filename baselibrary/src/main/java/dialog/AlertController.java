package dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;

/**
 * 作者： 苏晓伟 on 2017-9-29.
 * 邮箱：armstrong.su@b-psoft.com
 * Description:
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog alertDialog, Window window) {

        this.mDialog=alertDialog;
        this.mWindow=window;

    }

    public void setViewHelper(DialogViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }

    /**
     * 获取Dialog
     * @return
     */
    public AlertDialog getmDialog() {
        return mDialog;
    }

    /**
     * 获取Dialog的window
     * @return
     */
    public Window getmWindow() {
        return mWindow;
    }

    public void setmWindow(Window mWindow) {
        this.mWindow = mWindow;
    }

    public void setmDialog(AlertDialog mDialog) {
        this.mDialog = mDialog;
    }

    public static class AlertParams{

        public Context mContext;
        public int mThemeResId;
        //点击空白是否能取消 默认可以取消
        public boolean mCancelable=true;
        //dialog Cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog dissmiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局VIEW
        public View mView;
        //布局LAYOUT ID
        public int mViewLayoutResId;
        //存放字体的修改
        public SparseArray<CharSequence> mTextArray=new SparseArray<>();
        //存放点击事件
        public SparseArray<View.OnClickListener> mClickArray=new SparseArray<>();
        public int mWidth= ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimation=0;
        //位置
        public int mGravity= Gravity.CENTER;
        //高度
        public int mHeight=ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {

            this.mContext=context;
            this.mThemeResId=themeResId;

        }

        /**
         * 绑定和设置参数
         * @param mAlert
         */
        public void apply(AlertController mAlert){

            //设置参数

            //1.设置布局
            DialogViewHelper viewHelper=null;
            if(mViewLayoutResId!=0){
                viewHelper=new DialogViewHelper(mContext,mViewLayoutResId);
            }

            if(mView!=null){
                viewHelper=new DialogViewHelper();
                viewHelper.setContentView(mView);
            }



            if (viewHelper == null) {

                    throw  new IllegalArgumentException("请设置布局setContentView()");

            }
            //设置Controller的辅助类
            mAlert.setViewHelper(viewHelper);

            //给Dialog设置布局
            mAlert.getmDialog().setContentView(viewHelper.getContentView());

            //2.设置文本
            int textArraySize=mTextArray.size();
            for(int i=0;i<textArraySize;i++){
                mAlert.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }

            //3.设置点击

            int clickArraySize=mClickArray.size();
            for(int i=0;i<clickArraySize;i++){
                mAlert.setOnclickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }



            //配置自定义的效果 全屏 从底部弹出 默认动画
            Window window=mAlert.getmWindow();
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if(mAnimation!=0){
                window.setWindowAnimations(mAnimation);
            }
            //设置宽高
            WindowManager.LayoutParams params= window.getAttributes();
            params.width=mWidth;
            params.height=mHeight;
            window.setAttributes(params);
        }
    }

    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId,text);
    }

    public  <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     *
     * 设置点击事件
     * @param viewId
     * @param onClickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {

        mViewHelper.setOnclickListener(viewId,onClickListener);
    }

}
