package com.buy.stock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 股票界面
 * 
 * @author zj
 */
public class StockFragment extends BaseFragment {
	private final static String TAG = "StockFragment";

	private ListView stock_ListView = null;

	@Override
	public View onMyCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.stock_layout, null);
		stock_ListView = (ListView) parent.findViewById(R.id.stock_listview);
		return parent;
	}
	
}
