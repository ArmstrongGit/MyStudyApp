package com.study.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.study.R;

public class MoveActivity extends AppCompatActivity {

    Button btn1;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        btn1= (Button) findViewById(R.id.btn_clickme);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast result = new Toast(MoveActivity.this);
//
//                LayoutInflater inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = inflate.inflate(R.layout.customer_toast, null);
//                TextView tv = (TextView)view.findViewById(R.id.custom_toast_tv);
//                tv.setText("自定义吐司");
//
//                result.setView(view);
//                result.setDuration(Toast.LENGTH_LONG);
//                result.show();

                Snackbar snackbar=Snackbar.make(v,"",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showCustomToat();
                    }
                });
                Spanned spanned = Html.fromHtml("<p style='color:#b13f05'>是否进入瓜皮模式</p>");
                snackbar.setText(spanned);
                snackbar.show();
            }
        });
       
    }

    public void showCustomToat(){
        Toast result = new Toast(MoveActivity.this);

        LayoutInflater inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.customer_toast, null);
        TextView tv = (TextView)view.findViewById(R.id.custom_toast_tv);
        tv.setText("自定义吐司");

        result.setView(view);
        result.setDuration(Toast.LENGTH_LONG);
        result.show();
    }


}
