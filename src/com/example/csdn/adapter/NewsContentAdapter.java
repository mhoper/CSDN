package com.example.csdn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.News;
import com.example.csdn.Constant;
import com.example.csdn.R;
import com.example.csdn.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class NewsContentAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<News> mDatas = new ArrayList<News>();

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public NewsContentAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);

		//如果没有初始化，就先初始化
		if(!imageLoader.isInited()){
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		}
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.images)
				.showImageForEmptyUri(R.drawable.images)
				.showImageOnFail(R.drawable.images).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public void addList(List<News> datas) {
		// TODO Auto-generated method stub
		mDatas.addAll(datas);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		switch (mDatas.get(position).getType()) {
		case Constant.NEWS_TYPE_TITLE:
			return 0;

		case Constant.NEWS_TYPE_SUMMARY:
			return 1;
			
		case Constant.NEWS_TYPE_CONTENT:
			return 2;
			
		case Constant.NEWS_TYPE_IMG:
			return 3;
			
		case Constant.NEWS_TYPE_BOLD_TITLE:
			return 4;
		default:
			return -1;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		switch (mDatas.get(position).getType()) {
		case Constant.NEWS_TYPE_IMG:
			return true;

		default:
			return false;
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		News news = mDatas.get(position);
		Logger.e(news.toString());
		
		ViewHolder holder = null;
		if(null == convertView){
			holder = new ViewHolder();
			switch (news.getType()) {
			case Constant.NEWS_TYPE_TITLE:
				convertView = mInflater.inflate(R.layout.news_content_title_item, parent, false);
				holder.mTextView = (TextView) convertView.findViewById(R.id.text);
				break;

			case Constant.NEWS_TYPE_SUMMARY:
				convertView = mInflater.inflate(R.layout.news_content_summary_item, parent, false);
				holder.mTextView = (TextView) convertView.findViewById(R.id.text);
				break;

			case Constant.NEWS_TYPE_CONTENT:
				convertView = mInflater.inflate(R.layout.news_content_item, parent, false);
				holder.mTextView = (TextView) convertView.findViewById(R.id.text);
				break;

			case Constant.NEWS_TYPE_IMG:
				convertView = mInflater.inflate(R.layout.news_content_img_item, parent, false);
				holder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
				break;

			case Constant.NEWS_TYPE_BOLD_TITLE:
				convertView = mInflater.inflate(R.layout.news_content_bold_title_item, parent, false);
				holder.mTextView = (TextView) convertView.findViewById(R.id.text);
				break;
			}
			convertView.setTag(holder);
		}else{
			
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(null != news){
			switch (news.getType()) {
			case Constant.NEWS_TYPE_IMG:
				imageLoader.displayImage(news.getImageLink(), holder.mImageView, options);
				break;
			case Constant.NEWS_TYPE_TITLE:
				holder.mTextView.setText(news.getTitle());
				break;

			case Constant.NEWS_TYPE_SUMMARY:
				holder.mTextView.setText(news.getSummary());
				break;

			case Constant.NEWS_TYPE_CONTENT:
				holder.mTextView.setText("\u3000\u3000" + Html.fromHtml(news.getContent()));
				break;

			case Constant.NEWS_TYPE_BOLD_TITLE:
				holder.mTextView.setText("\u3000\u3000" + Html.fromHtml(news.getContent()));
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	private final class ViewHolder{
		TextView mTextView;
		ImageView mImageView;
	}

}
