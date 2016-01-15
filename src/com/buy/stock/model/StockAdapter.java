package com.buy.stock.model;

import com.buy.stock.R;
import com.buy.stock.utils.Utils;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StockAdapter extends MyBaseAdapter<Stock> {
	private Context context;

	public StockAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void reachBottom() {
	}

	@Override
	public View getCustomView(int position, View v, ViewGroup group) {
		Tag tag = null;
		if(null == v){
			tag = new Tag();
			View parent = LayoutInflater.from(context).inflate(R.layout.stock_item_layout, null);
			tag.mViewCompanyName = (TextView)parent.findViewById(R.id.id_view_companyname);
			tag.mViewCurrentPrice = (TextView)parent.findViewById(R.id.id_view_currentprice);
			tag.mViewPercent = (TextView)parent.findViewById(R.id.id_view_percent);
			tag.mViewCompanyCode = (TextView)parent.findViewById(R.id.id_view_companycode);
			v = parent;
			v.setTag(tag);
		}
		tag = (Tag) v.getTag();
		Stock sStock = dataList.get(position);
		tag.mViewCompanyName.setText(sStock.stockName);
		tag.mViewCurrentPrice.setText(context.getString(R.string.stock_currentPrice,new Object[]{Utils.formatFloat(sStock.currentPrice)}));
		tag.mViewCompanyCode.setText(sStock.stockId.substring(2, sStock.stockId.length()));
		float currentPrice = Float.parseFloat(sStock.currentPrice);
		float yestoryEndDayPrice = Float.parseFloat(sStock.yesterdayEndPrice);
		float delPrice = currentPrice - yestoryEndDayPrice;
		float percent = (delPrice/yestoryEndDayPrice) * 100;
		tag.mViewPercent.setText(Utils.formatFloat(percent)+"%");
		if(percent>0){
			tag.mViewCurrentPrice.setTextColor(context.getResources().getColor(R.color.stock_red));
			tag.mViewPercent.setBackgroundResource(R.drawable.radius_red);
		}else if(percent<0){
			tag.mViewCurrentPrice.setTextColor(context.getResources().getColor(R.color.stock_green));
			tag.mViewPercent.setBackgroundResource(R.drawable.radius_green);
		}else{
			tag.mViewCurrentPrice.setTextColor(Color.BLACK);
			tag.mViewPercent.setTextColor(Color.DKGRAY);
		}
		return v;
	}
	
	private class Tag {
		public TextView mViewCompanyName;
		public TextView mViewCompanyCode;
		public TextView mViewCurrentPrice;
		public TextView mViewPercent;
	}
}
