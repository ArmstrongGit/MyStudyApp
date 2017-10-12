package com.study.activity;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bpsoft.baselibrary.ioc.OnClick;
import com.bpsoft.baselibrary.ioc.ViewById;
import com.bpsoft.baselibrary.ioc.ViewUtil;
import com.study.R;

public class TanslationMDActivity extends AppCompatActivity {

    @ViewById(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanslation_md);
        ViewUtil.inject(this);
    }

    @OnClick({R.id.imageView})
    private void jump(View view){

        Intent intent=new Intent(this,MoveActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView,"iv_iauncher");
            startActivity(intent,optionsCompat.toBundle());
        }else{
            startActivity(intent);
        }

    }

}
