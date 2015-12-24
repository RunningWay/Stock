package com.buy.stock;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buy.stock.https.HttpUtils;
import com.buy.stock.model.Stock;
import com.buy.stock.model.StockAdapter;
import com.buy.stock.view.SlideListView;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 股票界面
 * 
 * @author zj
 */
public class StockFragment extends BaseFragment {
	private final static String TAG = "StockFragment";

	private RequestQueue mRequestQueue = null;
	
	private SlideListView slideCutListView ;
	private StockAdapter mStockAdapter = null;
		
	private Listener<String> volleyRequestListener = new Listener<String>() {
		@Override
		public void onResponse(String value) {
			Log.d(TAG, "Response content:"+value);
			if(TextUtils.isEmpty(value)){
				return;
			}
			
			List<Stock> stockList = new ArrayList<Stock>();
			
			String [] datas = value.split(";");
			for(String str:datas){
				String [] values = str.trim().split(",");
				if(TextUtils.isEmpty(str)){
					continue;
				}
				
				if(values == null || values.length != 33){
					continue;
				}
				
				Stock mStock = new Stock();
				mStock.stockName = values[0].substring(values[0].indexOf("\"")+1);
				mStock.dayStartPrice = values[1];
				mStock.yesterdayEndPrice = values[2];
				mStock.currentPrice = values[3];
				mStock.todayMaxPrice = values[4];
				mStock.todayMinPrice = values[5];
				mStock.traNumber = Integer.parseInt(values[8])/10000;
				
				mStock.buyOne = values[10];
				mStock.buyOnePrice = values[11];
				mStock.buyTwo = values[12];
				mStock.buyTwoPrice = values[13];
				mStock.buyThree = values[14];
				mStock.buyThreePrice = values[15];
				mStock.buyFour = values[16];
				mStock.buyFourPrice = values[17];
				mStock.buyFive = values[18];
				mStock.buyFivePrice = values[19];
				
				mStock.sellOne = values[20];
				mStock.sellOnePrice = values[21];
				mStock.sellTwo = values[22];
				mStock.sellTwoPrice = values[23];
				mStock.sellThree = values[24];
				mStock.sellThreePrice = values[25];
				mStock.sellFour = values[26];
				mStock.sellFourPrice = values[27];
				mStock.sellFive = values[28];
				mStock.sellFivePrice = values[29];
				
				stockList.add(mStock);
			}
			mStockAdapter.resetDataList(stockList);
		}
	};
	
	private ErrorListener volleyRequestErrorListener = new ErrorListener() {
		public void onErrorResponse(VolleyError volleyError) {
			Toast.makeText(getActivity(), "查询出错，可能是网络不通畅喔！", Toast.LENGTH_LONG).show();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRequestQueue = Volley.newRequestQueue(getActivity());
	}
	
	private void requestData(String stockCodes){
		String url = String.format(HttpUtils.SINA_STOCK_REQUEST_URL, stockCodes);
		StringRequest stockStringRequest = new StringRequest(Method.GET, url, volleyRequestListener, volleyRequestErrorListener);
		mRequestQueue.add(stockStringRequest);
		mRequestQueue.start();
	}
	
	@Override
	public View onMyCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.stock_layout, null);
		slideCutListView = (SlideListView)parent.findViewById(R.id.id_stock_listview);
		mStockAdapter = new StockAdapter(getActivity());
		slideCutListView.setAdapter(mStockAdapter);
		
		requestData("sh601003,sh600005,sz300158,sh600612,sh600621");
		return parent;
	}
}
