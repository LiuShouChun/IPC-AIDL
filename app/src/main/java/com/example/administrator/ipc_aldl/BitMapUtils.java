package com.example.administrator.ipc_aldl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * resWidth、reaHeight 为期望的宽高 即View加载的宽高。
 */
public class BitMapUtils {
    public static Bitmap decodeSampledBitMapFormResource(Resources res,int resID,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory 只会解析图片的宽/高 信息，并不会真正的去加载图片，所以真个操作是轻量级的
        BitmapFactory.decodeResource(res,resID,options);
        options.inSampleSize = calulateInSampleSize(reqHeight,reqWidth,options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resID,options);
    }

    private static int calulateInSampleSize(int reqHeight, int reqWidth, BitmapFactory.Options options) {
        //测量出来的真实宽高
        int height = options.outHeight;
        int width = options.outWidth;
        //像素缩略比
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth ){
          final int halfHeight = height / 2;
          final int halfWidth = width / 2;
          //最接近的的缩略比
          while ((halfHeight/inSampleSize) >= reqHeight && (halfWidth/inSampleSize) >= reqWidth){
              inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
