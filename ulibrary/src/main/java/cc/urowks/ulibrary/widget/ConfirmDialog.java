package cc.urowks.ulibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cc.urowks.ulibrary.R;


/**
 * 自定义 确认对话框
 * Created by wanghongjia on 2016/5/24.
 */
public class ConfirmDialog extends Dialog {
    private Context context;
    private String title;
    private String content;

    private String confirmBtnText;
    private String cancelBtnText;
    private boolean showCancelBtn;
    private ClickListenerInterface clickListenerInterface;


    public ConfirmDialog(Context context, String title) {
        super(context, R.style.styleConfirmDialog);
        this.context = context;
        this.title = title;
        this.confirmBtnText = "确认";
        this.cancelBtnText = "取消";
        this.showCancelBtn = true;
    }

    public ConfirmDialog(Context context, String title, String content) {
        super(context, R.style.styleConfirmDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirmBtnText = "确认";
        this.cancelBtnText = "取消";
        this.showCancelBtn = true;
    }

    public ConfirmDialog(Context context, String title, boolean showCancelBtn, String content) {
        super(context, R.style.styleConfirmDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirmBtnText = "确认";
        this.cancelBtnText = "取消";
        this.showCancelBtn = showCancelBtn;
    }

    public ConfirmDialog(Context context, String title, String content, String confirmBtnText, String cancelBtnText, boolean showCancelBtn) {
        super(context, R.style.styleConfirmDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirmBtnText = confirmBtnText;
        this.cancelBtnText = cancelBtnText;
        this.showCancelBtn = showCancelBtn;
    }

    public interface ClickListenerInterface {
        void doConfirm();

        void doCancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm, null);
        setContentView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.txt_title);
        TextView tvContent = (TextView) view.findViewById(R.id.txt_content);
        TextView tvConfirm = (TextView) view.findViewById(R.id.btn_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.btn_cancel);

        if (!showCancelBtn) {
            tvCancel.setVisibility(View.GONE);
        }
        tvTitle.setText(title);
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

        tvConfirm.setText(confirmBtnText);
        tvCancel.setText(cancelBtnText);

        tvConfirm.setOnClickListener(new DialogClickListener());
        tvCancel.setOnClickListener(new DialogClickListener());
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class DialogClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btn_confirm) {
                clickListenerInterface.doConfirm();

            } else if (i == R.id.btn_cancel) {
                clickListenerInterface.doCancel();

            }
        }
    }
}
