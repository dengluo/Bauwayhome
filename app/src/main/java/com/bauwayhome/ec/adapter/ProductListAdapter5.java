package com.bauwayhome.ec.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.activity.ProductDetailsActivity;
import com.bauwayhome.ec.bean.Product_Parts;
import com.bauwayhome.ec.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 产品列表适配器
 * Created by danny on 2018/3/14.
 */

public class ProductListAdapter5 extends BaseAdapter {

    private Context mContext;
    private List<Product_Parts> products;
    ViewHolder viewHolder;
    Product_Parts product;

    /**
     * 构造函数
     */
    public ProductListAdapter5(Context mContext, List<Product_Parts> products) {
        super();
        this.mContext = mContext;
        this.products = products;
    }

    public void setData(List<Product_Parts> list) {
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
    public View getView(final int position, View convertView, ViewGroup arg2) {
        product = products.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_product2, null);
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
        viewHolder.name.setText(product.getParts_name());
        viewHolder.model.setText(product.getModel());
        viewHolder.size.setText(product.getFlavor());
        viewHolder.battery.setText(product.getCapacity());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
        ImageLoader.getInstance().displayImage(
                product.getIconUrl(),
                viewHolder.image,
                ImageLoaderUtil.getDisplayImageOptions());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(products.get(position).getParts_url());
                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                intent.putExtra("uri", uri + "");
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