package com.buy.stock;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
	private static final String TAG = "MainAcitivty";

	private StockFragment stockFragment = new StockFragment();
	private StudyFragment studyFragment = new StudyFragment();

	public enum FragmentTab {
		FUND, STOCK, STUDY
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	private void switchFragment(FragmentTab tab) {
		FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		if (isFinishing()) {
			return;
		}

		BaseFragment useFragment = null;
		if (tab == FragmentTab.STOCK) {
			useFragment = stockFragment;
		} else if (tab == FragmentTab.STUDY) {
			useFragment = studyFragment;
		}
		mFragmentTransaction.replace(R.id.fragment_content, useFragment);
		mFragmentTransaction.commitAllowingStateLoss();
	}
}
