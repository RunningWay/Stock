package com.buy.stock.model;

import java.text.DecimalFormat;

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
			tag.mViewDayStartPrice = (TextView)parent.findViewById(R.id.id_view_daystartprice);
			tag.mViewCurrentPrice = (TextView)parent.findViewById(R.id.id_view_currentprice);
			tag.mViewYestoryDayPrice = (TextView)parent.findViewById(R.id.id_view_yesterdayendprice_name);
			tag.mViewHighPrice = (TextView)parent.findViewById(R.id.id_view_dayhighprice_name);
			tag.mViewLowPrice = (TextView)parent.findViewById(R.id.id_view_daylowprice_name);
			tag.mViewTotalNum = (TextView)parent.findViewById(R.id.id_view_daynum_name);
			v = parent;
			v.setTag(tag);
		}
		tag = (Tag) v.getTag();
		Stock sStock = dataList.get(position);
		tag.mViewCompanyName.setText(sStock.stockName);
		tag.mViewDayStartPrice.setText(context.getString(R.string.stock_dayStartPrice,new Object[]{formatFloat(sStock.dayStartPrice)}));
		tag.mViewCurrentPrice.setText(context.getString(R.string.stock_currentPrice,new Object[]{formatFloat(sStock.currentPrice)}));
		tag.mViewYestoryDayPrice.setText(context.getString(R.string.stock_yesterdayEndPrice, new Object[]{formatFloat(sStock.yesterdayEndPrice)}));
		tag.mViewHighPrice.setText(context.getString(R.string.stock_dayHighPrice, new Object[]{formatFloat(sStock.todayMaxPrice)}));
		tag.mViewLowPrice.setText(context.getString(R.string.stock_dayLowPrice,new Object[]{ formatFloat(sStock.todayMinPrice)}));
		tag.mViewTotalNum.setText(context.getString(R.string.stock_num, new Object[]{sStock.traNumber}));
		return v;
	}
	
	private String formatFloat(String value){
		DecimalFormat  fnum  =   new  DecimalFormat("##0.00");    
		return fnum.format(Float.parseFloat(value));   
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
