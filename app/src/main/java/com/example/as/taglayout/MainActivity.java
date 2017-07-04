package com.example.as.taglayout;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TagLayout mTagLayout = (TagLayout) findViewById(R.id.myTagLayout);
        mDatas.add("111111");
        mDatas.add("22222222222");
        mDatas.add("33333");
        mDatas.add("4444");
        mDatas.add("5555555");
        mDatas.add("66666");
        mDatas.add("8888888");
        mDatas.add("666666666666666666666");
        mDatas.add("77777777777777777777777777");

        mTagLayout.setAdapter(new TagAdapter() {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
//                View view =LayoutInflater.from(MainActivity.this).inflate(R.layout.child_tag_layout,parent,false);
                TextView mTV = new TextView(MainActivity.this);
                mTV.setText(mDatas.get(position));
                return mTV;
            }
        });


    }
}
