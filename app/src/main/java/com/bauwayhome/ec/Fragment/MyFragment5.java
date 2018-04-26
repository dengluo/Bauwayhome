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
import com.bauwayhome.ec.adapter.ProductListAdapter5;
import com.bauwayhome.ec.bean.Product_Parts;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * 烟油配件
 * Created by Danny on 2018/4/26 0028.
 */
public class MyFragment5 extends Fragment {

    private Context context;
    private ListView lv_product;// 产品列表视图
    private ProductListAdapter5 productListAdapter;// 产品列表适配器对象
    public List<Product_Parts> productList;
    Product_Parts product;
    public MyFragment5() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content1, container, false);
        context = this.getActivity();
        lv_product = (ListView) view.findViewById(R.id.lv_product);
        loadData();
        return view;
    }

    private void loadData() {
        String bql ="select * from Product_Parts";//查询所有
        new BmobQuery<Product_Parts>().doSQLQuery(bql,new SQLQueryListener<Product_Parts>(){

            @Override
            public void done(BmobQueryResult<Product_Parts> result, BmobException e) {
                if(e ==null){
                    List<Product_Parts> list = (List<Product_Parts>) result.getResults();
                    if(list!=null && list.size()>0){
                        productListAdapter = new ProductListAdapter5(context,list);// ProductListAdapter
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
