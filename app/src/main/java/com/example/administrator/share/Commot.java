package com.example.administrator.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.logo;
import static android.R.attr.path;
import static android.view.View.DRAWING_CACHE_QUALITY_AUTO;
import static android.view.View.DRAWING_CACHE_QUALITY_HIGH;
import static android.view.View.DRAWING_CACHE_QUALITY_LOW;

/**
 * Created by miao on 2017/11/24.
 */

public class Commot {

    public static Bitmap getImgFile(View view, Context context) {
        Bitmap bitmap = null;
        int width = view.getRight() - view.getLeft();
        int height = view.getBottom() - view.getTop();
        final boolean opaque = view.getDrawingCacheBackgroundColor() != 0 || view.isOpaque();
        Bitmap.Config quality;
        if (!opaque) {
            switch (view.getDrawingCacheQuality()) {
                case DRAWING_CACHE_QUALITY_AUTO:
                case DRAWING_CACHE_QUALITY_LOW:
                case DRAWING_CACHE_QUALITY_HIGH:
                default:
                    quality = Bitmap.Config.ARGB_8888;
                    break;
            }
        } else {
            quality = Bitmap.Config.RGB_565;
        }
        bitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics(),
                width, height, quality);
        bitmap.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        if (opaque) bitmap.setHasAlpha(false);
        boolean clear = view.getDrawingCacheBackgroundColor() != 0;
        Canvas canvas = new Canvas(bitmap);
        if (clear) {
            bitmap.eraseColor(view.getDrawingCacheBackgroundColor());
        }
        view.computeScroll();
        final int restoreCount = canvas.save();
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);
        canvas.restoreToCount(restoreCount);
        canvas.setBitmap(null);

        return bitmap;
//        return saveBitmapFile(bitmap);
    }

    public static Bitmap convertViewToBitmap(View view){

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap=view.getDrawingCache();

        return bitmap;
    }

    /**
     * Bitmap 保存成file
     *
     * @param bitmap
     */
    public static File saveBitmapFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), "/10p8/image/" + System.currentTimeMillis() + ".jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i("保存图片","3333333333");
            return file;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //保存图片到相册
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"/DCIM/camera/yidian/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 30, fos); //压缩图片
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("保存图片","222222222222222"+e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("保存图片","33333333332"+e.toString());
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("保存图片","222222222222222");
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    public  static void  saveImage(Bitmap bitmap){
        //把图片写入本地
        File file=new File(Environment.getExternalStorageDirectory(),"/DCIM/camera/yidian/");
        if (!file.exists()){
            file.mkdir();//创建这个文件夹
        }
        File file2 =new File(file,"agaafag.jpg");
        try {
            FileOutputStream stream=new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("异常",e.toString());
        }
    }
}
