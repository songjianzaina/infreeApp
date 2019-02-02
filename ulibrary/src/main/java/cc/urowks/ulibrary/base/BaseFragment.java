package cc.urowks.ulibrary.base;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cc.urowks.ulibrary.widget.LoadMoreListView;
import cc.urowks.ulibrary.widget.StateLayout;


/**
 * Base类就是专门给别人继承的
 * @author dzl
 *
 */
public abstract class BaseFragment extends Fragment {
	
	protected Activity context;
	protected StateLayout stateLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		System.out.println(getClass().getSimpleName() + "." + hashCode() + "， onCreateView()");
		context = getActivity();
		stateLayout = new StateLayout(context);
		
		View contentView = getContentView();
		if (contentView != null) {
			// 优先使用getContentView()的返回值做为正常加载到数据的显示界面
			stateLayout.setContentView(contentView);
		} else {
			// 如果getContentView()中没有返回值，则使用getContentViewLayoutId()的布局id来显示界面
			stateLayout.setContentView(getContentViewLayoutId());
		}
		
		initView();
		initListener();
//		initData(); 这个方法的调用在ViewPager的页面第一次被选择的时候才调用
		
		return stateLayout;
	}
	
	/**
	 * 提供这个方法，是为了可以省略强转操作
	 * @param id
	 * @return
	 */
	public <T> T findView(int id) {
		@SuppressWarnings("unchecked")
		T view = (T) stateLayout.findViewById(id);
		return view;
	}
	
	/**
	 * 当需要开子线程获取数据的时候调用requestAsyncTask方法，
	 * 然后在doInBackground方法中去写联网获取数据的代码，在onPostExecute中更新UI 
	 * @param requestType
	 */
	protected void requestAsyncTask(final int requestType) {
		new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... params) {
				// 这里是BaseFragment的内部，它是不知道子类要获取的数据是什么，所以调用抽象方法让子类去做获取数据的操作
				return BaseFragment.this.doInBackground(requestType);
			}
			
			@Override
			protected void onPostExecute(Object result) {
				// 把result数据显示到UI，父类也是不知道如何显示的，所以调用抽象的方法，让子类去决定如何显示Ui
				BaseFragment.this.onPostExecute(requestType, result);
			}
			
		}.execute();
	}
	
	/** 根据初始化数据来决定显示哪个状态View，如果数据结果是ok的，则返回true */
	public boolean checkInitData(ArrayList datas) {
		boolean result = false;
		if (datas == null) {
			stateLayout.showFailView();
		} else if (datas.isEmpty()) {
			stateLayout.showEmptyView();
		} else {
			stateLayout.showContentView();
			result = true;
		}
		return result;
	}
	
	/** 检查加载更多的数据，根据数据情况显示对应的View，如果数据ok就返回true */
	public boolean checkLoadMoreData(@SuppressWarnings("rawtypes") ArrayList datas, LoadMoreListView listView) {
		boolean result = false;
		listView.showLoading();
		if (datas == null) {
			listView.showLoadFail();
		} else if (datas.isEmpty()) {
			listView.showNoMore();
		} else {
			result = true;
		}
		return result;
	}
	
	/**
	 * 这个方法运行在子线程，可以在这个方法中做耗时的操作，如联网获取数据  
	 * @param requestType 请求类型
	 * @return
	 */
	protected abstract Object doInBackground(int requestType);
	
	/**
	 * 这个方法运行在主线程，可以在这个方法中做更新ui的操作
	 * @param requestType 请求类型
	 * @param result 结果
	 */
	protected abstract void onPostExecute(int requestType, Object result);

	/** 返回Fragment界面对应的标题 */
	public abstract CharSequence getTitle();
	
	/** 这个View是数据加载成功的View，因为每个界面加载成功的界面都不一样，所以这里没法决定用哪个View，所以写成了抽象方法 */
	public abstract View getContentView();
	
	/** 这个View是数据加载成功的View，因为每个界面加载成功的界面都不一样，所以这里没法决定用哪个View，所以写成了抽象方法 */
	public abstract int getContentViewLayoutId();
	
	/** 查找View的代码写到这个方法中 */
	public abstract void initView();
	
	/** 设置监听器的代码写到这个方法中 */
	public abstract void initListener();
	
	/** 初始化数据的代码写到这个方法中 */
	public abstract void initData();
	
	
}
