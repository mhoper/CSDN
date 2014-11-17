package com.example.csdn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.NewsItem;
import com.example.csdn.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class NewsItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<NewsItem> mDatas;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public NewsItemAdapter(Context context, List<NewsItem> datas) {
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);

		//如果没有初始化，就先初始化
		if(!imageLoader.isInited()){
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		}
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.images)
				.showImageForEmptyUri(R.drawable.images)
				.showImageOnFail(R.drawable.images).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}
	
	public void addAll(List<NewsItem> mDatas){
		this.mDatas.addAll(mDatas);
	}
	
	public void setDatas(List<NewsItem> mDatas){
		this.mDatas.clear();
		this.mDatas.addAll(mDatas);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.news_item_yidong, parent, false);
			holder = new ViewHolder();
			
			holder.mContent = (TextView) convertView.findViewById(R.id.id_content);
			holder.mDate = (TextView) convertView.findViewById(R.id.id_date);
			holder.mImg = (ImageView) convertView.findViewById(R.id.id_newsImg);
			holder.mTitle = (TextView) convertView.findViewById(R.id.id_title);
			
			convertView.setTag(holder);
		}else{
			
			holder = (ViewHolder) convertView.getTag();
		}
		
		NewsItem newsItem = mDatas.get(position);
		holder.mTitle.setText(newsItem.getTitle());
		holder.mContent.setText(newsItem.getContent());
		holder.mDate.setText(newsItem.getDate());
		
		if(newsItem.getImgLink() != null){
			holder.mImg.setVisibility(View.VISIBLE);
			imageLoader.displayImage(newsItem.getImgLink(), holder.mImg, options);
		}else{
			
			holder.mImg.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	private final class ViewHolder{
		TextView mTitle;
		TextView mContent;
		ImageView mImg;
		TextView mDate;
	}

}
