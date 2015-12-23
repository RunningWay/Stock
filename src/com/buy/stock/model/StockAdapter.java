package com.buy.stock.model;

import com.buy.stock.R;

import android.content.Context;
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
			tag.mViewDayStartPrice = (TextView)parent.findViewById(R.id.id_view_companyname);
			tag.mViewCurrentPrice = (TextView)parent.findViewById(R.id.id_view_companyname);
			tag.mViewYestoryDayPrice = (TextView)parent.findViewById(R.id.id_view_companyname);
			tag.mViewHighPrice = (TextView)parent.findViewById(R.id.id_view_companyname);
			tag.mViewLowPrice = (TextView)parent.findViewById(R.id.id_view_companyname);
			tag.mViewTotalNum = (TextView)parent.findViewById(R.id.id_view_companyname);
			v = parent;
			v.setTag(tag);
		}
		tag = (Tag) v.getTag();
		Stock sStock = dataList.get(position);
		tag.mViewCompanyName.setText(sStock.stockName);
		tag.mViewDayStartPrice.setText(context.getString(R.string.stock_dayStartPrice, sStock.dayStartPrice));
		tag.mViewCurrentPrice.setText(context.getString(R.string.stock_currentPrice,sStock.currentPrice));
		tag.mViewYestoryDayPrice.setText(context.getString(R.string.stock_yesterdayEndPrice, sStock.yesterdayEndPrice));
		tag.mViewHighPrice.setText(context.getString(R.string.stock_dayHighPrice, sStock.todayMaxPrice));
		tag.mViewLowPrice.setText(context.getString(R.string.stock_dayLowPrice, sStock.todayMinPrice));
		tag.mViewTotalNum.setText(context.getString(R.string.stock_num, sStock.traNumber));
		return v;
	}

	private class Tag {
		public TextView mViewCompanyName;
		public TextView mViewDayStartPrice;
		public TextView mViewCurrentPrice;
		public TextView mViewYestoryDayPrice;
		public TextView mViewHighPrice;
		public TextView mViewLowPrice;
		public TextView mViewTotalNum;
	}
}
