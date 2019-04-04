package cc.urowks.ulibrary.net.async_http;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;

import cc.urowks.ulibrary.bean.AppVersion;
import cc.urowks.ulibrary.util.AppUtil;
import cc.urowks.ulibrary.util.LogUtils;
import cc.urowks.ulibrary.util.ToastUtil;

import static cc.urowks.ulibrary.util.AppUtil.installApk;


public class UpdateChecker {
    private static final String TAG = "UpdateChecker";
    private Activity mContext;
    //下载apk的对话框
    private ProgressDialog mProgressDialog;

    private AppVersion appVersion;


    public UpdateChecker(Activity context) {
        mContext = context;
    }

    public void checkForUpdates() {
        UserApi.updateApp(new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {

            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public Object parseSuccessResponse(String rawJsonData) throws Throwable {
                LogUtils.i(TAG, "联网检查更新 rawJsonData=" + rawJsonData);
                appVersion = JSON.parseObject(rawJsonData, AppVersion.class);
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (appVersion.getVersionCode() > AppUtil.getVersionCode()) {
                            showUpdateDialog();
                        } else {
                            LogUtils.i(TAG, "已经是最新版本");
                        }
                    }
                });
                return null;
            }
        });
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle("有新版本");
        builder.setMessage(appVersion.getDesc());
        builder.setPositiveButton("立即更新",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        downLoadApk();
                    }
                });
        builder.setCancelable(false);
        if (appVersion.getEnforceFlag() == 0) {

            builder.setNegativeButton("稍后再说",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            builder.setCancelable(true);
        }
        builder.show();
    }

    private void downLoadApk() {
        // 指定文件类型
        HttpUtil.downloadFile(appVersion.getDownloadUrl(), new FileAsyncHttpResponseHandler(mContext) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, File file) {
                installApk(file);
            }

            @Override
            public void onStart() {
                super.onStart();
                ToastUtil.showToast("开始下载");
            }



            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
            }
        });
    }


}