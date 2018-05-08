package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.activity.SalesDetailsActivity;
import com.bauwayhome.ec.util.NetworkUtil;
import com.bauwayhome.ec.util.ToastUtil;

/**
 * Created by danny on 2018/5/7.
 * 促销
 */

public class FragmentSales extends Fragment implements View.OnClickListener {

    private View view_main;
    private Context context;
    private ImageView iv_sales_products;
    private ImageView iv_sales_get1,iv_sales_get2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        if (!NetworkUtil.isNetworkAvailable(context)) {
            ToastUtil.showShortToast(context, "网络连接异常!");
        }
        view_main = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_sales, null);
        iv_sales_products = (ImageView) view_main.findViewById(R.id.iv_sales_products);
        iv_sales_get1 = (ImageView) view_main.findViewById(R.id.iv_sales_get1);
        iv_sales_get2 = (ImageView) view_main.findViewById(R.id.iv_sales_get2);
        iv_sales_products.setOnClickListener(this);
        iv_sales_get1.setOnClickListener(this);
        iv_sales_get2.setOnClickListener(this);
        return view_main;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.setUserVisibleHint(hidden);
        if (hidden) {
            //可见时执行的操作
//            Log.e("isVisibleToUser11",hidden+"");
        } else {
            //不可见时执行的操作
//            Log.e("isVisibleToUser22",hidden+"");
            if (!NetworkUtil.isNetworkAvailable(context)) {
                ToastUtil.showShortToast(context, "网络连接异常!");
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sales_get1:
                ToastUtil.showShortToast(context, "恭喜领券成功");
                break;
            case R.id.iv_sales_get2:
                ToastUtil.showShortToast(context, "恭喜领券成功");
                break;
            case R.id.iv_sales_products:
                startActivity(new Intent(context, SalesDetailsActivity.class));
                break;
            default:
                break;
        }
    }
}
