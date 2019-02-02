package cc.urowks.ulibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cc.urowks.ulibrary.R;


public class LoadMoreListView extends ListView {

	private static final int LAODING = 0;
	private static final int LAOD_FAIL = 1;
	private static final int NO_MORE = 3;
	private ProgressBar mPbProgress;
	private TextView mTvLoadMore;
	private int mState = LAODING;
	private OnLoadMoreListener mListener;
	private boolean mIsLoadingMore;
	private View mView;

	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		initListener();
		initData();
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public LoadMoreListView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mView = View.inflate(getContext(), R.layout.item_listview_loadmore_footer, null);
		mPbProgress = (ProgressBar) mView.findViewById(R.id.item_pull_to_refresh_load_progress);
		mTvLoadMore = (TextView) mView.findViewById(R.id.item_pull_to_refresh_loadmore_text);
		addFooterView(mView);
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& getLastVisiblePosition() == getCount() - 1
						&& mListener != null && !mIsLoadingMore
						&& mState == LAODING) {
					mIsLoadingMore = true;
					mListener.loading();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		// 为footerView设置点击事件
		mView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mState == LAOD_FAIL && mListener != null) {
					showLoading();
					mListener.retry();
				}
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}

	/** 显示加载更多 */
	public void showLoading() {
		mState = LAODING;
		mPbProgress.setVisibility(View.VISIBLE);
		mTvLoadMore.setText("正在加载更多...");
	}

	/** 显示加载失败 */
	public void showLoadFail() {
		mState = LAOD_FAIL;
		mPbProgress.setVisibility(View.INVISIBLE);
		mTvLoadMore.setText("加载失败,点击重新加载");

	}

	/** 显示没有更多数据 */
	public void showNoMore() {
		mState = NO_MORE;
		mPbProgress.setVisibility(View.INVISIBLE);
		mTvLoadMore.setText("没有更多的数据了");

	}

	public void completeLoad() {
		mIsLoadingMore = false;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener listener) {
		mListener = listener;

	}

	public interface OnLoadMoreListener {
		void loading();

		void retry();
	}

}
