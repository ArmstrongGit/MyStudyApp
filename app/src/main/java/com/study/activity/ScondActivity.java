package com.study.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.R;
import com.study.adapter.HXTagsAdapter;
import com.study.beans.MTagEntity;
import com.study.beans.Student;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ScondActivity extends AppCompatActivity {

    private ScondActivity mContext;
    private Button button1,button2;
    private TextView tvResult;

    GridView gvTags;
    private HXTagsAdapter tagsAdapter;
    private List<MTagEntity> datas = new ArrayList<MTagEntity>();
    private List<String> mSelectTags=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scond);
        mContext=this;
        gvTags=(GridView)findViewById(R.id.gv_tags);
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
        MTagEntity mTagEntity1 = new MTagEntity("销售", false);
        datas.add(mTagEntity1);
        MTagEntity mTagEntity2 = new MTagEntity("外贸业务员", false);
        datas.add(mTagEntity2);
        MTagEntity mTagEntity3 = new MTagEntity("文员", false);
        datas.add(mTagEntity3);
        MTagEntity mTagEntity4 = new MTagEntity("客服主管", false);
        datas.add(mTagEntity4);
        MTagEntity mTagEntity5 = new MTagEntity("会计", false);
        datas.add(mTagEntity5);
        MTagEntity mTagEntity6 = new MTagEntity("设计", false);
        datas.add(mTagEntity6);
        MTagEntity mTagEntity7 = new MTagEntity("前台", false);
        datas.add(mTagEntity7);
        MTagEntity mTagEntity8 = new MTagEntity("+添加", false);
        datas.add(mTagEntity8);
        tagsAdapter = new HXTagsAdapter(this, datas);//实例化适配器
        gvTags.setAdapter(tagsAdapter);//设置适配器
        gvTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==datas.size()-1){
                    MTagEntity mTagEntity = new MTagEntity("添加测试", false);
                    if(datas.contains(mTagEntity)){
                        Toast.makeText(mContext, "该标签页已经存在", Toast.LENGTH_SHORT).show();
                    }else {
                        datas.add(datas.size()-1,mTagEntity);
                    }
                }else{
                    String chooseStr=datas.get(position).getText();
                    if(mSelectTags.contains(chooseStr)){
                        MTagEntity mTagEntity=datas.get(position);
                        mTagEntity.setChecked(!mTagEntity.isChecked());
                        datas.set(position,mTagEntity);
                        mSelectTags.remove(chooseStr);
                    }else if(mSelectTags.size()<3){
                        mSelectTags.add(datas.get(position).getText());
                        MTagEntity mTagEntity=datas.get(position);
                        mTagEntity.setChecked(!mTagEntity.isChecked());
                        datas.set(position,mTagEntity);
                    }else{
                        StringBuffer result=new StringBuffer();
                        result.append("已经选择超过三个！选择如下：");
                        for (String mSelectTag : mSelectTags) {
                            result.append(mSelectTag);
                        }
                        tvResult.setText(result);
                    }

                }
                tagsAdapter.notifyDataSetChanged();
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
