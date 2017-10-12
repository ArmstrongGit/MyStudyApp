package com.study.view;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.study.R;


/**
 * 作者： 苏晓伟 on 2017-9-27.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public class MaskFilterView extends android.view.View {


    public MaskFilterView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //关闭硬件加速，不然模糊遮罩效果看不见(硬件加速会跳过一些处理，一些API不支持)
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(255,100,100,100));
        RectF rectF=new RectF(100,100,200,200);
        //模糊遮罩滤镜效果
//        paint.setMaskFilter(new BlurMaskFilter(20,BlurMaskFilter.Blur.NORMAL));

        //浮雕滤镜效果
        /**
         *
         * direction，指长度为XXX的数组标量[x,y,z],用来指定光源的位置
         * ambient，指定周边背景光源（0~1）
         * specular，指镜面反射系数
         * blurRadius，指定模糊半径
         *
         */
//        paint.setMaskFilter(new EmbossMaskFilter(
//           new float[]{10,60,360},0.5f,10,30
//        ));
//        ColorMatrix matrix=new ColorMatrix(new float[]{
//                0,0,0,0,0,
//                0,1,0,0,100,
//                0,0,1,0,0,
//                0,0,0,1,0,
//        });
//        ColorMatrix matrix=new ColorMatrix(new float[]{
//                -1,0,0,0,255,
//                0,-1,0,0,255,
//                0,0,-1,0,255,
//                0,0,0,1,0,
//        });
        ColorMatrix matrix=new ColorMatrix(new float[]{
                1.2f,0,0,0,0,
                0,1.2f,0,0,0,
                0,0,1.2f,0,0,
                0,0,0,1.2f,0,
        });
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.icon3);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bitmap,100,300,paint);


//        canvas.drawRect(0,0,400,400,paint);

    }
}
