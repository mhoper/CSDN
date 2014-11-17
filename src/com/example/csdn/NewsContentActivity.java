package com.example.csdn;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bean.News;
import com.example.biz.NewsItemBiz;
import com.example.csdn.adapter.NewsContentAdapter;

public class NewsContentActivity extends Activity implements IXListViewLoadMore {

	private XListView mListView;
	private String url;
	
	private NewsItemBiz mNewsItemBiz;
	private List<News> mDatas;
	
	private ProgressBar mProgressBar;
	private NewsContentAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_content);
		
		mNewsItemBiz = new NewsItemBiz();
		
		Bundle extras = getIntent().getExtras();
		url = extras.getString("url");
		
		mAdapter = new NewsContentAdapter(this);
		
		mListView = (XListView) findViewById(R.id.id_listview);
		mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);
		
		mListView.setAdapter(mAdapter);
		mListView.disablePullRefreash();
		mListView.disablePullLoad();
		//mListView.setPullLoadEnable(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				News news = mDatas.get(position - 1);
				String imageLink = news.getImageLink();
				
				Intent intent = new Intent(NewsContentActivity.this, ImageShowActivity.class);
				intent.putExtra("url", imageLink);
				startActivity(intent);
			}
		});
		
		mProgressBar.setVisibility(View.VISIBLE);
		new LoadDataTask().execute();
		
	}
	
	class LoadDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				List<News> news = mNewsItemBiz.getNews(url).getNewses();
				mDatas = news;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Looper.prepare();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if(mDatas == null)
				return;
			
			mAdapter.addList(mDatas);
			mAdapter.notifyDataSetChanged();
			mProgressBar.setVisibility(View.GONE);
			
		}
		
	}
	
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	
	//·µ»Ø°´Å¥
	public void back(View view){
		finish();
	}
	
}
