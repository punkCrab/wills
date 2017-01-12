package com.wills.help.photo.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.wills.help.photo.model
 * Created by lizhaoyong
 * 2016/11/24.
 */

public class CameraUtils {

    private static final String saveDir = Environment.getExternalStorageDirectory() + "/wills/photos";

    public static File getPath(){
        File tempFile = null;
        File file = new File(saveDir);
        if (!file.exists()) file.mkdir();
        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
        String filename = "wills" + (t.format(new Date())) + ".jpg";
        tempFile = new File(saveDir, filename);
        return tempFile;
    }

    /**
     * 防止拍照用相册不显示
     * @param context
     * @param file
     */
    public static void insertCamera(Context context ,File file){
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("_data", file.toString());
        localContentValues.put("description", "save image ---");
        localContentValues.put("mime_type", "image/jpeg");
        ContentResolver localContentResolver = context.getContentResolver();
        Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        localContentResolver.insert(localUri, localContentValues);
    }

    /**
     * 保存bitmap
     * @param bmp
     * @return
     */
    public static File saveImage(Bitmap bmp){
        File file = getPath();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
