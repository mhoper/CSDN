package com.example.csdn;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.bean.CommonException;
import com.example.bean.NewsItem;
import com.example.biz.NewsItemBiz;
import com.example.csdn.adapter.NewsItemAdapter;
import com.example.csdn.dao.NetUtil;
import com.example.csdn.dao.NewsItemDao;
import com.example.csdn.util.AppUtil;
import com.example.csdn.util.ToastUtil;

public class MainFragment extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore {

	public static final int LOAD_MORE = 0x110;
	public static final int LOAD_REFRESH = 0x111;
	
	public static final int TIP_ERROR_NO_NETWORK = 0x112;
	public static final int TIP_ERROR_SERVER = 0x113;
	
	private boolean isFirstIn = true;
	private boolean isLoadingDataFromNetwork;
	
	private int newsType = Constant.NEWS_TYPE_YEJIE;
	private int currentPage = 1;
	private NewsItemBiz mNewsItemBiz;
	
	//�����ݿ⽻��
	private NewsItemDao mNewsItemDao;
	
	private XListView mXListView;
	private NewsItemAdapter mAdapter;
	private List<NewsItem> mDatas = new ArrayList<NewsItem>();

	
	public MainFragment(int newsType) {
		// TODO Auto-generated constructor stub
		this.newsType = newsType;
		mNewsItemBiz = new NewsItemBiz();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab_item_fragment_main, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mNewsItemDao = new NewsItemDao(getActivity());
		mAdapter = new NewsItemAdapter(getActivity(), mDatas);
		
		//��ʼ��
		mXListView = (XListView) getView().findViewById(R.id.id_xlistView);
		mXListView.setAdapter(mAdapter);
		mXListView.setPullRefreshEnable(this);
		mXListView.setPullLoadEnable(this);
		mXListView.setRefreshTime(AppUtil.getRefreshTime(getActivity(), newsType));
		
		//���õ������
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				NewsItem newsItem = mDatas.get(position - 1);
				Intent intent = new Intent(getActivity(), NewsContentActivity.class);
				intent.putExtra("url", newsItem.getLink());
				startActivity(intent);
			}
		});
		
		
		if(isFirstIn){
			//���ν���ʱˢ��
			mXListView.startRefresh();
			isFirstIn = false;
		}else{
			mXListView.NotRefreshAtBegin();
		}
	}

	@Override
	public void onRefresh() {
		
		new LoadDataTask().execute(LOAD_REFRESH	);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new LoadDataTask().execute(LOAD_MORE);
	}
	
	class LoadDataTask extends AsyncTask<Integer, Void, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			switch(params[0]){
			case LOAD_MORE:
				loadMoreData();
				break;
				
			case LOAD_REFRESH:
				return refreshData();
			}
			return -1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			switch(result){
			case TIP_ERROR_NO_NETWORK:
				ToastUtil.Toast(getActivity(), "û���������ӣ�");
				mAdapter.setDatas(mDatas);
				mAdapter.notifyDataSetChanged();
				break;
				
			case TIP_ERROR_SERVER:
				ToastUtil.Toast(getActivity(), "����������");
				break;
				
			default:
				break;
			}
			
			mXListView.setRefreshTime(AppUtil.getRefreshTime(getActivity(), newsType));
			mXListView.stopRefresh();
			mXListView.stopLoadMore();
		}
		
		private Integer refreshData() {
			if(NetUtil.checkNet(getActivity())){
				//��ȡ������
				try {
					List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
					mAdapter.setDatas(newsItems);
					
					isLoadingDataFromNetwork = true;
					//����ˢ��ʱ��
					AppUtil.setRefreshTime(getActivity(), newsType);
					//������ݿ�����
					mNewsItemDao.deleteAll(newsType);
					//�������ݿ�
					mNewsItemDao.add(newsItems);
					
				} catch (CommonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isLoadingDataFromNetwork = false;
					return TIP_ERROR_SERVER;
				}
				
			}else{
				isLoadingDataFromNetwork = false;
				//�����ݿ��������
				List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
				mDatas = newsItems;
				return TIP_ERROR_NO_NETWORK;
			}
			
			return -1;
		}

		/**
		 * ���ݵ�ǰ����������ж��Ǵ������ȡ�������ݿ����
		 */
		private void loadMoreData() {
			// �������ȡ
			if(isLoadingDataFromNetwork){
				currentPage += 1;
				try {
					List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
					mNewsItemDao.add(newsItems);
					mAdapter.addAll(newsItems);
					
				} catch (CommonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{ //�����ݿ����
				currentPage += 1;
				List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
				mAdapter.addAll(newsItems);
			}
		}

		
	}
}
