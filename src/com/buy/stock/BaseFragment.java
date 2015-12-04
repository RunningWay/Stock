package com.buy.stock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
	public BaseFragment(){};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return onMyCreateView(inflater, container, savedInstanceState);
	}
	
	public abstract View onMyCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState);
}
