package com.glwallpaperservice.testing.wallpapers.nehe.lesson08;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.markguerra.android.glwallpapertest.R;

public class GLImage {
    public static Bitmap mBitmap1;
    public static Bitmap mBitmap2;
    public static Bitmap mBitmap3;
    public static Bitmap mBitmap4;
    public static Bitmap mBitmap5;
    public static Bitmap mBitmap6;



    public static void load(Resources resources) {
        if(mBitmap1 == null){
            mBitmap1 = BitmapFactory.decodeResource(resources, R.drawable.nehe_android);
        }
        if(mBitmap2 == null){
            mBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.nehe_android);
        }
        if(mBitmap3 == null){
            mBitmap3 = BitmapFactory.decodeResource(resources, R.drawable.nehe_android);
        }
        if(mBitmap4 == null){
            mBitmap4 = BitmapFactory.decodeResource(resources, R.drawable.nehe_android);
        }
        if(mBitmap5 == null){
            mBitmap5 = BitmapFactory.decodeResource(resources, R.drawable.nehe_android);
        }
        if(mBitmap6 == null){
            mBitmap6 = BitmapFactory.decodeResource(resources, R.drawable.nehe_android);
        }
    }

    public static void setBitmap(int imgIndex, Bitmap bitmap) {
        switch (imgIndex){
            case 0:
                mBitmap1 = bitmap;
                break;
            case 1:
                mBitmap2 = bitmap;
                break;
            case 2:
                mBitmap3 = bitmap;
                break;
            case 3:
                mBitmap4 = bitmap;
                break;
            case 4:
                mBitmap5 = bitmap;
                break;
            case 5:
                mBitmap6 = bitmap;
                break;
        }
    }
}