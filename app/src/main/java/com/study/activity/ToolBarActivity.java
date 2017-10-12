package com.study.activity;

import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.study.R;
import com.study.base.BaseTranslucentActivity;

public class ToolBarActivity extends BaseTranslucentActivity {

    Toolbar toolbar;
    View nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        nav=findViewById(R.id.nav);
        setSupportActionBar(toolbar);

        setOrChangeTranslucentColor(toolbar,nav,getResources().getColor(R.color.colorPrimary));
        //设置Navigation的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item=  menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);
        //设置一出来就呈现搜索框
        searchView.setIconified(false);
        //设置呈现搜索框且不可关闭
//        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
//        EditText edSearch= (EditText) searchView.findViewById(R.id.search_src_text);
//        edSearch.setHint("请输入姓名或手机号");
//        searchView.setQueryHint("输入姓名或手机号");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ToolBarActivity.this, "提交文本"+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);

    }



}
