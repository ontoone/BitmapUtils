package com.mansoul.bitmaputils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 本地缓存
 * Created by Mansoul on 16/5/7.
 */
public class LocalCacheUtils {

    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bitmap_cache";

    //写本地缓存
    public void setLocalCache(Bitmap bitmap, String url) {
        File dir = new File(CACHE_PATH);
        if (!dir.exists() || dir.isDirectory()) {
            dir.mkdirs(); //创建文件夹
        }

        try {
            String fileName = MD5Encoder.encode(url);

            File cacheFile = new File(dir, fileName);

            // 参1:图片格式;参2:压缩比例0-100; 参3:输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读本地缓存
    public Bitmap getLocalCache(String url) {
        try {
            File cacheFile = new File(CACHE_PATH, MD5Encoder.encode(url));  //读取缓存

            if (cacheFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
