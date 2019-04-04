package cc.urowks.ulibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cc.urowks.ulibrary.R;
import cc.urowks.ulibrary.bean.ErrorBean;
import cc.urowks.ulibrary.net.retrofit2.BaseModel;
import cc.urowks.ulibrary.net.retrofit2.BasePresenter;
import cc.urowks.ulibrary.net.retrofit2.IBaseView;
import cc.urowks.ulibrary.util.ActivityManager;
import cc.urowks.ulibrary.util.ActivityUtil;
import cc.urowks.ulibrary.util.BroadcastUtil;
import cc.urowks.ulibrary.util.ClassReflectHelper;
import cc.urowks.ulibrary.util.SystemBarTintManager;
import cc.urowks.ulibrary.util.ToastUtil;
import cc.urowks.ulibrary.widget.MultipleStatusView;

/**
 * activity基类 Created by Robin on 15/6/27.
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements OnClickListener,IBaseView  {
    protected String TAG = getClass().getSimpleName();
    protected P mMvpPresenter;
    protected M mModel;
    public Context mContext;
    protected SystemBarTintManager tintManager;
    private long exitTime;
    protected Unbinder bind;
    protected MultipleStatusView mMultipleStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initUtils();
        if (getLayoutResId() != 0) {
            mMultipleStateView = new MultipleStatusView(this);
            setContentView(getLayoutResId());
            bind = ButterKnife.bind(this);
            if (isStatusBarTintEnable()) initStatusBar();
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        //MVP
        mMvpPresenter = ClassReflectHelper.getT(this, 0);
        mModel = ClassReflectHelper.getT(this, 1);
        if (this instanceof IBaseView) {
            if (mMvpPresenter != null && mModel != null) {
                mMvpPresenter.setVM(this, mModel);
            }
        }
        initView(savedInstanceState);
        Intent intent = getIntent();
        initData(intent, savedInstanceState);
        setListener();
    }

    protected abstract boolean isStatusBarTintEnable();

    private void initUtils() {
        ActivityUtil.init(this);
        ToastUtil.init(this);
        BroadcastUtil.init(this);
    }

    /**
     * 设置状态栏颜色
     */
    protected void initStatusBar() {
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.drawable.primary_gradient_bg);/*通知栏所需颜色*/
        ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }

    /**
     * 获取布局资源id @return 返回此界面的布局资源id
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化View @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据，会在{@link #initView(Bundle)}方法后立即执行 @param intent             启动此activity的intent @param savedInstanceState
     */
    protected abstract void initData(Intent intent, Bundle savedInstanceState);

    /**
     * 设置监听，会在{@link #initData(Intent, Bundle)}方法后立即执行
     */
    protected abstract void setListener();

    /**
     * 当View被点击时调用 @param v
     */
    protected abstract void onViewClick(View v);

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }
    /**
     * 作用同{@link #findViewById(int)}
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        if (mMvpPresenter != null) {
            mMvpPresenter.onDestroy();
        }
    }

    //返回键
    public void back() {
        finish();
    }

    //隐藏软键盘
    protected void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void exit(View view) {
        finish();
    }

    @NonNull
    protected String getTrim(TextView view) {
        return view.getText().toString().trim();
    }


    @Override
    public void onBackPressed() {
        //再按一次返回
        if (ActivityManager.getInstance().getCurrentActivity().getLocalClassName() == "MainActivity") {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        } else {
            finish();
        }


    }

    @Override
    public void showLoading(MultipleStatusView multipleStatusView, String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showBusinessError(ErrorBean error) {
        mMultipleStateView.showError();
    }

    @Override
    public void showException(ErrorBean error) {
        mMultipleStateView.showNoNetwork();
    }
}
