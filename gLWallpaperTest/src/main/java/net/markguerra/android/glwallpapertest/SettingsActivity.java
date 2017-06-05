package net.markguerra.android.glwallpapertest;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.glwallpaperservice.testing.wallpapers.nehe.lesson08.GLImage;
import com.glwallpaperservice.testing.wallpapers.nehe.lesson08.NeheLesson08WallpaperService;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends Activity {

    private static final int REQUEST_IMAGE = 56;
    private static final int REQUEST_SET_WALLPAPER = 5;
    private static final String IMAGE_CACHE_PATH = "cache_bitmap_";
    private GridView selected_images_gv;
    private List<Bitmap> imagesBitmaps;
    private MyAdapter myAdapter;
    private int imgIndex = 0;
    private Bitmap defaultBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void initView() {
        GLImage.load(getResources());
        try {
            defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nehe_android);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imagesBitmaps = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Bitmap bitmap = getBitmapFromDisk(i);
            if (bitmap != null) {
                imagesBitmaps.add(bitmap);
                GLImage.setBitmap(i,bitmap);
            } else {
                imagesBitmaps.add(defaultBitmap);
            }
        }
        myAdapter = new MyAdapter(this, imagesBitmaps);
        selected_images_gv = (GridView) findViewById(R.id.selected_images_gv);
        selected_images_gv.setAdapter(myAdapter);
        selected_images_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgIndex = position;
                Crop.pickImage(SettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        } else if (requestCode == REQUEST_SET_WALLPAPER) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "设置失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).withMaxSize(1080,1080).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
//            resultView.setImageURI(Crop.getOutput(result));
            Bitmap bitmap = BitmapFactory.decodeFile(Crop.getOutput(result).getPath(),getOptions(512,512));
            myAdapter.upData(imgIndex, bitmap);
            GLImage.setBitmap(imgIndex, bitmap);
            compressBitmap2Disk(imgIndex, bitmap);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存Bitmap到SD卡
     *
     * @param imgIndex
     * @param bitmap
     */
    private void compressBitmap2Disk(int imgIndex, Bitmap bitmap) {
        File file = new File(getCacheDir(), IMAGE_CACHE_PATH + imgIndex);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Bitmap从SD卡
     *
     * @param index
     * @return
     */
    private Bitmap getBitmapFromDisk(int index) {
        File file = new File(getCacheDir(), IMAGE_CACHE_PATH + index);
        if (!file.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public BitmapFactory.Options getOptions(int width,int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为falseBitmap bitmap = null;
        options.inSampleSize = caculateInSampleSize(options,width,height);
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;/* 下面两个字段需要组合使用 */
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        return options;
    }
    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    public void setWallpaper(View view) {
        setLiveWallpaper(getApplicationContext(), this, REQUEST_SET_WALLPAPER);
    }

    public void clearWallpaper(View view){
        //清理adapter
        imagesBitmaps.clear();
        for (int i = 0; i < 6; i++) {
            imagesBitmaps.add(defaultBitmap);
            myAdapter.notifyDataSetChanged();
        }
        //清理内存
        GLImage.mBitmap1.recycle();
        GLImage.mBitmap1 = null;
        GLImage.mBitmap2.recycle();
        GLImage.mBitmap2 = null;
        GLImage.mBitmap3.recycle();
        GLImage.mBitmap3 = null;
        GLImage.mBitmap4.recycle();
        GLImage.mBitmap4 = null;
        GLImage.mBitmap5.recycle();
        GLImage.mBitmap5 = null;
        GLImage.mBitmap6.recycle();
        GLImage.mBitmap6 = null;
        GLImage.load(getResources());
        //清理SD卡缓存
        if(getCacheDir().exists()){
            for (int i = 0; i < 6; i++) {
                File file = new File(getCacheDir(), IMAGE_CACHE_PATH + i);
                if(file.exists()){
                    file.delete();
                }
            }
        }
    }

    public void setLiveWallpaper(Context context, Activity paramActivity, int requestCode) {
        try {
            Intent localIntent = new Intent();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {//ICE_CREAM_SANDWICH_MR1  15
                localIntent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);//android.service.wallpaper.CHANGE_LIVE_WALLPAPER
                //android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT
                localIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT
                        , new ComponentName(context.getApplicationContext().getPackageName()
                                , NeheLesson08WallpaperService.class.getCanonicalName()));
            } else {
                localIntent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);//android.service.wallpaper.LIVE_WALLPAPER_CHOOSER
            }
            paramActivity.startActivityForResult(localIntent, requestCode);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    class MyAdapter extends BaseAdapter {

        private List<Bitmap> mData;
        private Context context;

        public MyAdapter(Context context, List<Bitmap> data) {
            this.context = context;
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Bitmap getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.iamge_item, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_item);
            imageView.setImageBitmap(mData.get(position));
            return imageView;
        }

        public void upData(int index, Bitmap bitmap) {
            mData.set(index, bitmap);
            notifyDataSetChanged();
        }
    }
}
