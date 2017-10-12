package com.study.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bpsoft.baselibrary.ioc.ViewById;
import com.bpsoft.framelibrary.DefaultNavigationBar;
import com.study.R;
import com.study.beans.Student;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;


public class ThirdActivity extends BaseActivity {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private TextView tvResult;

    @ViewById(R.id.button)
    Button button;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_third);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar= new
                DefaultNavigationBar.Builder
                (this)
                .setTitle("测试啊")
                .setRelBackOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .builder();
    }

    @Override
    protected void initView() {

        //1.注册广播
        EventBus.getDefault().register(ThirdActivity.this);
        tvResult=(TextView)findViewById(R.id.tv_result);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThirdActivity.this, 2/0+"修复bug测试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {

    }
    private void ininTest() {

        int[] ary = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};//要分割的数组
        int splitSize = 8;//分割的块大小
        Object[] subAry = splitAry(ary, splitSize);//分割后的子块数组
        for(Object obj: subAry){//打印输出结果
            int[] aryItem = (int[]) obj;
            for(int i = 0; i < aryItem.length; i++){
                System.out.print(aryItem[i] + ", ");
            }
            System.out.println();
        }
    }

    /**
     * splitAry方法<br>
     * @param ary 要分割的数组
     * @param subSize 分割的块大小
     * @return
     *
     */
    private static Object[] splitAry(int[] ary, int subSize) {
        int count = ary.length % subSize == 0 ? ary.length / subSize: ary.length / subSize + 1;

        List<List<Integer>> subAryList = new ArrayList<List<Integer>>();

        for (int i = 0; i < count; i++) {
            int index = i * subSize;
            List<Integer> list = new ArrayList<Integer>();
            int j = 0;
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);
                j++;
            }
            subAryList.add(list);
        }

        Object[] subAry = new Object[subAryList.size()];

        for(int i = 0; i < subAryList.size(); i++){
            List<Integer> subList = subAryList.get(i);
            int[] subAryItem = new int[subList.size()];
            for(int j = 0; j < subList.size(); j++){
                subAryItem[j] = subList.get(j).intValue();
            }
            subAry[i] = subAryItem;
        }

        return subAry;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveEvent(Student student){
        Toast.makeText(ThirdActivity.this,"接收到主界面发来的"+student.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除广播
        EventBus.getDefault().unregister(ThirdActivity.this);
    }


}
