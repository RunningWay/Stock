package com.buy.stock;

import com.buy.stock.model.Stock;
import com.buy.stock.model.StockAdapter;
import com.buy.stock.view.SlideListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 股票界面
 * 
 * @author zj
 */
public class StockFragment extends BaseFragment {
	private final static String TAG = "StockFragment";
	
	private SlideListView slideCutListView ;
	private StockAdapter mStockAdapter = null;
	
	@Override
	public View onMyCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		slideCutListView = new SlideListView(getActivity());
		mStockAdapter = new StockAdapter(getActivity());
		slideCutListView.setAdapter(mStockAdapter);
		
		for(int i=0;i<20;i++){
			Stock stock = new Stock();
			stock.stockName = "i:"+i;
			mStockAdapter.addData(stock);
		}
		return slideCutListView;
	}
	
	private void initData(){
		
	}
}
