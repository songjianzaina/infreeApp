package cc.urowks.ulibrary.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 全国省市地区工具类
 * <p/>
 * Created by jiang on 2015/10/13.
 */
public class RegionUtils {

    private static final String DB_NAME = "region.db";

    private static final String TABLE_NAME = "region";

    /**
     * 获取指定类型下的所有地区
     *
     * @param context 上下文
     * @param type    地区类型，1--省；2--市；3--区/县，如果类型为空将返回所有的地区
     * @return 如果获取失败将返回null
     */
    public static List<RegionVO> getRegionByType(Context context, String type) {
        SQLiteDatabase database = openDatabase(context);
        if (database == null) {
            return null;
        }
        Cursor cursor;
        if (TextUtils.isEmpty(type)) {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + RegionColumns.REGION_ID + " ASC", null);
        } else {
            String[] selectionArgs = new String[]{type};
            cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + RegionColumns.REGION_TYPE
                    + " = ? ORDER BY " + RegionColumns.REGION_ID + " ASC", selectionArgs);
        }
        List<RegionVO> regionList = cursorToList(cursor, true);
        database.close();
        return regionList;
    }

    /**
     * 获取指定地区的下一级地区集合
     *
     * @param context 上下文
     * @param id      父级地区id
     * @return 地区集合，如果获取失败将返回null
     */
    public static List<RegionVO> getRegionByParentId(Context context, String id) {
        SQLiteDatabase database = openDatabase(context);
        if (database == null || TextUtils.isEmpty(id)) {
            return null;
        }
        String[] selectionArgs = new String[]{id};
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + RegionColumns.PARENT_ID
                + " = ? ORDER BY " + RegionColumns.REGION_ID + " ASC", selectionArgs);
        List<RegionVO> regionList = cursorToList(cursor, true);
        database.close();
        return regionList;
    }

    /**
     * 通过id获取指定地区数据
     *
     * @param context 上下文
     * @param id      地区id
     * @return 地区数据，如果获取失败将返回null
     */
    public static RegionVO getRegionById(Context context, String id) {
        SQLiteDatabase database = openDatabase(context);
        if (database == null || TextUtils.isEmpty(id)) {
            return null;
        }
        String[] selectionArgs = new String[]{id};
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + RegionColumns.REGION_ID + " = ?", selectionArgs);
        if (cursor == null) {
            return null;
        }
        if (cursor.getCount() < 1) {
            cursor.close();
            return null;
        }
        int regionIdIndex = cursor.getColumnIndex(RegionColumns.REGION_ID);
        int regionNameIndex = cursor.getColumnIndex(RegionColumns.REGION_NAME);
        int regionTypeIndex = cursor.getColumnIndex(RegionColumns.REGION_TYPE);
        int parentIdIndex = cursor.getColumnIndex(RegionColumns.PARENT_ID);
        cursor.moveToNext();
        Integer regionId = cursor.getInt(regionIdIndex);
        String regionName = cursor.getString(regionNameIndex);
        Integer regionType = cursor.getInt(regionTypeIndex);
        Integer parentId = cursor.getInt(parentIdIndex);
        RegionVO region = new RegionVO(regionId, regionName, regionType, parentId);
        cursor.close();
        database.close();
        return region;
    }

    /**
     * 根据类型获取地区数量
     *
     * @param context 上下文
     * @param type    地区类型（1--省；2--市；3--区县），如果传null将返回所有的地区数量
     * @return 如果获取失败将返回0
     */
    public static int getRegionCount(Context context, String type) {
        SQLiteDatabase database = openDatabase(context);
        if (database == null) {
            return 0;
        }
        Cursor cursor;
        if (TextUtils.isEmpty(type)) {
            cursor = database.rawQuery("SELECT COUNT(*) AS counts FROM " + TABLE_NAME, null);
        } else {
            String[] selectionArgs = new String[]{type};
            cursor = database.rawQuery("SELECT COUNT(*) AS counts FROM " + TABLE_NAME + " WHERE " + RegionColumns.REGION_TYPE + " = ?", selectionArgs);
        }
        if (cursor == null) {
            return 0;
        }
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    /**
     * 将游标里的数据取出放到集合中，游标必须包含完整的数据表字段
     *
     * @param cursor      地区数据游标
     * @param isAutoClose 操作完成后是否关闭游标
     * @return 地区集合
     */
    private static List<RegionVO> cursorToList(Cursor cursor, boolean isAutoClose) {
        if (cursor == null) {
            return null;
        }
        int regionIdIndex = cursor.getColumnIndex(RegionColumns.REGION_ID);
        int regionNameIndex = cursor.getColumnIndex(RegionColumns.REGION_NAME);
        int regionTypeIndex = cursor.getColumnIndex(RegionColumns.REGION_TYPE);
        int parentIdIndex = cursor.getColumnIndex(RegionColumns.PARENT_ID);
        List<RegionVO> regionList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Integer regionId = cursor.getInt(regionIdIndex);
            String regionName = cursor.getString(regionNameIndex);
            Integer regionType = cursor.getInt(regionTypeIndex);
            Integer parentId = cursor.getInt(parentIdIndex);
            RegionVO region = new RegionVO(regionId, regionName, regionType, parentId);
            regionList.add(region);
        }
        if (isAutoClose) {
            cursor.close();
        }
        return regionList;
    }

    /**
     * 打开数据库
     *
     * @param context 上下文
     * @return 打开失败将返回null
     */
    private static SQLiteDatabase openDatabase(Context context) {
        if (!copyDatabase(context)) {
            // 拷贝数据库失败
            return null;
        }
        return context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    /**
     * 将assets目录下的数据库文件拷贝到系统默认目录下
     *
     * @param context 上下文
     * @return 拷贝成功返回true，否则返回false
     */
    private static boolean copyDatabase(Context context) {
        String dbDir = "/data/data/" + context.getPackageName() + "/databases";
        File dbFile = new File(dbDir, DB_NAME);
        if (dbFile.exists()) {
            return true;
        }
        dbFile.getParentFile().mkdirs();
        try {
            InputStream is = context.getAssets().open(DB_NAME);
            FileOutputStream fot = new FileOutputStream(dbFile);
            byte[] buffer = new byte[8 * 1024];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fot.write(buffer, 0, count);
            }
            fot.flush();
            fot.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 地区数据对象
     */
    public static class RegionVO {
        private Integer regionId;
        private String regionName;
        private Integer regionType; // 1：省；2：市；3：区/县
        private Integer parentId; // 上级地区id，如果是省或直辖市parentId=0

        public RegionVO() {

        }

        public RegionVO(Integer regionId, String regionName, Integer regionType, Integer parentId) {
            this.regionId = regionId;
            this.regionName = regionName;
            this.regionType = regionType;
            this.parentId = parentId;
        }

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public Integer getRegionType() {
            return regionType;
        }

        public void setRegionType(Integer regionType) {
            this.regionType = regionType;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        @Override
        public String toString() {
            return "RegionVO{" +
                    "regionId=" + regionId +
                    ", regionName='" + regionName + '\'' +
                    ", regionType=" + regionType +
                    ", parentId=" + parentId +
                    '}';
        }
    }

    /**
     * 地区表字段
     */
    private class RegionColumns {
        /**
         * 唯一行编号，Type：INTEGER
         */
        public static final String _ID = "_id";

        /**
         * 地区编号，Type：INTEGER
         */
        public static final String REGION_ID = "region_id";

        /**
         * 地区名称，Type：varchar(20)
         */
        public static final String REGION_NAME = "region_name";

        /**
         * 地区类型，Type：INTEGER，1--省，2--市，3--区县
         */
        public static final String REGION_TYPE = "region_type";

        /**
         * 父级id，Type：INTEGER
         */
        public static final String PARENT_ID = "parent_id";
    }

}
