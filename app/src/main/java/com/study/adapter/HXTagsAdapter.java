package com.study.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RadioButton;

import com.study.R;
import com.study.beans.MTagEntity;

import java.util.List;

/**
 * Created by 苏晓伟 on 2018/3/14.
 */

public class HXTagsAdapter extends BaseAdapter{

    private Context context;
    private List<MTagEntity> datas;
    public HXTagsAdapter(Context context, List<MTagEntity> datas){
        this.context=context;
        this.datas=datas;
    }
    //返回子项的个数
    @Override
    public int getCount() {
        return datas.size();
    }
    //返回子项对应的对象
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    //返回子项的下标
    @Override
    public long getItemId(int position) {
        return position;
    }
    //返回子项视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MTagEntity mTagEntity= (MTagEntity) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(context).inflate(R.layout.hx_grid_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mTag=(CheckedTextView) view.findViewById(R.id.btn_tag);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.mTag.setText(mTagEntity.getText());

        viewHolder.mTag.setChecked(mTagEntity.isChecked());
        Log.i("HXTAGSADAPTER","mTagEntity.isChecked()-->"+mTagEntity.isChecked());
        return view;
    }
    //创建ViewHolder类
    class ViewHolder{
        CheckedTextView mTag;
    }
}
