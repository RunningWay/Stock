package com.buy.stock.model;

public class Stock {
	public String stockId;// 股票ID
	public String stockName; // 股票名称
	public String dayStartPrice; // 开盘价
	public String yesterdayEndPrice; // 昨收
	public String currentPrice; // 当前价格
	public String todayMaxPrice; // 当日最高
	public String todayMinPrice; // 当日最低
	public int traNumber; // 成交量
	public String buyOne;// 买1
	public String buyOnePrice;//买1报价
	public String buyTwo;//买2
	public String buyTwoPrice;//买2报价
	public String buyThree;//买3
	public String buyThreePrice;//买3报价
	public String buyFour;//买4;
	public String buyFourPrice;//买4报价
	public String buyFive;//买5
	public String buyFivePrice;//买5报价
	public String sellOne;//卖1
	public String sellTwo;//卖2
	public String sellThree;//卖3
	public String sellFour;//卖4
	public String sellFive;//卖5
	public String sellOnePrice; //卖1报价
	public String sellTwoPrice;//卖2报价
	public String sellThreePrice;//卖3报价
	public String sellFourPrice;//卖4报价
	public String sellFivePrice;//卖5报价
	public String date;
	
	private String minUrl;//分时
	private String dayUrl;//日K
	private String weekUrl;//周K
	private String monthUrl; //月K
}
