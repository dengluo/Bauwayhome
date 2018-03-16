package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.adapter.ProductListAdapter2;
import com.bauwayhome.ec.bean.Product_Routine;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * 常规烤烟系列
 * Created by Danny on 2018/8/28 0028.
 */
public class MyFragment2 extends Fragment {

    private Context context;
    private ListView lv_product;// 产品列表视图
    private ProductListAdapter2 productListAdapter;// 产品列表适配器对象
    public List<Product_Routine> productList;
    Product_Routine product;
    public MyFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2, container, false);
        context = this.getActivity();
        lv_product = view.findViewById(R.id.lv_product);
        loadData();
        return view;
    }

    private void loadData() {
        String bql ="select * from Product_Routine";//查询所有
        new BmobQuery<Product_Routine>().doSQLQuery(bql,new SQLQueryListener<Product_Routine>(){

            @Override
            public void done(BmobQueryResult<Product_Routine> result, BmobException e) {
                if(e ==null){
                    List<Product_Routine> list = (List<Product_Routine>) result.getResults();
                    if(list!=null && list.size()>0){
                        productListAdapter = new ProductListAdapter2(context,list);// ProductListAdapter
                        lv_product.setAdapter(productListAdapter);// 为ListView绑定Adapter
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });
    }
}
