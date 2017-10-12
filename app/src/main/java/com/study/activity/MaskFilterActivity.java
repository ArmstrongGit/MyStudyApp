package com.study.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.R;
import com.study.view.MaskFilterView;

public class MaskFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaskFilterView view=new MaskFilterView(this);
        setContentView(view);
    }
}
