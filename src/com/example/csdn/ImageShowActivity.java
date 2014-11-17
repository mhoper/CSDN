package com.example.csdn;

import com.example.csdn.util.FileUtil;
import com.example.csdn.util.Http;
import com.polites.android.GestureImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ImageShowActivity extends Activity {

	private String url;
	private ProgressBar mLoading;
	private GestureImageView mGestureImageView;
	private Bitmap mBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_page);
		
		url = getIntent().getExtras().getString("url");
		mLoading = (ProgressBar) findViewById(R.id.loading);
		mGestureImageView = (GestureImageView) findViewById(R.id.image);
		
		new DownloadImgTask().execute();
	}
	
	public void back(View view){
		finish();
	}
	
	public void downloadImg(View view){
		mGestureImageView.setDrawingCacheEnabled(true);
		if(FileUtil.writeSDcard(url, mGestureImageView.getDrawingCache())){
			Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
		}else{
			
			Toast.makeText(getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT).show();
		}
		mGestureImageView.setDrawingCacheEnabled(false);
	}
	
	class DownloadImgTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mBitmap = Http.HttpGetBmp(url);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			mGestureImageView.setImageBitmap(mBitmap);
			mLoading.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
		
	}
}
