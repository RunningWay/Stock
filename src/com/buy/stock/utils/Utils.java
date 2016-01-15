package com.buy.stock.utils;

import java.text.DecimalFormat;

public class Utils {
	public static String formatFloat(float value){
		DecimalFormat  fnum  =   new  DecimalFormat("##0.00");    
		return fnum.format(value);   
	}
	
	public static String formatFloat(String value){
		DecimalFormat  fnum  =   new  DecimalFormat("##0.00");    
		return fnum.format(Float.parseFloat(value));   
	}
}
