package cc.urowks.ulibrary.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;



/**
 * Created by songjian on 2018/12/29.
 * 目前這個圖片工具類使用的是Glide框架 如果想換成其他的框架 直接在這裡修改即可 其他代碼不變
 */

public class ImageUtils {

    /**
     * 加载图片
     *
     * @param url
     * @param context
     * @param imageView
     */
    public static void loadImage(String url, Context context, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);

    }

    /**
     * 加载图片
     *
     * @param url
     * @param context
     * @param imageView
     */
    public static void loadHeaderImage(String url, Context context, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
//                .placeholder(R.mipmap.default_header)
                .into(imageView);

    }

    /**
     *
     * 是否禁止磁盘缓存加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param type
     *            缓存的类型
     *            <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *            <li>磁盘禁止缓存DiskCacheStrategy.NONE</li>
     */
    public static void loadImage(String url, Context context, ImageView imageView, DiskCacheStrategy type) {
        Glide.with(context).load(url).diskCacheStrategy(type).into(imageView);
    }

    /**
     *
     * 是否禁止内存缓存加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param skipMemoryCache
     *            禁止内存缓存 true为禁止
     */
    public static void loadImage(String url, Context context, ImageView imageView, boolean skipMemoryCache) {
        Glide.with(context).load(url).skipMemoryCache(skipMemoryCache).into(imageView);
    }

    /**
     *
     * 是否禁止内存/磁盘缓存加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param type
     *            缓存的类型
     *            <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *            <li>磁盘禁止缓存DiskCacheStrategy.NONE</li>
     * @param skipMemoryCache
     *            禁止内存缓存 true为禁止
     */
    public static void loadImage(String url, Context context, ImageView imageView, DiskCacheStrategy type,
                                 boolean skipMemoryCache) {
        Glide.with(context).load(url).skipMemoryCache(skipMemoryCache).diskCacheStrategy(type).into(imageView);
    }

    /**
     * 清除内存中的缓存 必须在UI线程中调用
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘中的缓存 必须在后台线程中调用，建议同时clearMemory()
     *
     * @param context
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    /**
     * 优先级加载图片
     * @param url
     * @param context
     * @param imageView
     * @param priority  优先级  Priority.LOW/Priority.HIGH
     */
    public static void loadImageWithPriority(String url, Context context, ImageView imageView,Priority priority) {
        Glide.with(context).load(url).priority(priority).into(imageView);
    }

}
