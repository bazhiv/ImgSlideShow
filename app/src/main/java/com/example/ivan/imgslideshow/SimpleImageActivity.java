package com.example.ivan.imgslideshow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.ivan.imgslideshow.R;

import java.io.File;
import java.util.Random;

public class SimpleImageActivity extends Activity {

    private static final int RESULT_SETTINGS = 1;
    private static final String IMGS_DIR = "/imgs";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_image);

        mImageView = (ImageView) findViewById(R.id.imageView);

        Uri uri = getRandomImageUri();
        if (uri == null) {
            Log.e("ImgSlideShow", "Can't find image to view!");
        }

        mImageView.setImageURI(getRandomImageUri());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;

        }

        return true;


//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
    }



    private Uri getRandomImageUri() {

        String rootImgDirName = Environment.getExternalStorageDirectory().getAbsolutePath() + IMGS_DIR;
        File rootImgDir = new File(rootImgDirName);
        if (!rootImgDir.exists()) {
            Log.w("MyApplication", String.format("Root image directory %s doesn't exist!", rootImgDirName));
        } else {
            if (!rootImgDir.isDirectory()) {
                Log.w("MyApplication", String.format("Root image directory %s is not a directory!", rootImgDirName));
            } else {
                if (!rootImgDir.canRead()) {
                    Log.e("MyApplication", String.format("Root image directory %s can't be red!", rootImgDirName));
                } else {
                    String[] imgNames = rootImgDir.list();
                    if (imgNames.length == 0) {
                        Log.w("MyApplication", String.format("Root image directory %s is empty!", rootImgDirName));
                    } else {
                        Random random = new Random();
                        int selected = random.nextInt(imgNames.length);
                        File selectedImg = new File(rootImgDirName + "/" + imgNames[selected]);
                        return Uri.fromFile(selectedImg);
                    }
                }
            }
        }
        return null;
    }
}

