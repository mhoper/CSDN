package com.example.csdn;

import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	private TabPageIndicator mIndicator;
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
		mViewPager = (ViewPager) findViewById(R.id.id_pager);
		
		mAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mViewPager, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("maxMemory:" + Runtime.getRuntime().maxMemory());
		System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
		System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
	}
}
