package com.study.beans;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.study.R;

public class SliderMenuActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_menu);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(SliderMenuActivity.this,drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close);
        //同步状态
        drawerToggle.syncState();
//        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //滑动过程中不断的回调 slideOffset:0~1
                View content= drawerLayout.getChildAt(0);
                View menu=drawerView;
                float scale=1-slideOffset;
                float leftScale= (float) (0.7+0.3*scale);
                float rightScale= (float) (1-0.3*scale);//0.7~1
                menu.setScaleX(rightScale);//1~0.7
                menu.setScaleY(rightScale);//1~0.7

                content.setScaleX(leftScale);
                content.setScaleY(leftScale);
                content.setTranslationX(slideOffset);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //打开


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //关闭

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //状态发生改变

            }
        });

    }



}
