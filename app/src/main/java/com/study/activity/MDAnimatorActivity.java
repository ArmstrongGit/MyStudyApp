package com.study.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bpsoft.baselibrary.ioc.ViewById;
import com.bpsoft.baselibrary.ioc.ViewUtil;
import com.study.R;

public class MDAnimatorActivity extends AppCompatActivity {

//    @ViewById(R.id.btn1)
//    Button btn1;
    @ViewById(R.id.first)
    LinearLayout first;

    @ViewById(R.id.second)
    ImageView second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdanimator);
        ViewUtil.inject(this);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onClick(View v) {
//                Animator animator= ViewAnimationUtils.createCircularReveal(
//                        btn1,//作用在哪
//                        0,//扩散的中心店
//                        0,//开始扩散的初始半径
//                        0, (float) Math.hypot(btn1.getWidth(),btn1.getHeight()));//扩散的结束半径
//                animator.setDuration(1000);
//                animator.setInterpolator(new AccelerateInterpolator());
//
//                animator.start();
//            }
//        });



    }


    /**
     * 1.翻转动画2.透明动画3.缩放动画
     * @param v
     */
    public void startFirstAnimation(View v){

        //1.翻转
        ObjectAnimator firstRotationAnim= ObjectAnimator.ofFloat(first,"rotationX",0f,25f);
        firstRotationAnim.setDuration(200);
        //2.透明度
        ObjectAnimator firstAlphaAnim= ObjectAnimator.ofFloat(first,"alpha",1,0.5f);
        firstAlphaAnim.setDuration(300);
        //3.缩放动画
        ObjectAnimator firstScaleXAnim= ObjectAnimator.ofFloat(first,"scaleX",1,0.8f);
        firstScaleXAnim.setDuration(300);
        ObjectAnimator firstScaleYAnim= ObjectAnimator.ofFloat(first,"scaleY",1,0.8f);
        firstScaleYAnim.setDuration(300);

        //恢复X轴的旋转
        ObjectAnimator firstResumeRepetAnim= ObjectAnimator.ofFloat(first,"rotationX",25f,0f);
        firstResumeRepetAnim.setDuration(200);
        firstResumeRepetAnim.setStartDelay(200);
        //恢复Y轴的缩放距离
        ObjectAnimator firstTransYAnim= ObjectAnimator.ofFloat(first,"translationY",0f,-0.1f*first.getHeight());
        firstTransYAnim.setDuration(300);


        //第二个view执行平移动画
        ObjectAnimator secondTransYAnim= ObjectAnimator.ofFloat(second,"translationY",second.getHeight(),0f);
        secondTransYAnim.setDuration(200);
        secondTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                second.setVisibility(View.VISIBLE);
            }
        });

        AnimatorSet set=new AnimatorSet();
        set.playTogether(firstRotationAnim,firstAlphaAnim,firstScaleXAnim,firstScaleYAnim,firstResumeRepetAnim,firstTransYAnim,secondTransYAnim);
        set.start();




    }


    public void startSecondAnimation(View v){
//1.翻转
        ObjectAnimator firstRotationAnim= ObjectAnimator.ofFloat(first,"rotationX",0f,25f);
        firstRotationAnim.setDuration(200);
        //2.透明度
        ObjectAnimator firstAlphaAnim= ObjectAnimator.ofFloat(first,"alpha",0.5f,1);
        firstAlphaAnim.setDuration(300);
        //3.缩放动画
        ObjectAnimator firstScaleXAnim= ObjectAnimator.ofFloat(first,"scaleX",0.8f,1);
        firstScaleXAnim.setDuration(300);
        ObjectAnimator firstScaleYAnim= ObjectAnimator.ofFloat(first,"scaleY",0.8f,1);
        firstScaleYAnim.setDuration(300);

        //恢复X轴的旋转
        ObjectAnimator firstResumeRepetAnim= ObjectAnimator.ofFloat(first,"rotationX",25f,0f);
        firstResumeRepetAnim.setDuration(200);
        firstResumeRepetAnim.setStartDelay(200);
        //恢复Y轴的缩放距离
        ObjectAnimator firstTransYAnim= ObjectAnimator.ofFloat(first,"translationY",-0.1f*first.getHeight(),0f);
        firstTransYAnim.setDuration(300);


        //第二个view执行平移动画
        ObjectAnimator secondTransYAnim= ObjectAnimator.ofFloat(second,"translationY",0f,second.getHeight());
        secondTransYAnim.setDuration(200);
        secondTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                second.setVisibility(View.INVISIBLE);
            }
        });

        AnimatorSet set=new AnimatorSet();
        set.playTogether(firstRotationAnim,firstAlphaAnim,firstScaleXAnim,firstScaleYAnim,firstResumeRepetAnim,firstTransYAnim,secondTransYAnim);
        set.start();
    }


}
