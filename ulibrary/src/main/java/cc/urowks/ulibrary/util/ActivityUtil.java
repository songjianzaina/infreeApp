package cc.urowks.ulibrary.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by songjian on 2017/1/6.
 */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static Context mContext;


    private ActivityUtil() {

    }


    public static void init(Context context) {
        mContext = context;
    }

    //Activity跳转
    public static void startActivity(Class clazz) {
        if (mContext == null) {
            LogUtils.i(TAG, "ActivityUtil未初始化");
            return;
        }
        mContext.startActivity(new Intent(mContext, clazz));
    }

    //Activity跳转 并携带数据
    public static void startActivity(Class clazz, String key, Object object) {
        if (mContext == null) {
            LogUtils.i(TAG, "ActivityUtil未初始化");
            return;
        }
        Intent intent = new Intent(mContext, clazz);

        if (object instanceof String) {
            intent.putExtra(key, (String) object);
        } else if (object instanceof String[]) {
            intent.putExtra(key, (String[]) object);
        } else if (object instanceof Integer) {
            intent.putExtra(key, (Integer) object);
        } else if (object instanceof Integer[]) {
            intent.putExtra(key, (Integer[]) object);
        } else if (object instanceof Serializable) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(key, (Serializable) object);
            intent.putExtras(bundle);
        } else if (object instanceof Float) {
            intent.putExtra(key, (Float) object);
        } else if (object instanceof Float[]) {
            intent.putExtra(key, (Float[]) object);
        } else if (object instanceof Boolean) {
            intent.putExtra(key, (Boolean) object);

        } else if (object instanceof Boolean[]) {
            intent.putExtra(key, (Boolean[]) object);

        } else if (object instanceof Byte) {
            intent.putExtra(key, (Byte) object);

        } else if (object instanceof Byte[]) {
            intent.putExtra(key, (Byte[]) object);

        } else if (object instanceof Character) {
            intent.putExtra(key, (Character) object);

        } else if (object instanceof Character[]) {
            intent.putExtra(key, (Character[]) object);

        } else if (object instanceof Short) {
            intent.putExtra(key, (Short) object);

        } else if (object instanceof Short[]) {
            intent.putExtra(key, (Short[]) object);

        } else if (object instanceof Double) {
            intent.putExtra(key, (Double) object);

        } else if (object instanceof Double[]) {
            intent.putExtra(key, (Double[]) object);

        } else if (object instanceof Long) {
            intent.putExtra(key, (Long) object);

        } else if (object instanceof Long[]) {
            intent.putExtra(key, (Long[]) object);

        } else if (object instanceof CharSequence) {
            intent.putExtra(key, (CharSequence) object);

        } else if (object instanceof CharSequence[]) {
            intent.putExtra(key, (CharSequence[]) object);

        } else if (object instanceof Parcelable) {
            intent.putExtra(key, (Parcelable) object);

        } else if (object instanceof Parcelable[]) {
            intent.putExtra(key, (Parcelable[]) object);

        } else {
            return;
        }

        mContext.startActivity(intent);
    }


}
