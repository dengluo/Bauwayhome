package com.bauwayhome.ec.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.bean.Product_Routine;
import com.bauwayhome.ec.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 产品列表适配器
 * Created by danny on 2018/3/14.
 */

public class ProductListAdapter2 extends BaseAdapter {

    private Context mContext;
    private List<Product_Routine> products;
    ViewHolder viewHolder;
    Product_Routine product;

    /**
     * 构造函数
     */
    public ProductListAdapter2(Context mContext, List<Product_Routine> products) {
        super();
        this.mContext = mContext;
        this.products = products;
    }

    public void setData(List<Product_Routine> list) {
        notifyDataSetChanged();
    }

    /**
     * 返回ListView的长度
     */
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);// list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        product = products.get(position);
        if (convertView == null && products.size() != 0) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_product, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_product_name);
            viewHolder.model = (TextView) convertView.findViewById(R.id.tv_product_model);
            viewHolder.size = (TextView) convertView.findViewById(R.id.tv_product_size);
            viewHolder.battery = (TextView) convertView.findViewById(R.id.tv_product_battery);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_product_image);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.ll_product_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(product.getIqos_name());
        viewHolder.model.setText(product.getIqos_model());
        viewHolder.size.setText(product.getIqos_size());
        viewHolder.battery.setText(product.getIqos_battery());
        Log.e("getIconUrl", product.getIconUrl());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
        ImageLoader.getInstance().displayImage(
                product.getIconUrl(),
                viewHolder.image,
                ImageLoaderUtil.getDisplayImageOptions());
        viewHolder.layout.setTag(position);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int)view.getTag();
                Uri uri = Uri.parse(products.get(position).getIqos_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public LinearLayout layout;
        public TextView name;
        public TextView model;
        public TextView size;
        public TextView battery;
        public ImageView image;
    }

}