package com.study;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import com.bpsoft.baselibrary.ioc.ViewById;
import com.bpsoft.baselibrary.ioc.ViewUtil;

public class AnimatorActivity extends AppCompatActivity {

    @ViewById(R.id.imageview)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ViewUtil.inject(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.
//                PropertyValuesHolder holder1=PropertyValuesHolder.ofFloat("alpha",1f,0.7f,1f);
//                PropertyValuesHolder holder2=PropertyValuesHolder.ofFloat("scaleX",1f,0.7f,1f);
//                PropertyValuesHolder holder3=PropertyValuesHolder.ofFloat("scaleY",1f,0.7f,1f);
//
//                ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(imageView,holder1,holder2,holder3);
//                animator.start();
                //2.
//                ObjectAnimator animator1=ObjectAnimator.ofFloat(imageView,"alpha",1f,0.7f,1f);
//                ObjectAnimator animator2=ObjectAnimator.ofFloat(imageView,"scaleX",1f,0.7f,1f);
//                ObjectAnimator animator3=ObjectAnimator.ofFloat(imageView,"scaleY",1f,0.7f,1f);
//
//                AnimatorSet animatorSet=new AnimatorSet();
//                animatorSet.setDuration(500);
////                animatorSet.play() 执行单个动画
//                //同时执行动画
//                animatorSet.playSequentially(animator1,animator2,animator3);
//                animatorSet.start();
//                //抛物线
//                ValueAnimator valueAnimator=new ValueAnimator();
//                valueAnimator.setDuration(4000);
//                valueAnimator.setObjectValues(new PointF());
//                //估值器--定义计算规则
//                valueAnimator.setEvaluator(new TypeEvaluator() {
//                    @Override
//                    public Object evaluate(float fraction, Object startValue, Object endValue) {
//                        //拿到每一个时间点的坐标
//                        //x=v*t
//                        PointF pointF=new PointF();
//                        pointF.x=50f*(fraction*4);
//                        //y=1/2*g*t*t
//                        pointF.y=0.5f*250f*(fraction*4)*(fraction*4);
//
//                        return pointF;
//                    }
//                });
//
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        PointF pointF= (PointF) animation.getAnimatedValue();
//                        imageView.setX(pointF.x);
//                        imageView.setY(pointF.y);
//                    }
//                });
//                //设置加速器
////                valueAnimator.setInterpolator(new AccelerateInterpolator(50));
//                valueAnimator.start();
                ObjectAnimator oa=ObjectAnimator.ofFloat(imageView,"translationY",0f,500f);
                oa.setDuration(500);
//                oa.setInterpolator(new AccelerateInterpolator(5));
//                oa.setInterpolator(new AccelerateDecelerateInterpolator());
                oa.setInterpolator(new AnticipateOvershootInterpolator(5));
                oa.start();

            }
        });



    }
}
