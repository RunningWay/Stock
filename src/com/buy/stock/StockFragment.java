package com.buy.stock;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buy.stock.https.HttpUtils;
import com.buy.stock.model.Stock;
import com.buy.stock.model.StockAdapter;
import com.buy.stock.utils.Utils;
import com.buy.stock.view.SlideListView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 股票界面
 * 
 * @author zj
 */
public class StockFragment extends BaseFragment {
	private final static String TAG = "StockFragment";
	
	private static final String STOCKCODE_SH = "sh000001";
	private static final String STOCKCODE_SZ = "sz399001";
	
	private RequestQueue mRequestQueue = null;

	private SlideListView slideCutListView;
	private StockAdapter  mStockAdapter = null;
	private RequestHandler mRequestHandler = null; 

	private TextView viewShCurrentPrice = null;
	private TextView viewShDelPrice = null;
	private TextView viewShDelPercent = null;
	private TextView viewSzCurrentPrice = null;
	private TextView viewSzDelPrice = null;
	private TextView viewSzDelPercent = null;
	
	private class RequestHandler extends Handler {
		protected final static int WHAT_REQUEST = 0;
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case WHAT_REQUEST:
				StringBuilder stockCodes = new StringBuilder(STOCKCODE_SH);
				stockCodes.append(",");
				stockCodes.append(STOCKCODE_SZ);
				stockCodes.append(",");
				//stockCodes.append();
				
				String url = String.format(HttpUtils.SINA_STOCK_REQUEST_URL,(String)msg.obj);
				
				Log.d(TAG, "StockRequest Url = " +url);
				StringRequest stockStringRequest = new StringRequest(Method.GET, url,volleyRequestListener, volleyRequestErrorListener);
				mRequestQueue.add(stockStringRequest);
				mRequestQueue.start();
				break;
			default:
				break;
			}
		}
	}

	private Listener<String> volleyRequestListener = new Listener<String>() {
		@Override
		public void onResponse(String value) {
			Log.d(TAG, "Response content:" + value);
			if (TextUtils.isEmpty(value)) {
				return;
			}

			List<Stock> stockList = new ArrayList<Stock>();

			String[] datas = value.split(";");
			StringBuilder stockCodes = new StringBuilder();
			
			for (String str : datas) {
				String[] values = str.trim().split(",");
				if (TextUtils.isEmpty(str)) {
					continue;
				}

				if (values == null || values.length != 33) {
					continue;
				}
				Stock mStock = new Stock();
				mStock.stockName = values[0]
						.substring(values[0].indexOf("\"") + 1);
				mStock.stockId = values[0].substring(
						values[0].indexOf("str") + 4,
						values[0].indexOf("str") + 12);
				mStock.dayStartPrice = values[1];
				mStock.yesterdayEndPrice = values[2];
				mStock.currentPrice = values[3];
				mStock.todayMaxPrice = values[4];
				mStock.todayMinPrice = values[5];
				mStock.traNumber = Long.parseLong(values[8]) / 10000;

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

				if(mStock.stockId.startsWith("sh6")||mStock.stockId.startsWith("sh0")){
					stockCodes.append(mStock.stockId).append(",");
				}else if(mStock.stockId.startsWith("sz0")||mStock.stockId.startsWith("sz3")){
					stockCodes.append(mStock.stockId).append(",");
				}
				
				float currentPrice = Float.parseFloat(mStock.currentPrice);
				float yestoryEndDayPrice = Float.parseFloat(mStock.yesterdayEndPrice);
				float delPrice = currentPrice - yestoryEndDayPrice;
				float percent = (delPrice/yestoryEndDayPrice) * 100;
				
				if(mStock.stockId.equals(STOCKCODE_SH)){
					viewShCurrentPrice.setText(Utils.formatFloat(currentPrice));
					viewShDelPrice.setText(Utils.formatFloat(delPrice));
					viewShDelPercent.setText(formatFloat(percent)+"%");
					if(percent < 0){
						viewShCurrentPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_green));
						viewShDelPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_green));
						viewShDelPercent.setTextColor(getActivity().getResources().getColor(R.color.stock_green));
					}else if(percent>0){
						viewShCurrentPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_red));
						viewShDelPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_red));
						viewShDelPercent.setTextColor(getActivity().getResources().getColor(R.color.stock_red));
					}else{
						viewShCurrentPrice.setTextColor(Color.BLACK);
						viewShDelPrice.setTextColor(Color.BLACK);
						viewShDelPercent.setTextColor(Color.BLACK);
					}
				}else if(mStock.stockId.equals(STOCKCODE_SZ)){
					viewSzCurrentPrice.setText(Utils.formatFloat(currentPrice)); 
					viewSzDelPrice.setText(Utils.formatFloat(delPrice)); 
					viewSzDelPercent.setText(formatFloat(percent)+"%"); 
					if(percent < 0){
						viewSzCurrentPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_green));
						viewSzDelPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_green));
						viewSzDelPercent.setTextColor(getActivity().getResources().getColor(R.color.stock_green));
					}else if(percent>0){
						viewSzCurrentPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_red));
						viewSzDelPrice.setTextColor(getActivity().getResources().getColor(R.color.stock_red));
						viewSzDelPercent.setTextColor(getActivity().getResources().getColor(R.color.stock_red));
					}else{
						viewSzCurrentPrice.setTextColor(Color.BLACK);
						viewSzDelPrice.setTextColor(Color.BLACK);
						viewSzDelPercent.setTextColor(Color.BLACK);
					}
				}else{
					stockList.add(mStock);
				}
			}
			
			mStockAdapter.resetDataList(stockList);
			
			Message msg = mRequestHandler.obtainMessage();
			msg.obj = stockCodes.toString();
			mRequestHandler.sendMessageDelayed(msg, 1000);
		}
	};
	
	private String formatFloat(float value){
		DecimalFormat  fnum  =   new  DecimalFormat("##0.00");    
		return fnum.format(value);   
	}

	private ErrorListener volleyRequestErrorListener = new ErrorListener() {
		public void onErrorResponse(VolleyError volleyError) {
			Toast.makeText(getActivity(), "查询出错，可能是网络不通畅喔！", Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRequestHandler = new RequestHandler();
		mRequestQueue = Volley.newRequestQueue(getActivity());
	}

	@Override
	public View onMyCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.stock_layout, null);
		slideCutListView = (SlideListView) parent.findViewById(R.id.id_stock_listview);
		viewShCurrentPrice = (TextView)parent.findViewById(R.id.id_stock_sh);
		viewShDelPrice = (TextView)parent.findViewById(R.id.sh_stock_delprice);
		viewShDelPercent = (TextView)parent.findViewById(R.id.sh_stock_percent);
		viewSzCurrentPrice = (TextView)parent.findViewById(R.id.id_stock_sz);
		viewSzDelPrice = (TextView)parent.findViewById(R.id.sz_stock_delprice);
		viewSzDelPercent =  (TextView)parent.findViewById(R.id.sz_stock_percent);
		
		mStockAdapter = new StockAdapter(getActivity());
		slideCutListView.setAdapter(mStockAdapter);
		
		Message msg = mRequestHandler.obtainMessage();
		msg.obj = STOCKCODE_SH+","+STOCKCODE_SZ+","+"sh601003,sh600005,sz300158,sh600612,sh600621,sh600290,sz000862";
		mRequestHandler.sendMessage(msg);
		return parent;
	}
}
