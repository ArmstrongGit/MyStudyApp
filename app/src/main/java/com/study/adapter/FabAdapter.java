package com.study.adapter;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.R;

import java.util.List;

/**
 * 作者： 苏晓伟 on 2017-8-28.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 ${ORGANIZATION_NAME}. All rights reserved.
 */

public class FabAdapter extends RecyclerView.Adapter {

    private final List<String> list;

    public FabAdapter(List<String> list) {
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fab_list_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String str= list.get(position);
        MyViewHolder mholder= (MyViewHolder) holder;
        mholder.tv.setText(str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.tv);
        }
    }

}
