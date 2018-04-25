package com.bauwayhome.ec.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bauwayhome.ec.R;


/**
 * @author yixiang
 *自定义圆点导航指示器
 */
public class RoundNavigationIndicator extends LinearLayout {

	private LinearLayout container;
	private int sum=0;
	private int selected=0;
	private Context context;

	public RoundNavigationIndicator(Context context) {
		super(context);
		this.context=context;
		init(context);
	}


	public RoundNavigationIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init(context);
	}


	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.image_navigation_indicaor_layout, this);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		container=(LinearLayout) findViewById(R.id.indicator);
	}

	//设置圆点总数
	public void setLenght(int sum){
		this.sum=sum;
	}

	//设置选中圆点的下标
	public void setSelected(int selected){
		container.removeAllViews();
		this.selected=selected;
	}


	//绘制指示器
	public void draw(){
		for(int i=0;i<sum;i++){
			ImageView imageview=new ImageView(context);
			imageview.setLayoutParams(new LayoutParams(40, 40));
			imageview.setPadding(5, 0, 5, 0);
			if(i==selected){
				imageview.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
			}else{
				imageview.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
			}
			container.addView(imageview);
		}
	}


}
