package com.buy.stock.model;

public class Stock {
	private String stockId;// 股票ID
	private String stockName; // 股票名称
	private String dayStartPrice; // 开盘价
	private String yesterdayEndPrice; // 昨收
	private String currentPrice; // 当前价格
	private String todayMaxPrice; // 当日最高
	private String todayMinPrice; // 当日最低
	private String traNumber; // 成交量
	private String buyOne;// 买1
	private String buyOnePrice;//买1报价
	private String buyTwo;//买2
	private String buyTwoPrice;//买2报价
	private String buyThree;//买3
	private String buyThreePrice;//买3报价
	private String buyFour;//买4;
	private String buyFourPrice;//买4报价
	private String buyFive;//买5
	private String buyFivePrice;//买5报价
	private String sellOne;//卖1
	private String sellTwo;//卖2
	private String sellThree;//卖3
	private String sellFour;//卖4
	private String sellFive;//卖5
	private String sellOnePrice; //卖1报价
	private String sellTwoPrice;//卖2报价
	private String sellThreePrice;//卖3报价
	private String sellFourPrice;//卖4报价
	private String sellFivePrice;//卖5报价
	private String date;
	
	private String minUrl;//分时
	private String dayUrl;//日K
	private String weekUrl;//周K
	private String monthUrl; //月K
}
