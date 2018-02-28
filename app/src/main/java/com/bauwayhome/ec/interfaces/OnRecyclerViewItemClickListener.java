package com.bauwayhome.ec.interfaces;

import android.view.View;

/**
 * Created by danny on 2018/2/28.
 */
public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, int position);

    void onItemChildClick(View view, int position);

}
