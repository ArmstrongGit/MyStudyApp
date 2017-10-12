package com.study.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.study.R;

public class TextInputActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        textInputLayout= (TextInputLayout) findViewById(R.id.tiv_username);

        //开启计数
        textInputLayout.setCounterEnabled(true);
        textInputLayout.setCounterMaxLength(12);

        //检测长度应低于6位数
        textInputLayout.getEditText().addTextChangedListener(new MinLengthTextWatcher(textInputLayout,"长度应低于6位数"));

    }

    class MinLengthTextWatcher implements TextWatcher{

        private TextInputLayout textInputLayout;
        private String errorstr;

        public MinLengthTextWatcher(TextInputLayout et,String errstr) {

            this.textInputLayout=et;
            this.errorstr=errstr;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //文字改变前回调

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //文字变化回调

        }

        @Override
        public void afterTextChanged(Editable s) {
            //文字改变后回
            if(textInputLayout.getEditText().getText().toString().length()<=6){
                textInputLayout.setErrorEnabled(false);
            }else{
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(errorstr);
            }
        }
    }



}
