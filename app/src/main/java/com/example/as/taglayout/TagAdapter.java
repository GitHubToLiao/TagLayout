package com.example.as.taglayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by as on 2017/6/26.
 */

public interface TagAdapter {
    int getCount();
    View getView(int position,View convertView,ViewGroup parent);
}
