package com.example.csdn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

	public static final String[] TITLES = new String[] {"业界", "移动", "研发", "程序员杂志", "云计算"};
	
	public TabAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = new MainFragment(arg0 + 1);
		return fragment;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return TITLES[position % TITLES.length];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TITLES.length;
	}

}
