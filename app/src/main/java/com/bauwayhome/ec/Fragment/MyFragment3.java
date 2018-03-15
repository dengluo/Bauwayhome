package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bauwayhome.ec.R;

/**
 * 盒子系列
 * Created by Danny on 2018/8/28 0028.
 */
public class MyFragment3 extends Fragment {

    private Context context;
    public MyFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content3, container, false);
        context = this.getActivity();
        return view;
    }
}
