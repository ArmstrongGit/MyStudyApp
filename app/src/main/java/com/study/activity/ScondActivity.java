package com.study.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.R;
import com.study.beans.Student;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ScondActivity extends AppCompatActivity {

    private ScondActivity mContext;
    private Button button1,button2;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scond);
        mContext=this;
        button1=(Button)findViewById(R.id.btn_1);
        button2=(Button)findViewById(R.id.btn_2);
        tvResult=(TextView)findViewById(R.id.tv_result);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Student("瓜皮高长宝"));
//                Intent i=new Intent(mContext,ThirdActivity.class);
//                startActivity(i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().register(mContext);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveEvent(Student student){
        tvResult.setText(student.getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mContext);
    }
}
