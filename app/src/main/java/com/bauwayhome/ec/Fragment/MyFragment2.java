package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bauwayhome.ec.R;

/**
 * 常规烤烟系列
 * Created by Danny on 2018/8/28 0028.
 */
public class MyFragment2 extends Fragment {

    private Context context;
    public MyFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2, container, false);
        context = this.getActivity();
        return view;
    }
}
