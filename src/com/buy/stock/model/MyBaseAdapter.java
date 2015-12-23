package com.buy.stock.model;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	private final static String TAG = "MyBaseAdapter";

	public List<T> dataList = new ArrayList<T>();

	public void addData(T t) {
		if (t == null) {
			return;
		}
		dataList.add(t);
		notifyDataSetChanged();
	}

	public void addDataToListTop(T t) {
		if (t == null) {
			return;
		}
		dataList.add(0, t);
		notifyDataSetChanged();
	}

	public void resetDataList(List<T> dataList) {
		if (dataList == null || dataList.size() == 0) {
			return;
		}
		this.dataList.clear();
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup group) {
		if (position == dataList.size() - 1) {
			reachBottom();
		}
		return getCustomView(position, v, group);
	}

	/**
	 * ListView已经滑动到最底部
	 */
	public abstract void reachBottom();

	/**
	 * 子适配器界面实现
	 * 
	 * @param position
	 * @param v
	 * @param group
	 * @return
	 */
	public abstract View getCustomView(int position, View v, ViewGroup group);
}
