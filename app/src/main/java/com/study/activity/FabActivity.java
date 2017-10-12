package com.study.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.study.R;
import com.study.adapter.FabAdapter;
import com.study.callback.HideScrollListener;
import com.study.listener.FabScrollListener;

import java.util.ArrayList;
import java.util.List;

public class FabActivity extends AppCompatActivity implements HideScrollListener{

    private RecyclerView recyclerview;
    private ImageButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        fab=(ImageButton)findViewById(R.id.fab);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("fab滑动隐藏");
        recyclerview.addOnScrollListener(new FabScrollListener(this));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<String> list=new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            list.add("测试数据"+i);
        }
        RecyclerView.Adapter adapter=  new FabAdapter(list);
        recyclerview.setAdapter(adapter);

    }

    @Override
    public void onHide() {
        //隐藏动画 属性动画
        RelativeLayout.LayoutParams layoutParmas= (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(fab.getHeight()+layoutParmas.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(3));
    }

    @Override
    public void onShow() {
        //显示动画
        RelativeLayout.LayoutParams layoutParmas= (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }
}
