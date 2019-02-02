package cc.urowks.ulibrary.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cc.urowks.ulibrary.InsworksApp;


/**
 * 图片工具类
 */
public class BitmapUtil {

    @Nullable
    public static File compressPictureFile(String sourcePicPath, String destDir, int width, int height) {
        Bitmap bitmap = fileToBitmap(sourcePicPath, width, height);
        if (bitmap == null) {
            return null;
        }
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String picName = sourcePicPath.substring(sourcePicPath.lastIndexOf(File.separator));
        File destPicFile = new File(dir, picName);
        if (destPicFile.exists()) {
            destPicFile.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destPicFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            return destPicFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bitmap.recycle();
        }
        return null;
    }

    @Nullable
    public static Bitmap fileToBitmap(String picPath, int width, int height) {
        if (width == 0 || height == 0) {
            throw new IllegalArgumentException("图片宽高不能为零：width = " + width
                    + "；height = " + height);
        }
        if (TextUtils.isEmpty(picPath)) {
            return null;
        }
        if (!new File(picPath).exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picPath, options);
        int ratioW = options.outWidth / width;
        int ratioH = options.outHeight / height;
        int ratio = Math.max(ratioW, ratioH);
        if (ratio <= 0) ratio = 1;
        options.inSampleSize = ratio;
        options.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeFile(picPath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getThumbnail(Uri uri, int size) throws FileNotFoundException {
        InputStream input = InsworksApp.getInstance().getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        StreamUtils.close(input);
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = InsworksApp.getInstance().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        StreamUtils.close(input);
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }
}
