package com.example.csdn.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.NewsItem;
import com.example.csdn.util.Logger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsItemDao {

	private DBHelper dbHelper;
	
	public NewsItemDao(Context context){
		dbHelper = new DBHelper(context);
	}

	public void add(List<NewsItem> mDatas) {
		// TODO Auto-generated method stub
		for (NewsItem newsItem : mDatas) {
			add(newsItem);
		}
	}
	
	//insert
	public void add(NewsItem newsItem){
		Logger.e("add newstype " + newsItem.getNewsType());
		String sql = "insert into tb_newsItem(title, link, date, imgLink, content, newstype) values(?,?,?,?,?,?);";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//°ó¶¨²ÎÊý
		db.execSQL(sql, new Object[]{newsItem.getTitle(), newsItem.getLink(), newsItem.getDate(), newsItem.getImgLink(), newsItem.getContent(), newsItem.getNewsType()});
		db.close();
	}
	
	//delete
	public void deleteAll(int newsType) {
		// TODO Auto-generated method stub
		Logger.e("delete newstype " + newsType);
		String sql = "delete from tb_newsItem where newstype = ?;";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(sql, new Object[]{newsType});
		db.close();
	}

	//select
	public List<NewsItem> list(int newsType, int currentPage) {
		// TODO Auto-generated method stub
		Logger.e("newstype " + newsType);
		Logger.e("currentPage " + currentPage);
		
		//0-9, 10-19...
		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		try {
			
			int offset = 10 * (currentPage - 1);
			String sql = "select title, link, date, imgLink, content, newstype from tb_newsItem where newstype = ? limit ? , ?;";
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery(sql, new String[]{newsType + "", offset + "", "" + (offset + 10)});
			
			NewsItem newsItem = null;
			
			while(c.moveToNext()){
				newsItem = new NewsItem();
				
				String title = c.getString(0);
				String link = c.getString(1);
				String date = c.getString(2);
				String imgLink = c.getString(3);
				String content = c.getString(4);
				Integer newstype  = c.getInt(5);
				
				newsItem.setTitle(title);
				newsItem.setLink(link);
				newsItem.setDate(date);
				newsItem.setImgLink(imgLink);
				newsItem.setContent(content);
				newsItem.setNewsType(newstype);
				
				newsItems.add(newsItem);
			}
			c.close();
			db.close();
			Logger.e("newsItems.size(): " + newsItems.size());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return newsItems;
	}
}
